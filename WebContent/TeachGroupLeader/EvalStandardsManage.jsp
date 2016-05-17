<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组长添加课程设计评分标准</title>
<script>
	$(function() {
		var teacherGroupId = "";//定义教师小组的编号
		
		//生成分页
		//有些参数是可选的，比如lang，若不传有默认值
		kkpager.generPageHtml({
			pno : ${pageBean.currentPage},
			//总页码
			total : ${pageBean.totalPage},
			//总数据条数
			totalRecords : ${pageBean.allRows},
			//链接前部
			hrefFormer : 'cdTechGroupLeC_findStillEvalStand',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}		 				 
		});
		
		// 点击添加小组
		$("button.addEvalStand").click(function(){
			teacherGroupId = $(this).prev().val();
			$("#cdteacherGroupId").val(teacherGroupId);
		});
	});
</script>
</head>
<body>
	<!-- banner-start -->
	<jsp:include page="../UIRef/JspRef/StudentHeader.jsp"></jsp:include>
	<!-- banner-end -->
	<!-- content-start -->
	<div class="container-fluid">
		<!-- left-nav-start -->
		<jsp:include page="../UIRef/JspRef/TeacherleftInstructor.jsp"></jsp:include>
		<!-- left-nav-end -->
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<ol class="breadcrumb" style="width: 100%;">
				<li><a href="#">课程设计平台管理系统</a></li>
				<li><a href="#">组长管理模块</a></li>
				<li class="active">组长制定评分标准</li>
			</ol>
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="clearfix">
						<span class="glyphicon glyphicon-chevron-right"></span>添加组员
					</div>
				</div>
				<div class="panel-body">
					<div class="row">
						<table class="table table-hover table-bordered">
							<thead>
								<tr class="text-primary">
									<th class="text-center">序号</th>
									<th class="text-center">课程编号</th>
									<th class="text-center">课程计划编号</th>
									<th class="text-center">课程计划名称</th>
									<th class="text-center">专业</th>
									<th class="text-center">学期</th>
									<th class="text-center">总学分</th>
									<th class="text-center">总学时</th>
									<th class="text-center">操作</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="#request.pageBean.list" id="acc"
									status="item">
									<tr>
										<td><s:property value="%{#item.getIndex()+1}" /></td>
										<td><s:property value="#acc[0]" /></td>
										<td><s:property value="#acc[1]" /></td>
										<td><s:property value="#acc[2]" /></td>
										<td><s:property value="#acc[3]" /></td>
										<td><s:property value="#acc[4]" /></td>
										<td><s:property value="#acc[5]" /></td>
										<td><s:property value="#acc[6]" /></td>
										<td class="text-center"><input type="hidden"
											value="<s:property value="#acc[7]" />" />
											<button class="btn btn-warning addEvalStand" data-toggle="modal"
												data-target="#addStandards">添加评分标准</button></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
						<!-- 分页的位置 -->
						<div id="kkpager"></div>
					</div>
				</div>
			</div>

			<!--添加评分标准-->
			<div class="modal fade" id="addStandards" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<!-- 表单域 -->
						<form action="cdTechGroupLeC_addEvalStandards.action" method="post">
							<!-- 隐藏域 -->
							<input type="hidden" id="cdteacherGroupId"
								name="evalstandards.cdteachergroup.cdteacherGroupId" value="" />
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">添加课程设计标准</h4>
							</div>
							<div class="modal-body">
								<div class="form-horizontal" role="form">
									<div class="form-group">
										<label for="firstname" class="col-sm-3 control-label">考勤系数</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" id="firstname"
												name="evalstandards.attendCoe" placeholder="请输入考勤系数">
										</div>
									</div>
									<div class="form-group">
										<label for="lastname" class="col-sm-3 control-label">过程考核系数</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" id="lastname"
												name="evalstandards.procAccCoe" placeholder="请输入过程考核系数">
										</div>
									</div>
									<div class="form-group">
										<label for="lastname" class="col-sm-3 control-label">学生自评系数</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" id="lastname"
												name="evalstandards.selfCoe" placeholder="请输入学生自评系数">
										</div>
									</div>
									<div class="form-group">
										<label for="lastname" class="col-sm-3 control-label">答辩系数</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" id="lastname"
												name="evalstandards.replyCoe" placeholder="请输入答辩系数">
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button type="submit" class="btn btn-primary">提交更改</button>
							</div>
						</form>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
			</div>

		</div>
	</div>
</body>
</html>