<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>分配课程设计小组组长</title>
<script>
	$(function() {
		//指定组长的时候提交AJAX，获得教师数据
		$(".assignLeaderBtn").click(
			function() {
				//找到thead
				var $tbody = $("#teacherContent");
				//找到选中的这个课程计划编号和名称
				var cdplanId = $(this).parents("tr").find("td").eq(1).text();
				var cdplanName = $(this).parents("tr").find("td").eq(3).text();							
				//先清空里面的教师，防止重复添加							
				$("#teacherContent tr").remove();
					$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath }/cdplanleader_getTeachers.action",
						data : {
						},
						success : function(backData,textStatus, ajax) {
							var obj = JSON.parse(backData);
							for (var i = 0; i < obj.length; i++) {
								//obj[i][1]就是每个教师的工号，obj[i][2]就是每个教师的姓名
								//构造tr，并附加在表格中
								var appendHtml = "<tr><td>"
									+ obj[i][1]
									+ "</td><td>"
									+ obj[i][2]
									+ "</td><td class='text-center'><button class='btn btn-success selectItem'><span class='glyphicon glyphicon-ok-sign'></span>选择</button></td></tr>";
								//找到thead并添加
								$tbody.append(appendHtml);
							}
							//有了元素后，再添加每个教师后面按钮的单击事件，否则放在别的地方没有效果，因为当时按钮还是没有的
							//模态框中的jQuery
							var id;
							//每次先清空选中的教师
							$("#result").empty();
							$(".selectItem").click(function() {
								var $tr = $(this).parents("tr").find("td");
								id = $tr.eq(0).text(); //将id提升一下，以便后面访问到
								var name = $tr.eq(1).text();
								var str = "<p>您选择了  &nbsp;<em>工号:</em><span class='text-primary' id='selectedId'>"
									+ id
									+ "</span>&nbsp;&nbsp;<em>姓名:</em><span class='text-primary' id='selectedName'>"
									+ name
									+ "</span>&nbsp;作为"																		
									+ "<span class='text-primary' id='cdplanId'>"
									+ cdplanName + "</span>"
									+"课程设计小组"									
									+"组长&nbsp;&nbsp;<button class='btn btn-danger' id='deleteSelected'><span class='glyphicon glyphicon-remove-sign'></span>删除</button></p>";
								$("button.selectItem").attr("disabled","disabled");
								$("#result").empty().append(str);
								$("#deleteSelected").click(function() {
									$("button.selectItem").removeAttr("disabled");
									$(this).parents("p").remove();
									id = undefined;//再设置undefined
								});
							});
							//成功之后点击确定提交按钮的话，就进行提交。在这里进行提交，就不用重新获取那些数据了
							//注意：这里不使用jQuery的点击事件，因为没弹出一个模态框，就会追加事件。
							document.getElementById("realAssignLeaderBtn").onclick = function(){
								//进行数据的提交。就是提交教师工号和课程计划编号
								if(id == undefined){
									alert("请指定教师！");
								}else{
									window.location.href="cdplanleader_addLeaderTeacher.action?teacher.teacherWorkId="+ id +"&cdplan.cdplanId="+cdplanId;
								}
							};
						}
					});
				});
		//删除该课程计划的组长
		$(".deleteLeader").click(function(){
			//获得这行的课程计划编号
			var cdplanId = $(this).parents("tr").find("td").eq(1).text();
			//提交AJAX
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath }/cdplanleader_deleteLeaderTeacher.action",
				data : {
					"cdplan.cdplanId" : cdplanId
				},
				success : function(backData,textStatus, ajax) {
					//根据返回值来判断弹出的模态框
					if(backData == 0){
						$("#cannotDelete").modal();
					}
					if(backData == 1){
						$("#alreadyDelete").modal();
					}
				}
			});
		});
	});
