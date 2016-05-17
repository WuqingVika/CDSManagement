<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>添加课程设计题目</title>
<script type="text/javascript">
	$(function() {
		//$('#table2').hide();
		$('#showhas').click(function() {
			$("#table2").toggle();
		});
		$('#cDTeacherGroupId').change(function() {
			var n=$('#cDTeacherGroupId').val();
			alert(n);
		});
		$('#addCDTopic2').click(function(){
			var cDTeacherGroupId=$('#cDTeacherGroupId').val();
			var topics=$('#topics').val();
			var topicsDescribtion=$('#topicsDescribtion').val();
			var isSelfChoosed=$('#isSelfChoosed').val();
			//alert(cDTeacherGroupId+" "+topics+" "+topicsDescribtion+"  "+isSelfChoosed);
			if (confirm("您确定要添加吗？")) {
				window.location = "teacher_addCdTopic.action?cDTeacherGroupId="+cDTeacherGroupId
						+"&topics="+topics+"&topicsDescribtion="+topicsDescribtion
						+ "&isSelfChoosed"+isSelfChoosed;
		}
		});
		
	});
</script>
</head>

<body>
	<jsp:include page="../UIRef/JspRef/StudentHeader.jsp"></jsp:include>
	<!--主体部分-->
	<div class="container-fluid">
		<jsp:include page="../UIRef/JspRef/TeacherleftInstructor.jsp"></jsp:include>


		<!--主体内容-->
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<!--面包屑导航-->
			<ol class="breadcrumb" style="width: 100%;">
				<li><a href="#">课程设计平台管理系统</a></li>
				<li><a href="#">设计题目管理</a></li>
				<li class="active">制定课程设计题目</li>
			</ol>
			<div id="wqadd">
				<!--添加课程设计题目-->
				<form action="teacher_addCdTopic.action" method="post">
					<div class="panel panel-info">
						<div class="panel-heading">添加课程设计选题</div>
						<div class="panel-body">

							<div class="form-group">
								<h4 class="control-label" for="inputSuccess1">选择课程设计计划名</h4>
								<select class="form-control" id="cdteacherGroupId" name="cdteacherGroupId">
								<c:forEach items="${cdPlanNames}" var="cdPlanName" varStatus="varSta">
								<option value="${cdPlanName[1]}">${cdPlanName[2]}</option>
								</c:forEach>
								</select> <br />
								<h4 class="control-label" for="inputSuccess1">题目名称</h4>
								<input type="text" class="form-control" id="topics"
									name="topics" placeholder="请输入课程设计题目名称..." /> <br />
								<h4 class="control-label" for="inputSuccess1">题目介绍</h4>
								<textarea class="form-control" id="topicsDescribtion" name="topicsDescribtion"
									placeholder="请输入课程设计简介..."></textarea>
								<br />
								<h4 class="control-label" for="inputSuccess1">是否是自由选题</h4>
								<select class="form-control " id="isSelfChoosed" name="isSelfChoosed">
									<option value="1">--允许学生自由选择题目--</option>
									<option value="0">--不允许学生自由选择题目--</option>
								</select>
								<hr />
								<div class="text-center">
									<button type="submit" id="addCDTopic" class="btn btn-success" style="width: 200px;">
										<span class="glyphicon glyphicon-check"></span>添加
									</button>
								</div>
							</div>
						</div>
					</div>
				</form>
				<!--查看已经添加的题目-->
				<div class="panel panel-success">
					<div class="panel-heading">
						<div class="clearfix">
							<span class="pull-left"> <span
								class="glyphicon glyphicon-th-list"></span>已添加题目
							</span> <span class="pull-right">
								<button id="showhas" class="btn btn-primary">
									<span class="glyphicon glyphicon-transfer"></span>显示/隐藏
								</button>
							</span>
						</div>
					</div>
					<div class="panel-body">
						<table id="table2"
							class="table table-hover table-bordered table-striped">
							<thead>
								<tr class="text-primary">
									<th>编号</th>
									<th>题目名称</th>
									<th>命题方式</th>
									<th>最少人数</th>
									<th>最高人数</th>
									<th style="text-align: center;">操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td>吴庆</td>
									<td>01/04/2012</td>
									<td>Default</td>
									<td>Default</td>
									<td class="text-center">
										<button class="btn btn-info" data-toggle="modal"
											data-target="#edit">
											<span class="glyphicon glyphicon-edit"></span>审核
										</button>
										<button class="btn btn-warning">
											<span class="glyphicon glyphicon-edit"></span>详情
										</button>
									</td>
								</tr>
								<tr>
									<td>1</td>
									<td>吴庆</td>
									<td>01/04/2012</td>
									<td>Default</td>
									<td>Default</td>
									<td class="text-center">
										<button class="btn btn-info" data-toggle="modal"
											data-target="#edit">
											<span class="glyphicon glyphicon-edit"></span>审核
										</button>
										<button class="btn btn-warning">
											<span class="glyphicon glyphicon-edit"></span>详情
										</button>
									</td>
								</tr>
								<tr>
									<td>1</td>
									<td>吴庆</td>
									<td>01/04/2012</td>
									<td>Default</td>
									<td>Default</td>
									<td class="text-center">
										<button class="btn btn-info" data-toggle="modal"
											data-target="#edit">
											<span class="glyphicon glyphicon-edit"></span>审核
										</button>
										<button class="btn btn-warning">
											<span class="glyphicon glyphicon-edit"></span>详情
										</button>
									</td>
								</tr>
								<tr>
									<td>1</td>
									<td>吴庆</td>
									<td>01/04/2012</td>
									<td>Default</td>
									<td>Default</td>
									<td class="text-center">
										<button class="btn btn-info" data-toggle="modal"
											data-target="#edit">
											<span class="glyphicon glyphicon-edit"></span>审核
										</button>
										<button class="btn btn-warning">
											<span class="glyphicon glyphicon-edit"></span>详情
										</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!--wqadd结尾-->
		</div>
	</div>
</body>

</html>