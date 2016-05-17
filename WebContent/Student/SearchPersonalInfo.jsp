<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>个人信息</title>
</head>
<body>
	<!-- banner-start -->
	<jsp:include page="../UIRef/JspRef/StudentHeader.jsp"></jsp:include>
	<!-- banner-end -->

	<!-- content-start -->
	<div class="container-fluid">
		<!-- left-nav-start -->
		<jsp:include page="../UIRef/JspRef/StudentLeft.jsp"></jsp:include>
		<!-- left-nav-end -->
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<ol class="breadcrumb" style="width: 100%;">
				<li><a href="#">课程设计平台管理系统</a></li>
				<li><a href="#">个人信息管理</a></li>
				<li class="active">查看个人信息</li>
			</ol>
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="clearfix">
						<span style="line-height: 30px;"><span
							class="glyphicon glyphicon-chevron-right"></span>学生个人信息</span> <a
							class="btn btn-primary pull-right" href="student_toPDF.action"><span
							class="glyphicon glyphicon-new-window"></span>导出为pdf文档</a>
					</div>
				</div>
				<div class="panel-body">
					<div class="row">
						<table class="table table-hover table-bordered">
							<tr>
								<td align="right">学生姓名：</td>
								<td>${student.stuName}</td>
							</tr>
							<tr>
								<td align="right">学生学号：</td>
								<td>${student.studentId}</td>
							</tr>
							<tr>
								<td align="right">学院：</td>
								<td>${student.classes.major.college.collegeName}</td>
							</tr>
							<tr>
								<td align="right">专业：</td>
								<td>${student.classes.major.majorName}</td>
							</tr>
							<tr>
								<td align="right">班级：</td>
								<td>${student.classes.className}</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="panel-footer">
					<p class="text-center">
						<span class="glyphicon glyphicon-grain"></span>如果信息有误请及时与学校信息中心联系
					</p>
				</div>
			</div>
		</div>
	</div>
	<!-- content-end -->
</body>
</html>