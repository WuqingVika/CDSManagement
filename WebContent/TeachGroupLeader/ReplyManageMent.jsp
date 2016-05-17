<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>答辩计划管理</title>
<script>
	$(function() {
		$("input[type=checkbox]#checkAll").click(function() {
			$(".checkItem").prop("checked", $(this).is(":checked"));
		});
		//添加答辩计划
		$("#viewApplied").click(function() {
			$("button.btnAdd").show();
			$("button.btnDel").hide();
			$("button.btnSearch").hide();
		});
		//查看已经分配的答辩计划
		$("#viewNotApplied").click(function() {
			$("button.btnAdd").hide();
			$("button.btnDel").show();
			$("button.btnSearch").show();
		});
	});
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
				<li><a href="#">个人信息管理</a></li>
				<li class="active">制定答辩任务</li>
			</ol>
			<div class="well well-sm" style="background: none; border: none;">
				<div class="clearfix">
					<span class="pull-left">
						<form class="form-inline">
							课程设计计划 <select class="form-control">
								<option>--请选择课程设计计划--</option>
							</select>
							<button class="btn btn-success">查询</button>
						</form>
					</span> <span class="pull-right">
						<div class="btn-group">
							<button type="button" class="btn btn-warning dropdown-toggle"
								data-toggle="dropdown">
								请选择查询的方式 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a style="cursor: pointer;" id="viewApplied">查看未分配的小组</a></li>
								<li><a style="cursor: pointer;" id="viewNotApplied">查看已分配的小组</a></li>
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
							<th>小组编号</th>
							<th>课程设计计划名称</th>
							<th>小组课题名称</th>
							<th>指导老师</th>
							<th>专业名称</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>2323</td>
							<td>c#课程设计</td>
							<td>基于电子商务网站的xxx软件系统的设计与实现</td>
							<td>孔磊</td>
							<td>计算机科学与技术</td>
							<td>
								<button class="btn btn-success btnAdd" data-toggle="modal"
									data-target="#myModal">
									<span class="glyphicon glyphicon-ok-sign"></span>添加答辩计划
								</button>
								<button class="btn btn-info btnSearch" data-toggle="modal"
									data-target="#ViewResults">
									<span class="glyphicon glyphicon-search"></span>查看答辩计划
								</button>
								<button class="btn btn-danger btnDel" data-toggle="modal"
									data-target="#deleteSure">
									<span class="glyphicon glyphicon-remove"></span>删除答辩计划
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<!--模态框部分-->
			<div class="modal fade bs-example-modal-lg" id="myModal"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-lg">
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
									<form class="form-horizontal" role="form">
										<div class="form-group">
											<label for="firstname" class="col-sm-3 control-label">答辩地点</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" id="firstname"
													placeholder="输入答辩地点">
											</div>
										</div>
										<div class="form-group">
											<label for="lastname" class="col-sm-3 control-label">开始时间</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" id="lastname"
													placeholder="输入开始时间">
											</div>
										</div>
										<div class="form-group">
											<label for="lastname" class="col-sm-3 control-label">结束时间</label>
											<div class="col-sm-9">
												<input type="text" class="form-control" id="lastname"
													placeholder="请输入结束时间">
											</div>
										</div>
									</form>
								</div>
							</div>

							<div class="panel panel-info">
								<div class="panel-heading">
									<span class="glyphicon glyphicon-search"></span>选择答辩老师
								</div>
								<div class="panel-body">
									<form class="form-horizontal" role="form">
										<div class="form-group">
											<label for="firstname" class="col-sm-2 control-label">请选择学院</label>
											<div class="col-sm-10">
												<select class="form-control">
													<option>--请选择学院--</option>
												</select>
											</div>
										</div>
									</form>
									<table class="table table-hover table-striped table-bordered">
										<thead>
											<tr class="text-primary">
												<th style="width: 100px;" class="text-center"><input
													type="checkbox" id="checkAllOrNot" />全/全不选</th>
												<th>工号</th>
												<th>姓名</th>
											</tr>
										</thead>
										<tbody id="tableContent">
											<tr>
												<td class="text-center"><input type="checkbox"
													class="checkItem"></td>
												<td>xs003</td>
												<td>陈鹏</td>
											</tr>
											<tr>
												<td class="text-center"><input type="checkbox"
													class="checkItem"></td>
												<td>xs003</td>
												<td>陈鹏</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary">提交更改</button>
						</div>
					</div>
				</div>
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
											<td>xxx</td>
										</tr>
										<tr>
											<td>答辩时间</td>
											<td>xxx</td>
										</tr>
										<tr>
											<td>答辩开始时间</td>
											<td>xxx</td>
										</tr>
										<tr>
											<td>答辩结束时间</td>
											<td>xxx</td>
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
												<th>所在学院</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>2</td>
												<td>2324</td>
												<td>孔磊</td>
												<td>信电学院</td>
											</tr>
											<tr>
												<td>2</td>
												<td>2324</td>
												<td>孔磊</td>
												<td>信电学院</td>
											</tr>
											<tr>
												<td>2</td>
												<td>2324</td>
												<td>孔磊</td>
												<td>信电学院</td>
											</tr>
											<tr>
												<td>2</td>
												<td>2324</td>
												<td>孔磊</td>
												<td>信电学院</td>
											</tr>
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
				</div>
			</div>
		</div>
	</div>
</body>
</html>