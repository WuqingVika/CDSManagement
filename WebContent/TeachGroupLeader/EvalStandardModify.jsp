<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组长查看评分标准</title>
<script>
	$(function() {
		
		var evalStand = "";
		
		//生成分页
		//有些参数是可选的，比如lang，若不传有默认值
		kkpager.generPageHtml({
			pno : ${pageBean.currentPage},
			//总页码
			total : ${pageBean.totalPage},
			//总数据条数
			totalRecords : ${pageBean.allRows},
			//链接前部
			hrefFormer : 'cdTechGroupLeC_findAlladdedEvalStand',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}		 				 
		});
		
		//全选
		$("#checkAllOrNot").click(
				function() {
					$("input[type=checkbox].checkItem").prop("checked",
							$(this).is(":checked"));
				});
		//添加组员
		$("button#addMember")
				.click(
						function() {
							$(this).attr("disabled", "disabled");
							var str = "<tr><td colspan='4'><form action='' class='form-inline'>工号：<input type='text' class='form-control' placeholder='请输入老师的工号' style='width:160px;'/>&nbsp;姓名:<input type='text' class='form-control' placeholder='请输入老师的姓名' style='width:160px;'/>&nbsp&nbsp;&nbsp;<button class='btn btn-primary'>添加</button>&nbsp;<button class='btn btn-success' id='removeElement'>取消</button></form></td></tr>";
							$("#tContent").append(str);
							$("#removeElement").click(function() {
								$(this).parents("tr").remove();
								$("button#addMember").removeAttr("disabled");
							});
						});
		// 查看评分标准
		$("button.searchEvalStand").click(function(){
			evalStand = $(this).prev().val();
			$.ajax({
				url:"cdTechGroupLeC_searchEvalStand.action",
				data:{"evalstandards.evalStandId":evalStand},
				type:"post",
				dataType:"json",
				async:false,
				success:function(data){
					 $("#attendCoe").html(data.attendCoe);
					 $("#procCoe").html(data.procAccCoe);
					 $("#selfCoe").html(data.selfCoe);
					 $("#replyCoe").html(data.replyCoe);
				},
				error:function(error){
					alert(error.responseText);
				}
			});
		});
		
		// 删除评分标准
		$("button.deleteEvalStand").click(function(){
			evalStand = $(this).prev().prev().val();
			// 弹出模态框
			$("#deleteSure").modal();
		});
		
		// 执行删除
		$("#sureDel").click(function(){
			window.location.href="cdTechGroupLeC_deleteEvalStand.action?evalstandards.evalStandId="+evalStand;
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
				<li class="active">查看评分标准</li>
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
									<th class="text-center">编号</th>
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
										<td class="text-center">
											<input type="hidden" value="<s:property value="#acc[7]" />"/>											
											<button class="btn btn-info searchEvalStand" data-toggle="modal"
												data-target="#viewStandards">查看评分标准</button>
											<button class="btn btn-danger deleteEvalStand">删除评分标准</button></td>
									</tr>
								</s:iterator>								 
							</tbody>
						</table>
						<!-- 分页的位置 -->
						<div id="kkpager"></div>	
					</div>
				</div>
			</div>			 

			<!--查看评分标准-->
			<div class="modal fade" id="viewStandards" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">查看评分标准</h4>
						</div>
						<div class="modal-body">
							<table class="table table-hover table-striped">
								<tr>
									<td>考勤系数</td>
									<td id="attendCoe"></td>
								</tr>
								<tr>
									<td>过程考核系数</td>
									<td id="procCoe"></td>
								</tr>
								<tr>
									<td>学生自评系数</td>
									<td id="selfCoe"></td>
								</tr>
								<tr>
									<td>答辩系数</td>
									<td id="replyCoe"></td>
								</tr>
							</table>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary">提交更改</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
			</div>
			
			<!--删除确认-->
			<div class="modal fade" id="deleteSure" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">消息提示</h4>
						</div>
						<div class="modal-body">
							<h5 class="text-danger">
								<span class="glyphicon glyphicon-question-sign" id="info">你确定要删除该记录吗，删除后不可恢复?</span>
							</h5>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" id="sureDel" data-dismiss="modal">
								<span class="glyphicon glyphicon-remove"></span>确定
							</button>
							<button type="button" class="btn btn-info" data-dismiss="modal">
								<span class="glyphicon glyphicon-share-alt"></span>取消
							</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
			</div>
		</div>
	</div>
</body>
</html>