<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
		<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
		<title>添加过程考核计划</title>
		<link rel="stylesheet" href="<%=basePath%>/UIRef/css/datapicker/default.css" />
		<script type="text/javascript" src="<%=basePath%>/UIRef/js/zebra_datepicker.js"></script>
			<script type="text/javascript">
			$(function() {
				//alert("hi");
				$("#startTime").Zebra_DatePicker({
					direction: true,
					pair: $("#endTime")
				});
				$("#endTime").Zebra_DatePicker({
					direction: 1
				});
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
				<li><a href="#">过程考核计划管理</a></li>
				<li class="active">添加过程考核计划</li>
			</ol>
				<div class="panel panel-info">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-pencil"></span>新增过程考核计划
					</div>
					<div class="panel-body">
						<!--主体内容-->					 
						<form class="form-horizontal" action="teacherMakeProPlan_addProPlan.action" role="form">
							<div class="form-group">
								<label for="classId" class="col-sm-2 control-label"><span class="glyphicon glyphicon-hand-right">
									
								</span>选择课程计划名</label>
								<div class="col-sm-10">
									<select class="form-control" id="pcd.cdteachergroup.cdteacherGroupId" name="pcd.cdteachergroup.cdteacherGroupId">
										<c:forEach items="${Cdteachergroups}" var="Cdteachergroup" varStatus="varSta">
										<option value="${Cdteachergroup[1]}">${Cdteachergroup[2]}</option>
										</c:forEach>
									</select> 
								</div>
							</div>
							<div class="form-group">
								<label for="cdTitleName" class="col-sm-2 control-label"><span class="glyphicon glyphicon glyphicon-flag"></span>过程考核名称</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="pcd.processName" name="pcd.processName" placeholder="请输入过程考核名称..." />
								</div>
							</div>
							<div class="form-group">
								<label for="startTime" class="col-sm-2 control-label"><span class="glyphicon  glyphicon-time"></span>开始时间</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" size="30" name="pcd.startTime" id="startTime" /> 
								</div>
							</div>
							<div class="form-group">
								<label for="endTime" class="col-sm-2 control-label"><span class="glyphicon glyphicon-remove-circle"></span>结束时间</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" size="30" name="pcd.endTime" id="endTime" />
								</div>
							</div>
							<div class="form-group">
								<label for="cdDetail" class="col-sm-2 control-label"><span class="control-label glyphicon"></span>过程考核详细</label>
								<div class="col-sm-10">
									<textarea class="form-control" style="height: 200px;" id="pcd.processDescribtion" name="pcd.processDescribtion" placeholder="请输入课程考核详细..."></textarea>
								</div>
							</div>
							<div class="text-center">
								<input type="submit" class=" glyphicon glyphicon-check btn btn-success" style="width:200px;" value="添加"/>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</body>

</html>