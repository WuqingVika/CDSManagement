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

<title>查看过程考核计划</title>
<script type="text/javascript" src="<%=basePath%>/UIRef/js/wqupdate.js"></script>
<link rel="stylesheet" href="<%=basePath%>/UIRef/css/datapicker/default.css" />
<script type="text/javascript" src="<%=basePath%>/UIRef/js/zebra_datepicker.js"></script>
<!-- 引入分页的javacript类库 -->
<script type="text/javascript"
	src="<%=basePath%>/UIRef/js/kkpager.min.js"></script>
<!-- 引入分页的css样式 -->
<link rel="stylesheet" href="<%=basePath%>/UIRef/css/kkpager_blue.css">
<script type="text/javascript">
		//init
		$(function(){
			//生成分页
			//有些参数是可选的，比如lang，若不传有默认值
			kkpager.generPageHtml({
				pno : ${results.currentPage},
				//总页码
				total : ${results.totalPage},
				//总数据条数
				totalRecords : ${results.allRows},
				//链接前部
				hrefFormer : 'teacherMakeProPlan_viewProcessPlan',
				//链接尾部
				hrefLatter : '.action',
				getLink : function(n){
					return this.hrefFormer + this.hrefLatter + "?pno="+n;
				}		 				 
			});
			
			$("#cdplanIdSelect").click(function(){
				$("#cdPlanId").val($(this).val());
			});
			//$("#cdPlanId").val("-1");
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
				<li class="active">查看过程考核计划</li>
			</ol>
			<!--主要内容-->
			<div class="panel panel-info">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-pencil">制定过程考核计划</span>
				</div>
				<div class="panel-body">
					<!--  wq开始-->
					<div id="wqadd">
						<div class="panel-heading panel-info clearfix">
							<div class="pull-left">
								<form role="form" class="form-inline"
									action="teacherMakeProPlan_viewProcessPlan.action" method="post">
									<input type="hidden" value="<s:property value="cdplanId"/>" name="cdplanId" id="cdPlanId"/>
									<div class="form-group ">
										<label> 选择课程设计计划名 </label> 
										<select class="form-control" id="cdplanIdSelect">
											<option value="0">查询全部</option>
											<c:forEach items="${Cdteachergroups}" var="Cdteachergroup"
												varStatus="varSta">
												<option value="${Cdteachergroup[0]}">${Cdteachergroup[2]}</option>
											</c:forEach>
										</select>
										<button type="submit" class="btn btn-success">查询</button>

									</div>
								</form>
							</div>
							<div class="pull-right">

								<div class="dropdown">
									
									<button class="btn btn-danger dropdown-toggle" type="button"
										id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="true">
										操作 <span class="caret"></span>
									</button>
									<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
										<li><a href="teacherMakeProPlan_viewCdplanNameExs.action">新增考核计划</a></li>
										<li class="divider"></li>
										<li><a href="#">考核计划导出</a></li>

									</ul>
								</div>

							</div>
						</div>
						<table class="table table-striped table-bordered">
							<thead>
								<tr class="text-primary">
									<th>编号</th>
									<th>课程设计计划名</th>
									<th>过程考核名称</th>
									<th>过程考核详细</th>
									<th>开始时间</th>
									<th>结束时间</th>

									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${results.list}" var="result">
									<tr>
										<td>${result[2]}</td>
										<td>${result[1]}</td>
										<td>${result[7]}</td>
										<td>${result[8]}</td>
										<td>${result[5]}</td>
										<td>${result[6]}</td>
										<td><label>
												<button class="btn btn-info">
													<span class="glyphicon glyphicon-edit"></span>修改
												</button>
										</label> <span>
												<button name="deletePlan"
													class="btn btn-danger glyphicon glyphicon-remove">
													删除</button>
										</span></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="7" class="info">
										<div id="kkpager"></div>
									</td>
								</tr>
							</tbody>
						</table>

						<div class="modal fade" id="Udialog" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<form id="UForm" class="form-horizontal bv-form" action="teacherMakeProPlan_updateProPlan.action">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="myModalLabel">修改过程考核计划</h4>
										</div>
										<!-- modal-header结束-->

										<div class="modal-body"
											style="overflow-y: scroll; height: 400px;">

											<!--操作类型为 修改  -->
											<input type="hidden" name="pcd.processAssShId" id="processAssShId" />

											<br />
											<div class="form-group">
												<label class="col-lg-3 control-label"> 过程考核名称 </label>
												<div class="col-lg-5">
													<input type="text" class="form-control" name="pcd.ProcessName"
														id="ProcessName" />
												</div>
											</div>
											<br />
											<div class="form-group ">
												<label class="col-lg-3 control-label"> 开始时间 </label>
												<div class="col-lg-5">
													<input type="text" class="form-control" name="pcd.startTime"
														id="startTime" style="width: 200px;" />
												</div>
											</div>
											<div class="form-group ">
												<label class="col-lg-3 control-label"> 结束时间 </label>
												<div class="col-lg-5">
													<input type="text" class="form-control" name="pcd.endTime"
														id="endTime" style="width: 200px;" />
												</div>
											</div>

											<div class="form-group">
												<label class="col-lg-3 control-label"> 过程详细 </label>
												<div class="col-lg-5">
													<textarea class="form-control" name="pcd.ProcessDescribtion"
														style="width: 300px; height: 200px;" id="ProcessDescribtion"></textarea>
												</div>
											</div>

										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">关闭</button>
											<button class="btn btn-primary" type="submit">提交更改</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!--wq end-->

				</div>
			</div>
		</div>
	</div>
</body>

</html>