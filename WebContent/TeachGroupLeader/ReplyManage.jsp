<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/UIRef/bootstrap/datetimepicker/css/bootstrap-datetimepicker.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/UIRef/bootstrap/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/UIRef/bootstrap/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>答辩计划管理</title>
<script>
	$(function() {
		// 开始先加载所有的未曾分配的课程设计计划
		loadNeedReplyPageData(-1);
		// 加载所有的课程设计计划		
		loadAllCdPlan();
		// 加载所有的答辩小组
		loadReplyGroup();
		// 查看某学院下的所有的教师
		searchAllTeachers();
		// 定义一个全局变量
		var searchType = 0;//0：代表查询未曾分配的，1代表查询已经分配的
		// 定义一个全局变量，代表课程设计计划编号
		var cdplanId = -1;//-1:代表查询所有的不添加筛选条件，否则是按照课程设计计划查询所需的数据
		// 定义一个全局变量，用户标记是选择教师小组还是新增的小组
		var replyGroupType = 0;// 0:代表选择，1：代表新增
		// 定义一个教师数组
		var teacherStr = new Array();		 

		// 判断是否选择
		$("input[type=checkbox]#checkAll").click(function() {
			$(".checkItem").prop("checked", $(this).is(":checked"));
			if($(this).is(":checked")){
				// 获取到所有的选项
				var $items = $("input.checkItem");
				// 轮询所有的选项
				$items.each(function(){
					teacherStr.push($(this).parents("td").next().text());
				});				
			}else{
				// 清空数组
				teacherStr.splice(0,teacherStr.length);				
			}
			// 把所有的教师放到隐藏域中
			$("#teacherStr").val(teacherStr.join(","));
		});
		//添加答辩计划
		$("#viewApplied").click(function() {
			$("#addAppliedItem").show();
			$("#delAppliedItem").hide();
			$("button.btnAdd").show();
			$("button.btnDel").hide();
			$("button.btnSearch").hide();
		});
		//查看已经分配的答辩计划
		$("#viewNotApplied").click(function() {
			$("#addAppliedItem").hide();
			$("#delAppliedItem").show();
			$("button.btnAdd").hide();
			$("button.btnDel").show();
			$("button.btnSearch").show();
		});

		// 时间
		$('.form_datetime').datetimepicker({
			language : 'zh-CN',
			format : "yyyy-mm-dd hh:mm:ss", // 选择日期后，文本框显示的日期格式
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			forceParse : 0,
			showMeridian : 1
		});

		// 加载所有的课程设计计划
		function loadAllCdPlan() {
			$.ajax({
				url : "cdTechGroupLeC_getAllCdPlan.action",
				type : "post",
				async : false,
				success : function(data) {
					var obj = JSON.parse(data);
					var $container = $("#cdPlan");
					var str = "";
					for (var i = 0; i < obj.length; i++) {
						str += "<option value='"+obj[i][0]+"'>" + obj[i][1]
								+ "</option>";
					}
					$container.append(str);
				},
				error : function(error) {
					alert(error.responseText);
				}
			});
		}

		//加载所有的数据（所有的未曾分配的课程设计计划）
		function loadNeedReplyPageData(cdplanId) {
			$
					.ajax({
						url : "cdTechGroupLeC_findAllNeedSupCD.action",
						data : {
							cdplanId : cdplanId
						},
						type : "post",
						async : false,
						success : function(data) {
							var $contianer = $("#tContent");
							var obj = JSON.parse(data);
							var str = "";
							for (var i = 0; i < obj.length; i++) {
								str += "<tr><td class='text-center'>"
										+ (i + 1)
										+ "</td><td class='text-center'>"
										+ obj[i][0]
										+ "</td><td class='text-center'>"
										+ obj[i][1]
										+ "</td><td class='text-center'>"
										+ obj[i][2]
										+ "</td><td class='text-center'>"
										+ obj[i][3]
										+ "</td><td class='text-center'>"
										+ obj[i][4]
										+ "</td><td class='text-center'>"
										+ obj[i][5]
										+ "</td><td class='text-center'><input type='hidden' value='"+obj[i][6]+"'/><button class='btn btn-success btnAdd' data-toggle='modal'"
						+"data-target='#myModal'><span class='glyphicon glyphicon-ok-sign'>"
										+ "</span>添加答辩计划</button></td></tr>";
							}
							$contianer.empty().append(str);
							// 添加答辩计划
							$("button.btnAdd").click(function(){
								$("#stuGroupId").val($(this).prev().val());
							});
						},
						error : function(error) {
							alert(error.responseText);
						}
					});
		}

		//加载所有的数据（所有的已经分配的课程设计计划）
		function loadReplyedPageData(cdplanId) {
			$.ajax({
						url : "cdTechGroupLeC_findAllSupedCD.action",
						data : {
							cdplanId : cdplanId
						},
						type : "post",
						async : false,
						success : function(data) {
							var $contianer = $("#tContent");
							var obj = JSON.parse(data);
							var str = "";
							for (var i = 0; i < obj.length; i++) {
								str += "<tr><td class='text-center'>"
										+ (i + 1)
										+ "</td><td class='text-center'>"
										+ obj[i][0]
										+ "</td><td class='text-center'>"
										+ obj[i][1]
										+ "</td><td class='text-center'>"
										+ obj[i][2]
										+ "</td><td class='text-center'>"
										+ obj[i][3]
										+ "</td><td class='text-center'>"
										+ obj[i][4]
										+ "</td><td class='text-center'>"
										+ obj[i][5]
										+ "</td><td><input type='hidden' value='"
										+obj[i][7]+"' /><input type='hidden' value='"+obj[i][8]+"' /><button "
						+"class='btn btn-info btnFindReplyPlan' data-toggle='modal' data-target='#ViewResults'>"
										+ "<span class='glyphicon glyphicon-search'></span>查看答辩计划</button>&nbsp;<button class='btn btn-danger btnDeleReply'><span class='glyphicon glyphicon-remove'></span>删除答辩计划</button></td></tr>";
							}
							$contianer.empty().append(str);
							// 删除答辩计划
							$("button.btnDeleReply").click(function(){
								var replyPlanId = $(this).prev().prev().prev().val();
								if(confirm("你确定要删除吗?")){
									window.location.href= "cdTechGroupLeC_delteReplyPlan.action?theReplyPlanId="+replyPlanId;
								}else{
									alert("记录未删除!");
								}
							});
							// 根据编号查询所有的答辩计划信息
							$("button.btnFindReplyPlan").click(function(){
							    var replyPlanId = $(this).prev().prev().val();
								var replyGroupId = $(this).prev().val();								
								// 查看所有的答辩计划信息
								$.ajax({
									url : "cdTechGroupLeC_searchReplyPlan.action",
									data : {
										"replyplan.replyPlanId":replyPlanId
									},
									type : "post",
									async : false,
									success : function(data) {									 
										var obj = JSON.parse(data);										 
										$("#replyLocation").text(obj.replyLocation);	
										$("#replyName").text(obj.replyName);
										$("#replyDescribtion").text(obj.replyDescribtion);
										$("#replyStart").text(convert(obj.startTime.toString()));
										$("#replyEnd").text(convert(obj.endTime.toString()));
									},
									error:function(error){
										alert(error.responseText);
									}
								});													 
							// 加载答辩小组下的所有的成员
							$.ajax({
								url : "cdTechGroupLeC_findMemByReplyGroupId.action",
								data : {
									"theReplyGroupId" : replyGroupId
								},
								type : "post",
								async : false,
								success : function(data) {									 
									var $container = $("#replyTeacherMembers");
									var str = "";
									var obj = JSON.parse(data);
									for (var i = 0; i < obj.length; i++) {
										str += "<tr><td>" + (i + 1) + "</td><td>"
												+ obj[i][1] + "</td><td>" + obj[i][2]
												+ "</td></tr>";
									}									 
									$container.empty().append(str);
								},
								error : function(error) {
									alert(error.responseText);
								}
							});
							});
						},
						error : function(error) {
							alert(error.responseText);
						}
					});
		}

		// 按照课程设计计划类别查询
		$("#searchInfo").click(function() {
			// 获取到当前的选择的课程设计计划的值
			cdplanId = $("#cdPlan").val();
			//判断查询的方式（已经分配的还是未曾分配的）
			//0:未曾分配的
			if (searchType == 0) {
				loadNeedReplyPageData(cdplanId);
			} else {// 否则已经分配的
				loadReplyedPageData(cdplanId);
			}
		});

		// 按照是否分配来查询
		// 1.未曾分配
		$("#NReply").click(function() {
			searchType = 0;// 赋值
			loadNeedReplyPageData(cdplanId);
		});
		// 2.已经分配
		$("#YReply").click(function() {
			searchType = 1;// 赋值
			loadReplyedPageData(cdplanId);
		});

		// 当前小组
		$("#btnEx").click(function() {
			$(this).addClass("btn-warning");
			$("#btnAd").removeClass("btn-warning");
			$("#existingGroup").show();
			$("#addGroup").hide();
			replyGroupType = 0;
			$("#subTypeId").val(0);
		});

		// 添加小组
		$("#btnAd").click(function() {
			$(this).addClass("btn-warning");
			$("#btnEx").removeClass("btn-warning");
			$("#existingGroup").hide();
			$("#addGroup").show();
			replyGroupType = 1;
			$("#subTypeId").val(1);
		});

		// 加载所有的答辩小组
		function loadReplyGroup() {
			$.ajax({
				url : "cdTechGroupLeC_findAllReplyGroups.action",
				type : "post",
				async : false,
				success : function(data) {					 
					var obj = JSON.parse(data);					
					var len = obj.length;
					var str = "";
					var $container = $("#chTeachGroup");
					for (var i = 0; i <len; i++) {					 
						str += "<option value='"+obj[i][0]+"'>"
								+ obj[i][1] + "</option>";
					}
					
					$container.empty().append(str);
				},
				error : function(error) {
					alert(error.responseText);
				}
			});
		}

		// 查看学院下的所有的教师
		function searchAllTeachers() {
			$.ajax({
						url : "cdTechGroupLeC_findAllTeachers.action",
						type : "post",
						async : false,
						success : function(data) {
							var $container = $("#teachers");
							var str = "";
							var obj = JSON.parse(data);
							for (var i = 0; i < obj.length; i++) {
								str += "<tr><td class='text-center'><input type='checkbox'  class='checkItem'/></td>"
										+ "<td>"
										+ obj[i][0]
										+ "</td><td>"
										+ obj[i][1]
										+ "</td><td>"
										+ obj[i][2]
										+ "</td></tr>";
							}
							$container.empty().append(str);
							// 绑定勾选事件
							$("input.checkItem").click(function(){
								// 轮询查询所有的勾选过的
							    var $checkeds =	$("input.checkItem:checked");
								// 清空数组
								teacherStr.splice(0,teacherStr.length);
								// 把勾选过的放到数组中
								$checkeds.each(function(){
									teacherStr.push($(this).parents("td").next().text());
								});
								// 把所有的教师放到隐藏域中
								$("#teacherStr").val(teacherStr.join(","));
							});
						},
						error : function(error) {
							alert(error.responseText);
						}
					});
		}

		// 查看小组的成员
		$("#chTeachGroup").change(
				function() {
					var replyGroupId = $(this).val();
					$.ajax({
						url : "cdTechGroupLeC_findMemByReplyGroupId.action",
						data : {
							theReplyGroupId : replyGroupId
						},
						type : "post",
						async : false,
						success : function(data) {
							var $container = $("#replyTeachers");
							var str = "";
							var obj = JSON.parse(data);
							for (var i = 0; i < obj.length; i++) {
								str += "<tr><td>" + (i + 1) + "</td><td>"
										+ obj[i][1] + "</td><td>" + obj[i][2]
										+ "</td></tr>";
							}
							$container.empty().append(str);
						},
						error : function(error) {
							alert(error.responseText);
						}
					});
				});
		 
		 

	});
	// 将时间戳转化为标准时间
	function convert(Time) {
		var timeString = "";
		timeString = Time.replace("/Date(", "").replace(")/", "").trim();
		var quit = timeString.substring(7, 10);
		timeString = timeString.replace(quit, "");
		return new Date(parseInt(timeString)* 1000).toLocaleString();
	}
