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

<title>添加学生成绩</title>
<script type="text/javascript" src="<%=basePath%>/UIRef/js/addStuScore.js"></script>
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
		hrefFormer : 'StuSco_findAddScore',
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
				<li class="active">添加学生成绩</li>
			</ol>
			<!--主要内容-->
			<div class="panel panel-info">
				<div class="panel-heading">
					<span class="glyphicon glyphicon-pencil">添加学生成绩</span>
				</div>
				<div class="panel-body">
					<!--  wq开始-->
					<div id="wqadd">
						<div class="panel-heading panel-info clearfix">
							<div class="pull-left">
								<form role="form" class="form-inline"
									action="StuSco_findAddScore.action" method="post">
									<div class="form-group ">
										<label> 学生编号 </label> 
										<input type="text"  name="cdplanId" id="cdPlanId"/>
										<button type="submit" class="btn btn-success">查询</button>
									</div>
								</form>
							</div>
							<div class="pull-right">
							</div>
						</div>
						<table class="table table-striped table-bordered">
							<thead>
								<tr class="text-primary">
									<th>编号</th>
									<th class="hidden">老师小组编号</th>
									<th>学号</th>
									<th>姓名</th>
									<th class="hidden">课程设计ID</th>
									<th>课程设计名</th>
									<th class="hidden">题目编号</th>
									<th>题目</th>
									<th>自评成绩</th>
									<th class="hidden">学生小组编号</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${pageBean.list}" var="addScore" >
									<tr>
									    <td>${addScore[0]}</td>
									    <td class="hidden">${addScore[1]}</td>
										<td>${addScore[4]}</td>
										<td>${addScore[2]}</td>
										<td class="hidden">${addScore[5]}</td>
										<td>${addScore[6]}</td>
										<td class="hidden">${addScore[7]}</td>
										<td>${addScore[8]}</td>
										<td>${addScore[9]}</td>
										<td class="hidden">${addScore[3]}</td>
										<td><label>
												<button class="btn btn-info">
													<span class="glyphicon glyphicon-save-file"></span>添加学生成绩
												</button>
										</label> </td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="9" class="info">
										<div id="kkpager"></div>
									</td>
								</tr>
							</tbody>
						</table>

						<div class="modal fade" id="Udialog" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<form id="UForm" class="form-horizontal bv-form" action="StuSco_addListScore.action">
								<div class="modal-content">
									<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="myModalLabel">添加学生成绩</h4>
										</div>
										<!-- modal-header结束-->

										<div class="modal-body">
											<h3 class="control-label glyphicon glyphicon-user" for="inputSuccess1">
			                                   <a id="stuName" style="text-decoration:none;">学生姓名</a></h3>
												<h3 class="control-label glyphicon " for="inputSuccess1">同学</h3>
											<!--操作类型为 添加学生成绩   -->
											<input type="hidden" name="stuId" id="stuId" />
											<input type="hidden" name="cdplanId" id="cdplanId" />
											<input type="hidden" name="cddesignTopId" id="cddesignTopId" />
											<input type="hidden" name="cdteachergroupId" id="cdteachergroupId" />
											<input type="hidden" name="stugroupid" id="stugroupid" />
											<input type="hidden" name="ssc.selEvScore" id="selfScore" />
											
											<div class="panel panel-warning">
												<div class="panel panel-heading">平时材料情况</div>
												
												
												<div id="wqshowFile" class="panel panel-body">
												
								               </div>
							                </div><br/>
							                <div class="panel panel-warning">
												<div class="panel panel-heading">答辩情况</div>
												
												<div id="wqshowReply" class="panel panel-body">
												
								               </div>
							                </div>
											 <div class="panel panel-info">
											 <div class="pull-right"><button class="btn btn-info" type="button" id="showscoreSt"><span class="glyphicon glyphicon-eye-open"></span>查看评分标准</button></div>
												
												<div class="panel panel-heading">添加分数</div>
												<div  id="showSTandSco"></div>
												<div class='form-group'>
													<label for='firstname' class='col-sm-3 control-label'>平时成绩</label>
													<div class='col-sm-9'>
													<input type='text' class='form-control' name='ssc.examScore'
									                 placeholder="平时/材料成绩..." />
									                 </div>
								                 </div>
								                 <div class='form-group'>
													<label for='firstname' class='col-sm-3 control-label'>考勤成绩</label>
													<div class='col-sm-9'>
													<input type='text' class='form-control' name='ssc.attendanceScore'
									                 placeholder="请输入考勤成绩..." />
									                 </div>
								                 </div>
								                 <div class='form-group'>
													<label for='firstname' class='col-sm-3 control-label'>答辩成绩</label>
													<div class='col-sm-9'>
													<input type='text' class='form-control' name='ssc.replyScore'
									                 placeholder="请输入答辩成绩..." />
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
						
						<!-- 查看评分标准 -->
							<div class="modal fade" id="Stdialog" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-hidden="true">&times;</button>
											<h4 class="modal-title" id="myModalLabel">查看评分标准</h4>
										</div>
										<!-- modal-header结束-->

										<div class="modal-body" style="height:300px;">
											<div class="panel panel-warning">
												<div class="panel panel-heading">评分标准</div>
												<div id="standScore" class="panel panel-body">
												
								               </div>
							                </div>
								
							                </div>
											<div class="modal-footer">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">关闭</button>
											
										    </div>
										</div>
									
								</div>
							</div>
							<!-- 查看评分标准结束 -->
					</div>
					<!--wq end-->

				</div>
			</div>
		</div>
	</div>
</body>

</html>