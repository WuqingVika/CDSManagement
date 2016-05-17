$(document).ready(function() {
	$(".modal").draggable({
		handle : ".modal-header",
		cursor : "all-scroll"
	});
	
		//为修改按钮绑定事件
		$("label button").click(function() {
			$("#showSTandSco").empty();//清空评分标准
			//成绩编号；
			var scoreId = $(this).parents("tr").find("td:eq(0)").text();//当前行的第一列
			var cdteachergroupId=$(this).parents("tr").find("td:eq(5)").text();//当前行的第一列
			//平时成绩
			//alert(cdteachergroupId);
			var examScore=$(this).parents("tr").find("td:eq(8)").text();
			//答辩成绩
			var replyScore=$(this).parents("tr").find("td:eq(10)").text();
			//考勤成绩
			var attendanceScore=$(this).parents("tr").find("td:eq(9)").text();
			//自评
			
			$("#attendanceScore").val(attendanceScore);
			$("#scoreId").val(scoreId);
			$("#replyScore").val(replyScore);
			$("#examScore").val(examScore);
			$.ajax({//获取评分标准
				type : "get",
				url : "StuSco_getstandScore.action?cdteachergroupId="+cdteachergroupId,
				
				dataType : "json",
				success : function(data) {
					var str = "";
					for ( var i=0;i<data.length;i++) {
						str += "<input type='hidden' name='attendCoe' value='"+data[i][0]+"'/>" +
								"<input type='hidden' name='procAccCoe' value='"+data[i][1]+"'/>" +
								"<input type='hidden' name='replyCoe' value='"+data[i][2]+"'/>" +
								"<input type='hidden' name='selfCoe' value='"+data[i][3]+"'/>";
					}
					$("#showSTandSco").append(str);	
				}
			});//获取评分标准；
			//弹出修改界面
			$("#Udialog").modal();
			});
	})

