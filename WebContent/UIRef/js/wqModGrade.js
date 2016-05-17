$(document).ready(function() {
	
	$("#cdplanIdSelect").click(function(){
		$("#cdplanId").val($(this).val());
	});
	
	$("#cddesignTopIdSelect").click(function(){
		$("#cddesignTopId").val($(this).val());
	});
	$("#cddesignTopIdSelect").change(function(){
		$("#cddesignTopId").val($(this).val());
	});
	//联动查询题目编号 
	$("#cdplanIdSelect").change(function(){
		//alert("he");
		$("#cddesignTopIdSelect").empty();	
		getcddesignTopId();
		$("#cddesignTopId").val($("#cddesignTopIdSelect").val());
	});
	
function getcddesignTopId(){
		
		var cdplanId=$("#cdplanIdSelect").val();
		$.ajax({
			type : "get",
			url : "StuGrade_gradeCName.action?cdplanId="+cdplanId,
			dataType : "json",
			success : function(data) {
				var optionString = "";
				for ( var i=0;i<data.length;i++) {
					var json = data[i];	
					optionString += "<option value=\"" + data[i][0] + "\">"
							+ data[i][1]+ "</option>";
				}
				$("#cddesignTopIdSelect").append(optionString);	
				$("#cddesignTopId").val($("#cddesignTopIdSelect").val());
			}
		});
	}
	//模态框可移动 
	$(".modal").draggable({
		handle : ".modal-header",
		cursor : "all-scroll"
	});
	//为删除按钮绑定事件
		$("span button").click(function() {
			var id = $(this).parents("tr").find("td:eq(0)").text();//当前行的第一列
				if (confirm("您确定要删除这条记录吗？")) {
					//window.location = ""
						//	+ id;
				}
			});
		//为修改按钮绑定事件
		$("label button").click(function() {
			$("#wqshow").empty();
			//学生编号
			var stuId = $(this).parents("tr").find("td:eq(0)").text();//当前行的第一列
			//课题编号
			var cddesignTopId = $(this).parents("tr").find("td:eq(3)").text();//当前行的第一列
			$.ajax({//得到该学生的所有阶段的考核成绩
				type : "get",
				url : "StuGrade_getListSco.action?stuId="+stuId+"&cddesignTopId="+cddesignTopId,
				dataType : "json",
				success : function(data) {
					var divShow = "";
					for (var i=0;i<data.length;i++) {
						divShow += "<input type='hidden' value='"+data[i][0]+"' " +
								"name='processdocuments["+i+"].processDocId'/>" +//隐藏的材料编号
								"<div class='form-group'><label for='firstname'";
						divShow += "class='col-sm-3 control-label'>"+data[i][1]+"</label>" +//考核名称
								"<div class='col-sm-9'><input type='text' " +
								"class='form-control' name='processdocuments["+i+"].score'" +
								"placeholder='"+data[i][1]+"修改分数' value='"+data[i][2]+"' /></div></div>";
					}
					$("#wqshow").empty().append(divShow);	
				}
			});
			$("#stuName").text($(this).parents("tr").find("td:eq(1)").text());
				//弹出修改界面
				$("#Udialog").modal();

			});

	})
