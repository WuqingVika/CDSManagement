<%@page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>学生信息管理</title>
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
			hrefFormer : 'studentinfo_index',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}
		});
		//选择班级选项的改变事件
		$("#selectClassAsBtn").change(function(){
			//进行表单提交
			$("#selectClass").submit();
		});
		//设置当前选中的班级		
		$("#selectClassAsBtn").val(${currentClass });
		//修改按钮的点击，就给form表单填充数据
		$("button.modify").click(function() {
			var sid = $(this).parents("tr").find("td").eq(1).text(); //学生编号
			var id = $(this).parents("tr").find("td").eq(3).text(); //学号
			var name = $(this).parents("tr").find("td").eq(4).text(); //学生姓名
			$("#updateId").val(sid);
			$("#studentId").val(id);
			$("#stuName").val(name);
		});
		//更改按钮提交
		$("#updateStudentBtn").click(function(){
			//先进行判断再进行提交
			if($("#studentId").val().trim() == ""){
				alert("请输入学生学号");
			}else if($("#stuName").val().trim() == ""){
				alert("请输入学生姓名");
			}else if($("#selCla").val() == -1){
				alert("请选择学生所在班级");
			}else{
				$("#updateStudentForm").submit();
			}
		});
		//添加学生按钮(真实)开始提交
		$("#addStudentRealBtn").click(function(){
			$("#addStudentForm").submit();
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
		//点击删除按钮提交请求，查看学生能否删除
		$("button.delete").click(function(){
			//获得这行数据的第二列(教师编号，该列在表格上是不可见的)
			var stuId = $(this).parents("tr").find("td").eq(1).text();
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/studentinfo_delete.action",
				data: {
					'student.stuId': stuId
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
			<span class="glyphicon glyphicon-education"></span>学生信息管理
		</div>
		<div class="panel-body">
			<div class="clearfix">
				<div class="pull-left">
					账号：<b>${teacherDirectorSession.account.accountId } </b>
					  &nbsp;&nbsp;
					姓名：<b> ${teacherDirectorSession.teacherName }(教研室主任) </b>
					 &nbsp;&nbsp;
					操作学院：<b>${teacherDirectorSession.college.collegeName } </b>
					<form id="selectClass" class="form-inline" role="form">
						<div class="form-group">
							<label for="name" class="text-info">选择显示的班级</label> 
							<select class="form-control" name="classId" id="selectClassAsBtn">
								<option value="-1">显示全部班级</option>
								<c:forEach items="${allClass }" var="aclass" varStatus="varSta">
									<option value="${aclass.classId }">${aclass.className }</option>
								</c:forEach>
							</select>
						</div>
					</form>
				</div>
				<div class="pull-right">
					<button class="btn btn-warning" data-toggle="modal"	data-target="#uploadFileModal">
						<span class="glyphicon glyphicon-log-in"></span>导入数据
					</button>
					<a class="btn btn-success" href="${pageContext.request.contextPath }/studentinfo_export.action">
						<span class="glyphicon glyphicon-log-out"></span>导出为excel
					</a>
					<button class="btn btn-info" data-toggle="modal" data-target="#addStudentModal">
						<span class="glyphicon glyphicon-plus"></span>添加学生
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
							<th class="text-center">学生学号</th>							
							<th class="text-center">姓名</th>
							<th class="text-center">班级名称</th>
							<th class="text-center">专业名称</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageBean.list}" var="student" varStatus="varSta">
							<tr>
								<td>${varSta.count }</td>
								<td  style="display:none">${student[1] }</td>
								<td>${student[2] }</td>
								<td>${student[3] }</td>
								<td>${student[4] }</td>
								<td>${student[5] }</td>
								<td>${student[6] }</td>
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
				<c:set var="studentCounts" value="${fn:length(pageBean.list) }"></c:set>
				<c:if test="${studentCounts == 0 }">
					<h3>没有学生记录！</h3>
				</c:if>
				<div id="kkpager"></div>
			</div>
		</div>
	</div>

	<!--下面是模态框部分-->
	<!-- 修改的模态框是myModal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">修改学生信息</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="updateStudentForm" action="studentinfo_update.action" role="form">
						<div class="form-group">
							<label for="firstname" class="col-sm-2 control-label">学号</label>
							<div class="col-sm-10">
								<input id="updateId" name="student.stuId" style="display:none" type="text" />
								<input type="text" class="form-control" id="studentId" name="student.studentId"
									placeholder="请输入学生学号">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="stuName" name="student.stuName"
									placeholder="请输入学生姓名">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">班级</label>
							<div class="col-sm-10">
								<select id="selCla" name="classId" class="form-control">
									<option value="-1">--请选择班级--</option>
									<c:forEach items="${allClass }" var="aclass" varStatus="varSta">
										<option value="${aclass.classId }">${aclass.className }</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" id="updateStudentBtn">提交更改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	
	<!-- 添加学生的模态框 -->
	<div class="modal fade" id="addStudentModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加学生</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" action="studentinfo_add.action" id="addStudentForm" role="form">
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">登录账号</label>
							<div class="col-sm-10">
								<input type="text" id="studentLoginId" name="account.accountId" class="form-control" placeholder="请输入学生登录账号">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">学生学号</label>
							<div class="col-sm-10">
								<input type="text" id="studentId2" name="student.studentId" class="form-control" placeholder="请输入学号">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">学生姓名</label>
							<div class="col-sm-10">
								<input type="text" id="studName2" name="student.stuName" class="form-control" placeholder="请输入学生姓名">
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">所在班级</label>
							<div class="col-sm-10">
								<select id="selCla2" name="classId" class="form-control">
									<option value="-1">--请选择班级--</option>
									<c:forEach items="${allClass }" var="aclass" varStatus="varSta">
										<option value="${aclass.classId }">${aclass.className }</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" id="addStudentRealBtn" class="btn btn-primary">添加学生</button>
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
					<h4 class="modal-title" id="myModalLabel">添加学生</h4>
				</div>
				<div class="modal-body">
					<form id="uploadFileForm" action="${pageContext.request.contextPath }/studentinfo_import" enctype="multipart/form-data" class="form-horizontal" method="post" role="form">
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
						已经成功删除了该学生及其账户信息！
					</h5>
				</div>
				<div class="modal-footer">
					<a class="btn btn-danger" href="studentinfo_index.action">
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
						<font color="red">该学生有相关的课程计划小组数据，无法删除！</font>
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
