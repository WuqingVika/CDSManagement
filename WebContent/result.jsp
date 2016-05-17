<!-- 
	***************************该页面是分页的测试页面**********************
 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 首先要引入jquery类库 -->
<script type="text/javascript" src="UIRef/jQuery/jquery-2.2.2.min.js"></script>
<!-- 引入分页的javacript类库 -->
<script type="text/javascript" src="UIRef/js/kkpager.min.js"></script>
<!-- 引入分页的css样式 -->
<link rel="stylesheet" href="UIRef/css/kkpager_blue.css">
<script type="text/javascript">
//init
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
		hrefFormer : 'test_testPageBean',
		//链接尾部
		hrefLatter : '.action?',
		getLink : function(n){
			return this.hrefFormer + this.hrefLatter + "?pno="+n;
		}		 				 
	});
});
</script>
<title>Insert title here</title>
</head>
<body>
	<center>
		<a href="toExcel.action">导出为excel</a>
		<a href="toPDF.action">导出为pdf</a>
		<s:form action="exportExcel.action" method="post" enctype="multipart/form-data">		
			<s:file name="uploadFile"></s:file>
			<br/>
			<s:submit value="导入excel"></s:submit>
		</s:form>
		
		<table border="1" cellpadding="0" cellspacing="0" width="100%">
			<thead style="background: lightblue;">
				<tr>
					<th>编号</th>
					<th>数据库序号</th>
					<th>账号</th>
					<th>密码</th>
					<th>角色</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#request.pageBean.list" id="acc" status="item">
					<tr>
						<td><s:property value="%{#item.getIndex()+1}" /></td>
						<td><s:property value="#acc.accId" /></td>
						<td><s:property value="#acc.accountId" /></td>
						<td><s:property value="#acc.passwords" /></td>
						<td><s:property value="#acc.role" /></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>		
	</center>
	<!-- 分页的位置 -->
	<div id="kkpager"></div>	 
</body>
</html>