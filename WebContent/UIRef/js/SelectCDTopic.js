$(function() {	 
	//全选全不选操作
	$("#checkAll").click(function() {
		$("input.checkItem").prop("checked", $(this).is(":checked"));
	});
	
	//设置每一个模态框都可以拖拽
	$("div.thmd").draggable({
		handle: ".modal-header",
		cursor: "all-scroll"
	});
	 
	//定义一个全局变量用于保存中间的div元素
	var $GroupItem = $("#ChosingGroup");
 
	$("#btnSelectCD").click(function() {
		if ($("#selectedCDCon").is(":empty")) {
			//把对应的div添加到父容器中
			$("#ChosingGroup").detach(); //在html文档对象中移除该对象
			$("#selectedCDCon").empty().append($GroupItem); //把中间的变量添加到容器中						 
			$("#ChosingGroup").slideDown(800); //显示到容器中
			$(this).html("<span class='glyphicon glyphicon-chevron-up'></span>收起，清空选组操作");
		} else {
			//移除容器中的div
			$("#ChosingGroup").slideUp(800);
			setTimeout(function() {
				$("#ChosingGroup").detach();
			}, 800);
			$(this).html("<span class='glyphicon glyphicon-chevron-down'><span>我已选择课题,开始组队");
		}
		return false;
	});
});