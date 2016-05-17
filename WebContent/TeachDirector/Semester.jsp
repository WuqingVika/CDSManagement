<%@page language="java" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>学期信息管理</title>
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
			hrefFormer : 'terminfo_index',
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
			$("#termName").val(name);
			$("#updateId").val(id);
		});
		//点击修改按钮
		$("#changeTermBtn").click(function(){
			//进行提交
			$("#changeTermForm").submit();
		});
		
		//添加
		$("#addTermBtn").click(function(){
			$("#addTerm").modal();
		});
		//添加提交
		$("#handleTermBtn").click(function(){
			if($("#addTermName").val().trim() == ''){
				
			}else{
				$("#handleTermForm").submit();
			}			
		});
		//导入excel
		$("#uploadFileBtn").click(function(){
			$("#uploadFileForm").submit();
		});
		//点击删除按钮提交请求，查看数据库中这个学期是否有专业
		$(".delete").click(function(){
			//获得这行数据的第二列(学期编号)
			var termId = $(this).parents("tr").find("td").eq(1).text();
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/terminfo_delete.action",
				data: {
					'term.termId': termId
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
			<span class="glyphicon glyphicon-education"></span>学期信息管理
		</div>
		<div class="panel-body">
			<div class="clearfix">
				<div class="pull-right">
					<button class="btn btn-warning" data-toggle="modal"
						data-target="#uploadFileModal">
						<span class="glyphicon glyphicon-log-in"></span>导入excel
					</button>
					<a class="btn btn-success" href="${pageContext.request.contextPath }/terminfo_export">
						<span class="glyphicon glyphicon-log-out"></span>导出为excel
					</a>
					<button class="btn btn-info" id="addTermBtn">
						<span class="glyphicon glyphicon-plus"></span>添加学期
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
							<th class="text-center">学期编号</th>
							<th class="text-center">学期名称</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageBean.list}" var="term" varStatus="varSta">
							<tr>
								<td>${varSta.count }</td>
								<td>${term[0] }</td>
								<td>${term[1] }</td>
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
				<c:set var="termCounts" value="${fn:length(pageBean.list) }"></c:set>
				<c:if test="${termCounts == 0 }">
					<h3>没有学期记录！</h3>
				</c:if>
				<!-- 分页的位置 -->
				<div id="kkpager"></div>
			</div>
		</div>
	</div>

	<!--下面是模态框部分-->
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
					<form id="changeTermForm" action="${pageContext.request.contextPath }/terminfo_update" class="form-horizontal" role="form">
						<div class="form-group">
							<input id="updateId" name="term.termId" style="display:none" type="text" />
							<label for="lastname" class="col-sm-2 control-label">学期名称</label>
							<div class="col-sm-10">
								<input type="text" name="term.termName" class="form-control" id="termName">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" id="changeTermBtn">提交更改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	</div>
	
	<!-- 添加学期 -->
	<div class="modal fade" id="addTerm" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加学期</h4>
				</div>
				<div class="modal-body">
					<form id="handleTermForm" action="${pageContext.request.contextPath }/terminfo_add" class="form-horizontal" role="form">
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">学期名称</label>
							<div class="col-sm-10">
								<input name="term.termName" id="addTermName" type="text" required class="form-control">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" id="handleTermBtn" class="btn btn-danger">
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
					<h4 class="modal-title" id="myModalLabel">添加学院</h4>
				</div>
				<div class="modal-body">
					<form id="uploadFileForm" action="${pageContext.request.contextPath }/terminfo_import" enctype="multipart/form-data" class="form-horizontal" method="post" role="form">
						<div class="form-group">
							<label for="lastname" class="col-sm-2 control-label">选择要上传的文件</label>
							<div class="col-sm-10">
								<input name="uploadFile" id="uploadFile" type="file" class="form-control"
									placeholder="请上传文件">
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
						已经成功删除了该学期！
					</h5>
				</div>
				<div class="modal-footer">
					<a class="btn btn-danger" href="terminfo_index.action">
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
						<font color="red">该学期有相关课程数据，无法删除！</font>
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
