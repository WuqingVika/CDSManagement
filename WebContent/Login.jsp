<%@page language="java" pageEncoding="utf-8" %>
<%@include file="UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>用户登录</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/UIRef/css/login.css"/>
<script type="text/javascript">
 	$(function(){
 		$("#changeNext").click(function(){
 			$("#code").attr("src","getSecurityCode.action?timestap="+new Date().getTime());
 		}); 		
 	});
</script>
</head>
<body>
	<div class="container">
			<div class="container pull-left" style="color:white;">
				<h3 class="text-muted" ><em><span><img src="${pageContext.request.contextPath }/UIRef/image/logo.png" style="border-radius: 50%;width:30px;height:30px;"/></span>课程设计平台管理系统</em></h3>				 
			</div>		
			<form class="formLogin" action="login.action" method="post">
				<div class="text-center">
					<h2 class="form-signin-heading" style="color:antiquewhite">用户登录</h2>
				</div>						
				<p class="text-danger" style="height:20px;"><em>${message}</em></p>		
				<div class="form-group">
					<label for="inputEmail" class="sr-only">用户账号</label>					 
					<input type="text" id="accountId" name="accountId" class="form-control" placeholder="请输入您的账号" required autofocus>					
				</div>
				<div class="form-group">					
					<label for="inputPassword" class="sr-only">用户密码</label>
					<input type="password" id="passwords" name="passwords" class="form-control" placeholder="请输入您的密码" required>
				</div>	
				<div class="form-group">
					<label for="code" class="sr-only">输入验证码</label>
					<input type="text" class="form-control" name="securityCode" placeholder="输入验证码" id="securityCode"/>					
				</div>		 
				<div class="form-group">
					<img class="pull-left" id="code" src="getSecurityCode.action" alt="验证码"/>
					<a href="#" class="pull-left" id="changeNext">换一张</a>	
					<div class="checkbox pull-right" style="color:white;">
						<label>
							<input type="checkbox" value="remember-me" checked="checked"> 记住我
						</label>
					</div>	
				</div>
				<br />			 			
				<br />	
				<br />			 
				<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
			</form>
		</div>
		<div class="footer text-center" style="padding-top:100px;">
			<p style="color:white;">徐州工程学院信电工程学院</p>
		</div>
		<!-- /container -->
	</body>
</body>
</html>