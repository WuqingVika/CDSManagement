<%@page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>教师信息管理</title>
<script>
	$(function() {
		//分页显示
		kkpager.generPageHtml({
			pno : ${pageBean.currentPage },
			//总页码
			total : ${pageBean.totalPage},
			//总数据条数
			totalRecords : ${pageBean.allRows},
			//链接前部
			hrefFormer : 'teacherinfo_index',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}
		});
		//修改
		$("button.modify").click(function() {
			var tid = $(this).parents("tr").find("td").eq(1).text();
			var id = $(this).parents("tr").find("td").eq(3).text();
			var name = $(this).parents("tr").find("td").eq(4).text();
			$("#teacherId").val(id);
			$("#teacherName").val(name);
			$("#updateId").val(tid);
			//点击修改按钮获得所有的学院以便选择，这个就提交到教师的方法即可
			//先清空专业，第一项除外
			$("#addColleges option:gt(0)").remove();
			//先进行AJAX提交，获得所有的专业
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/teacherinfo_getCollege.action",
				data: {
					
				},
				success: function(backData, textStatus, ajax){
					var obj = JSON.parse(backData);
					$(obj.collegeName).each(function(){
						//拿到下拉列表框
						$("#addColleges").append($("<option>" + this + "</option>"));
					});
				}
			});
		});
		//更改按钮提交
		$("#updateTeacherBtn").click(function(){
			//先进行判断再进行提交
			if($("#teacherId").val().trim() == ""){
				alert("请输入教师工号");
			}else if($("#teacherName").val().trim() == ""){
				alert("请输入教师姓名");
			}else if($("#addColleges").val() == -1){
				alert("请选择教师所在学院");
			}else{
				$("#updateTeacherForm").submit();
			}
		});
		//添加教师按钮(真实)开始提交
		$("#addTeacherRealBtn").click(function(){
			$("#addTeacherForm").submit();
		});
		//导入excel
		$("#uploadFileBtn").click(function(){
			//判断是否选择了文件
			if($("#uploadFile").val() == ""){
				alert("请选择文件")
			}else{
				$("#uploadFileForm").submit();
			}
		});
		//点击删除按钮提交请求，查看教师能否删除
		$("button.delete").click(function(){
			//获得这行数据的第二列(教师编号，该列在表格上是不可见的)
			var teacherId = $(this).parents("tr").find("td").eq(1).text();
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/teacherinfo_delete.action",
				data: {
					'teacher.teacherId': teacherId
				},
				success: function(backData, textStatus, ajax){
					if(backData == 1){
						$("#alreadyDelete").modal();
					}
					if(backData == 0){
						$("#cannotDelete").modal();
					}
				}
			});
		});
	});
