$(function() {
	//设置每一个模态框都可以拖拽
	$("div.modal").draggable({
		handle: ".modal-header",
		cursor: "all-scroll"
	});
		
	//添加组员
	$("#addUserInfo").click(function() {
		var str = "<tr><td colspan='6' class='form-inline'><span>学号:</span><input type='text' id='studentId' class='form-control' placeholder='请输入组员的学号' style='width:140px;'/>&nbsp;姓名：<input type='text' class='form-control' placeholder='请输入组员的名字' style='width:140px;'/>&nbsp;&nbsp;<button class='btn btn-info' type='button' id='addStuMembers'><span class=' glyphicon glyphicon-ok-sign'></span>添加</button>&nbsp;&nbsp;<button class='btn btn-success' type='button' id='cancelAddMem'><span class='glyphicon glyphicon-repeat'></span>取消</button></td><tr>";
		$("#groupMembers").append(str);
		$(this).attr("disabled", true);
		//取消添加组员
		$("#cancelAddMem").click(function() {
			$(this).parents("tr").remove();
			$("#addUserInfo").attr("disabled", false);			
		});		
		
		//添加成员
		$("#addStuMembers").click(function(){			 	
			var str = "";
			var stuGroupMembId = $("#addStuGroupMembers").val();	 
			$.ajax({
				url : "student_addStuMembers.action",
				data : {					
					stuGroupId: stuGroupMembId,
					studentId :$("#studentId").val()
				},
				async:false,
				type : "post",
				dataType : "json",
				success : function(data) {					 
					var $container = $("#groupMembers");
					for (var i = 0; i < data.length; i++) {
						str += "<tr><td>" + (i + 1) + "</td><td>"
								+ data[i][0] + "</td><td>" + data[i][1]
								+ "</td><td>" + data[i][2]
								+ "</td><td>" + data[i][3]
								+ "</td><td>" + data[i][4]
								+ "</td></tr>";
					}
					$container.empty().append(str);
					$("#addUserInfo").removeAttr("disabled");
				},
				error : function(error) {
					alert("添加组员失败!");
				}
			});
		});
	});
	
	//收起
	$("button.closeGroupInfo").click(function() {
		var $content = $(this).parents("div.panel-heading").next();
		if ($content.is(":visible")) {
			$(this).empty().append("<span class='glyphicon glyphicon-chevron-down'></span>");
			$content.slideUp(300);
		} else {
			$(this).empty().append("<span class='glyphicon glyphicon-chevron-up'></span>");
			$content.slideDown(300);
		}
	});
	//全部隐藏
	$("#allShow").click(function() {
		$("div.groupDetail").slideUp(300);
		$("button.closeGroupInfo").empty().append("<span class='glyphicon glyphicon-chevron-down'></span>");
	});
	//全部显示
	$("#allHide").click(function() {
		$("div.groupDetail").slideDown(300);
		$("button.closeGroupInfo").empty().append("<span class='glyphicon glyphicon-chevron-up'></span>");
	});
});