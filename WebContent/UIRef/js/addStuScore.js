$(document).ready(function() {
	$(".modal").draggable({
		handle : ".modal-header",
		cursor : "all-scroll"
	});
	//点击查看评分标准
	$("#showscoreSt").click(function(){
		//弹出评分标准模态框
		$("#Stdialog").modal();
	});
	
		//为添加分数按钮绑定事件
		$("label button").click(function() {
			
			var cdteachergroupId=$(this).parents("tr").find("td:eq(1)").text();
			var stuId = $(this).parents("tr").find("td:eq(0)").text();//当前行的第一列
			var cdplanId=$(this).parents("tr").find("td:eq(4)").text();
			var cddesignTopId=$(this).parents("tr").find("td:eq(6)").text();
			var stugroupid=$(this).parents("tr").find("td:eq(9)").text();
			$("#stuName").text($(this).parents("tr").find("td:eq(3)").text());
			$("#wqshowFile").empty();
			$("#wqshowReply").empty();
			$("#showSTandSco").empty();//清空评分标准
			//自评
			$("#selfScore").val($(this).parents("tr").find("td:eq(8)").text());
			
			$("#stuId").val(stuId);
			$("#cdplanId").val(cdplanId);
			$("#stugroupid").val(stugroupid);
			$("#cddesignTopId").val(cddesignTopId);
			$("#cdteachergroupId").val(cdteachergroupId);
			$.ajax({//获取平时材料情况
				type : "post",
				url : "StuSco_getUsualFile.action",
				data : {
					stuId:stuId,
					cdplanId : cdplanId,
					cddesignTopId : cddesignTopId,
					cdteachergroupId:cdteachergroupId,
					stugroupid:stugroupid
				},
				dataType : "json",
				success : function(data) {
					var str = "";
					for ( var i=0;i<data.length;i++) {
						str += "<table  class='table table-striped'><tr><td style=''>"+
								"<p class='text-danger'><span class='glyphicon glyphicon-paperclip'></span>&nbsp;材料名称:"+
								"</p></td><td><p class='text-danger'>"+data[i][0]+
								"</p></td></tr><tr><td style='width: 20%;'><p class='text-warning'>"+
								"<span class='glyphicon glyphicon-tags'></span>&nbsp;分数："+
								"</p></td><td><p class='text-info'>" +data[i][1]+"</p><a style='color:red;'>"+data[i][2]+"</a>"+
								"</td></tr>"+
								"</table>";
					}
					$("#wqshowFile").append(str);	
					
				}
			});//获取平时材料情况；
			
			$.ajax({//获取答辩情况
				type : "post",
				url : "StuSco_getreplyList.action",
				data : {
					stuId:stuId,
					cdplanId : cdplanId,
					cddesignTopId : cddesignTopId,
					cdteachergroupId:cdteachergroupId,
					stugroupid:stugroupid
				},
				dataType : "json",
				success : function(data) {
					var str = "";
					for ( var i=0;i<data.length;i++) {
						str += "<span class='glyphicon glyphicon-pushpin'></span></br><table  class='table table-striped'><tr><td style=''>"+
						"<p class='text-danger'><span class='glyphicon glyphicon-question-sign'></span>&nbsp;问题:"+
						"</p></td><td><p class='text-danger'>"+data[i][0]+
						"</p></td></tr><tr><td style='width: 20%;'><p class='text-warning'>"+
						"<span class='glyphicon glyphicon-comment'></span>&nbsp;回答："+
						"</p></td><td><p class='text-warning'>" +data[i][1]+
						"</p></td></tr>"+
						"<tr><td style='width: 20%;'><p class='text-info'><span class='glyphicon glyphicon-leaf'></span>"+
						"&nbsp;答辩老师：</p></td><td><p class='text-info'>" +data[i][2]+
						"</p></td></tr>" +
						"</table><hr/>";
					}
					$("#wqshowReply").append(str);	
					
				}
			});//获取答辩记录；
			
			$.ajax({//获取评分标准
				type : "get",
				url : "StuSco_getstandScore.action",
				data : {
					stuId:stuId,
					cdplanId : cdplanId,
					cddesignTopId : cddesignTopId,
					cdteachergroupId:cdteachergroupId,
					stugroupid:stugroupid
				},
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

	
