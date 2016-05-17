<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<title>查看过程计划</title>
<script>
	$(function() {
		var teacherGroupId;
		var teacherId;
		
		//设置每一个模态框都可以拖拽
		$(".modal").draggable({
			handle : ".modal-header",
			cursor : "all-scroll"
		});
		
		//生成分页
		//有些参数是可选的，比如lang，若不传有默认值
		kkpager.generPageHtml({
			pno : ${pageBean.currentPage},
			//总页码
			total : ${pageBean.totalPage},
			//总数据条数
			totalRecords : ${pageBean.allRows},
			//链接前部
			hrefFormer : 'cddesigntask_searchAllCD',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}		 				 
		});
		
		//查看课程设计计划
		$("button.searchCDP").click(function(){
			teacherGroupId = $(this).prev().val();
			teacherId = $(this).prev().prev().val();
			var taskName = $(this).parents("tr").find("td").eq(2).text();
			$("#toExcel").attr("href","cddesigntaskExcel.action?teacherId="+teacherId+"&cdTeacherGroupId="+teacherGroupId+"&taskName="+taskName);
			$.ajax({
				url:"cddesigntask_findAllProcShedulePlan.action",
				data:{teacherId:teacherId,cdTeacherGroupId:teacherGroupId},
				type:"post",
				dataType:"json",
				success:function(data){
					var $container = $("#shedualContent");	
					var str = "";
					for(var i = 0;i<data.length;i++){
						str +="<tr><td>"+(i+1)+"</td><td>"+data[i][0]
						+"</td><td>"+data[i][1]+"</td><td>"+convert(data[i][2].toString())+"</td><td>"+convert(data[i][3].toString())+"</td></tr>";
					}
					$container.empty().append(str);
				},
				error:function(error){
					alert(error.responseText);
				}
			});
		});
				 
	});
	
	// 将时间戳转化为标准时间
	function convert(Time) {
		var timeString = "";
		timeString = Time.replace("/Date(", "").replace(")/", "").trim();
		var quit = timeString.substring(7, 10);
		timeString = timeString.replace(quit, "");
		return new Date(parseInt(timeString)* 1000).toLocaleString();
	}
 
 
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
				<li class="active">查看过程考核计划</li>
			</ol>
			<table class="table table-hover table-striped table-bordered">
				<thead>
					<tr class="text-primary">
						<th>编号</th>
						<th>课程设计编号</th>
						<th>课程设计题目</th>
						<th>指导老师</th>
						<th>查看阶段任务</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.pageBean.list" id="cd" status="item">
						<tr>
							<td><s:property value="%{#item.getIndex()+1}" /></td>
							<td><s:property value="#cd[0]" /></td>
							<td><s:property value="#cd[1]" /></td>
							<td><s:property value="#cd[2]" /></td>
							<td>
								<input type="hidden" value="<s:property value="#cd[3]" /> " /> 
								<input type="hidden" value="<s:property value="#cd[4]" />" /> 
								<button class="btn btn-success searchCDP" data-toggle="modal"
									data-target="#searchCDPlan">查看阶段性任务</button></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<!--分页-->
			<div class="well well-sm text-center">
				<!-- 分页的位置 -->
				<div id="kkpager"></div>
			</div>

			<!--下面是模态框部分-->
			<!-- 查看课程设计阶段的任务 -->
			<div class="modal fade bs-example-modal-lg" id="searchCDPlan"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">查看课程设计阶段任务</h4>
						</div>
						<div class="modal-body">
							<div class="well well-sm"
								style="border: none; background: white; height: 30px;">
								<div class="clearfix">
									<a class="btn btn-primary pull-right" id="toExcel" href="cddesigntaskExcel.action">
										<span class="glyphicon glyphicon-log-out"></span>导出为excel
									</a>
								</div>
							</div>
							<table class="table table-hover table-striped table-bordered">
								<thead style="background: lightblue;">
									<tr class="text-primary">
										<th>序号</th>
										<th>过程考核名称</th>
										<th>过程考核详细</th>
										<th>开始时间</th>
										<th>结束时间</th>
									</tr>
								</thead>
								<tbody id="shedualContent">
								<!-- 这里是动态加载的部分 -->								 
								</tbody>
							</table>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-warning"
								data-dismiss="modal">
								<span class="glyphicon glyphicon-remove-circle"></span>关闭页面
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
</body>
</html>