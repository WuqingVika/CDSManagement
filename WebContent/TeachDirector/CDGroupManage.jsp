<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>管理课程设计群</title>
<script type="text/javascript">
	$(function(){
		//分页显示
		kkpager.generPageHtml({
			pno : ${pageBean.currentPage },
			//总页码
			total : ${pageBean.totalPage},
			//总数据条数
			totalRecords : ${pageBean.allRows},
			//链接前部
			hrefFormer : 'cdplaninfo_index',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
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
				<span class="glyphicon glyphicon-chevron-right"></span>添加课程设计
			</div>
		</div>
		<div class="panel-body">
		<div class="clearfix">
				<div class="pull-left">
					<!-- 表格上面的左面 -->
					账号：<b>${teacherDirectorSession.account.accountId }</b>
					  &nbsp;&nbsp;
					姓名：<b>${teacherDirectorSession.teacherName }(教研室主任) </b>
					 &nbsp;&nbsp;
					操作学院：<b>${teacherDirectorSession.college.collegeName } </b>
					<h4>
						<font color="red">
						<b>确保数据正确以及添加相应的专业和学期数据再导入！导入后不可更改！
						</b>
						</font>
					</h4>
				</div>
				<div class="pull-right">
					<button class="btn btn-warning" data-toggle="modal"	data-target="#uploadFileModal">
						<span class="glyphicon glyphicon-log-in"></span>导入excel课程计划
					</button>
				</div> <br /> <br /> <br />
			<div class="row">
				<table class="table table-hover table-bordered">
					<thead>
						<tr class="text-primary">
							<th class="text-center">序号</th>
							<th class="text-center">编号</th>
							<th class="text-center">课程计划号</th>
							<th class="text-center">课程计划名称</th>
							<th class="text-center">总学分</th>
							<th class="text-center">总学时</th>
							<th class="text-center">专业</th>
							<th class="text-center">学期</th>							
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${pageBean.list}" var="cdplan" varStatus="varSta">
						<tr>
							<td>${varSta.count }</td>
							<td>${cdplan[0] }</td>
							<td>${cdplan[1] }</td>
							<td>${cdplan[2] }</td>
							<td>${cdplan[3] }</td>
							<td>${cdplan[4] }</td>
							<td>${cdplan[5] }</td>
							<td>${cdplan[6] }</td>
						</tr>						
					</c:forEach>
					</tbody>
				</table>
			</div>
			<c:set var="cdplanCounts" value="${fn:length(pageBean.list) }"></c:set>
			<c:if test="${cdplanCounts == 0 }">
				<h3>没有课程计划记录！</h3>
			</c:if>
			<div id="kkpager"></div>
		</div>
	</div>
</div>
</div>
	<!--下面是模态框部分-->
<!-- 上传文件导入excel-->
	<div class="modal fade" id="uploadFileModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加课程计划</h4>
				</div>
				<div class="modal-body">
					<form id="uploadFileForm" action="${pageContext.request.contextPath }/cdplaninfo_import.action" enctype="multipart/form-data" class="form-horizontal" method="post" role="form">
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
<%@include file="../UIRef/JspRef/MainFrameworkFoot.jsp"%>