<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>答辩考核</title>
<link rel="stylesheet" href="<%=basePath%>/UIRef/css/datapicker/default.css" />
<script type="text/javascript" src="<%=basePath%>/UIRef/js/zebra_datepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>/UIRef/js/ReplyManage.js"></script>
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
				<li><a href="#">学生成绩管理</a></li>
				<li class="active">答辩考核</li>
			</ol>
			<!--查看所选择的内容-->
			
			<form role="form" class="form-inline" action="Reply_getAllGroup.action" method="post">

								<div class="form-group ">
									<label>
										选择答辩课设计划名
									</label>
									<input type="hidden" value="<s:property value="stugroupid"/>" name="stugroupid" id="stugroupid"/>
									<select class="form-control" id="stugroupidSelect">
											<c:forEach items="${replyNames}" var="replyName"
												varStatus="varSta">
												<option value="${replyName[2]}">${replyName[1]}</option>
											</c:forEach>
									</select>
									<input type="hidden" value="<s:property value="cddesignTopId"/>" name="cddesignTopId" id="cddesignTopId"/>
									<label>
										选题课题
									</label>
									<select class="form-control" id="cddesignTopIdSelect" >
									</select>
									<button type="submit" class="btn btn-success">
										<span class="glyphicon glyphicon-search "></span>查询
									</button>
									
								</div>
							</form>

						<!--查看分配情况-->
			
						<div class="modal-header">
								<div class="pull-right">
								<div class="dropdown">
										<button class="btn btn-danger dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
											操作
										<span class="caret"></span>
										</button>
										<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
											<li>
												<a id="allShow" href="#">全部隐藏</a>
												</li>
											<li class="divider"></li>
											<li><a id="allHide" href="#">全部展开</a>
									
							</li>

										</ul>
									</div>
								
							</div>
							<br />
						</div>
						<div class="modal-body">
							<!--控制面板-->
							<c:forEach items="${mrs}" var="mr">
							<!--选择课题内容部分开始-->
							<div id="classGroupInfoCont">
								<!--每一项的内容-->
								<div class="panel panel-info GroupShow">
								
									<div class="panel-heading">
										<div class="clearfix">
											<span class="pull-left"><span
												class="glyphicon glyphicon-tasks"></span></span> 小组编号：${mr.stugroupId}<span
												class="pull-right">
												<button class="btn btn-success closeGroupInfo" name="wqhide"
													style="font-size: 10px;">
													<span class="glyphicon glyphicon-chevron-up"></span>
												</button>
												
											</span>
										</div>
									</div>
								<!--每一项的内容-->
						<div class="panel-body groupDetail">
										<table class="table table-striped table-hover">
											<thead class="text-danger">
												<tr>
												    <th>答辩编号</th>
													<th>学号</th>
													<th>姓名</th>
													<th>状态</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
											
											    <c:forEach items="${mr.members}" var="student">
												<tr>
												    <td>${mr.replyplanId}</td>
													<td>${student[0]}</td>
													<td>${student[1]}</td>
													<td>${student[2]}</td>
													<td><span>
													<button  name="reply" class="btn btn-info glyphicon glyphicon-leaf">
														答辩
											        </button> 
												</span></td>
												</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								<!--每一项的内容end-->
								</div>
								
							</div>
							</c:forEach>
							<!--选择课题内容部分结束-->
						</div>
						<div class="modal-footer">
							
						</div>
				
		
				<!-- /.modal -->
			<!--答辩模态框-->
			<div class="modal fade" id="Replydialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<form id="UForm" class="form-horizontal bv-form" action="">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
													&times;
												</button>
												<h4 class="modal-title" id="myModalLabel">
													答辩情况记录
												</h4>
											</div>
											<!-- modal-header结束-->
											<div class="modal-body" >
												<!--操作类型为 修改  -->
												<input type="hidden" name="checkPlanId" id="checkPlanId" />

												<br />
													<!--每一次加载的内容-->
								<div class="panel panel-info">
									<div class="panel-heading">
										<div class="clearfix">
											<span class="pull-left"> <span
												class="glyphicon glyphicon-leaf"></span>
												<a id="stuName" style="text-decoration: none;font-size: 25px;font-weight: true"></a>同学的答辩记录
											</span> 
											<span class="pull-right"><button type="button"
												id="addReply" title="添加答辩记录" class="btn btn-success"
												style="font-size: 10px;">
												<span class="glyphicon glyphicon-plus">添加问题记录</span>
											</button> </span>
										</div>
									</div>
									<div class="panel-body" id="wqReply" >
										<!--里面是具体的内容-->
								
									</div>
									<div id="divagain">
									
									</div>
								</div><!--每一次增加的内容-->
										
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">
													关闭
												</button>
												<button class="btn btn-primary" type="submit">
													提交更改
												</button>
											</div>
										</form>
									</div>
								</div>
							</div>
		
			<!--答辩模态框 结束！-->
		</div>
	</div>
	<!-- content-end -->
</body>
</html>