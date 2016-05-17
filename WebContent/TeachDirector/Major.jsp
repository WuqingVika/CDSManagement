<%@page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>专业信息管理</title>
<script>
	$(function(){
		//分页显示
		kkpager.generPageHtml({
			pno : ${pageBean.currentPage },
			//总页码
			total : ${pageBean.totalPage},
			//总数据条数
			totalRecords : ${pageBean.allRows},
			//链接前部
			hrefFormer : 'majorinfo_index',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}
		});
		
		//修改
		$("button.modify").click(function() {
			//先显示隐藏域的值
			var id = $(this).parents("tr").find("td").eq(1).text();
			var name = $(this).parents("tr").find("td").eq(2).text();
			$("#majorName").val(name);
			$("#updateId").val(id);
		});
		//点击修改按钮
		$("#changeMajorBtn").click(function(){
			//进行提交
			$("#changeMajorForm").submit();
		});
		//添加
		$("#addMajorBtn").click(function(){
			$("#addMajor").modal();
		});
		//添加提交
		$("#handleMajorBtn").click(function(){
			if($("#addMajorName").val().trim() == ''){
				
			}else{
				$("#handleMajorForm").submit();
			}		
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
		
		//点击删除按钮提交请求，查看数据库中这个专业是否有班级
		$(".delete").click(function(){
			//获得这行数据的第二列(专业编号)
			var majorId = $(this).parents("tr").find("td").eq(1).text();
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/majorinfo_delete.action",
				data: {
					'major.majorId': majorId
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
			<span class="glyphicon glyphicon-education"></span>专业信息管理
		</div>
		<div class="panel-body">
			<div class="clearfix">
				<div class="pull-left">
					账号：<b>${teacherDirectorSession.account.accountId } </b>
					  &nbsp;&nbsp;
					姓名：<b> ${teacherDirectorSession.teacherName }(教研室主任) </b>
					 &nbsp;&nbsp;
					操作学院：<b> ${teacherDirectorSession.college.collegeName } </b>
				</div>
				<div class="pull-right">
					<button class="btn btn-warning" data-toggle="modal"	data-target="#uploadFileModal">
						<span class="glyphicon glyphicon-log-in"></span>导入excel
					</button>
					<a class="btn btn-success" href="${pageContext.request.contextPath }/majorinfo_export.action">
						<span class="glyphicon glyphicon-log-out"></span>导出为excel
					</a>
					<button class="btn btn-info" id="addMajorBtn">
						<span class="glyphicon glyphicon-plus"></span>添加专业
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
							<th class="text-center">专业编号</th>
							<th class="text-center">专业名称</th>
							<th class="text-center">学院名称</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageBean.list}" var="major" varStatus="varSta">
							<tr>
								<td>${varSta.count }</td>
								<td>${major[0] }</td>
								<td>${major[1] }</td>
								<td>${major[2] }</td>
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
				<c:set var="majorCounts" value="${fn:length(pageBean.list) }"></c:set>
				<c:if test="${majorCounts == 0 }">
					<h3>没有专业记录！</h3>
				</c:if>
				<div id="kkpager"></div>
			</div>
		</div>
	</div>

	<!--下面是模态框部分-->
	<div>
	<!-- 修改专业的模态框 -->
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
					<form id="changeMajorForm" action="${pageContext.request.contextPath }/majorinfo_update" class="form-horizontal" role="form">
						<div class="form-group">
							<input id="updateId" name="major.majorId" style="display:none" type="text" />
							<label for="lastname" class="col-sm-2 control-label">专业名称</label>
							<div class="col-sm-10">
								<input type="text" name="major.majorName" class="form-control" id="majorName">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" id="changeMajorBtn">提交更改</button>
				</div>
			</div>
		</div>
	</div>
	</div>
	
	<!-- 添加专业 -->
	<div class="modal fade" id="addMajor" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加专业</h4>
				</div>
				<div class="modal-body">
					<form id="handleMajorForm" action="${pageContext.request.contextPath }/majorinfo_add" class="form-horizontal" role="form">
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">专业名称</label>
							<div class="col-sm-10">
								<input name="major.majorName" id="addMajorName" type="text" required class="form-control">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="handleMajorBtn" class="btn btn-danger">
						<span class="glyphicon glyphicon-remove-sign"></span>确定
					</button>
					<button type="button" class="btn btn-info" data-dismiss="modal">
						<span class="glyphicon glyphicon-share-alt"></span>取消
					</button>
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
					<h4 class="modal-title" id="myModalLabel">添加专业</h4>
				</div>
				<div class="modal-body">
					<form id="uploadFileForm" action="${pageContext.request.contextPath }/majorinfo_import" enctype="multipart/form-data" class="form-horizontal" method="post" role="form">
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
						已经成功删除了该专业！
					</h5>
				</div>
				<div class="modal-footer">
					<a class="btn btn-danger" href="majorinfo_index.action">
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
						<font color="red">该专业有相关的班级数据，无法删除！</font>
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
