<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<title>上传材料</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/UIRef/js/ViewChoosedCDTopic.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/UIRef/js/UploadProcessFile.js"></script>
<script>
	$(function(){
		//生成分页
		//有些参数是可选的，比如lang，若不传有默认值
		kkpager.generPageHtml({
			pno : ${pageBean.currentPage},
			//总页码
			total : ${pageBean.totalPage},
			//总数据条数
			totalRecords : ${pageBean.allRows},
			//链接前部
			hrefFormer : 'cddesigntask_uploadCDTask',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}		 				 
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
				<li class="active">上传阶段性任务材料</li>
			</ol>
			<table class="table table-bordered table-striped">
				<thead>
					<tr class="text-primary">
						<th class="text-center">序号</th>
						<th class="text-center">课程设计编号</th>
						<th class="text-center">课程设计名称</th>
						<th class="text-center">课程设计简介</th>
						<th class="text-center">操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.pageBean.list" id="cd" status="item">
						<tr>
							<td><s:property value="%{#item.getIndex()+1}" /></td>
							<td><s:property value="#cd[0]" /></td>
							<td><s:property value="#cd[1]" /></td>
							<td><s:property value="#cd[2]" /></td>
							<td><input type="hidden"
								value="<s:property value="#cd[3]" /> " /> <input type="hidden"
								value="<s:property value="#cd[4]" />" />
								<button class="btn btn-success seeTask">
									查看计划任务&nbsp;<span class="glyphicon glyphicon-folder-open"></span>
								</button>
						</tr>
					</s:iterator>
				</tbody>
			</table>

			<div class="well well-sm text-center">
				<!-- 分页的位置 -->
				<div id="kkpager"></div>
			</div>

		</div>
	</div>
	<!-- content-end -->

	<!-- 上传文件 -->
	<div class="modal fade" id="UploadFile" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<span class="glyphicon glyphicon-open"></span>上传文件
					</h4>
				</div>
				<div class="modal-body text-center">
					<form action="cddesigntaskUploadFile.action" enctype="multipart/form-data"
						method="post">
						<input type="hidden" id="file" value="" name="processSheId"/>
						<center>						 
							<input type="file" name="uploadFile"
								style="border: 1px solid gray; border-radious: 2px; background-color: gray;" />
							<br /> <input type="submit" value="开始提交" class="btn btn-primary" />
						</center>
					</form>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>

</body>
</html>