</script>
<%@include file="../UIRef/JspRef/MainFrameworkNav.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<ol class="breadcrumb" style="width: 100%;">
		<li><a href="#">课程设计平台管理系统</a></li>
		<li class="active">添加课程设计群</li>
	</ol>
	<div class="panel panel-info">
		<div class="panel-heading">
			<div class="clearfix">
				<span class="glyphicon glyphicon-chevron-right"></span>指定组长
			</div>
		</div>
		账号：<b>${teacherDirectorSession.account.accountId }</b>
		  &nbsp;&nbsp;
		姓名：<b>${teacherDirectorSession.teacherName }(教研室主任) </b>
		 &nbsp;&nbsp;
		操作学院：<b>${teacherDirectorSession.college.collegeName } </b>
		<div class="panel-body">
			<div class="row">
				<table class="table table-hover table-bordered">
					<thead>
						<tr class="text-primary">
							<th class="text-center">序号</th>
							<th class="text-center">课程计划编号</th>
							<th class="text-center">课程计划名称</th>
							<th class="text-center">总学分</th>
							<th class="text-center">总学时</th>
							<th class="text-center">小组组长</th>
							<th class="text-center">专业</th>
							<th class="text-center">学期</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cdplans }" var="cdplan" varStatus="varSta">
							<tr>
								<td>${varSta.count }</td>
								<td style="display: none">${cdplan.cdplanId }</td>
								<td>${cdplan.cdplanNum }</td>
								<td>${cdplan.cdplanName }</td>
								<td>${cdplan.totalCredits }</td>
								<td>${cdplan.totalClassHour }</td>
								<td><c:choose>
										<c:when test="${fn:length(cdplan.cdteachergroups) > 0}">
											<c:forEach items="${cdplan.cdteachergroups}" begin="0"
												end="0" var="teach">
												<b>${teach.teacher.teacherName } </b>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<font color="red">暂未指定组长</font>
										</c:otherwise>
									</c:choose></td>
								<td>${cdplan.major.majorName }</td>
								<td>${cdplan.term.termName }</td>
								<td class="text-center"><c:choose>
										<c:when test="${fn:length(cdplan.cdteachergroups) > 0}">
											<button class="btn btn-danger deleteLeader">
												<span class="glyphicon glyphicon-remove"></span>删除组长
											</button>
										</c:when>
										<c:otherwise>
											<button class="btn btn-primary assignLeaderBtn"
												data-toggle="modal" data-target="#myModal">
												<span class="glyphicon glyphicon-tag"></span>指定组长
											</button>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!--下面是模态框部分-->
	<!-- 指定组长的部分 -->
	<div class="modal fade bs-example-modal-lg" id="myModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">指定小组组长</h4>
				</div>
				<div class="modal-body">
					<div class="panel panel-info">
						<div class="panel-heading">
							<span class="glyphicon glyphicon-search"></span>制定课程设计计划组长
						</div>
						<div class="panel-body">
							<div class="content">
								<table class="table table-hover table-bordered table-striped">
									<thead id="addTeachersTHead">
										<tr class="text-warning">
											<th>工号</th>
											<th>姓名</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="teacherContent">
										<!-- 这里的教师由AJAX添加 -->
									</tbody>
								</table>
							</div>
							<hr />
							<div>
								<p>
									<em>你的选择是：</em>
								</p>
								<div id="result"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" id="realAssignLeaderBtn" class="btn btn-primary">提交更改</button>
				</div>
			</div>
		</div>
	</div>

	<!--已经删除的模态框-->
	<div class="modal fade" id="alreadyDelete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">确认</h4>
				</div>
				<div class="modal-body">
					<h5 class="text-danger">
						<span class="glyphicon glyphicon-info-sign">&nbsp;已经成功删除该组组长</span>
					</h5>
				</div>
				<div class="modal-footer">
					<a type="button" class="btn btn-danger" href="">
						<span class="glyphicon glyphicon-remove-sign"></span>确定
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

	<!--不能删除的模态框-->
	<div class="modal fade" id="cannotDelete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">确认</h4>
				</div>
				<div class="modal-body">
					<h5 class="text-danger">
						<span class="glyphicon glyphicon-info-sign">&nbsp;
						<font color="red">该组课程设计已经开始，不能删除！</font>
						</span>
					</h5>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove-sign"></span>确定
					</button>
				</div>
			</div>
		</div>
	</div>
<%@include file="../UIRef/JspRef/MainFrameworkFoot.jsp"%>