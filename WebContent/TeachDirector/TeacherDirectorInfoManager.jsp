<%@page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>教研室主任信息管理</title>
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
			hrefFormer : 'teacherdirectorinfo_index',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}
		});
		
		//添加按钮就先提交AJAX获得学院
		$("#addTeacherDirector").click(function(){
			//进行AJAX提交，获得学院
			//直接用之前写过的action即可
			//先清空
			$("#selectCollege option:gt(0)").remove();
			$("#selectTeacher option:gt(0)").remove(); //这里也清空教师的
			$.ajax({
				type: "POST",
				url: "teacherinfo_getCollege.action",
				data: {
				},
				success: function(backData, textStatus, ajax){
					//成功的话，添加select
					var obj = JSON.parse(backData);
					$(obj.collegeName).each(function(){
						//拿到下拉列表框
						$("#selectCollege").append($("<option>" + this + "</option>"));
					});
				}
			});			
		});
		//如果学院的选项发生变化，也进行AJAX提交，获得教师
		$("#selectCollege").change(function(){
			//拿到选择教师的框
			if($("#selectCollege option:selected").text() != "请选择学院"){
				//进行AJAX提交
				//先清空选择教师的			
				$("#selectTeacher option:gt(0)").remove();
				$.ajax({
					type: "POST",
					url: "teacherdirectorinfo_getTeacher.action",
					data: {
						collegeName : $("#selectCollege option:selected").text()
					},
					success: function(backData, textStatus, ajax){
						//成功的话，添加select
						var obj = JSON.parse(backData);
						for(var i = 0;i<obj.length;i++){
							var teacherWorkId = obj[i][1];
							var teacherName = obj[i][2];
							//拿到下拉列表框
							$("#selectTeacher").append($("<option value=" + teacherWorkId + ">" + teacherName + "</option>"));
						}
					}
				});	
			}else{
				//直接清空
				$("#selectTeacher option:gt(0)").remove();
			}
		});
		//点击确定按钮的时候，就提交教师的姓名来升级为教研室主任
		$("#handleTeacherName").click(function(){
			//先判断有没有选择教师
			if($("#selectTeacher option:selected").text() != "请选择教师"){
				//增加教师，提交教师的工号
				window.location.href = "teacherdirectorinfo_add.action?teacherWorkId=" + $("#selectTeacher").val() ;
			}
		});
		//添加提交
		$("#handleTermBtn").click(function(){
			if($("#addTermName").val().trim() == ''){
				
				
			}else{
				$("#handleTermForm").submit();
			}			
		});
		//点击删除按钮提交请求，查看数据库中这个学期是否有专业
		$(".delete").click(function(){
			//获得这行数据的第二列(账号)
			var accId = $(this).parents("tr").find("td").eq(1).text();
			$.ajax({
				type: "POST",
				url: "teacherdirectorinfo_delete.action",
				data: {
					'accId': accId
				},
				success: function(backData, textStatus, ajax){
					if(backData == 1){
						$("#alreadyDelete").modal();
					}else{
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
			<span class="glyphicon glyphicon-education"></span>教研室主任管理
		</div>
		<div class="panel-body">
			<div class="clearfix">
				<div class="pull-right">
					<button class="btn btn-info" id="addTeacherDirector" data-toggle="modal" data-target="#addTeacherDirectorModal">
						<span class="glyphicon glyphicon-plus"></span>添加教研室主任
					</button>
				</div>
			</div>
			<div>
				<table class="table table-hover table-striped table-bordered">
					<thead>
						<tr class="text-warning">
							<th class="text-center">序号</th>
							<th class="text-center">登录账号</th>
							<th class="text-center">工号</th>
							<th class="text-center">姓名</th>
							<th class="text-center">学院</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageBean.list}" var="teacherDirector" varStatus="varSta">
							<tr>
								<td>${varSta.count }</td>
								<td style="display:none">${teacherDirector[0] }</td>
								<td>${teacherDirector[1] }</td>
								<td style="display:none">${teacherDirector[2] }</td>
								<td>${teacherDirector[3] }</td>
								<td>${teacherDirector[4] }</td>
								<td>${teacherDirector[5] }</td>
								<td class="text-center">
									<button class="btn btn-danger delete" data-toggle="modal"
										data-target="#deleteSure">
										<span class="glyphicon glyphicon-remove-sign"></span>删除
									</button>
								</td>
							</tr>							
						</c:forEach>
					</tbody>
				</table>
				<c:set var="teacherDirectCounts" value="${fn:length(pageBean.list) }"></c:set>
				<c:if test="${teacherDirectCounts == 0 }">
					<h3>没有教研室主任记录！</h3>
				</c:if>
				<!-- 分页的位置 -->
				<div id="kkpager"></div>
			</div>
		</div>
	</div>
</div>
	<!--下面是模态框部分-->
	<!-- 添加教研室主任 -->
	<div class="modal fade" id="addTeacherDirectorModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加教研室主任</h4>
				</div>
				<div class="modal-body">
					<form id="handleTeacherDirectorForm" action="teacherdirectorinfo_add.action" class="form-horizontal" role="form">
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">选择学院</label>
							<div class="col-sm-10">
								<select class="form-control" id="selectCollege">
									<option>请选择学院</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">选择教师</label>
							<div class="col-sm-10">
								<select class="form-control" id="selectTeacher">
									<option>请选择教师</option>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="handleTeacherName" class="btn btn-danger">
						<span class="glyphicon glyphicon-remove-sign"></span>确定
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
						已经成功删除了该教研室主任！
					</h5>
				</div>
				<div class="modal-footer">
					<button class="btn btn-danger" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove-sign" id="deleteFresh"></span>确定
					</button>
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
						<font color="red">删除失败！</font>
					</h5>
				</div>
				<div class="modal-footer">
					<a type="button" class="btn btn-danger" href="#">
						<span class="glyphicon glyphicon-remove-sign"></span>确定
					</a>
				</div>
			</div>
		</div>
	</div>

<%@include file="../UIRef/JspRef/MainFrameworkFoot.jsp"%>
