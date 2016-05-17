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
		<title>查看学生成绩</title>	 
		<script type="text/javascript" src="<%=basePath%>/UIRef/js/wqModGrade.js"></script>
	</head>
	
	<body>
	<jsp:include page="../UIRef/JspRef/StudentHeader.jsp"></jsp:include>
		<div class="container-fluid">
			<jsp:include page="../UIRef/JspRef/TeacherleftInstructor.jsp"></jsp:include>
			<!--主体内容             -->
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb" style="width: 100%;">
				<li><a href="#">课程设计平台管理系统</a></li>
				<li><a href="#">学生成绩管理</a></li>
				<li class="active">查看学生成绩</li>
			</ol>
				<!--主要内容-->
				<div class="panel panel-info">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-pencil">查看学生成绩</span>
					</div>
					<div class="panel-body">
						<!--  wq开始-->
						<div id="wqadd">
							<div class="panel-heading panel-info clearfix">
								<div class="pull-left">
								<input type="hidden" value="<s:property value="cdplanId"/>" name="cdplanId" id="cdplanId"/>
									
									<form role="form" class="form-inline" method="post" action="StuGrade_showStuCdName.action">
										<div class="form-group">
											<label>
										选择课程设计计划名
									        </label>
									<select class="form-control" id="cdplanIdSelect">
											<c:forEach items="${Cdteachergroups}" var="Cdteachergroup"
												varStatus="varSta">
												<option value="${Cdteachergroup[0]}">${Cdteachergroup[2]}</option>
											</c:forEach>
									</select>
									<input type="hidden" value="<s:property value="cddesignTopId"/>" name="cddesignTopId" id="cddesignTopId"/>
									<label>
										选择题目
									</label>
									<select class="form-control" id="cddesignTopIdSelect" >
									</select>
											<button type="submit" class="btn btn-success">
												查询
											</button>
										</div>
									</form>
								</div>
								<div class="pull-right">
									<div class="dropdown">
										<a href="" class="btn btn-success">显示所有</a>
										<button class="btn btn-danger dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
											操作
											<span class="caret"></span>
										</button>
										<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
											<li><a href="MakeProcessPlan.html">导出成绩</a></li>											
										</ul>
									</div>
								</div>
							</div>
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr  class="text-primary" >
										<th class="text-center" >
											学号
										</th>
										<th class="text-center" >
											学生姓名
										</th>
										<th class="text-center" >
											小组编号
										</th>
										<th class="hidden" >
											题目编号
										</th>
										<th class="text-center" >
											设计题目
										</th>
										<th  class="text-center" >
											各阶段成绩详情
										</th>										
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${stuCdNames}" var="stuCdName">
										<tr>
											<td class="text-center">
												${stuCdName[0]}
											</td>
											<td class="text-center">
												${stuCdName[1]}
											</td>
											<td class="text-center">
												${stuCdName[2]}
											</td>
											<td class="hidden">
												${stuCdName[3]}
											</td>
											<td class="text-center">
												${stuCdName[4]}
											</td>
											<td class='text-center'>
												<label>
													<button  class="btn btn-info glyphicon glyphicon-edit">修改分数</button>
												</label>
											</td>
										</tr>
									</c:forEach>	
									<tr>
										<td colspan="5" class="info">
											<div class="pull-left">
												<nav>
													<ul class="pagination" style="margin:0;">
														<li><a href="#">首页</a></li>
														<li class="disabled"><a href="#">上一页</a></li>
														<li><a href="#">下一页</a></li>
														<li><a href="#">尾页</a></li>
													</ul>
												</nav>
											</div>
											<div class="pull-right">
												当前第{4}页 共{5}页 每页显示{6}条 共有{7}条
											</div>
										</td>
									</tr>
								</tbody>
							</table>

							<div class="modal fade" id="Udialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<form id="UForm" class="form-horizontal bv-form" action="StuGrade_upGradeScore.action">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
													&times;
												</button>
												<h3 class="control-label glyphicon glyphicon glyphicon-flag" for="inputSuccess1">
			                                   <a id="stuName" style="text-decoration:none;">学生姓名</a></h3>
												<h3 class="control-label glyphicon " for="inputSuccess1">同学</h3>
												<input type="hidden" name="stuId" id="stuId" />
												<br />
											</div>
											<!-- modal-header结束-->

											<div class="modal-body" style="overflow-y: scroll; height: 400px;">
												<div class="panel panel-warning">
												<div class="panel panel-heading">修改学生成绩</div>
												
												<div id="wqshow" class="panel panel-body">
									
								               </div>
							                </div>
												
												
											</div><!--modal-body结束  -->
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
						</div>
					</div>
					<!--  wq结束-->
				</div>
			</div></div>
	</body>

</html>