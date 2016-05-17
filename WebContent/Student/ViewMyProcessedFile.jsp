<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<title>查看已经上传的过程材料</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/UIRef/js/ViewMyProcessedFile.js"></script>
<script type="text/javascript">
//init
$(function(){	
	
	var teacherId = "";//教师的编号
	var teacherGroupId ="";//教师小组的编号
	var cdDesignTipicId = "";//课程设计小组的编号
	var docId="";//文档编号
	
	//生成分页
	//有些参数是可选的，比如lang，若不传有默认值
	kkpager.generPageHtml({
		pno : ${pageBean.currentPage},
		//总页码
		total : ${pageBean.totalPage},
		//总数据条数
		totalRecords : ${pageBean.allRows},
		//链接前部
		hrefFormer : 'cddesigntask_searchCDTask',
		//链接尾部
		hrefLatter : '.action',
		getLink : function(n){
			return this.hrefFormer + this.hrefLatter + "?pno="+n;
		}		 				 
	});
	
		
	//点击查看所有上传的材料
	$("button.searchProcessDoc").click(function(){
		teacherId = $(this).prev().prev().val();
		teacherGroupId = $(this).prev().val();
		cdDesignTipicId = $(this).parents("tr").find("td").eq(1).text();
		//动态加载自己提交材料的信息部分
		$.ajax({
			url:"cddesigntask_findAllSubmited.action",
			data:{teacherId:teacherId,cdTeacherGroupId:teacherGroupId},
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				var str = "";
				var $container = $("#selfSubmit");
				for(var i = 0;i<data.length;i++){
					str +="<tr><td>"+(i+1)+"</td><td>"
					+data[i][0]+"</td><td>"+data[i][1]+"</td><td>"
					+convert(data[i][2].toString())+"</td><td>"+data[i][3]+"</td><td>"+isNull(data[i][4])
					+"</td><td><a class='btn btn-danger removeElem'><span class='glyphicon glyphicon-remove-sign'>"
					+"</span>&nbsp;删除</a></td><td><a href='cddesigntaskDownloadFile.action?processDocId="+data[i][0]
					+"' class='btn btn-success'>"
					+"<span class='glyphicon glyphicon-circle-arrow-down'>"
					+"</span>&nbsp;下载</a></td></tr>";
				}	
				$container.empty().append(str);				
			},
			error:function(error){
				alert(error.responseText);
			}
		});
		//动态加载其余同学提交材料的信息部分
		$.ajax({
			url:"cddesigntask_findAllStuGroupOtherSub.action",
			data:{teacherId:teacherId,cdTeacherGroupId:teacherGroupId,cdDesignTopicId:cdDesignTipicId},
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				var str = "";
				var $container = $("#groupOthers");
				for(var i = 0;i<data.length;i++){
					str += "<tr><td>"+(i+1)+"</td><td>"+data[i][0]+"</td><td>"+data[i][1]
					+"</td><td>"+data[i][2]+"</td><td>"+convert(data[i][3].toString())+"</td><td>"
					+"<a class='btn btn-success' href='cddesigntaskDownloadFile.action?processDocId="
							+data[i][0]+"'><span class='glyphicon "
					+"glyphicon-circle-arrow-down'></span>&nbsp;下载</a></td></tr>";
				}
				$container.empty().append(str);				 
			},
			error:function(error){
				alert(error.responseText);
			}
		});
		//绑定点击事件
		$("a.removeElem").click(function(){
			$("#SureDelete").modal();
			docId = $(this).parents("tr").find("td").eq(1).text();			 
		});		
	});
	
	//删除材料
	$("#sureDelete").click(function(){
		$.ajax({
			url:"cddesigntask_deleteDoc.action",
			data:{processDocId:docId},
			type:"post",
			dataType:"json",
			success:function(data){
				if(data == "innerError"){
					$("#info").empty().html("对不起服务器内部，暂时无法执行此操作!");
					$("#infoDescribtion").modal();					
				}else if(data == "opError"){
					$("#info").empty().html("对不起该该文件已经被批阅无法执行此操作!");
					$("#infoDescribtion").modal();					
				}else{					 
					//操作成功,重新加载数据
					$("#info").empty().html("删除成功!");
					$("#infoDescribtion").modal();
					//动态加载自己提交材料的信息部分
					$.ajax({
						url:"cddesigntask_findAllSubmited.action",
						data:{teacherId:teacherId,cdTeacherGroupId:teacherGroupId},
						type:"post",
						dataType:"json",
						async:false,
						success:function(data){
							var str = "";
							var $container = $("#selfSubmit");
							for(var i = 0;i<data.length;i++){
								str +="<tr><td>"+(i+1)+"</td><td>"
								+data[i][0]+"</td><td>"+data[i][1]+"</td><td>"
								+convert(data[i][2].toString())+"</td><td>"+data[i][3]+"</td><td>"+isNull(data[i][4])
								+"</td><td><a class='btn btn-danger removeElem'><span class='glyphicon glyphicon-remove-sign'>"
								+"</span>&nbsp;删除</a></td><td><a href='cddesigntaskDownloadFile.action?processDocId="+data[i][0]
								+"' class='btn btn-success'>"
								+"<span class='glyphicon glyphicon-circle-arrow-down'>"
								+"</span>&nbsp;下载</a></td></tr>";
							}	
							$container.empty().append(str);	
							//绑定点击事件
							$("a.removeElem").click(function(){
								$("#SureDelete").modal();
								docId = $(this).parents("tr").find("td").eq(1).text();			 
							});
						},
						error:function(error){
							alert(error.responseText);
						}
					});
				}
			},
			error:function(error){
				alert(error.responseText);
			}
		});
	});
	
});

