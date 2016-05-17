$(function() {
	$(".modal").draggable({
		handle : ".modal-header",
		cursor : "all-scroll"
	});
		var chooseText=$("#ifPass").val();
		if(chooseText=='0'){
			$("#nopassdiv").hide();
		}
		else{
		$("#nopassdiv").show();	
		}
		$("#ifPass").change(function(){
		var chooseText=$("#ifPass").val();
		if(chooseText=='0'){
			$("#nopassdiv").hide();
		}
		else{
			$("#nopassdiv").show();
		}
		
		
		})
	
	$("#addNoPass").click(function() {
		$("#PassState").val("");
		
		$("#isAdd").val(1);//判断该老师是否添加评论
		var str = "<tr><td colspan='3'><form action='#' class='form form-inline'><span>"+
		"</span><input type='text' class='form-control' name='nopassName' placeholder='请输入课题不通过原因' style='width:350px;'/>&nbsp;"+		
		"&nbsp;<button  type='button' class='btn btn-success' id='cancelAddnopass'><span class='glyphicon glyphicon-repeat'>"+
		"</span>取消</button>&nbsp;&nbsp;<button type='button' name='addComment' class='btn btn-info'><span class=' glyphicon glyphicon-ok-sign'></span>添加</button></form></td><tr>";
		
		$("#groupNoPass").append(str);
		$("#addNoPass").attr("disabled", true);
		$("#cancelAddnopass").click(function() {
			$("#isAdd").val(0);
			$(this).parents("tr").remove();
			$("#addNoPass").attr("disabled", false);
		});//点击确认，将它显示在方本区域
		$("button[name='addComment']").click(function(){
			$("#isAdd").val(1);
			$("#PassState").val($("input[name='nopassName']").val());
			$(this).parents("tr").remove();
			$("#addNoPass").attr("disabled", false);
		});
	});
	
	$("button[name='shenH']").click(function() {
		
		var topic= $(this).parents("tr").find("td:eq(2)").text();
		var stuGroupId= $(this).parents("tr").find("td:eq(1)").text();
		$("#groupMembers").empty();	
		$("#stugroupId").val(parseInt(stuGroupId.toString()));
		$("#topicsDescribtion").text(topic);
		$.ajax( {
			type : "get",
			url : "teacher_showGroupMembers.action?stuGroupId="+stuGroupId,
			dataType : "json",
			success : function(GroupMembers) {
				var tr;	
				//alert(GroupMembers[0][2]+GroupMembers.length+GroupMembers[1][2]+GroupMembers[0].length);
				 for(var i=0;i< GroupMembers.length;i++){
						    tr += "<tr>";
							tr += "<td>"+GroupMembers[i][0]+"</td>";//序号
							tr += "<td>"+GroupMembers[i][1]+"</td>";//学号
							tr += "<td class=text-center >" + GroupMembers[i][2]+"</td>";//姓名
							tr += "<td class=text-center>"+GroupMembers[i][3]+"</td>";//班级
							tr += "<td class=text-center >" + GroupMembers[i][4] + "</td>";//学院
							tr += "<td class=text-center>"+GroupMembers[i][5]+"</td>";//专业
							tr += "</tr>";
				 }
				$("#groupMembers").append(tr);			
			}
		});
		loadNopass();
		$("#ShenHe").modal();
		
    });//审核按钮事件结束
	function loadNopass(){
		$("#groupNoPass").empty();
		$.ajax({
			type : "get",
			url : "teacher_getNopass.action",
			dataType : "json",
			success : function(comments) {
				var str="";
				for ( var i=0;i<comments.length;i++) {
					str+="<tr><td class='hidden'>"+comments[i][0]+"</td><td>" +comments[i][2]+
					"</td><td><button name='checkNopass' type='button' class='btn btn-info'>" +
					"<span class='glyphicon glyphicon-check'></span>选择</button>" +
					" </td></tr>";
				}
				$("#groupNoPass").append(str);
				$("button[name='checkNopass']").click(function(){
					$("#PassState").val($(this).parents("tr").find("td:eq(1)").text());
					$("#isAdd").val(0);
				});
			}
		});
		
	}
});
