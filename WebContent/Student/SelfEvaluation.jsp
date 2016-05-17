<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<title>课程设计自我评价</title>
<script>
	$(function() {
		//定义一个课程设计编号
		var cdDesignTopicId = "";
		//定义一个小组编号
		var stuGroupId = "";
		
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
			hrefFormer : 'cddesigntask_toSelfEval',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}		 				 
		});
		
		//课程设计自评
		$("button.selfEval").click(function(){
			//获取到当前的课程设计编号
			$("#hidCdTopicId").val($(this).parents("tr").find("td").eq(1).text());		 
		});
		
		//查看组员情况
		$("button.groupEval").click(function(){
			//获取到小组的编号
			stuGroupId = $(this).prev().val();
			//获取到当前课程设计的编号
			cdDesignTopicId = $(this).parents("tr").find("td").eq(1).text();	
			//加载组员自评情况
			$.ajax({
				url:"cddesigntask_searchStuGroupEval.action",
				data:{stuGroupId:stuGroupId,cdDesignTopicId:cdDesignTopicId},
				type:"post",
				dataType:"json",
				success:function(data){
					var $container = $("#stuGroupContent");
					var str = "";
					for(var i = 0;i<data.length;i++){
						str += "<tr><td>"+(i+1)+"</td><td>"+data[i][0]+"</td><td>"+data[i][1]+"</td><td>"+data[i][2]+"</td><td>"+data[i][3]+"</td></tr>";
					}	
					$container.empty().append(str);
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
		<jsp:include page="../UIRef/JspRef/StudentLeft.jsp"></jsp:include>
		<!-- left-nav-end -->
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
			<!--面包屑导航-->
			<ol class="breadcrumb" style="width: 100%;">
				<li><a href="#">课程设计平台管理系统</a></li>
				<li><a href="#">课程设计自评管理</a></li>
				<li class="active">自评</li>
			</ol>
			<table class="table table-hover table-striped table-bordered">
				<thead>
					<tr class="text-primary">
						<th class="text-center">序号</th>
						<th class="text-center">课程设计编号</th>
						<th class="text-center">课程设计名称</th>
						<th class="text-center">指导老师</th>
						<th class="text-center">自评</th>
						<th class="text-center">查看评价</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.pageBean.list" id="cddesigntopic"
						status="item">
						<tr>
							<td><s:property value="%{#item.getIndex()+1}" /></td>
							<td><s:property value="#cddesigntopic[0]" /></td>
							<td><s:property value="#cddesigntopic[1]" /></td>
							<td><s:property value="#cddesigntopic[2]" /></td>
							<td>
								<button class="btn btn-success selfEval" data-toggle="modal"
									data-target="#myModal">
									<span class="glyphicon glyphicon-thumbs-up"></span>添加个人自评
								</button>
							</td>
							<td><input type="hidden"
								value="<s:property value="#cddesigntopic[5]" />" />
								<button class="btn btn-info groupEval" data-toggle="modal"
									data-target="#searchComments">
									<span class="glyphicon glyphicon-search"></span>查看组员自评情况
								</button></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<!--分页部分-->
			<div class="well well-sm text-center">
				<!-- 分页的位置 -->
				<div id="kkpager"></div>
			</div>

			<!--模态框部分-->
			<!-- 自我评价模态框 -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<form action="cddesigntask_addSelfEvaluation.action" method="post">
						<input type="hidden" value="" id="hidCdTopicId" name="cdDesignTopicId" />
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">课程设计自我评价</h4>
							</div>
							<div class="modal-body">
								<div class="form-horizontal" role="form">
									<div class="form-group">
										<label for="selfScore" class="col-sm-2 control-label">自评分数:</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="selfScore"
												placeholder="请输入课程设计自我评价分数" name="selfevaluation.selfScore">
										</div>
									</div>
									<div class="form-group">
										<label for="resons" class="col-sm-2 control-label">评分理由:</label>
										<div class="col-sm-10">
											<textarea class="form-control" id="resons" name="selfevaluation.selfDescribtion"
												placeholder="请输入课程设计评价的理由"></textarea>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-warning"
									data-dismiss="modal">
									<span class="glyphicon glyphicon-share-alt"></span>关闭窗口
								</button>
								<button type="submit" class="btn btn-primary">
									<span class="glyphicon glyphicon-ok-sign"></span>提交自评
								</button>
							</div>
						</div>
						<!-- /.modal-content -->
					</form>
				</div>
				<!-- /.modal -->
			</div>

			<!--查看组员自评情况的模态框-->
			<div class="modal fade bs-example-modal-lg" id="searchComments"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">查看组员自评情况</h4>
						</div>
						<div class="modal-body">
							<table class="table table-striped table-bordered table-hover">
								<thead class="text-primary">
									<tr>
										<th>序号</th>
										<th>组员学号</th>
										<th>组员姓名</th>
										<th>自评分数</th>
										<th>自评说明</th>
									</tr>
								</thead>
								<tbody id="stuGroupContent">
									 <!-- 这里是动态加载的内容 -->
								</tbody>
							</table>
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
	<!-- content-end -->
</body>
</html>