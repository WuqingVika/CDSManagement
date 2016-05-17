<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>修改密码</title>
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
				<li class="active">修改密码</li>
			</ol>

			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="clearfix">
						<span style="line-height: 30px;"><span
							class="glyphicon glyphicon-chevron-right"></span>修改密码</span>
					</div>
				</div>
				<div class="panel-body">
					<div class="row">
						<form class="form-horizontal" role="form" action="student_modifyAccountPass.action" method="post">
							<div class="form-group">
								<label for="firstname" class="col-sm-2 control-label">输入密码：</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" name="account.passwords" id="firstname"
										placeholder="请输入您的密码">
								</div>
							</div>
							<div class="form-group">
								<label for="lastname" class="col-sm-2 control-label">确认密码：</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" id="lastname"
										placeholder="请再次输入密码">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" class="btn btn-primary">修改密码</button>
								</div>
							</div>
						</form>
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