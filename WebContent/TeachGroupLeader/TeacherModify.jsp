<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组长查看组员</title>
<script>
	$(function() {
	 
		var teacherGroupId = "";
		
		//生成分页
		//有些参数是可选的，比如lang，若不传有默认值
		kkpager.generPageHtml({
			pno : ${pageBean.currentPage},
			//总页码
			total : ${pageBean.totalPage},
			//总数据条数
			totalRecords : ${pageBean.allRows},
			//链接前部
			hrefFormer : 'cdTechGroupLeC_findAllAllowedMem',
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
							var str = "<tr><td colspan='4'><form class='form-inline'>工号：<input type='text' name='teacherWorkId' id='teacherWorkId' class='form-control' placeholder='请输入老师的工号' style='width:160px;'/>&nbsp;姓名:<input type='text' class='form-control' placeholder='请输入老师的姓名' style='width:160px;'/>&nbsp&nbsp;&nbsp;<button type='button' id='addMem' class='btn btn-primary'>添加</button>&nbsp;<button class='btn btn-success' id='removeElement'>取消</button></form></td></tr>";
							$("#tContent").append(str);							 
							// 添加对象
							$("#addMem").click(function(){
								$.ajax({
									url:"cdTechGroupLeC_addTeacher.action",
									data:{teacherWorkId:$("#teacherWorkId").val(),teacherGroupId:teacherGroupId},
									type:"post",
									dataType:"json",
									async:false,
									success:function(data){
										if(data == "success"){
											$("#info").empty().html("添加教师成功！");
											$("#infoDescribtion").modal();
										}else{
											$("#info").empty().html("对不起添加教师失败！");
											$("#infoDescribtion").modal();
										}
										//重新加载列表
										loadTeachGroup(teacherGroupId);
									},
									error:function(error){
										alert(error.responseText);
									}
								});
							});
							// 移除对象
							$("#removeElement").click(function() {
								$(this).parents("tr").remove();
								$("button#addMember").removeAttr("disabled");
							});
						});
		//查看组员
		$("button.searchGM").click(function(){			
			teacherGroupId = $(this).prev().val();
			loadTeachGroup(teacherGroupId);			
		});
		
		 
		
		// 加载教师小组信息
		function loadTeachGroup(teacherGroupId){
			$("#addMember").removeAttr("disabled");
			// 动态加载教师的信息
			$.ajax({
				url:"cdTechGroupLeC_findTeachGroupMem.action",
				data:{teacherGroupId:teacherGroupId},
				type:"post",
				async:false,
				dataType:"json",
				success:function(data){
					 var str = "";
					 var $container = $("#tContent");
					 for(var i = 0;i<data.length;i++){						  
						 str += "<tr><td>"+(i+1)+"</td><td class='text-center'>"+data[i][1]+"</td><td class='text-center'>"+data[i][2]
						 +"</td><td class='text-center'><input type='hidden' value='"+data[i][0]+
						 "'/><button type='button' class='btn btn-danger deleteThGoMe'><span class='glyphicon glyphicon-remove-sign'></span>删除</a></td></tr>";
					 }					 
					 $container.empty().append(str);
					 //绑定删除事件
					 $("button.deleteThGoMe").click(function(){
						 var teacherId = $(this).prev().val();
						 var $element = $(this);
						 $.ajax({
								url:"cdTechGroupLeC_removeTeachGroupMem.action",
								data:{teacherGroupId:teacherGroupId,teacherId:teacherId},
								type:"post",
								async:false,
								dataType:"json",
								success:function(data){								 
									if(data == "success"){
										$("#info").empty().html("删除成功！");
										$("#infoDescribtion").modal();
										$element.parents("tr").remove();
										if($("#tContent tr").length == 0){
											window.location.href = "cdTechGroupLeC_findAllAllowedMem.action";
										}
									}else{
										$("#info").empty().html("删除失败！");
										$("#infoDescribtion").modal();	
									}																							
								},
								error:function(error){
									 alert(error.responseText);
								}
						 });						 						 					
					 });					
				},
				error:function(error){
					alert(error.responseText);
				}
			});
		}
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
				<li class="active">查看组员</li>
			</ol>
			<div class="panel panel-info">
				<div class="panel-heading">
					<div class="clearfix">
						<span class="glyphicon glyphicon-chevron-right"></span>查看组员
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
										<td class="text-center"><input type="hidden"
											value="<s:property value="#acc[7]" />" />
											<button class="btn btn-success searchGM" data-toggle="modal"
												data-target="#viewGroupMember">查看组员</button></td>
									</tr>
								</s:iterator>
							</tbody>
						</table>
					</div>
					<!-- 分页的位置 -->
					<div id="kkpager"></div>
				</div>
			</div>

			<!--查看组员-->
			<div class="modal fade" id="viewGroupMember" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">查看组员</h4>
						</div>
						<div class="modal-body">
							<div class="clearfix">
								<span class="pull-right"><button class="btn btn-success"
										id="addMember">
										<span class="glyphicon glyphicon-plus"></span>添加
									</button> </span>
							</div>
							<table class="table table-hover table-striped">
								<thead>
									<tr class="text-primary">
										<th class="text-center">序号</th>
										<th class="text-center">组员编号</th>
										<th class="text-center">姓名</th>
										<th class="text-center">操作</th>
									</tr>
								</thead>
								<tbody id="tContent">
									<!-- 这里是动态加载数据部分 -->
								</tbody>
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

			<!--消息提示-->
			<div class="modal fade" id="infoDescribtion" tabindex="-1"
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
								<span class="glyphicon glyphicon-question-sign" id="info"></span>
							</h5>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-info" data-dismiss="modal">
								<span class="glyphicon glyphicon-share-alt"></span>关闭
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