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
		<!--左边的指导老师导航部分-->
		<div class="col-sm-3 col-md-2 sidebar">
			<div class="row">
				<div class="panel-group" id="accordion">
					<div class="panel panel-danger">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="btn btn-block" data-toggle="collapse"
									data-parent="#accordion" href="#collapseOne"> <span
									class="glyphicon glyphicon-pushpin"></span>课程设计题目管理
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">
									<ul class="nav nav-stacked">
										<li><a href="${pageContext.request.contextPath}/teacher_viewCdplanName.action">
										<span class="glyphicon glyphicon-share-alt"></span>制定课程设计题目</a></li>
										<li><a href="${pageContext.request.contextPath}/teacher_viewCdplanNameExs.action"><span
												class="glyphicon glyphicon-share-alt"></span>审核学生已选题目</a></li>
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
									class="glyphicon glyphicon-edit"></span>过程考核计划管理
								</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">

									<ul class="nav nav-stacked">
										<li><a href="${pageContext.request.contextPath}/teacher_viewCdplanNameMakecdt.action"><span
												class="glyphicon glyphicon-share-alt"></span>查看过程考核计划</a></li>
										<li><a href="${pageContext.request.contextPath}/teacherMakeProPlan_viewCdplanNameExs.action"><span
												class="glyphicon glyphicon-share-alt"></span>添加过程考核计划</a></li>
										<li><a href="${pageContext.request.contextPath}/ViewPF_getCdplanName.action"><span
												class="glyphicon glyphicon-share-alt"></span>查阅过程材料</a></li>
									
									</ul>
								</div>
							</div>
						</div> 
					</div>
				
					<div class="panel panel-warning">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="btn btn-block" data-toggle="collapse"
									data-parent="#accordion" href="#collapseFive"> <span
									class=" glyphicon glyphicon-leaf"></span>学生成绩管理
								</a>
							</h4>
						</div>
						<div id="collapseFive" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">

									<ul class="nav nav-stacked">
										<li><a href="${pageContext.request.contextPath}/StuGrade_gradePName.action"><span
												class="glyphicon glyphicon-share-alt"></span>查看学生过程考核成绩</a></li>
									    <li><a href="${pageContext.request.contextPath}/Reply_replyPName.action"><span
												class="glyphicon glyphicon-share-alt"></span>答辩考核</a></li>
										<li><a href="${pageContext.request.contextPath}/StuSco_findAddScore.action"><span
													class="glyphicon glyphicon-share-alt"></span>添加学生成绩</a></li>
										<li><a href="${pageContext.request.contextPath}/StuSco_viewTotalScore.action"><span
													class="glyphicon glyphicon-share-alt"></span>查看学生成绩</a></li>
								
									</ul>
								</div>
							</div>
						</div>						
					</div>
										 				
					<div class="panel panel-success">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="btn btn-block" data-toggle="collapse"
									data-parent="#accordion" href="#collapseSix"> <span
									class=" glyphicon glyphicon-file"></span>组长管理模块
								</a>
							</h4>
						</div>
						<div id="collapseSix" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="row">
									<ul class="nav nav-stacked">
										<li><a
											href="cdTechGroupLeC_findAllNeedAllowCD.action"><span
												class="glyphicon glyphicon-share-alt"></span>添加我的课程设计小组成员</a></li>
										<li><a
											href="cdTechGroupLeC_findAllAllowedMem.action"><span
												class="glyphicon glyphicon-share-alt"></span>查看课程设计小组成员</a></li>
										<li><a
											href="cdTechGroupLeC_findStillEvalStand.action"><span
												class="glyphicon glyphicon-share-alt"></span>制定小组评分标准</a></li>
										<li><a
											href="cdTechGroupLeC_findAlladdedEvalStand.action"><span
												class="glyphicon glyphicon-share-alt"></span>查看小组评分标准</a></li>
										<li><a
											href="${pageContext.request.contextPath}/TeachGroupLeader/ReplyManage.jsp"><span
												class="glyphicon glyphicon-share-alt"></span>答辩计划管理</a></li>
										 
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