</script>
<%@include file="../UIRef/JspRef/MainFrameworkNav.jsp"%>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="panel panel-info">
		<div class="panel-heading">
			<span class="glyphicon glyphicon-education"></span>教师信息管理
		</div>
		<div class="panel-body">
			<div class="clearfix">
				 
				<div class="pull-left">
					账号：<b>${teacherDirectorSession.account.accountId } </b>
					  &nbsp;&nbsp;
					姓名：<b> ${teacherDirectorSession.teacherName }(教研室主任) </b>
					 &nbsp;&nbsp;
					操作学院：<b>${teacherDirectorSession.college.collegeName } </b>
				</div>
				<div class="pull-right">
					<button class="btn btn-warning" data-toggle="modal"	data-target="#uploadFileModal">
						<span class="glyphicon glyphicon-log-in"></span>导入数据
					</button>
					<a class="btn btn-success" href="${pageContext.request.contextPath }/teacherinfo_export.action">
						<span class="glyphicon glyphicon-log-out"></span>导出为excel
					</a>
					<button class="btn btn-info" data-toggle="modal" data-target="#addTeacherModal">
						<span class="glyphicon glyphicon-plus"></span>添加教师
					</button>
				</div>
			</div>
			<div>
				<div class="well well-sm" style="border: none; background: none;">

				</div>
				<table class="table table-hover table-striped table-bordered">
					<thead>
						<tr class="text-warning">
							<th class="text-center">序号</th>
							<th class="text-center">登录账号</th>
							<th class="text-center">工号</th>
							<th class="text-center">教师姓名</th>
							<th class="text-center">所在学院</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageBean.list}" var="teacher" varStatus="varSta">
							<tr>
								<td>${varSta.count }</td>
								<td  style="display:none">${teacher[0] }</td>
								<td>${teacher[1] }</td>
								<td>${teacher[2] }</td>
								<td>${teacher[3] }</td>
								<td>${teacherDirectorSession.college.collegeName  }</td>
								<td class="text-center">
									<button class="btn btn-primary modify" data-toggle="modal"
										data-target="#myModal">
										<span class="glyphicon glyphicon-repeat"></span>修改
									</button>&nbsp;&nbsp;
									<button class="btn btn-danger delete">
										<span class="glyphicon glyphicon-remove-sign"></span>删除
									</button>
								</td>
							</tr>							
						</c:forEach>
					</tbody>
				</table>
				<c:set var="teacherCounts" value="${fn:length(pageBean.list) }"></c:set>
				<c:if test="${teacherCounts == 0 }">
					<h3>没有教师记录！</h3>
				</c:if>
				<div id="kkpager"></div>
			</div>
		</div>
	</div>

	<!--下面是模态框部分-->
	<!-- 修改教师的部分 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">修改信息</h4>
				</div>
				<div class="modal-body">
					<form id="updateTeacherForm" action="teacherinfo_update.action" class="form-horizontal" role="form">
						<div class="form-group">
							<label for="firstname" class="col-sm-2 control-label">教师工号</label>
							<div class="col-sm-10">
								<input id="updateId" name="teacher.teacherId" style="display:none" type="text" />
								<input type="text" name="teacher.teacherWorkId" class="form-control" id="teacherId"
									placeholder="请输入教师工号">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="teacher.teacherName" id="teacherName"
									placeholder="请输入教师姓名">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">选择学院</label>
							<div class="col-sm-10">
								<select id="addColleges" name="collegeName" class="form-control">
									<option value="-1">--请选择学院--</option>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" id="updateTeacherBtn" class="btn btn-primary">提交更改</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 添加教师的模态框 -->
	<div class="modal fade" id="addTeacherModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加教师</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" action="teacherinfo_add.action" id="addTeacherForm" role="form">
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">登录账号</label>
							<div class="col-sm-10">
								<input type="text" id="teacherLoginId" name="account.accountId" class="form-control" placeholder="请输入教师登录账号">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">教师工号</label>
							<div class="col-sm-10">
								<input type="text" id="teacherWorkId2" name="teacher.teacherWorkId" required class="form-control" placeholder="请输入教师工号">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">教师名称</label>
							<div class="col-sm-10">
								<input type="text" id="teacherName2" name="teacher.teacherName" class="form-control" placeholder="请输入教师名称">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" id="addTeacherRealBtn" class="btn btn-primary">添加教师</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 上传文件导入excel-->
	<div class="modal fade" id="uploadFileModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加教师</h4>
				</div>
				<div class="modal-body">
					<form id="uploadFileForm" action="${pageContext.request.contextPath }/teacherinfo_import" enctype="multipart/form-data" class="form-horizontal" method="post" role="form">
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">选择要上传的文件</label>
							<div class="col-sm-10">
								<input id="uploadFile" name="uploadFile" id="uploadFile" type="file" class="form-control">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="uploadFileBtn" class="btn btn-danger">
						<span class="glyphicon glyphicon-remove-sign"></span>确定导入
					</button>
					<button type="button" class="btn btn-info" data-dismiss="modal">
						<span class="glyphicon glyphicon-share-alt"></span>取消
					</button>
				</div>
			</div>
		</div>
	</div>

 	<!-- 已经删除的模态框-->
	<div class="modal fade" id="alreadyDelete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">提示</h4>
				</div>
				<div class="modal-body">
					<h5>
						已经成功删除了该教师及其账户信息！
					</h5>
				</div>
				<div class="modal-footer">
					<a class="btn btn-danger" href="teacherinfo_index.action">
						<span class="glyphicon glyphicon-remove-sign" id="deleteFresh"></span>确定
					</a>
				</div>
			</div>
		</div>
	</div>
	
 <!-- 不能删除的模态框-->
	<div class="modal fade" id="cannotDelete" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">提示</h4>
				</div>
				<div class="modal-body">
					<h5>
						<font color="red">该教师有相关的课程计划小组数据，无法删除！</font>
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
</div>
<%@include file="../UIRef/JspRef/MainFrameworkFoot.jsp"%>
