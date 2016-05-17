<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>审核学生题目</title>		
<script type="text/javascript" src="<%=basePath %>/UIRef/js/ExSelect.js"></script>
<!-- 引入分页的javacript类库 -->
<script type="text/javascript" src="UIRef/js/kkpager.min.js"></script>
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
		hrefFormer : 'teacher_FindExTopic',
		//链接尾部
		hrefLatter : '.action',
		getLink : function(n){
			return this.hrefFormer + this.hrefLatter + "?pno="+n+"&cdplanId="+$("#cdplanIdSelect").val();
		}		 				 
	});
	

	$("#cdplanIdSelect").click(function(){
		//alert($(this).val());
		$("#cdPlanId").val($(this).val());
	});
});
</script>
<!-- 引入分页的css样式 -->
<link rel="stylesheet" href="UIRef/css/kkpager_blue.css">
	</head>
	<body>	
        <jsp:include page="../UIRef/JspRef/StudentHeader.jsp"></jsp:include>
		<div class="container-fluid">
		<!-- left 导航 -->
			<jsp:include page="../UIRef/JspRef/TeacherleftInstructor.jsp"></jsp:include>
		<!-- left 导航end -->
			<!--主体内容             -->
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<!--面包屑导航-->
			<ol class="breadcrumb" style="width: 100%;">
				<li><a href="#">课程设计平台管理系统</a></li>
				<li><a href="#">设计题目管理</a></li>
				<li class="active">审核已选题目</li>
			</ol>
				<div class="container-fluid">
					<div class="row-fluid">
						<div class="span12">
							<br />
							<form role="form" class="form-inline" action="teacher_FindExTopic.action" method="post">

								<div class="form-group ">
									<label>
										选择课程设计计划名
									</label>
									<input type="hidden" value="<s:property value="cdplanId"/>" name="cdplanId" id="cdPlanId"/>
									<select class="form-control" id="cdplanIdSelect" >
								<c:forEach items="${Cdteachergroups}" var="Cdteachergroup" varStatus="varSta">
								<option value="${Cdteachergroup[0]}">${Cdteachergroup[2]}</option>
								</c:forEach>
								</select> 
									
									<button type="submit" class="btn btn-success">
										<span class="glyphicon glyphicon-search "></span>查询
									</button>
								</div>
							</form>
							<h4 class="text-center text-warning">学生自拟题目审核</h4>
							<table id="table1 " class="table table-hover table-striped table-hover table-bordered">
								<thead>
									<tr>
										<th>
											编号
										</th>
										<th>
											小组编号
										</th>
										<th>
											题目
										</th>									
										<th class="hidden">
											题目简介
										</th>
										<th style="text-align: center; ">
											操作
										</th>
									</tr>
								</thead>
								<tbody>
								 <c:forEach items="${pageBean.list}" var="result">
									<tr>
									    
										<td>
											${result[7]}
										</td>
										<td>
											${result[0]}
										</td>
										<td>
											${result[4]}
										</td>
										<td class="hidden">
											${result[5]}
										</td>										
										<td class="text-center ">
											<button name="shenH" class="btn btn-info"><span class="glyphicon glyphicon-edit"></span>审核</button>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
							<div id="kkpager"></div>	
							<!--
                            这里是审核模态框
                            -->
                            <div class="modal" id="ShenHe" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
													&times;
												</button>
												<h4 class="modal-title" id="myModalLabel">
													审核学生题目
												</h4>
											</div>
											<!-- modal-header结束-->
											<div class="modal-body">
												<ul id="myTab" class="nav nav-tabs">
												<li class="active"><a href="#home" data-toggle="tab">
														题目详情</a></li>
												<li><a href="#ios" data-toggle="tab">题目审核</a></li>
												</ul>
											    <div id="myTabContent" class="tab-content">
												<!--自己提交的过程材料--><!-- x -->
												   <div class="tab-pane fade in active" id="home">
													  <div class="form-group">
														<label for="cdTitleName" class="col-sm-2 control-label"><span class="glyphicon ">														
														</span>题目简介</label>
														<p class="text-info">
														<span class="glyphicon glyphicon-apple"></span>&nbsp;<a id="topicsDescribtion" style="text-decoration:none;"></a>
													    </p>
													  </div>
													  <div class="form-group">
														<label for="cdTitleName" class="col-sm-2 control-label"><span class="glyphicon ">														
														</span>组员信息</label>
													  </div>
														<table class="table table-striped">
													      <thead>
														  <tr class="text-primary">
															<th>序号</th>
															<th>学号</th>
															<th>姓名</th>
															<th>班级</th>
															<th>学院</th>
															<th>专业</th>
														  </tr>
													      </thead>
													    <tbody id="groupMembers">
														  
													</tbody>
												</table>
												</div>	<!-- x -->	
												<!--审核-->
												<div class="tab-pane fade" id="ios">
												<form id="UForm" class="form-horizontal bv-form" method="post" action="teacher_ShenHe.action">
												<br /> <input type="hidden" id="stugroupId" name="stugroupId"></input>
												<div class="form-group">
													<label class="col-lg-3 control-label text-danger">
														审核是否通过
													</label>
													<div class="col-lg-5">
													<select class="form-control" id="ifPass" name="ifPass">
														<option value="0">
															通过
														</option>
														<option value="1">
															不通过
														</option>														
													</select>
													</div>
												</div>
												<br />
												<div id="nopassdiv">
												<div class="clearfix">
										      <span class="pull-right"><button
												id="addNoPass" title="添加不通过原因" type="button" class="btn btn-success"
												style="font-size: 10px;">
												<span class="glyphicon glyphicon-plus"></span>
											</button> </span>
									        </div>
									       
									    			<table class="table table-striped">
													<thead>
														<tr class="text-primary">
															<th class="hidden">序号</th>
															<th>历史</th>	
															<th>操作</th>	
														</tr>
													</thead>
													<tbody id="groupNoPass">
													
													</tbody>
								      				</table>
								      				<br/><input type="hidden" id="isAdd" name="isAdd"/>
								      				<span class="pull-left text-info glyphicon glyphicon-remove-circle">不通过原因</span> <br/><br/>
													<input type="text" class='form-control' readonly placeholder="添加或选择不通过原因..." id="PassState" name="PassState"/>
													
													</div><span class="pull-right"><button class="btn btn-primary" type="submit">
													确认
												</button></span></form>	
												<br/>	<br/>			
												</div>
												<!---->
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">
													关闭
												</button>
												
											</div>
										
									</div>
								</div>
							</div>
                            <!--
                            	审核模态框结束
                            -->
						</div>
						<!-- span12结束 -->
					</div>
					
				</div>
				 
			</div>
			<!--主体内容 结束            -->
		</div>
	</body>


</html>