$(function() {
	// 设置每一个模态框都可以拖拽
	$(".modal").draggable({
		handle : ".modal-header",
		cursor : "all-scroll"
	});
	
	// 将时间戳转化为标准时间
	function convert(Time) {
		var timeString = "";
		timeString = Time.replace("/Date(", "").replace(")/", "").trim();
		var quit = timeString.substring(7, 10);
		timeString = timeString.replace(quit, "");
		return new Date(parseInt(timeString)* 1000).toLocaleString();
	}

	// 查看阶段性的任务
	$("button.seeTask")
			.click(
					function() {
						var teacherGroupId = $(this).prev().val();
						var teacherId = $(this).prev().prev().val();
						$(this).attr("disabled", true);
						var $str = $("<tr class='theHiddenTR' style='display: none;'><td colspan='5'><table class='table table-striped table-bordered'><thead style='background: lightcyan;'><tr class='text-primary'>"
								+ "<th>编号</th><th>任务描述</th><th>开始时间</th><th>结束时间</th><th>操作</th></tr></thead><tbody class='taskListCont'></tbody></table>"
								+ "<div class='text-center'><button class='btn btn-warning returnTop'><span class='glyphicon glyphicon-triangle-top'></span>"
								+ "隐藏收回</button></div></td>" + "</tr>");
						$str.insertAfter($(this).parents("tr"));
						// 定义一个str添加内容
						var str = "";
						// 动态加载内容部分
						$
								.ajax({
									url : "cddesigntask_findAllProcessShe.action",
									data : {
										teacherId : teacherId,
										cdTeacherGroupId : teacherGroupId
									},
									dataType : "json",
									type : "post",
									async : false,
									success : function(data) {
										for (var i = 0; i < data.length; i++) {
											str += "<tr><td>"
													+ (i + 1)
													+ "</td><td>"
													+ data[i][0]
													+ "</td><td>"
													+ convert(data[i][2].toString())
													+ "</td><td>"
													+ convert(data[i][3].toString())
													+ "</td><td><input type='hidden' value='"+data[i][4]+"'><button "
													+ "class='btn btn-info uploadMyFile'><span class='glyphicon glyphicon-circle-arrow-up'></span>上传我的资料</button></td></tr>";
										}
									},
									error : function(error) {
										alert(error.responseText);
									}
								});
						// 把内容填充到具体的位置
						$(this).parents("tr").next().find("tbody.taskListCont")
								.append($(str));
						$str.fadeIn(500);
						// 返回顶部
						$("button.returnTop").click(
								function() {
									var $elem = $(this).parents(
											"tr.theHiddenTR");
									$elem.hide();
									$(this).parents("tr.theHiddenTR").prev()
											.find("button.seeTask").removeAttr(
													"disabled");
									setTimeout(function() {
										$elem.remove();
									}, 500);
								});
						//上传文件
						$("button.uploadMyFile").click(function(){
							$("#file").val($(this).prev().val());
							$("#UploadFile").modal();
						});
					});
});

 
