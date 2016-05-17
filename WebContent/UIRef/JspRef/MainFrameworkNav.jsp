<%@page language="java" pageEncoding="utf-8"%>
</head>
<nav class="navbar navbar-inverse navbar-fixed-top banner">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">课程设计平台管理系统</a>
		</div>		 
	</div>
</nav>
 
<div class="container-fluid">
	<div class="row">
		<!--
                	作者：dntchenpeng@163.com
                	时间：2016-03-23
                	描述：左边的导航栏部分
                -->
		<div class="col-sm-3 col-md-2 sidebar">
			<div class="row">
				<div class="panel-group" id="accordion">
					<div class="panel panel-danger">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="btn btn-block" data-toggle="collapse"
									data-parent="#accordion" href="#collapseOne"> <span
									class="glyphicon glyphicon-user"></span>校级基础数据管理
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">
									<ul class="nav nav-stacked">
										<li><a
											href="${pageContext.request.contextPath}/collegeinfo_index.action"><span
												class="glyphicon glyphicon-share-alt"></span>学院信息管理</a></li>
										<li><a
											href="${pageContext.request.contextPath}/terminfo_index.action"><span
												class="glyphicon glyphicon-share-alt"></span>学期信息管理</a></li>
										<li><a
											href="${pageContext.request.contextPath}/teacherdirectorinfo_index.action"><span
												class="glyphicon glyphicon-share-alt"></span>教研室主任信息管理</a></li>								
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-success">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="btn btn-block text-left" data-toggle="collapse"
									data-parent="#accordion" href="#collapseTwo"> <span
									class="glyphicon glyphicon-user"></span>学院级基础数据管理
								</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">
									<ul class="nav nav-stacked">
										<li><a
											href="${pageContext.request.contextPath}/majorinfo_index.action"><span
												class="glyphicon glyphicon-share-alt"></span>专业信息管理</a></li>
										<li><a
											href="${pageContext.request.contextPath}/classinfo_index.action"><span
												class="glyphicon glyphicon-share-alt"></span>班级信息管理</a></li>
										<li><a
											href="${pageContext.request.contextPath}/teacherinfo_index.action"><span
												class="glyphicon glyphicon-share-alt"></span>教师信息管理</a></li>
										<li><a
											href="${pageContext.request.contextPath}/studentinfo_index.action"><span
												class="glyphicon glyphicon-share-alt"></span>学生信息管理</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-success">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="btn btn-block text-left" data-toggle="collapse"
									data-parent="#accordion" href="#collapseThree"> <span
									class="glyphicon glyphicon-edit"></span>课程计划及组长管理
								</a>
							</h4>
						</div>
						<div id="collapseThree" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">
									<ul class="nav nav-stacked">
										<li><a
											href="${pageContext.request.contextPath}/cdplaninfo_index.action"><span
												class="glyphicon glyphicon-share-alt"></span>课程设计管理</a>
										</li>
										<li>
										<a href="${pageContext.request.contextPath}/cdplanleader_index.action"><span
												class="glyphicon glyphicon-share-alt"></span>管理课程设计组长</a>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$(function() {
						$("div.collapse").collapse("hide");
					});
				</script>
			</div>
		</div>
	</div></div>