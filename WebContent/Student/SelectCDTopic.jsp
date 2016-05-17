<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>选择课程设计题目</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/UIRef/js/SelectCDTopic.js"></script>
<script type="text/javascript">
//init
$(function(){
	
	//定义一个全局变量用于保存学生的索引值
	var stuIndex = 0;
	
	//生成分页
	//有些参数是可选的，比如lang，若不传有默认值
	kkpager.generPageHtml({
		pno : ${pageBean.currentPage},
		//总页码
		total : ${pageBean.totalPage},
		//总数据条数
		totalRecords : ${pageBean.allRows},
		//链接前部
		hrefFormer : 'student_searchCDChoosing',
		//链接尾部
		hrefLatter : '.action',
		getLink : function(n){
			return this.hrefFormer + this.hrefLatter + "?pno="+n;
		}		 				 
	});
	
	//查看评分标准
	$("button.seeStandards").click(function(){
		var standardsId = $(this).prev().val();
		$.ajax({
			url:"student_getEvalStandards.action",
			data:{scoreStandards:standardsId},
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				$("#attendCoe").empty().text(data.attendCoe);
				$("#procAccCoe").empty().text(data.procAccCoe);
				$("#replyCoe").empty().text(data.replyCoe);
				$("#selfCoe").empty().text(data.selfCoe);
				$("#entCod").empty().text(data.attendCoe);
				$("#procCod").empty().text(data.procAccCoe);
				$("#selfCod").empty().text(data.selfCoe);
				$("#repCod").empty().text(data.replyCoe);
			},
			error:function(error){
				alert(error.responseText);
			}
		});
				
	});
	
	//查看课程设计题目
	$("button.selectCD").click(function(){
		var groupId = $(this).prev().val();
		var id = $(this).parents("tr").find("td").eq(1).text();
		$("#teacherGroup").val(groupId); 	
		$("#tgroupId").val(groupId);
		//加载小组里的所有老师
		$.ajax({
			url:"student_getAllGroupTutors.action",
			data:{cdTeacherGroupId:groupId},
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				var str = "";
				for(var i = 0;i<data.length;i++){					 
					str += "<option value='"+data[i][0]+"'>"+data[i][1]+"</option>";				 
				}	
				$("#tutors").empty().append("<option value='-1'>--请选择导师--</option>").append(str);
			},
			error:function(error){
				alert(error.responseText);
			}		 
		});
		//加载所有的学生
		getStudents(id);
		//加载所有的教师小组下的课题信息
		getCdTopic(groupId);
	});
	
	//删除所选的组员
	$("button#deleteAll").click(function() {
		var $items = $("#myBody input.checkItem:checked");
		$.each($items, function(i, elem) {
			var $tr = $(elem).parents("tr");
			$tr.remove();
			$("#students button.userInfo[title=" + $tr.find("td").eq(1).text() + "]").attr("class","btn btn-info userInfo").removeAttr("disabled");
		});
	});
	
	//添加学生
	function getStudents(id){
		//定义结果字符串
		var str = "";		 
		$.ajax({
			url:"student_getAllStudetnJSON.action",		 
			type:"post",
			data:{cdPlanId:id},
			dataType:"json",
			async:false,
			success:function(data){
				for(var i = 0;i<data.length;i++){
					str += "<button type='button' class='btn btn-info userInfo' style='margin-left:10px;margin-bottom:10px;' stuId='" + data[i][0] + "' title='"+data[i][1]+"'><span class='glyphicon glyphicon-user'></span>" + data[i][2] + "</button>";
				}
				$("#students").empty().append(str);
				//添加
				$("button.userInfo").click(function() {
					var $element = $(this).attr({
						"disabled": "disabled",						
					}).addClass("btn-warning");
					var $result = $("<tr><td><input type='checkbox' class='checkItem'/></td><td>" + $element.attr("title") + "<input type='hidden' name='studentgroup.students.stuId' value='"+$element.attr("stuId")+"'/></td><td>" + $element.text() + "</td><td class='text-center'><button class='btn btn-danger removeContent'><span class='glyphicon glyphicon-remove'></span>删除</button></td></tr>");
					stuIndex++;
					$("#myBody").append($result);
					$result.find("button.removeContent").click(function() {
						var $element = $result.remove();
						var $tds = $element.find("td");
						$("#students button.userInfo[title=" + $tds.eq(1).text() + "]").removeAttr("disabled").removeClass("btn-warning");
					});
				});
			},
			error:function(error){
				alert(error.responseText);
			}
		});
	}
	
	//添加课题信息
	function getCdTopic(cdgroupId){
		var str = "";
		$.ajax({
			url:"student_getAllTopicsByTGId.action",		 
			type:"post",
			data:{cdTeacherGroupId:cdgroupId},
			dataType:"json",
			async:false,
			success:function(data){				 
				 var len = data.length;
				 for(var i=0;i<len;i++){
					 str+="<tr><td><input class='radioItem' type='radio' name='cdtgroup'/></td>"+
					 "<td>"+data[i][0]+"</td><td>"+data[i][1]+"</td><td>"
					 +data[i][2]+"</td><td>"+data[i][3]+"</td></tr>";
				 }
				 $("#teacherTopics").empty().append(str);	
				//选择课题
					$("input.radioItem").click(function(){
						var topicId = $(this).parents("tr").find("td").eq(1).text();
						$("#topicId").val(topicId);
					});
			},
			error:function(error){
				alert(error.responseText);
			}
		});
	}
	
	//点击自主选题
	$("#self").click(function(){
		$("#submitForm").attr("action","student_selfSelectCDTopic.action");
	});
	//点击选择指导老师题目
	$("#teacher").click(function(){
		$("#submitForm").attr("action","student_selectCDTopic.action");
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
				<li class="active">课程设计选题</li>
			</ol>
			<div class="panel panel-default">
				<div class="panel-body">
					<table class="table table-hover table-striped table-bordered">
						<thead>
							<tr class="text-primary">
								<th class="text-center">序号</th>
								<th class="text-center">课程计划号</th>
								<th class="text-center">课程计划名称</th>
								<th class="text-center">专业名称</th>
								<th class="text-center">学期</th>
								<th class="text-center">总学分</th>
								<th class="text-center">总学时</th>
								<th class="text-center">课程设计组组长</th>
								<th class="text-center">查看评分标准</th>
								<th class="text-center">操作</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="#request.pageBean.list" id="element"
								status="item">
								<tr>
									<td><s:property value="%{#item.getIndex()+1}" /></td>
									<td><s:property value="#element[0]" /></td>
									<td><s:property value="#element[1]" /></td>
									<td><s:property value="#element[2]" /></td>
									<td><s:property value="#element[3]" /></td>
									<td><s:property value="#element[4]" /></td>
									<td><s:property value="#element[5]" /></td>
									<td><s:property value="#element[6]" /></td>
									<td><input type="hidden"
										value="<s:property value="#element[7]" />" />
										<button class="btn btn-info seeStandards" data-toggle="modal"
											data-target="#evalStand">查看评分标准</button></td>
									<td><input type="hidden"
										value="<s:property value="#element[8]" />" />
										<button class="btn btn-primary selectCD" data-toggle="modal"
											data-target="#chosing">选择课题</button></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<div class="panel-footer text-center">
					<!-- 分页的位置 -->
					<div id="kkpager"></div>
				</div>
			</div>


			<!--下面是模态框部分-->
			<!-- 查看评分标准模态框 -->
			<div class="modal fade thmd" id="evalStand" tabindex="-1"
				role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">评分标准</h4>
						</div>
						<div class="modal-body">
							<table class="table table-hover table-striped">
								<tr>
									<td>考勤系数</td>
									<td>
										<p id="attendCoe" class="text-danger"></p>
									</td>
								</tr>
								<tr>
									<td>过程考核系数</td>
									<td>
										<p id="procAccCoe" class="text-success"></p>
									</td>
								</tr>
								<tr>
									<td>学生自评系数</td>
									<td>
										<p id="selfCoe" class="text-info"></p>
									</td>
								</tr>
								<tr>
									<td>答辩系数</td>
									<td>
										<p id="replyCoe" class="text-primary"></p>
									</td>
								</tr>
							</table>
							<p class="text-danger">
								<em> 成绩=考勤成绩*<span id="entCod" class="text-primary"></span>+过程考核成绩*<span
									id="procCod" class="text-primary"></span>+学生自评系数*<span
									id="selfCod" class="text-primary"></span>+答辩系数*<span
									id="repCod" class="text-primary"></span>
								</em>
							</p>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"
								data-dismiss="modal">关闭</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
			</div>
			<!-- /.modal -->

			<!--选择课题模态框 -->
			<div class="modal fade bs-example-modal-lg thmd" id="chosing"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<form action="student_selfSelectCDTopic.action" id="submitForm"
					method="post">
					<!-- 隐藏域设定课程设计教师小组的编号 -->
					<input type="hidden" id="tgroupId"
						name="cddesigntopics.cdteachergroup.cdteacherGroupId" value="" />
					<!-- 隐藏域结束 -->
					<!-- 选择课题的隐藏域 -->
					<input type="hidden" id="topicId" name="studentgroup.cddesigntopics.cddesignTopId" />
					<!-- 隐藏于结束 -->
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">选择课程设计题目</h4>
							</div>
							<div class="modal-body">
								<!--选择课程设计题目-->
								<div class="panel panel-info">
									<div class="panel-heading">
										<span class="glyphicon glyphicon-pencil"></span>选择课程设计题目
									</div>
									<div class="panel-body">
										<ul id="myTab" class="nav nav-tabs">
											<li class="active"><a href="#home" id="self"
												data-toggle="tab"> 自主选题 </a></li>
											<li><a href="#ios" id="teacher" data-toggle="tab">选择老师指定的题目</a></li>
										</ul>
										<div id="myTabContent" class="tab-content">
											<div class="tab-pane fade in active" id="home">
												<!-- 自主选题 -->
												<div role="form">
													<div class="form-group">
														<label for="name" class="text-primary">课程设计名称</label> <input
															name="cddesigntopics.topics" type="text"
															class="form-control" placeholder="请输入课程设计名称">
													</div>
													<div class="form-group">
														<label for="name" class="text-primary">请选择指导老师</label> <select
															name="cddesigntopics.teacher.teacherId" id="tutors"
															class="form-control">
															<option value="-1">--请选择指导老师--</option>
														</select>
													</div>
													<div class="form-group">
														<label for="name" class="text-primary">课程设计简介</label>
														<textarea class="form-control" rows="3"
															name="cddesigntopics.topicsDescribtion"
															placeholder="请输入课程设计简介"></textarea>
													</div>
												</div>
											</div>
											<div class="tab-pane fade" id="ios">
												<!-- 选择老师指定的题目 -->
												<table
													class="table table-hover table-striped table-bordered">
													<thead>
														<tr class="text-primary">
															<th style="width: 60px;" class="text-left">选择</th>
															<th class="text-center">课程设计题目编号</th>
															<th class="text-center">课程设计名称</th>
															<th class="text-center">课程设计简介</th>
															<th class="text-center">指导老师</th>
														</tr>
													</thead>
													<tbody id="teacherTopics">
														<!-- 这里是动态加载的部分 -->
													</tbody>
												</table>
											</div>
										</div>
										<!--组队-->
										<!--选题完毕-->
										<button type="button" class="btn btn-info btn-block"
											id="btnSelectCD">我已选题，组建团队</button>
									</div>
								</div>
								<!--这里是动态加载学生组员信息的部分-->
								<div id="selectedCDCon"></div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
								<button type="submit" class="btn btn-primary">提交更改</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</div>
				</form>
			</div>
			<!-- /.modal -->


			<!--这是选择学生小组信息的div-->
			<div id="ChosingGroup" style="display: none;">
				<div class="panel panel-success">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-check"></span>选择组员
					</div>
					<div class="panel-body">
						<div style="background: lightgoldenrodyellow; border: none;"
							class="well" style="padding-bottom: 20px;padding-top: 20px;"
							id="students">
							<span class="text-info"><em>请选择你的组员,可以选择的组员有:</em></span> <br />
							<hr />
							<!--里面是具体的同学信息-->
						</div>
						<table class="table table-bordered table-hover">
							<thead>
								<tr>
									<th class="text-primary" style="width: 120px;"><input
										type="checkbox" id="checkAll" />&nbsp;全选/全不选</th>
									<th class="text-primary text-center">组员学号</th>
									<th class="text-primary text-center">组员姓名</th>
									<th class="text-primary text-center">操作</th>
								</tr>
							</thead>
							<tbody id="myBody">
								<!--里面是具体的内容-->
							</tbody>
						</table>
					</div>
					<div class="panel-footer clearfix">
						<button type="button" class="btn btn-warning pull-right"
							id="deleteAll">
							<span class="glyphicon glyphicon-share-alt"></span>删除选中的组员
						</button>
					</div>
				</div>
			</div>
			<!--选择学生小组信息的div结束-->

		</div>
	</div>
	<!-- content-end -->
</body>
</html>