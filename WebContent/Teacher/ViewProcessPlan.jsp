<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>查看过程考核计划</title>
<script>
	$(function() {
		//设置每一个模态框都可以拖拽
		$(".modal").draggable({
			handle : ".modal-header",
			cursor : "all-scroll"
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
			<!--面包屑导航-->
			<ol class="breadcrumb" style="width: 100%;">
				<li><a href="#">课程设计平台管理系统</a></li>
				<li><a href="#">课程设计选题管理</a></li>
				<li class="active">查看过程考核计划</li>
			</ol>
			<table class="table table-hover table-striped table-bordered">
				<thead>
					<tr class="text-primary">
						<th>课程设计编号</th>
						<th>课程设计题目</th>
						<th>指导老师</th>
						<th>查看阶段任务</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>3213</td>
						<td>基于反倒是jfk撒的软件的设计与实现</td>
						<td>孔磊</td>
						<td>
							<button class="btn btn-success" data-toggle="modal"
								data-target="#searchCDPlan">查看阶段性任务</button>
						</td>
					</tr>
					<tr>
						<td>3213</td>
						<td>基于反倒是jfk撒的软件的设计与实现</td>
						<td>孔磊</td>
						<td>
							<button class="btn btn-success" data-toggle="modal"
								data-target="#searchCDPlan">查看阶段性任务</button>
						</td>
					</tr>
					<tr>
						<td>3213</td>
						<td>基于反倒是jfk撒的软件的设计与实现</td>
						<td>孔磊</td>
						<td>
							<button class="btn btn-success" data-toggle="modal"
								data-target="#searchCDPlan">查看阶段性任务</button>
						</td>
					</tr>
					<tr>
						<td>3213</td>
						<td>基于反倒是jfk撒的软件的设计与实现</td>
						<td>孔磊</td>
						<td>
							<button class="btn btn-success" data-toggle="modal"
								data-target="#searchCDPlan">查看阶段性任务</button>
						</td>
					</tr>
					<tr>
						<td>3213</td>
						<td>基于反倒是jfk撒的软件的设计与实现</td>
						<td>孔磊</td>
						<td>
							<button class="btn btn-success" data-toggle="modal"
								data-target="#searchCDPlan">查看阶段性任务</button>
						</td>
					</tr>
				</tbody>
			</table>
			<!--分页-->
			<div class="well well-sm text-center">
				<nav>
				<ul class="pagination">
					<li><a href="#" aria-label="Previous"> <span
							aria-hidden="true">&laquo;</span>
					</a></li>
					<li><a href="#">1</a></li>
					<li><a href="#">2</a></li>
					<li><a href="#">3</a></li>
					<li><a href="#">4</a></li>
					<li><a href="#">5</a></li>
					<li><a href="#" aria-label="Next"> <span
							aria-hidden="true">&raquo;</span>
					</a></li>
				</ul>
				</nav>
			</div>

			<!--下面是模态框部分-->
			<!-- 查看课程设计阶段的任务 -->
			<div class="modal fade bs-example-modal-lg" id="searchCDPlan"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">查看课程设计阶段任务</h4>
						</div>
						<div class="modal-body">
							<div class="well well-sm"
								style="border: none; background: white; height: 30px;">
								<div class="clearfix">
									<button class="btn btn-primary pull-right">
										<span class="glyphicon glyphicon-log-out"></span>导出为excel
									</button>
								</div>
							</div>
							<table class="table table-hover table-striped table-bordered">
								<thead style="background: lightblue;">
									<tr class="text-primary">
										<th>序号</th>
										<th>过程考核名称</th>
										<th>过程考核详细</th>
										<th>开始时间</th>
										<th>结束时间</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>1</td>
										<td>开题报告</td>
										<td>法艰苦拉萨jfk到拉萨解放了肯定撒；飞机打瞌睡啦jfk到拉萨jfk都洒了</td>
										<td>2016-04-05</td>
										<td>2016-04-09</td>
									</tr>
									<tr>
										<td>2</td>
										<td>开题报告</td>
										<td>法艰苦拉萨jfk到拉萨解放了肯定撒；飞机打瞌睡啦jfk到拉萨jfk都洒了</td>
										<td>2016-04-05</td>
										<td>2016-04-09</td>
									</tr>
									<tr>
										<td>3</td>
										<td>开题报告</td>
										<td>法艰苦拉萨jfk到拉萨解放了肯定撒；飞机打瞌睡啦jfk到拉萨jfk都洒了</td>
										<td>2016-04-05</td>
										<td>2016-04-09</td>
									</tr>
									<tr>
										<td>4</td>
										<td>开题报告</td>
										<td>法艰苦拉萨jfk到拉萨解放了肯定撒；飞机打瞌睡啦jfk到拉萨jfk都洒了</td>
										<td>2016-04-05</td>
										<td>2016-04-09</td>
									</tr>
									<tr>
										<td>5</td>
										<td>开题报告</td>
										<td>法艰苦拉萨jfk到拉萨解放了肯定撒；飞机打瞌睡啦jfk到拉萨jfk都洒了</td>
										<td>2016-04-05</td>
										<td>2016-04-09</td>
									</tr>
									<tr>
										<td>6</td>
										<td>开题报告</td>
										<td>法艰苦拉萨jfk到拉萨解放了肯定撒；飞机打瞌睡啦jfk到拉萨jfk都洒了</td>
										<td>2016-04-05</td>
										<td>2016-04-09</td>
									</tr>
									<tr>
										<td>6</td>
										<td>开题报告</td>
										<td>法艰苦拉萨jfk到拉萨解放了肯定撒；飞机打瞌睡啦jfk到拉萨jfk都洒了</td>
										<td>2016-04-05</td>
										<td>2016-04-09</td>
									</tr>
									<tr>
										<td>6</td>
										<td>开题报告</td>
										<td>法艰苦拉萨jfk到拉萨解放了肯定撒；飞机打瞌睡啦jfk到拉萨jfk都洒了</td>
										<td>2016-04-05</td>
										<td>2016-04-09</td>
									</tr>
									<tr>
										<td>6</td>
										<td>开题报告</td>
										<td>法艰苦拉萨jfk到拉萨解放了肯定撒；飞机打瞌睡啦jfk到拉萨jfk都洒了</td>
										<td>2016-04-05</td>
										<td>2016-04-09</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-warning"
								data-dismiss="modal">
								<span class="glyphicon glyphicon-remove-circle"></span>关闭页面
							</button>
							<button type="button" class="btn btn-success">
								<span class="glyphicon glyphicon-share-alt"></span>提交材料
							</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
			</div>
		</div>
	</div>
	<!-- content-end -->
</body>
</html>