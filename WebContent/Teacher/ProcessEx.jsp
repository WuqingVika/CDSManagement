<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>过程考核管理</title>
<script type="text/javascript" src="../UIRef/js/procEx.js"></script>
<script type="text/javascript">
	$(function() {
		//alert("hey");
		$('#table1 tbody tr:odd').css('background-color', '#DDFFFF');
		$('#table1 tbody tr:even').css('background-color', '#E8DBF0');
		$('#table2 tbody tr:odd').css('background-color', '#DDFFFF');
		$('#table2 tbody tr:even').css('background-color', '#E8DBF0');
	});
</script>
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
				<li><a href="#">过程考核管理</a></li>
				<li class="active">过程考核</li>
			</ol>
			<!--  wq开始-->
			<div id="wqadd">
				<!--copy-->

				<!-- 模态框 -->
				<div class="modal fade" id="edit" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<form id="AForm" class="form-horizontal bv-form" action=""
								method="post">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true">&times;</button>
									<h4 class="modal-title" id="myModalLabel">添加考核记录</h4>
								</div>
								<!-- modal-header结束-->

								<div class="modal-body"
									style="overflow-y: scroll; height: 400px;">

									<!--操作类型为 批改  -->
									<label> 实验内容 </label>
									<div></div>
									<br /> <label> 打分 </label>
									<div class="panel-body">
										<table class="table table-bordered table-hover"
											style="background-color: #C1E2B3;">
											<thead>
												<tr class=" text-info success ">
													<th class="hidden">过程考核计划编号</th>
													<th class="text-center">学生姓名</th>
													<th class="text-center">分数</th>

												</tr>
											</thead>
											<tbody id="pigaiTbody">

											</tbody>
											<tfoot>
												<tr>
													<td colspan="4" class="info"></td>
												</tr>
											</tfoot>
										</table>
									</div>

								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">关闭</button>
									<button type="submit" name="SureEdit" class="btn btn-success">
										提交考核记录</button>
								</div>
							</form>
						</div>
					</div>
				</div>

				<!--主要内容-->
				<div class="panel panel-info">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-pencil">过程考核</span>
					</div>
					<div class="panel-body">
						<div class="row">
							<div>
								<div class="panel panel-default" style="border: none;">
									<div class="panel-heading panel-info clearfix"
										style="background: none;">

										<div class=" pull-left">
											<form role="form" class="form-inline"
												action="questionServlet" method="post">

												<div class="form-group">

													<label>课题内容:</label> <input type="text" value="${Ind}"
														class="form-control" id="Introduce" name="Introduce"
														size="30" />
													<button type="submit" class="btn btn-success">查询</button>

												</div>
											</form>
										</div>

										<div class=" pull-right">
											<div class="dropdown">
												<a href="" class="btn btn-success">显示所有</a>
												<button class="btn btn-default dropdown-toggle"
													type="button" id="dropdownMenu1" data-toggle="dropdown"
													aria-haspopup="true" aria-expanded="true">
													操作 <span class="caret"></span>
												</button>
												<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
													<li><a data-toggle="modal" href="#Adialog">新增活动</a></li>
													<li class="divider"></li>
													<li><a href="questionServlet?operaType=4">活动导出</a></li>
													<li><a href="questionServlet?operaType=5">活动导入</a></li>

												</ul>
											</div>
										</div>
										<!--pull-right" -->
									</div>
									<div class="panel-body">
										<table class="table table-striped">
											<thead>
												<tr>
													<th>编号</th>
													<th>设计题目</th>
													<th>过程考核详细</th>
													<th>过程考核名称</th>
													<th>开始时间</th>
													<th>结束时间</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${pager.data}" var="Act">
													<tr>
														<td>1</td>
														<td>关于仓库管理系统的设计与实现</td>
														<td>考核阶段一</td>
														<td>考核是否完成需求分析</td>
														<td>01/04/2012</td>
														<td>01/04/2012</td>
														<td><label>
																<button name="APEx" class="btn btn-info">
																	<span class="glyphicon glyphicon-edit"></span>添加考核记录
																</button>
														</label></td>
													</tr>
													<tr>
														<td>1</td>
														<td>关于仓库管理系统的设计与实现</td>
														<td>考核阶段一</td>
														<td>考核是否完成需求分析</td>
														<td>01/04/2012</td>
														<td>01/04/2012</td>
														<td><label>
																<button name="APEx" class="btn btn-info">
																	<span class="glyphicon glyphicon-edit"></span>添加考核记录
																</button>
														</label></td>
													</tr>
												</c:forEach>
											</tbody>
											<tfoot>
												<tr>
													<td colspan="7"><page:page
															pageSize="${pager.pageSize }" url="questionServlet"
															totalRows="${pager.totalRows}"
															currentPage="${pager.currentPage}" subjectId="${si}"
															questionTypeId="${qi}" quesContent="${qc}" /></td>
												</tr>
											</tfoot>
										</table>
									</div>

								</div>
							</div>

							<!--copyend-->
						</div>
					</div>
				</div>

				<!--  wq结束-->
			</div>
		</div>
	</div>
</body>

</html>