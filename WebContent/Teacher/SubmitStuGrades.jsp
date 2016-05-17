<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>提交学生成绩</title>
<script type="text/javascript" src="../js/wq/wqsubGrade.js"></script>
</head>

<body>
	<jsp:include page="../UIRef/JspRef/StudentHeader.jsp"></jsp:include>
	<div class="container-fluid">
		<jsp:include page="../UIRef/JspRef/TeacherleftInstructor.jsp"></jsp:include>
		<!--主体内容             -->
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<!--面包屑导航-->
			<ol class="breadcrumb" style="width: 100%;">
				<li><a href="#">课程设计平台管理系统</a></li>
				<li><a href="#">学生成绩管理</a></li>
				<li class="active">提交学生成绩</li>
			</ol>
			<!--主要内容-->
			<div class="panel panel-info">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-pencil">提交学生成绩</span>
				</div>
				<div class="panel-body">
					<!--  wq开始-->
					<div id="wqadd">
						<div class="panel-heading panel-info clearfix">
							<div class=" pull-left">
								<form action="<%=basePath%>explistAction.action" method="post"
									class="form-inline">
									<div class="form-group">
										<label> 查看课题 </label> <select class="form-control"
											name="courseId" id="courseId" style="width: 600px;">
											<option value="0">--请选择课题名称--</option>
											<option value="1">--课题 名称一--</option>
											<option value="2">--课题名称 二--</option>
										</select>
										<button type="submit" class="btn btn-success">查询</button>
									</div>
									<!--form group结尾-->
								</form>
							</div>
						</div>
						<!---->
						<div class="panel-body">
							<table class="table table-bordered table-hover">
								<thead>
									<tr class="text-primary">
										<th class="text-center">设计课题编号</th>
										<th class="text-center">课程设计名称</th>
										<th class="text-center">小组</th>
										<th class="text-center">操作</th>

									</tr>
								</thead>
								<tbody>
									<c:forEach items="${sessionScope.pigaiResult}" var="expList">
										<tr>
											<td class=text-center>1</td>
											<td class=text-center>关于欣迈餐饮管理系统的设计与实现</td>
											<td class=text-center>张萌萌小组</td>

											<td class="text-center"><label>
													<button name="wqssg" class="btn btn-info">
														<span class="glyphicon glyphicon-edit"></span>批改成绩
													</button>
											</label></td>
										</tr>
										<tr>
											<td class=text-center>1</td>
											<td class=text-center>关于欣迈餐饮管理系统的设计与实现</td>
											<td class=text-center>张萌萌小组</td>

											<td class="text-center"><label>
													<button name="wqssg" class="btn btn-info">
														<span class="glyphicon glyphicon-edit"></span>批改成绩
													</button>
											</label></td>
										</tr>
										<tr>
											<td class=text-center>1</td>
											<td class=text-center>关于欣迈餐饮管理系统的设计与实现</td>
											<td class=text-center>张萌萌小组</td>

											<td class="text-center"><label>
													<button name="wqssg" class="btn btn-info">
														<span class="glyphicon glyphicon-edit"></span>批改成绩
													</button>
											</label></td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>

								</tfoot>
							</table>
						</div>
						<!--表格外面一层-->

						<!-- 模态框（修改） -->
						<div class="modal fade" id="edit" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<form id="AForm" class="form-horizontal bv-form" action=""
										method="post">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="myModalLabel">添加学生成绩</h4>
										</div>
										<!-- modal-header结束-->

										<div class="modal-body"
											style="overflow-y: scroll; height: 400px;">

											<h3 class="control-label glyphicon glyphicon glyphicon-flag"
												for="inputSuccess1">关于欣迈餐饮管理系统的设计与实现</h3>

											<br />

											<div class="panel-body">
												<table class="table table-bordered table-hover"
													style="background-color: #C1E2B3;">
													<thead>
														<tr class=" text-info success ">
															<th class="hidden">设计课题编号</th>
															<th class="text-center">学生姓名</th>
															<th class="text-center">状态信息</th>
															<th class="text-center">考核成绩</th>
															<th class="text-center">答辩成绩</th>
															<th class="text-center">考核成绩</th>
														</tr>
													</thead>
													<tbody id="pigaiTbody">
														<tr>
															<td class="hidden">1</td>
															<td>张萌</td>
															<td>组长</td>
															<td><input id="text" style="width: 50px;" value="80" />
															</td>
															<td><input id="text" style="width: 50px;" value="80" />
															</td>
															<td><input id="text" style="width: 50px;" value="80" />
															</td>
														</tr>
													</tbody>
													<tfoot>
														<tr>
															<td colspan="6" class="info"></td>
														</tr>
													</tfoot>
												</table>
											</div>

										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">关闭</button>
											<button type="submit" name="SureEdit" class="btn btn-success">
												提交学生成绩</button>
										</div>
									</form>
								</div>
							</div>
						</div>

					</div>
					<!--  wq结束-->
				</div>
			</div>
		</div>
	</div>
</body>

</html>