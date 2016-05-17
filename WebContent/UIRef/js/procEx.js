$(document).ready(function() {
	//alert("dkfjdskf");
	$("button[name='APEx']").click(function() {
			$("#pigaiTbody").empty();
			//var xid = $(this).parents("tr").find("td:eq(0)").text();
			//var filename = $(this).parents("tr").find("td:eq(6)").text();//获得文件名
			//var stuName = $(this).parents("tr").find("td:eq(2)").text();//当前行的第3列
			
			
//				$.ajax( {
//					type : "post",
//					url : "showScore.action?stuName=" + stuName+"&xid="+xid+"&filename="+filename,
//					dataType : "json",
//					success : function(data) {
//					//alert(data);
//					var tr;
//					
//					var j=0;
//					 for(var i in data){
//                  var jsonObj = data[i];
//                   tr += "<tr>";
//					tr += "<td  class='hidden' ><input type='text' name='expScores["+j+"].id' value='"+jsonObj.id+"'></td>";
//					tr += "<td  class=text-center><input type='text' readonly name='expScores["+j+"].tmid' value='"  + jsonObj.tmid + "'></td>";
//					tr += "<td  class=text-center ><input size='8' type='text' name='expScores["+j+"].score' value='" + jsonObj.score + "'> </td>";
//					tr += "</tr>";
//					j++;
//			   }
//					 //alert(parseInt(j));
//					 $("#pigaiTbody").append(tr);		
//				
//					  $("button[name='SureEdit']").click(function() {
//					     if (confirm("您确定要修改这位同学分数吗？")) {
//						window.location.href = "upListScore.action";
//							alert("修改成功！");
//						//这里再次调用						
//						//
//						
//					};
//					 });
//				}
//				});
				//弹出修改界面
					$("#edit").modal();
			});

		//为表单绑定验证事件
		$('#UForm').bootstrapValidator({
			message: 'This value is not valid',
        	feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
            	},
            fields: {
            	introduce: {
                validators: {
                    notEmpty: {
                        message: '活动介绍不能为空'
                    }
                }
            }
            }
		});

	


function ASub() {
	$("#AForm").submit();
}
});