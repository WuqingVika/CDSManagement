$(document).ready(function() {
	$(".modal").draggable({
		handle : ".modal-header",
		cursor : "all-scroll"
	});

	$("#cdplanIdSelect").click(function(){
		$("#cdplanId").val($(this).val());
	});
	//new
	$("#cddesignTopIdSelect").click(function(){
		$("#cddesignTopId").val($(this).val());
	});
	$("#processAssShIdSelect").click(function(){
		$("#processAssShId").val($(this).val());
	});
	//new end!!
  //getcddesignTopId();
//联动查询题目编号 
	$("#cdplanIdSelect").change(function(){
		//alert("he");
		$("#cddesignTopIdSelect").empty();	
		getcddesignTopId();
	});
//联动查询阶段名称
	$("#cddesignTopIdSelect").click(function(){
		$("#processAssShIdSelect").empty();	
		getprocessAssShId();
	});
	//查询题目编号 
	function getcddesignTopId(){
		
		var cdplanId=$("#cdplanIdSelect").val();
		$.ajax({
			type : "get",
			url : "ViewPF_getTopic.action?cdplanId="+cdplanId,
			dataType : "json",
			success : function(data) {
				var optionString = "";
				for ( var i=0;i<data.length;i++) {
					var json = data[i];	
					optionString += "<option value=\"" + data[i][0] + "\">"
							+ data[i][1]+ "</option>";
				}
				$("#cddesignTopIdSelect").append(optionString);			
			}
		});
	}
	//查询考核阶段编号 
	function getprocessAssShId(){
		var cdplanId=$("#cdplanIdSelect").val();
		var cddesignTopId=$("#cddesignTopIdSelect").val();
		$.ajax({
			type : "get",
			url : "ViewPF_getProcessName.action?cdplanId="+cdplanId+"&cddesignTopId="+cddesignTopId,
			dataType : "json",
			success : function(data) {
				var optionString = "";
				for ( var i=0;i<data.length;i++) {
					var json = data[i];	
					optionString += "<option value=\"" + data[i][1] + "\">"
							+ data[i][2]+ "</option>";
				}
				$("#processAssShIdSelect").append(optionString);	
				$("#processAssShId").val($("#processAssShIdSelect").val());
			}
		});
	}
		//为查阅学生考核材料按钮绑定事件
		$("span button").click(function() {
			
			$("#pigaiTbody").empty();
			//当前行的第一列,学生小组编号 
			var stugroupid = $(this).parents("tr").find("td:eq(0)").text();
			//考核阶段编号
			var processAssShId=$(this).parents("tr").find("td:eq(1)").text();
			//该学生小组的课程设计题目名称
			var topics=$(this).parents("tr").find("td:eq(2)").text();
			//过程考核阶段名称
			var ProcessName=$(this).parents("tr").find("td:eq(3)").text();//课程设计题目名称
			$("#topics").text(topics);
			$("#ProcessName").text(ProcessName);
			//弹出该小组完成的考核材料信息进行查阅下载
			$.ajax( {
				type : "get",
				url : "ViewPF_viewProDoc.action?stugroupid="+stugroupid
				+"&processAssShId="+processAssShId,
				dataType : "json",
				success : function(data) {
					var tr;	
					 for(var i=0;i< data.length;i++){
							    tr += "<tr>";
								tr += "<td class='hidden'>"+data[i][0]+"</td>";//隐藏的材料编号
								tr += "<td>"+data[i][1]+"</td>";//学生姓名
								tr += "<td name='role' class='text-center'>" + getBrief(data[i][2].toString())+"</td>";//角色
								tr += "<td class=text-center>"+data[i][3]+"</td>";//考核材料名称
								tr += "<td class='hidden'>" + data[i][4] + "</td>";//附件信息
								tr += "<td class=text-center>"+convert(data[i][5].toString())+"</td>";//上传时间
								tr += "<td class=text-center>"+data[i][6]+"</td>";//是否查阅
								tr +="<td><button type='button' class='btn btn-info center' " +
										"name='downloadProcessFile'>" +
										"<span class='glyphicon glyphicon-download-alt'></span>下载</button></td>";
								tr += "</tr>";
					 }
					 
					$("#pigaiTbody").append(tr);
					$("button[name=downloadProcessFile]").click(function(){
						var processDocId=$(this).parents("tr").find("td:eq(0)").text();
						
						if(confirm("您确定要下载吗?")){
							window.location.href="ViewPFGetDownload.action?processDocId="+processDocId;
						}
					});
				
				}
			});
			
			//将时间戳转化为标准时间
			function convert(Time) {
				var timeString = "";
				timeString = Time.replace("/Date(", "").replace(")/", "").trim();
				var quit = timeString.substring(7, 10);
				timeString = timeString.replace(quit, "");
				return new Date(parseInt(timeString)* 1000).toLocaleString();
			}

			//弹出模态框；
			$("#CYdialog").modal();
			});
		//添加角色信息；
		function getBrief(role){
			if(role=='组长'){
				return "组长<span class='glyphicon glyphicon-star'></span>";
			}
				
			else return "组员<span class='glyphicon glyphicon-star-empty'></span>";
		}
		//为老师打分按钮绑定事件
		$("label button").click(function() {
			
			var id = $(this).parents("tr").find("td:eq(0)").text();//当前行的第一列
			$("#DaFenTbody").empty();
			//当前行的第一列,学生小组编号 
			var stugroupid = $(this).parents("tr").find("td:eq(0)").text();
			//考核阶段编号
			var processAssShId=$(this).parents("tr").find("td:eq(1)").text();
			//该学生小组的课程设计题目名称
			var topics=$(this).parents("tr").find("td:eq(2)").text();
			//过程考核阶段名称
			var ProcessName=$(this).parents("tr").find("td:eq(3)").text();//课程设计题目名称
			$("#topics2").text(topics);
			$("#ProcessName2").text(ProcessName);
			//弹出该小组学生的成绩 进行分数操作
			$.ajax( {
				type : "get",
				url : "ViewPF_showScore.action?stugroupid="+stugroupid
				+"&processAssShId="+processAssShId,
				dataType : "json",
				success : function(data) {
					var tr;	
					 for(var i=0;i< data.length;i++){
							    tr += "<tr>";
								tr += "<td class='hidden'><input type='hidden' size='8' value='"+data[i][0]+"' name='processdocuments["+i+"].processDocId'/></td>";//隐藏的材料编号
								tr += "<td>"+data[i][1]+"</td>";//学生姓名
								tr += "<td name='role' class='text-center'>" + getBrief(data[i][2].toString())+"</td>";//角色
								tr += "<td class=text-center><input type='text' size='6' name='processdocuments["+i+"].score' value='"+data[i][4]+"' readonly/></td>";//成绩
								tr += "<td class=text-center><input type='text' name='processdocuments["+i+"].tutorOpinion' value='" + data[i][3] + "' readonly/></td>";//评语
								tr +="<td><button type='button' class='btn btn-info center' " +
										"name='updateScore'>" +
										"<span class='glyphicon glyphicon-pencil'></span>修改</button></td>";
								tr += "</tr>";
					 }
					 
					$("#DaFenTbody").append(tr);
					$("button[name=updateScore]").click(function(){
						$(this).val('正在修改');
						//var processDocId=$(this).parents("tr").find("td:eq(0)").text();
							$(this).parents("tr").find("td:eq(3) input").removeAttr("readonly");
							$(this).parents("tr").find("td:eq(4) input").removeAttr("readonly");
					});
				
				}
			});//ajax获取数据
			$("#DFdialog").modal();
				

			});
		//模态框可移动
		$(".modal").draggable({   
			    handle: ".modal-header",   
			    cursor: 'move',   
			    refreshPositions: false  
			});   
		//为表单绑定验证事件
		$('#UForm').bootstrapValidator({
			message: 'This value is not valid',
        	feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
            	},
            fields: {
            	questionContent: {
                validators: {
                    notEmpty: {
                        message: '试题内容不能为空'
                    }
                }
            },
            
            },
		});

	})
//填充科目

//更改pageSize
function querySize() {
	window.location = "questionServlet?currentPage=" + $("#currentPage").val()
			+ "&pageSize=" + $("#amount").val() + "&subjectId="
			+ $("#subjects").val() + "&quesType=" + $("#quesType").val()
			+ "&quesContent=" + $("#quesContent").val();

}

function ASub() {
	$("#AForm").submit();
}