//将时间戳转化为标准时间
function convert(Time) {
	var timeString = "";
	timeString = Time.replace("/Date(", "").replace(")/", "").trim();
	var quit = timeString.substring(7, 10);
	timeString = timeString.replace(quit, "");
	return new Date(parseInt(timeString)* 1000).toLocaleString();
}

//判断是否为空
function isNull(str){
	return str==null?"暂无评论":str;
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
				<li><a href="#">过程材料管理</a></li>
				<li class="active">查看已经提交的材料</li>
			</ol>
			<table class="table table-hover table-striped table-bordered">
				<thead>
					<tr class="text-primary">
						<th>序号</th>
						<th>课程设计编号</th>
						<th>课程设计题目</th>
						<th>指导老师</th>
						<th>操作</th>
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
								<button class="btn btn-success searchProcessDoc"
									data-toggle="modal" data-target="#myModal">
									<span class="glyphicon glyphicon-th-list"></span>查看过程材料
								</button>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<!--分页-->
			<div class="well well-sm text-center">
				<!-- 分页的位置 -->
				<div id="kkpager"></div>
			</div>
			<!--一下为模态框部分-->
			<!-- 查看资料模态框 -->
			<div class="modal fade bs-example-modal-lg" id="myModal"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title">查看课程设计过程材料</h4>
						</div>
						<div class="modal-body">
							<ul id="myTab" class="nav nav-tabs">
								<li class="active"><a href="#home" data-toggle="tab">
										查看自己提交的材料 </a></li>
								<li><a href="#ios" data-toggle="tab">查看成员提交的材料</a></li>

							</ul>
							<div id="myTabContent" class="tab-content">
								<!--自己提交的过程材料-->
								<div class="tab-pane fade in active" id="home">
									<table class="table table-striped">
										<thead>
											<tr class="text-info">
												<th>序号</th>
												<th>材料的编号</th>
												<th>材料的名称</th>
												<th>上传时间</th>
												<th>评分</th>
												<th>导师评语</th>
												<th>操作</th>
												<th>下载</th>
											</tr>
										</thead>
										<tbody id="selfSubmit">
											<tr>
												<td>1</td>
												<td>dsads009</td>
												<td>开题报告</td>
												<td>2015-04-05 22:08</td>
												<td>88</td>
												<td>内容详实</td>
												<td></td>
												<td></td>
											</tr>
										</tbody>
									</table>
								</div>
								<!--组员提交的过程材料-->
								<div class="tab-pane fade" id="ios">
									<table class="table table-striped">
										<thead>
											<tr class="text-info">
												<th>序号</th>
												<th>材料的编号</th>
												<th>材料的名称</th>
												<th>上传人</th>
												<th>上传时间</th>
												<th>下载</th>
											</tr>
										</thead>
										<tbody id="groupOthers">
											<tr>
												<td>1</td>
												<td>dsads009</td>
												<td>开题报告</td>
												<th>陈鹏</th>
												<th>2042</th>
												<td><a class="btn btn-success"><span
														class="glyphicon glyphicon-circle-arrow-down"></span>&nbsp;下载</a>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
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

			<!--确认删除-->
			<div class="modal fade" id="SureDelete" tabindex="-1" role="dialog"
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
								<span class="glyphicon glyphicon-question-sign">&nbsp;你确定要删除该资料吗?</span>
							</h5>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-warning" id="sureDelete">
								<span class="glyphicon glyphicon-ok-sign"></span>确定
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
		
		    <!--消息提示-->
			<div class="modal fade" id="infoDescribtion" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
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
	<!-- content-end -->
</body>
</html>