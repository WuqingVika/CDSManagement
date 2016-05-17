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
<title>查阅过程材料</title>
<script type="text/javascript" src="<%=basePath%>/UIRef/js/wqupdateVPF.js"></script>
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
				<li class="active">查阅过程材料</li>
			</ol>
				<!--主要内容-->
				<div class="panel panel-info">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-eye-open">查阅过程材料</span>
					</div>
					<div class="panel-body">
						<!--  wq开始-->
						<div id="wqadd">
							<div class="panel-heading panel-info clearfix">
								<div class=" pull-left">
									<form action="ViewPF_getPFile.action" method="post" class="form-inline">
										<input type="hidden" value="<s:property value="cdplanId"/>" name="cdplanId" id="cdplanId"/>
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
									<label>
										选择考核阶段
									</label>
									<input type="hidden" value="<s:property value="processAssShId"/>" name="processAssShId" id="processAssShId"/>
								
									<select class="form-control" id="processAssShIdSelect" >
									</select>
											<button type="submit" class="btn btn-success">
												查询
											</button>
										</div>
										<!--form group结尾-->
									</form>
								</div>

							</div>
							<!---->

							<div class="panel-body">
								<table class="table table-bordered table-hover">
									<thead>
										<tr class="text-primary">
											<th class="text-center">
												小组编号
											</th>
											<th class="text-center">
												编号
											</th>
											<th class="text-center">
												设计题目名称
											</th>
											<th class="text-center">
												阶段
											</th>
											
											<th colspan="2" class="text-center">
												操作
											</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${results}" var="result">
											<tr>
												<td class=text-center>
													${result[0]}
												</td>
												<td class=text-center>
													${result[2]}
												</td>
												<td class=text-center>
													${result[1]}
												</td>
												<td class=text-center>
													${result[7]}
												</td>											
												<td class="text-center">
													<span>
														<button class="btn btn-success center">
															<span class="glyphicon glyphicon-download-alt"></span>查阅
														</button>
													</span>
												</td>
												<td class="text-center">
													<label>
														<button name="btnPigai" class="btn btn-info center">
															<span class="glyphicon glyphicon-edit"></span>打分
														</button>
													</label>
												</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
										</tr>
									</tfoot>
								</table>
							</div>
							<!--表格外面一层-->
								<!-- 模态框（学生资料下载 -->
							<div class="modal fade" id="CYdialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<form id="AForm" class="form-horizontal bv-form" action="" method="post">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
													&times;
												</button>
											<h4 class="modal-title glyphicon glyphicon-download-alt" id="myModalLabel">
									                      学生资料下载
								           </h4>
											</div>
											<!-- modal-header结束-->

											<div class="modal-body" style="overflow-y: height: 400px;">

												<h3 class="control-label glyphicon glyphicon glyphicon-flag" for="inputSuccess1">
												<a id="topics" style="text-decoration:none;">课程设计题目名称</a></h3>
												<h5 class="control-label" for="inputSuccess1">
												<a id="ProcessName" style="text-decoration:none;">过程材料阶段名称</a></h5>
												<br />
												<div class="panel-body">
													<table class="table table-bordered table-hover" style="background-color:#C1E2B3;">
														<thead>
															<tr class=" text-info success ">
																<th class="hidden">
																	processDocId文档编号
																</th>
																<th class="text-center">
																	学生姓名
																</th>																
																<th class="text-center">
																	角色
																</th>
																<th class="text-center">
																	考核材料名称
																</th>
																<th class="hidden">
																	附件信息
																</th>
																<th class="text-center">
																	上传时间
																</th>
																<th class="text-center">
																	是否查阅
																</th>
																<th class="text-center">
																	操作
																</th>
																
															</tr>
														</thead>
														<tbody id="pigaiTbody">
															
														</tbody>
														<tfoot>
															<tr>
																<td colspan="8" class="info">

																</td>
															</tr>
														</tfoot>
													</table>
												</div>

											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">
													关闭
												</button>
												
											</div>
										</form>
									</div>
								</div>
							</div><!--模结束 -->
									<!-- 模态框（评语分数） -->
							<div class="modal fade" id="DFdialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<form id="AForm" class="form-horizontal bv-form" action="ViewPF_upListScore.action" method="post">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
													&times;
												</button>
												<h4 class="modal-title glyphicon glyphicon-edit" id="myModalLabel">
									               提交学生分数情况
								           </h4>
											</div>
											<!-- modal-header结束-->

											<div class="modal-body" style="overflow-y: scroll; height: 400px;">

												<h3 class="control-label glyphicon glyphicon glyphicon-flag" for="inputSuccess1">
												<a id="topics2" style="text-decoration:none;">课程设计题目名称</a></h3>
												<h5 class="control-label " for="inputSuccess1">
												<a id="ProcessName2" style="text-decoration:none;">过程材料阶段名称</a></h5>

												<br />

												<div class="panel-body">
													<table class="table table-bordered table-hover" style="background-color:#C1E2B3;">
														<thead>
															<tr class=" text-info success ">
																<th class="hidden">
																	设计课题编号
																</th>
																<th class="text-center">
																	学生姓名
																</th>													
																<th class="text-center">
																	状态
																</th>
																<th class="text-center">
																	考核成绩
																</th>
																<th class="text-center">
																	考核评语
																</th>
																<th class="text-center">
																	操作
																</th>
															</tr>
														</thead>
														<tbody id="DaFenTbody">
															<tr>
																<td class="hidden">
																	1
																</td>
																<td>张萌</td>
																<td>组长</td>
																<td>
																	<input id="text" style="width: 50px;" value="80" />
																</td>
																<td>
																	<input id="text" style="width: 200px;" value="这一阶段不错" />
																</td>
																
														<td class="text-center">
															<label>
																<button name="wqssg" class="btn btn-info"><span class="glyphicon glyphicon-edit"></span>修改成绩</button>
															</label>
												        </td>
																
															</tr>
														</tbody>
														<tfoot>
															<tr>
																<td colspan="6" class="info">

																</td>
															</tr>
														</tfoot>
													</table>
												</div>

											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">
													关闭
												</button>
												<button type="submit" id="SureEdit" class="btn btn-success">
													提交学生成绩
												</button>
											</div>
										</form>
									</div>
								</div>
							</div><!--模结束 -->
					
						</div>
						<!--wqadd结束-->
					</div>
				</div>
			</div>
		</div>
	</body>

</html>