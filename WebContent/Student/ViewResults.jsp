<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../UIRef/JspRef/MainFrameworkRef.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<title>查看成绩</title>
<script>
	$(function() {			 	
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
			hrefFormer : 'cddesigntask_findStudentScore',
			//链接尾部
			hrefLatter : '.action',
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "?pno="+n;
			}		 				 
		});
		
			
		//将时间戳转化为标准时间
		function convert(Time) {
			var timeString = "";
			timeString = Time.replace("/Date(", "").replace(")/", "").trim();
			var quit = timeString.substring(7, 10);
			timeString = timeString.replace(quit, "");
			return new Date(parseInt(timeString)* 1000).toLocaleString();
		}
		
		//查看答辩情况
		$("button.searchReply").click(function(){
			$("#title").html($(this).parents("tr").find("td").eq(3).text()+"&nbsp;&nbsp;课程设计答辩情况");
			$("#replyScore").html($(this).parents("tr").find("td").eq(6).text());
			var stuGroupId = $(this).prev().val();			
			//下面为加载答辩内容
			$.ajax({
				url:"cddesigntask_findReplyRecords.action",
				data:{stuGroupId:stuGroupId},
				dataType:"json",
				type:"post",
				success:function(data){					 
					//获取容器
					var $container = $("#replyDetails");									
					for(var i = 0;i<data.length;i++){					 					
						var $elem = $("#replyPart").clone(true);
						//添加问题
						$elem.find(".teacher").html(data[i][1]);
						$elem.find(".teachQuestion").html(data[i][0]);
						//添加答案
						$elem.find(".stuAnswer").html(data[i][2]);
						$elem.find(".replyTime").html(convert(data[i][3].toString()));
						$container.append($elem.show());
					}
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
				<li><a href="#">课程设计成绩查询</a></li>
				<li class="active">查询课程设计成绩</li>
			</ol>
			<!--查询内容-->
			<table class="table table-hover table-bordered table-striped">
				<thead>
					<tr class="text-primary">
						<th class="text-center">序号</th>
						<th class="text-center">课程设计编号</th>
						<th class="text-center">课程设计计划名</th>
						<th class="text-center">课程设计课题名称</th>
						<th class="text-center">指导老师</th>
						<th class="text-center">考勤成绩</th>
						<th class="text-center">答辩成绩</th>
						<th class="text-center">考核成绩</th>
						<th class="text-center">自评成绩</th>
						<th class="text-center">总分</th>
						<th class="text-center">查看答辩情况</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="#request.pageBean.list" id="record"
						status="item">
						<tr>
							<td><s:property value="%{#item.getIndex()+1}" /></td>
							<td><s:property value="#record[0]" /></td>
							<td><s:property value="#record[1]" /></td>
							<td><s:property value="#record[2]" /></td>
							<td><s:property value="#record[3]" /></td>
							<td><s:property value="#record[4]" /></td>
							<td><s:property value="#record[5]" /></td>
							<td><s:property value="#record[6]" /></td>
							<td><s:property value="#record[7]" /></td>
							<td><s:property value="#record[8]" /></td>
							<td class="text-center"><input type="hidden"
								value="<s:property value="#record[9]" />" />
								<button class="btn btn-info searchReply" data-toggle="modal"
									data-target="#myModal">
									<span class="glyphicon glyphicon-comment"></span>&nbsp;查看答辩情况
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
			<!-- 模态框（Modal） -->
			<div class="modal fade bs-example-modal-lg" id="myModal"
				tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
				aria-hidden="true">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">查看答辩情况</h4>
						</div>
						<div class="modal-body">
							<div id="myCommentsConent">
								<!--每一次加载的内容-->
								<div class="panel panel-info">
									<div class="panel-heading">
										<div class="clearfix">
											<span class="pull-left"> <span
												class="glyphicon glyphicon-leaf"></span><span id="title"></span>
											</span> <span class="pull-right"> 得分:<span
												class="text-danger" id="replyScore"></span>分.
											</span>
										</div>
									</div>
									<div class="panel-body" id="replyDetails">
										<!--里面是具体的内容-->
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary"
								data-dismiss="modal">
								<span class="glyphicon glyphicon-share-alt"></span>关闭页面
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

	<!-- 公共的部分 -->
	<table class="table table-striped" id="replyPart"
		style="display: none;">
		<tr>
			<td style="">
				<p class="text-danger">
					<span class="glyphicon glyphicon-question-sign"></span><span
						class="teacher"></span>&nbsp;问题:
				</p>
			</td>
			<td>
				<p class="text-danger teachQuestion"></p>
			</td>
		</tr>
		<tr>
			<td style="width: 20%;">
				<p class="text-warning">
					<span class="glyphicon glyphicon-comment"></span>&nbsp;回答：
				</p>
			</td>
			<td>
				<p class="text-info stuAnswer"></p>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<em class="text-info"><span>提问时间:&nbsp;</span><span class="replyTime"></span></em>				
			</td>
		</tr>
	</table>
</body>
</html>