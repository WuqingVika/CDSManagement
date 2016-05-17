<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<title>查看已经选择的课题</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/UIRef/js/ViewChoosedCDTopic.js"></script>
<script>
	$(function() {		
		//生成分页
		//有些参数是可选的，比如lang，若不传有默认值
		kkpager.generPageHtml({
			pno : ${pageBean.currentPage},
			//总页码
			total : ${pageBean.totalPage},
			//总数据条数
			totalRecords : ${pageBean.allRows},
			//链接前部
			hrefFormer : 'student_findAllSelfChoosed',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}		 				 
		});
				
		//设置每一个模态框都可以拖拽
		$(".modal").draggable({
			handle : ".modal-header",
			cursor : "all-scroll"
		});

		//查看组员
		$("button.searchAllGroupMem").click(
				function() {
					//设置添加组员可用
					$("#addUserInfo").removeAttr("disabled");
					var stuGroupId = $(this).prev().val();
					$("#addStuGroupMembers").val(stuGroupId);
					var str = "";
					$.ajax({
						url : "student_findAllGroupMembers.action",
						data : {
							"studentgroup.stuGroupId" : stuGroupId
						},
						type : "post",
						dataType : "json",
						success : function(data) {
							var $container = $("#groupMembers");
							for (var i = 0; i < data.length; i++) {
								str += "<tr><td>" + (i + 1) + "</td><td>"
										+ data[i][0] + "</td><td>" + data[i][1]
										+ "</td><td>" + data[i][2]
										+ "</td><td>" + data[i][3]
										+ "</td><td>" + data[i][4]
										+ "</td></tr>";
							}
							$container.empty().append(str);
						},
						error : function(error) {
							alert(error.responseText);
						}
					});
				});	
		//查看班级选题情况
		$("button.searchClassDetail").click(function(){
			var cdplanId = $(this).prev().val();
			var str = "";
			$.ajax({
				url : "student_findAllCDByClaAndCD.action",
				data : {
					"cdPlanId" : cdplanId
				},
				type : "post",
				dataType : "json",
				async:false,
				success : function(data) {					 
					//获取到父容器
					var $topcontainer = $("div#classGroupInfoCont");
					//清空父容器里面的内容
					$topcontainer.empty();
					for(var i = 0;i<data.length;i++){						 
						//获取到每一个元素
					    var $elem = $("#hiddenPart>div").clone(true);					    
					    $elem.find(".topic").html(data[i][1]+
					    		"&nbsp;&nbsp;&nbsp;&nbsp;组长:<em class='text-danger'>"
					    		+data[i][2]+"</em>");	
					    //加载组员信息
					    $.ajax({
						url : "student_findAllGroupMembers.action",
						data : {
							"studentgroup.stuGroupId" : data[i][3]
						},
						type : "post",
						async:false,
						dataType : "json",
						success : function(data2) {
							var str2 = "";
							var $container = $elem.find("tbody.thcontent");
							for (var i = 0; i < data2.length; i++) {
								str2 += "<tr><td>"
										+ data2[i][0] + "</td><td>" + data2[i][1]
										+ "</td></tr>";
							}							
							
							$container.empty().append(str2);
						},
						error : function(error) {
							alert(error.responseText);
						}
					});
					$topcontainer.append($elem.show());
					    
					}															 				
				},
				error : function(error) {
					alert(error.responseText);
				}
			});
		});	
		
		//退出学生小组
		$("button.exitStuGroup").click(function(){
			var stuGroupId = $(this).prev().val();
		    $("#hiddenStuGroupId").val(stuGroupId);		    
		});
		
		//确认退出小组
		$("#sureExit").click(function(){
			window.location.href="student_exitStuGroup.action?stuGroupId="+$("#hiddenStuGroupId").val();
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
		<jsp:include page="../UIRef/JspRef/StudentLeft.jsp"></jsp:include>
		<!-- left-nav-end -->
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<!--面包屑导航-->
			<ol class="breadcrumb" style="width: 100%;">
				<li><a href="#">课程设计平台管理系统</a></li>
				<li><a href="#">课程设计选题管理</a></li>
				<li class="active">查看选题结果</li>
			</ol>
			<ul id="myTab" class="nav nav-tabs">
				<li class="active"><a href="#home" id="self" data-toggle="tab">
						自主选题 的结果</a></li>
				<li><a href="student_findAllChooseThCD.action">选择老师指定的题目的结果</a></li>
				<li><a href="student_findAllChoosedCD.action">所有选题结果</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="home">
					<!--查看所选择的内容-->
					<table class="table table-hover table-bordered">
						<thead>
							<tr class="text-primary">
								<th class="text-center">编号</th>
								<th class="text-center">课程设计题目编号</th>
								<th class="text-center">课程设计题目</th>
								<th class="text-center">课程设计简介</th>
								<th class="text-center">提交时间</th>
								<th class="text-center">是否通过</th>
								<th class="text-center">指导老师</th>
								<th class="text-center">指导老师意见</th>
								<th class="text-center">查看组员</th>
								<th class="text-center">查看班级分组的情况</th>
								<th class="text-center">操作</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="#request.pageBean.list" id="cdtopic"
								status="item">
								<tr>
									<td><s:property value="%{#item.getIndex()+1}" /></td>
									<td><s:property value="#cdtopic[0]" /></td>
									<td><s:property value="#cdtopic[1]" /></td>
									<td><s:property value="#cdtopic[2]" /></td>
									<td><s:date name="#cdtopic[3]"
											format="yyyy-MM-dd hh:MM:SS" /></td>
									<td><s:if test="#cdtopic[4]==0">
											<p class="text-danger">否</p>
										</s:if> <s:else>
											<p class="text-success">是</p>
										</s:else></td>
									<td><s:property value="#cdtopic[5]" /></td>
									<td><s:if test="#cdtopic[6] == null">
											<p class="text-warning">暂无导师评价</p>
										</s:if> <s:else>
											<p>
												<s:property value="#cdtopic[6]" />
											</p>
										</s:else> <s:property value="#cdtopic[6]" /></td>
									<td><input type="hidden"
										value="<s:property value="#cdtopic[7]" />" />
										<button class="btn btn-success searchAllGroupMem"
											data-toggle="modal" data-target="#searchGoupNumber">
											<span class="glyphicon glyphicon-user"></span>查看组员
										</button></td>
									<td><input type="hidden"
										value="<s:property value="#cdtopic[8]" />" />
										<button class="btn btn-info searchClassDetail"
											data-toggle="modal" data-target="#classGroupDetail">
											<span class="glyphicon glyphicon-education"></span>&nbsp;班级选题情况
										</button></td>
									<td><input type="hidden"
										value="<s:property value="#cdtopic[7]" />" />
										<button class="btn btn-danger exitStuGroup" data-toggle="modal"
											data-target="#ExitGroup">
											<span class="glyphicon glyphicon-log-in"></span>&nbsp;退出小组
										</button></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<div class="tab-pane fade" id="th"></div>
				<div class="tab-pane fade" id="all"></div>
			</div>

			<!--分页-->
			<div class="well well-sm text-center">
				<!-- 分页的位置 -->
				<div id="kkpager"></div>
			</div>

			<!--一下为模态框部分-->

			<!-- 查看组员模态框 -->
			<div class="modal fade" id="searchGoupNumber" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<input type="hidden" id="addStuGroupMembers" />
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">查看小组成员</h4>
						</div>
						<div class="modal-body">

							<div class="panel panel-info">
								<div class="panel-heading">
									<div class="clearfix">
										<span class="pull-left">组员信息</span> <span class="pull-right"><button
												id="addUserInfo" title="添加组员" class="btn btn-success"
												style="font-size: 10px;">
												<span class="glyphicon glyphicon-plus"></span>
											</button> </span>
									</div>
								</div>
								<div class="panel-body">
									<table class="table table-striped">
										<thead>
											<tr class="text-primary">
												<th>序号</th>
												<th>学号</th>
												<th>姓名</th>
												<th>班级</th>
												<th>学院</th>
												<th>专业</th>
											</tr>
										</thead>
										<tbody id="groupMembers">
										</tbody>
									</table>
								</div>
							</div>

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-warning"
								data-dismiss="modal">
								<span class="glyphicon glyphicon-share-alt"></span>关闭
							</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
			</div>

			<!--查看班级分配情况模态框-->
			<div class="modal fade bs-example-modal-lg" id="classGroupDetail"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">查看班级分配情况</h4>
						</div>
						<div class="modal-body">
							<!--控制面板-->
							<div class="well well-sm"
								style="border: none; background: white;">
								<button class="btn btn-primary" id="allShow">
									<span class='glyphicon glyphicon-resize-small'></span>全部收起
								</button>
								&nbsp;&nbsp;
								<button class="btn btn-success" id="allHide">
									<span class='glyphicon glyphicon-fullscreen'></span>全部展开
								</button>
							</div>
							<!--选择课题内容部分开始-->
							<div id="classGroupInfoCont"></div>
							<!--选择课题内容部分结束-->
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"
								data-dismiss="modal">关闭</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
			</div>

			<!--退出小组-->
			<div class="modal fade" id="ExitGroup" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">确认</h4>
						</div>
						<div class="modal-body">
							<h5 class="text-danger">
								<span class="glyphicon glyphicon-question-sign">&nbsp;你确定要退出该小组吗?</span>
							</h5>
						</div>
						<div class="modal-footer">
							<input type="hidden" id="hiddenStuGroupId" value=""/>
							<button type="button" class="btn btn-warning" id="sureExit">
								<span class="glyphicon glyphicon-log-out"></span>确定
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
	<!-- content-end -->

	<!-- 动态加载课题信息的div部分 -->
	<div id="hiddenPart" style="display: none;">
		<!--每一项的内容-->
		<div class="panel panel-info GroupShow">
			<div class="panel-heading">
				<div class="clearfix">
					<span class="pull-left"><span
						class="glyphicon glyphicon-tasks"></span><span class="topic"></span></span>
					<span class="pull-right">
						<button class="btn btn-success closeGroupInfo"
							style="font-size: 10px;">
							<span class="glyphicon glyphicon-chevron-up"></span>
						</button>
					</span>
				</div>
			</div>
			<div class="panel-body groupDetail">
				<table class="table table-striped table-hover">
					<thead class="text-danger">
						<tr>
							<th>学号</th>
							<th>姓名</th>
						</tr>
					</thead>
					<tbody class="thcontent">
						<!-- 动态加载的内容 -->
					</tbody>
				</table>
			</div>
		</div>
		<!-- 每一项的内容结束 -->
	</div>
</body>
</html>