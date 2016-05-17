<%@page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>班级信息管理</title>
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
			hrefFormer : 'classinfo_index',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}
		});
		//删除
		$("button.delete").click(function() {

		});
		//点击修改按钮，获得所有的专业，以便修改专业
		$("button.modify").click(function() {
			//先清空专业，第一项除外
			$("#addMajors option:gt(0)").remove();
			//先进行AJAX提交，获得所有的专业
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/classinfo_getMajor.action",
				data: {
					
				},
				success: function(backData, textStatus, ajax){
					var obj = JSON.parse(backData);
					for(var i = 0;i<obj.length;i++){
						var majorName = obj[i][2]; //拿到每个学院的名称
						//拿到下拉列表框
						$("#addMajors").append($("<option>" + majorName + "</option>"));
					}
				}
			});
			var id = $(this).parents("tr").find("td").eq(1).text();
			var name = $(this).parents("tr").find("td").eq(2).text();
			$("#updateId").val(id);
			$("#className").val(name);
		});
		//点击确定修改按钮，先要进行判断，再确定是否提交
		$("#changeClassBtn").click(function(){
			//先进行判断
			if($("#className").val().trim() == ''){
				alert("班级名称不能为空！");
			}else if($("#addMajors").val() == -1){
				alert("请选择专业！");
			}else{
				//进行提交
				$("#changeClassForm").submit();
			}			
		});
		//添加班级按钮的单击事件，先进行AJAX查询所有专业
		$("#addClassBtn").click(function(){
			//先清空专业，第一项除外
			$("#addMajor2 option:gt(0)").remove();
			//先进行AJAX提交，获得所有的专业
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/classinfo_getMajor.action",
				data: {
					
				},
				success: function(backData, textStatus, ajax){
					var obj = JSON.parse(backData);
					for(var i = 0;i<obj.length;i++){
						var majorName = obj[i][2]; //拿到每个学院的名称
						//拿到下拉列表框
						$("#addMajor2").append($("<option>" + majorName + "</option>"));
					}
				}
			});
			//已经获得了数据，显示模态框
			$("#addClassModal").modal();
		});
		//添加学院的单击事件，也是先判断
		$("#addClassRealBtn").click(function(){
			//先进行判断
			if($("#className2").val().trim() == ''){
				alert("班级名称不能为空！");
			}else if($("#addMajor2").val() == -1){
				alert("请选择专业！");
			}else{
				//进行提交
				$("#addClassForm").submit();
			}	
		});

		//点击删除按钮提交请求，查看数据库中这个班级是否有学生
		$(".delete").click(function(){
			//获得这行数据的第二列(班级编号)
			var classId = $(this).parents("tr").find("td").eq(1).text();
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/classinfo_delete.action",
				data: {
					'classes.classId': classId
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
			<span class="glyphicon glyphicon-education"></span>班级信息管理
		</div>
		<div class="panel-body">
			<div class="clearfix">
				<div class="pull-left">
					账号：<b>${teacherDirectorSession.account.accountId }</b>
					  &nbsp;&nbsp;
					姓名：<b>${teacherDirectorSession.teacherName }(教研室主任) </b>
					 &nbsp;&nbsp;
					操作学院：<b>${teacherDirectorSession.college.collegeName } </b>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" href="${pageContext.request.contextPath }/classinfo_export.action">
						<span class="glyphicon glyphicon-log-out"></span>导出为excel
					</a>
					<button class="btn btn-info" id="addClassBtn">
						<span class="glyphicon glyphicon-plus"></span>添加班级
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
							<th class="text-center">班级编号</th>
							<th class="text-center">班级名称</th>
							<th class="text-center">专业名称</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageBean.list}" var="classes" varStatus="varSta">
							<tr>
								<td>${varSta.count }</td>
								<td>${classes[0] }</td>
								<td>${classes[1] }</td>
								<td>${classes[2] }</td>
								<td class="text-center">
									<button class="btn btn-primary modify" data-toggle="modal"
										data-target="#myModal">
										<span class="glyphicon glyphicon-repeat"></span>修改
									</button>&nbsp;&nbsp;
									<button class="btn btn-danger delete" data-toggle="modal"
										data-target="#deleteSure">
										<span class="glyphicon glyphicon-remove-sign"></span>删除
									</button>
								</td>
							</tr>							
						</c:forEach>
					</tbody>
				</table>
				<div id="kkpager"></div>
			</div>
		</div>
	</div>

	<!--下面是模态框部分-->
	<!-- 更改班级的模态框 -->
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
					<form class="form-horizontal" action="classinfo_update.action" id="changeClassForm" role="form">
						<div class="form-group">
							<input id="updateId" name="classes.classId" style="display:none" type="text" />
							<label for="lastname" class="col-sm-2 control-label">班级名称</label>
							<div class="col-sm-10">
								<input type="text" name="classes.className" required class="form-control" id="className"
									placeholder="请输入班级名称">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">选择专业</label>
							<div class="col-sm-10">
								<select id="addMajors" name="majorName" class="form-control">
									<option value="-1">--请选择专业--</option>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" id="changeClassBtn" class="btn btn-primary">提交更改</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 添加班级的模态框 -->
	<div class="modal fade" id="addClassModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加班级</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" action="classinfo_add.action" id="addClassForm" role="form">
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">班级名称</label>
							<div class="col-sm-10">
								<input type="text" id="className2" name="classes.className" required class="form-control" placeholder="请输入班级名称">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">选择专业</label>
							<div class="col-sm-10">
								<select id="addMajor2" name="majorName" class="form-control">
									<option value="-1">--请选择专业--</option>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" id="addClassRealBtn" class="btn btn-primary">提交更改</button>
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
						已经成功删除了该班级！
					</h5>
				</div>
				<div class="modal-footer">
					<a class="btn btn-danger" href="classinfo_index.action">
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
						<font color="red">该班级有相关的学生数据，无法删除！</font>
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
