$(document).ready(function() {

		//为修改按钮绑定事件
		$("label button").click(function() {
			
//			var id = $(this).parents("tr").find("td:eq(0)").text();//当前行的第一列
//				$.ajax( {
//					type : "get",
//					url : "questionServlet?operaType=3&quesId=" + id,
//					dataType : "json",
//					success : function(data) {
//						//初始化数据
//					$("#id").val(data.id);
//					$("#Usubjects").val(data.subjectId);
//					$("#questionType").val(data.questionTypeId);
//					$("#questionContent").val(data.questionContent);
//					$("#choiceA").val(data.choiceA);
//					$("#choiceB").val(data.choiceB);
//					$("#choiceC").val(data.choiceC);
//					$("#choiceD").val(data.choiceD);
//					$("#rightAnswer").val(data.rightAnswer);
//				}
//				});
				//弹出修改界面
				$("#Udialog").modal();

			});
		//模态框可移动
		$(".modal").draggable({   
			    handle: ".modal-header",   
			    cursor: 'move',   
			    refreshPositions: false  
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
            	questionContent: {
                validators: {
                    notEmpty: {
                        message: '试题内容不能为空'
                    }
                }
            },
            
            },
		});

	})
//填充科目
function createSubjectCombox() {
	$.ajax( {
		type : "get",
		url : "subjectServlet",
		dataType : "json",
		success : function(data) {
			var optionString = "<option value=\"0\">--请选择法律科目--</option>";
			for ( var i in data) {
				var json = data[i];
				optionString += "<option value=\"" + json.subjectId + "\">"
						+ json.subjectname + "</option>";
			}
			$("#subjects").append(optionString);
			$("#Usubjects").append(optionString);
			$("#Asubjects").append(optionString);
			//选中科目、题型
			$("#subjects").val($("#si").val());
			$("#quesType").val($("#qi").val());

		}

	})
}

//更改pageSize
function querySize() {
	window.location = "questionServlet?currentPage=" + $("#currentPage").val()
			+ "&pageSize=" + $("#amount").val() + "&subjectId="
			+ $("#subjects").val() + "&quesType=" + $("#quesType").val()
			+ "&quesContent=" + $("#quesContent").val();

}

function ASub() {
	$("#AForm").submit();
}
