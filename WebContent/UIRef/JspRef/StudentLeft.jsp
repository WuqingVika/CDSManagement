<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
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
									class="glyphicon glyphicon-user"></span>个人信息管理模块
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">
									<ul class="nav nav-stacked">
										<li><a href="student_getStudentInfo.action"><span
												class="glyphicon glyphicon-share-alt"></span>查看个人信息</a></li>
										<li><a href="${pageContext.request.contextPath}/Student/ModifyPass.jsp"><span
												class="glyphicon glyphicon-share-alt"></span>修改密码</a></li>
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
									class="glyphicon glyphicon-edit"></span>课程设计选题管理
								</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">

									<ul class="nav nav-stacked">
										<li><a href="student_searchCDChoosing.action"><span
												class="glyphicon glyphicon-share-alt"></span>课程设计选题</a></li>
										<li><a href="student_findAllSelfChoosed.action"><span
												class="glyphicon glyphicon-share-alt"></span>查看选题结果</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-info">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="btn btn-block" data-toggle="collapse"
									data-parent="#accordion" href="#collapseThree"> <span
									class="glyphicon glyphicon-th-list"></span>课设过程任务管理
								</a>
							</h4>
						</div>
						<div id="collapseThree" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">

									<ul class="nav nav-stacked">
										<li><a href="cddesigntask_searchAllCD.action"><span
												class="glyphicon glyphicon-share-alt"></span>查看过程考核计划</a></li>
										<li><a href="cddesigntask_uploadCDTask.action"><span
												class="glyphicon glyphicon-share-alt"></span>提交过程材料</a></li>
										<li><a href="cddesigntask_searchCDTask.action"><span
												class="glyphicon glyphicon-share-alt"></span>查看已提交的材料</a></li>
									</ul>

								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-warning">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="btn btn-block" data-toggle="collapse"
									data-parent="#accordion" href="#collapseFour"> <span
									class="glyphicon glyphicon-thumbs-up"></span>课程设计自评管理
								</a>
							</h4>
						</div>
						<div id="collapseFour" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">

									<ul class="nav nav-stacked">
										<li><a href="cddesigntask_toSelfEval.action"><span
												class="glyphicon glyphicon-share-alt"></span>课程设计成绩自评</a></li>
									</ul>

								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-success">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="btn btn-block" data-toggle="collapse"
									data-parent="#accordion" href="#collapseFive"> <span
									class="glyphicon glyphicon-file"></span>课程设计成绩查询
								</a>
							</h4>
						</div>
						<div id="collapseFive" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">

									<ul class="nav nav-stacked">
										<li><a href="cddesigntask_findStudentScore.action"><span
												class="glyphicon glyphicon-share-alt"></span>查看课程设计成绩</a></li>
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
	</div>
</body>
</html>