</script>
</head>
<body>
	<!-- banner-start -->
	<jsp:include page="../UIRef/JspRef/StudentHeader.jsp"></jsp:include>
	<!-- banner-end -->
	<!-- content-start -->
	<div class="container-fluid">
		<!-- left-nav-start -->
		<jsp:include page="../UIRef/JspRef/TeacherleftInstructor.jsp"></jsp:include>
		<!-- left-nav-end -->
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<ol class="breadcrumb" style="width: 100%;">
				<li><a href="#">课程设计平台管理系统</a></li>
				<li><a href="#">组长管理模块</a></li>
				<li class="active">答辩任务管理</li>
			</ol>
			<div class="well well-sm" style="background: none; border: none;">
				<div class="clearfix">
					<span class="pull-left">
						<div class="form-inline">
							课程设计计划 <select class="form-control" id="cdPlan">
								<option value="-1">--请选择课程设计计划--</option>
							</select>
							<button type="button" class="btn btn-success" id="searchInfo">查询</button>
						</div>
					</span> <span class="pull-right">
						<div class="btn-group">
							<button type="button" class="btn btn-warning dropdown-toggle"
								data-toggle="dropdown">
								请选择查询的方式 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a id="NReply" style="cursor: pointer;"
									id="viewApplied">查看未分配的小组</a></li>
								<li><a id="YReply" style="cursor: pointer;"
									id="viewNotApplied">查看已分配的小组</a></li>
							</ul>
						</div>
					</span>
				</div>
			</div>
			<div id="addReply">
				<!--添加课程设计答辩计划-->
				<table class="table table-hover table-bordered table-striped">
					<thead>
						<tr class="text-primary">
							<th class="text-center">编号</th>
							<th class="text-center">课程设计计划名称</th>
							<th class="text-center">小组课题名称</th>
							<th class="text-center">指导老师</th>
							<th class="text-center">小组组长</th>
							<th class="text-center">专业名称</th>
							<th class="text-center">学期</th>
							<th class="text-center" style="width:270px;">答辩计划操作</th>						 
						</tr>
					</thead>
					<tbody id="tContent">
						<!-- 这里是动态加载的内容 -->
					</tbody>
				</table>
			</div>

			<!--模态框部分-->
			<div class="modal fade bs-example-modal-lg" id="myModal"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<form action="cdTechGroupLeC_addReplyPlan.action" method="post">
						<!-- 隐藏域 -->
						<input type="hidden" name="subType" value="0" id="subTypeId"/>
						<input type="hidden" name="stuGroupId" value="" id="stuGroupId"/>
						<input type="hidden" name="teachersString" value="" id="teacherStr" />
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">添加课题答辩计划</h4>
							</div>
							<div class="modal-body">
								<div class="panel panel-warning">
									<div class="panel panel-heading">填写答辩情况</div>
									<div class="panel panel-body">
										<div class="form-horizontal" role="form">
											<div class="form-group">
												<label for="firstname" class="col-sm-3 control-label">答辩名称</label>
												<div class="col-sm-9">
													<input type="text" class="form-control" id="replyplan.replyName" name="replyplan.replyName"
														placeholder="输入答辩名称">
												</div>
											</div>
											<div class="form-group">
												<label for="firstname" class="col-sm-3 control-label">答辩地点</label>
												<div class="col-sm-9">
													<input type="text" class="form-control" id="replyplan.replyLocation" name="replyplan.replyLocation"
														placeholder="输入答辩地点">
												</div>
											</div>
											<div class="form-group">
												<label for="lastname" class="col-sm-3 control-label">开始时间</label>
												<div class="input-group date form_datetime col-sm-9"
													data-date="2016-01-01T05:25:07Z"
													data-date-format="yyyy-MM-dd HH:mm:ss"
													style="margin-left: 230px; width: 280px;"
													data-link-field="dtp_input1">
													<input class="form-control" name="replyplan.startTime" size="16"
														type="text" value="" placeholder="请选择开始的时间" readonly>
													<span class="input-group-addon"><span
														class="glyphicon glyphicon-remove"></span></span> <span
														class="input-group-addon"><span
														class="glyphicon glyphicon-th"></span></span>
												</div>
												<input type="hidden" id="dtp_input1" value="" />
											</div>
											<div class="form-group">
												<label for="lastname" class="col-sm-3 control-label">结束时间</label>
												<div class="input-group date form_datetime col-sm-9"
													data-date="2016-01-01T05:25:07Z"
													data-date-format="yyyy-MM-dd HH:mm:ss"
													style="margin-left: 230px; width: 280px;"
													data-link-field="dtp_input1">
													<input class="form-control" name="replyplan.endTime" size="16"
														type="text" value="" placeholder="请选择结束的时间" readonly>
													<span class="input-group-addon"><span
														class="glyphicon glyphicon-remove"></span></span> <span
														class="input-group-addon"><span
														class="glyphicon glyphicon-th"></span></span>
												</div>
												<input type="hidden" id="dtp_input1" value="" />
											</div>
											<div class="form-group">
												<label for="firstname" class="col-sm-3 control-label">答辩描述</label>
												<div class="col-sm-9">
													<textarea class="form-control" id="replyplan.replyDescribtion" name="replyplan.replyDescribtion"
														placeholder="输入答辩描述"></textarea>
												</div>
											</div>
										</div>										
									</div>
								</div>
								
								<!-- 选择答辩教师 -->
								<div class="panel panel-info">
									<div class="panel-heading">
										<div class="clearfix">
											<span class="pull-left"> <span
												class="glyphicon glyphicon-search"></span>选择答辩老师
											</span> <span class="pull-right"> <span><button
														class="btn btn-primary btn-warning" type="button" id="btnEx">
														<span class="glyphicon glyphicon-bookmark"></span>已有的小组
													</button></span> <span><button class="btn btn-success" type="button" id="btnAd">
														<span class="glyphicon glyphicon-plus"></span>重新添加小组
													</button></span>
											</span>
										</div>
									</div>
									<div class="panel-body">
										<!-- 已经有的小组 -->
										<div id="existingGroup">
											<div class="form-inline">
												<label class="text-danger">选择教师答辩小组：</label> <select
													class="form-control" style="width: 100%" id="chTeachGroup"
													name="replygroup.replyGroupId">
													<option value="-1">--请选择小组--</option>
												</select>
											</div>
											<hr />
											<table
												class="table table-bordered table-hover table-stripped"
												id="teachGroupMems">
												<thead>
													<tr class="text-primary">
														<th>编号</th>
														<th>工号</th>
														<th>教师姓名</th>
													</tr>
												</thead>
												<tbody id="replyTeachers">
													<!-- 这里是动态加载部分 -->
												</tbody>
											</table>
										</div>
										<!-- 添加小组 -->
										<div id="addGroup" style="display: none;">
											<div class="form-inline">
												<span class="text-danger">输入小组名称</span> <input type="text"
													class="form-control" name="replygroup.replyGroupName" placeholder="请输入小组的名称">
											</div>
											<hr />
											<table class="table table-bordered table-hover table-striped"
												id="teachGroupMems">
												<thead>
													<tr class="text-primary">
														<th style="width: 120px;"><input type="checkbox"
															name="" id="checkAll" />全/全不 选</th>
														<th>编号</th>
														<th>工号</th>
														<th>教师姓名</th>
													</tr>
												</thead>
												<tbody id="teachers">
													<!-- 里面是动态加载的内容 -->
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button type="submit" class="btn btn-primary">添加答辩计划</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</form>
				</div>
				<!-- /.modal -->
			</div>


			<!--查看部分-->
			<div class="modal fade bs-example-modal-lg" id="ViewResults"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">查看计划</h4>
						</div>
						<div class="modal-body">
							<div class="panel panel-info">
								<div class="panel-heading">查看答辩信息</div>
								<div class="panel-body">
									<table class="table table-striped">
										<tr>
											<td>答辩地点</td>
											<td id="replyLocation"></td>
										</tr>
										<tr>
											<td>答辩名称</td>
											<td id="replyName"></td>
										</tr>
										<tr>
											<td>答辩描述</td>
											<td id="replyDescribtion"></td>
										</tr>
										<tr>
											<td>答辩开始时间</td>
											<td id="replyStart"></td>
										</tr>
										<tr>
											<td>答辩结束时间</td>
											<td id="replyEnd"></td>
										</tr>
									</table>
								</div>
							</div>
							<div class="panel panel-warning">
								<div class="panel-heading">答辩老师信息</div>
								<div class="panel panel-body">
									<table class="table table-striped">
										<thead>
											<tr class="text-primary">
												<th>序号</th>
												<th>答辩老师工号</th>
												<th>答辩老师姓名</th>										 
											</tr>
										</thead>
										<tbody id="replyTeacherMembers">
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger">
								<span class="glyphicon glyphicon-remove-sign"></span>确定
							</button>
							<button type="button" class="btn btn-info" data-dismiss="modal">
								<span class="glyphicon glyphicon-share-alt"></span>取消
							</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
			</div>

			<!--删除确认-->
			<div class="modal fade" id="deleteSure" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">确认</h4>
						</div>
						<div class="modal-body">
							<h5 class="text-danger">
								<span class="glyphicon glyphicon-question-sign">&nbsp;你确定要删除选中的记录吗?</span>
							</h5>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger">
								<span class="glyphicon glyphicon-remove-sign"></span>确定
							</button>
							<button type="button" class="btn btn-info" data-dismiss="modal">
								<span class="glyphicon glyphicon-share-alt"></span>取消
							</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
			</div>
		</div>
	</div>
</body>
</html>