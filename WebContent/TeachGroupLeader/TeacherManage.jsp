<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组长添加组员</title>
<script>
	$(function() {
		
		var teachers = new Array();
		var cdTeacherGroup = "";
		
		//生成分页
		//有些参数是可选的，比如lang，若不传有默认值
		kkpager.generPageHtml({
			pno : ${pageBean.currentPage},
			//总页码
			total : ${pageBean.totalPage},
			//总数据条数
			totalRecords : ${pageBean.allRows},
			//链接前部
			hrefFormer : 'cdTechGroupLeC_findAllNeedAllowCD',
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
		$("button.addGropMem").click(function(){
			cdTeacherGroup = $(this).prev().val();					 	
			//加载所有的教师
			$.ajax({
				url:"cdTechGroupLeC_findAllTeachers.action",
				type:"post",
				dataType:"json",
				success:function(data){					 					 
					var str = "";
					for(var i = 0;i<data.length;i++){
						str+="<tr><td><input type='checkbox' class='checkItem'></td><td>"+data[i][0]+"</td><td>"+data[i][3]+"</td><td>"+data[i][4]+"</td></tr>";
					}
					$("#tableContent").empty().append(str);
				},
				error:function(error){
					alert(error.responseText);
				}
			});
		});
		
		//提交后台
		$("#addThMem").click(function(){
			teachers.splice(0,teachers.length);
			//把选中的教师添加到数据中
			var $items = $("input.checkItem:checked");
			$items.each(function(){
				teachers.push($(this).parent().next().text());
				alert($(this).parent().next().text());
			});				 
			$.ajax({
				url:"cdTechGroupLeC_addTeachers.action",
				type:"post",
				async:false,
				data:{teachersString:teachers.join(","),teacherGroupId:cdTeacherGroup},
				success:function(data){
					alert("添加组员成功!");
					// 超链接
					window.location.href = "cdTechGroupLeC_findAllAllowedMem.action";
				},
				error:function(error){
					alert(error.responseText);
				}
			}); 
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
				<li class="active">添加组员</li>
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
											<button class="btn btn-warning addGropMem" data-toggle="modal"
												data-target="#addGroupMember">添加组员</button>
										</td>
									</tr>
								</s:iterator>								 
							</tbody>
						</table>
					</div>
				
					<!-- 分页的位置 -->
					<div id="kkpager"></div>
				</div>
			</div>
			<!--添加组员-->
			<div class="modal fade bs-example-modal-lg" id="addGroupMember"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">添加课程设计组组员</h4>
						</div>
						<div class="modal-body">							 
							<table class="table table-hover table-striped table-bordered">
								<thead>
									<tr class="text-primary">
										<th style="width: 100px;" class="text-center"><input
											type="checkbox" id="checkAllOrNot" />全/全不选</th>
										<th>教师编号</th>
										<th>工号</th>
										<th>姓名</th>
									</tr>
								</thead>
								<tbody id="tableContent">
									 <!-- 查看学院的所有的用户 -->								 
								</tbody>
							</table>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="button" id="addThMem" class="btn btn-primary">添加组员</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
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
									<tr>
										<td>1</td>
										<td>20130501140</td>
										<td>张三</td>
										<td class="text-center">
											<button class="btn btn-danger delete">
												<span class="glyphicon glyphicon-remove-sign"></span>删除
											</button>
										</td>
									</tr>
									<tr>
										<td>1</td>
										<td>20130501140</td>
										<td>张三</td>
										<td class="text-center">
											<button class="btn btn-danger delete">
												<span class="glyphicon glyphicon-remove-sign"></span>删除
											</button>
										</td>
									</tr>
									<tr>
										<td>1</td>
										<td>20130501140</td>
										<td>张三</td>
										<td class="text-center">
											<button class="btn btn-danger delete">
												<span class="glyphicon glyphicon-remove-sign"></span>删除
											</button>
										</td>
									</tr>
									<tr>
										<td>1</td>
										<td>20130501140</td>
										<td>张三</td>
										<td class="text-center">
											<button class="btn btn-danger delete">
												<span class="glyphicon glyphicon-remove-sign"></span>删除
											</button>
										</td>
									</tr>
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

			<!--添加评分标准-->
			<div class="modal fade" id="addStandards" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">添加课程设计标准</h4>
						</div>
						<div class="modal-body">
							<form class="form-horizontal" role="form">
								<div class="form-group">
									<label for="firstname" class="col-sm-3 control-label">考勤系数</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="firstname"
											placeholder="请输入考勤系数">
									</div>
								</div>
								<div class="form-group">
									<label for="lastname" class="col-sm-3 control-label">过程考核系数</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="lastname"
											placeholder="请输入过程考核系数">
									</div>
								</div>
								<div class="form-group">
									<label for="lastname" class="col-sm-3 control-label">学生自评系数</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="lastname"
											placeholder="请输入学生自评系数">
									</div>
								</div>
								<div class="form-group">
									<label for="lastname" class="col-sm-3 control-label">答辩系数</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="lastname"
											placeholder="请输入答辩系数">
									</div>
								</div>
							</form>
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
									<td>23%</td>
								</tr>
								<tr>
									<td>过程考核系数</td>
									<td>12%</td>
								</tr>
								<tr>
									<td>学生自评系数</td>
									<td>23%</td>
								</tr>
								<tr>
									<td>答辩系数</td>
									<td>12%</td>
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
		</div>
	</div>
</body>
</html>