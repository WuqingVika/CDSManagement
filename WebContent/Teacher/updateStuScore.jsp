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

<title>修改学生成绩</title>
<script type="text/javascript" src="<%=basePath%>/UIRef/js/updateStuScore.js"></script>
<script type="text/javascript" src="UIRef/js/kkpager.min.js"></script>

<!-- 引入分页的css样式 -->
<link rel="stylesheet" href="UIRef/css/kkpager_blue.css">
<script type="text/javascript">
//init
$(function(){
	//生成分页
	//有些参数是可选的，比如lang，若不传有默认值
	kkpager.generPageHtml({
		pno : ${pageBean.currentPage},
		//总页码
		total : ${pageBean.totalPage},
		//总数据条数
		totalRecords : ${pageBean.allRows},
		//链接前部
		hrefFormer : 'StuSco_viewTotalScore',
		//链接尾部
		hrefLatter : '.action',
		getLink : function(n){
			return this.hrefFormer + this.hrefLatter + "?pno="+n;
		}		 				 
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
				<li><a href="#">学生成绩管理</a></li>
				<li class="active">修改学生成绩</li>
			</ol>
			<!--主要内容-->
			<div class="panel panel-info">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-pencil">修改学生成绩</span>
				</div>
				<div class="panel-body">
					<!--  wq开始-->
					<div id="wqadd">
						<div class="panel-heading panel-info clearfix">
							<div class="pull-left">
								<form role="form" class="form-inline"
									action="StuSco_viewTotalScore.action" method="post">
									
								</form>
							</div>
							<div class="pull-right">
							</div>
						</div>
						<table class="table table-striped table-bordered">
							<thead>
								<tr class="text-primary">
								   <th>编号</th>
								   <th class="hidden">学生编号</th>
									<th>学号</th>
									<th>姓名</th>
									<th>课程设计名</th>
									<th class="hidden">题目编号</th>
									<th>设计题目</th>
									<th>自评成绩</th>
									<th>平时成绩</th>
									<th>考勤成绩</th>
									<th>答辩成绩</th>
									<th>总分</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${pageBean.list}" var="updateScore" >
							
									<tr>
										<td>${updateScore[0]}</td>
										<td class="hidden">${updateScore[1]}</td>
										<td>${updateScore[2]}</td>
										<td>${updateScore[3]}</td>
										<td>${updateScore[6]}</td>
										<td class="hidden">${updateScore[7]}</td>
										<td>${updateScore[8]}</td>
										<td>${updateScore[9]}</td>
										<td>${updateScore[11]}</td>
										<td>${updateScore[10]}</td>
										<td>${updateScore[13]}</td>
										<td>${updateScore[14]}</td>
										<td><label>
												<button class="btn btn-info">
													<span class="glyphicon glyphicon-edit"></span>修改
												</button>
										</label> </td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="13" class="info">
										<div id="kkpager"></div>
									</td>
								</tr>
							</tbody>
						</table>

						<div class="modal fade" id="Udialog" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<form id="UForm" class="form-horizontal bv-form" action="StuSco_updateLiSSc.action">
								<div class="modal-content">
									<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="myModalLabel">修改学生成绩</h4>
										</div>
										<!-- modal-header结束-->

										<div class="modal-body">

											<!--操作类型为 修改学生成绩   -->
											<input type="hidden" name="ssc.id" id="scoreId" />
											 <div class="panel panel-warning">
												<div class="panel panel-heading">修改学生成绩</div>
												<div  id="showSTandSco"></div>
												
												<div class='form-group'>
													<label for='firstname' class='col-sm-3 control-label'>平时成绩</label>
													<div class='col-sm-9'>
													<input type='text' class='form-control' id="examScore" name='ssc.examScore'
									                 placeholder="修改平时/材料成绩..." />
									                 </div>
								                 </div>
								                 <div class='form-group'>
													<label for='firstname' class='col-sm-3 control-label'>考勤成绩</label>
													<div class='col-sm-9'>
													<input type='text' class='form-control' id="attendanceScore"  name='ssc.attendanceScore'
									                 placeholder="修改考勤成绩..." />
									                 </div>
								                 </div>
								                 <div class='form-group'>
													<label for='firstname' class='col-sm-3 control-label'>答辩成绩</label>
													<div class='col-sm-9'>
													<input type='text' class='form-control' id="replyScore" name='ssc.replyScore'
									                 placeholder="修改答辩成绩..." />
									                 </div>
								                 </div>
								               </div>
							                </div>
											<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">关闭</button>
											<button class="btn btn-primary" type="submit">提交更改</button>
										    </div>
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