$(document).ready(function() {
	$(".modal").draggable({
		handle : ".modal-header",
		cursor : "all-scroll"
	});
	$("#startTime").Zebra_DatePicker({
			  direction: true,
			  pair: $("#endTime")
			});
			$('#table1 tbody tr:odd').css('background-color','#DDFFFF');
			$('#table1 tbody tr:even').css('background-color','#E8DBF0');
			$("#endTime").Zebra_DatePicker({
			  direction: 1
			});
			
	//为删除按钮绑定事件
		$("button[name='deletePlan']").click(function() {
			var processAssShId = $(this).parents("tr").find("td:eq(0)").text();//当前行的第一列
				if (confirm("您确定要删除这条记录吗？")) {
					window.location = "teacherMakeProPlan_del.action?processAssShId="
					+ processAssShId;
				}
			});
	//createSubjectCombox();
	
		
		//为修改按钮绑定事件
		$("label button").click(function() {
			$("#startTime").Zebra_DatePicker({
			  direction: true,
			  pair: $("#endTime")
			});
			
			$("#endTime").Zebra_DatePicker({
			  direction: 1
			});
			
			var id = $(this).parents("tr").find("td:eq(0)").text();//当前行的第一列
			//alert(id);
			$("#processAssShId").val(id);
			$("#ProcessName").val($(this).parents("tr").find("td:eq(2)").text());
			//开始时间
			$("#startTime").val($(this).parents("tr").find("td:eq(4)").text());
			//结束时间
			$("#endTime").val($(this).parents("tr").find("td:eq(5)").text());
			//结束时间
			$("#ProcessDescribtion").val($(this).parents("tr").find("td:eq(3)").text());
			//弹出修改界面
			$("#Udialog").modal();
			});
	})


function ASub() {
	$("#AForm").submit();
}
