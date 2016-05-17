$(function() {

	//设置每一个模态框都可以拖拽
	$("div.modal").draggable({
		handle: ".modal-header",
		cursor: "all-scroll"
	});
	$("#stugroupidSelect").click(function(){
		$("#stugroupid").val($(this).val());
	});
	
	$("#cddesignTopIdSelect").click(function(){
		$("#cddesignTopId").val($(this).val());
	});
	$("#cddesignTopIdSelect").change(function(){
		$("#cddesignTopId").val($(this).val());
	});
	//联动查询题目编号 
	$("#stugroupidSelect").click(function(){
		$("#stugroupid").val($(this).val());
		$("#cddesignTopIdSelect").empty();	
		getcddesignTopId();
		$("#cddesignTopId").val($("#cddesignTopIdSelect").val());
	});
	//将时间戳转化为标准时间
	function convert(Time) {
		var timeString = "";
		timeString = Time.replace("/Date(", "").replace(")/", "").trim();
		var quit = timeString.substring(7, 10);
		timeString = timeString.replace(quit, "");
		return new Date(parseInt(timeString)* 1000).toLocaleString();
	}
function getcddesignTopId(){
		
		var stugroupid=$("#stugroupidSelect").val();
		$.ajax({
			type : "get",
			url : "Reply_replyCName.action?stugroupid="+stugroupid,
			dataType : "json",
			success : function(data) {
				var optionString = "";
				for ( var i=0;i<data.length;i++) {
					var json = data[i];	
					optionString += "<option value=\"" + data[i][0] + "\">"
							+ data[i][1]+ "</option>";
				}
				$("#cddesignTopIdSelect").append(optionString);	
				$("#cddesignTopId").val($("#cddesignTopIdSelect").val());
			}
		});
	}
	
	//答辩
	$("button[name='reply']").click(function() {
		$("#divagain").empty();
		$("#addReply").attr("disabled", false);
		$("#wqReply").empty();//清空里面记录的数据
		var stuName=$(this).parents("tr").find("td:eq(2)").text();//学生姓名
		$("#stuName").text(stuName);
		var replyplanId=$(this).parents("tr").find("td:eq(0)").text();//答辩编号
		var stuId=$(this).parents("tr").find("td:eq(1)").text();//学号
		$.ajax({
			type : "get",
			url : "Reply_getRecord.action?replyplanId="+replyplanId+"&stuId="+stuId,
			dataType : "json",
			success : function(data) {
				var str="";
				for(var i=0;i<data.length;i++){
					str+="<table name='wqtable' class='table table-striped'><tr><td style=''>";
					str+="<p class='text-danger'><span class='glyphicon glyphicon-question-sign'></span>&nbsp;问题:";
					str+="</p></td><td><p class='text-danger'>"+data[i][4]+"?" +
							"</p></td></tr><tr><td style='width: 20%;'><p class='text-warning'>" +
							"<span class='glyphicon glyphicon-comment'></span>&nbsp;回答：" +
							"</p></td><td><p class='text-info'>"+data[i][5]+"</p></td></tr>" +
							"<tr><td style='width: 20%;'><p class='text-info'><span class='glyphicon glyphicon-time'></span>" +
							"&nbsp;时间：</p></td><td><p class='text-info'>"+convert(data[i][6].toString())+"</p>" +
							"</td></tr></table>";
				}
				$("#wqReply").append(str);
			}
			
			
		});
		$("#Replydialog").modal();
		//添加问题记录
		$("#addReply").click(function() {
			//alert("hi");
			$("#divagain").empty();
			//alert(stuId+"   "+replyplanId);
			var str = 
			"<form action='Reply_addRec.action' method='post' class='form form-inline'><table class='table table-striped'><tr><td style=''><p class='text-danger'>" +
			"<input type='hidden' value='"+stuId+"' name='stuId'/>" +
			"<input type='hidden' value='"+replyplanId+"' name='replyplanId'/>"+
			"<span class='glyphicon glyphicon-question-sign'></span>&nbsp;问题:</p></td><td><p class='text-danger'>"+
			"<input type='text' class='form-control' placeholder='请输入问题' name='rec.question' style='width:300px;'/></p></td></tr><tr>"+
			"<td style='width: 20%;'><p class='text-warning'><span class='glyphicon glyphicon-comment'></span>&nbsp;回答：</p>"+
			"</td><td><p class='text-info'>"+											
			"<input type='text' class='form-control' placeholder='请输入回答内容' name='rec.answers' style='width:300px;'/></p></td></tr>"+
			"<td style='width: 20%;'><p class='text-warning'><span class='glyphicon glyphicon-time'></span>&nbsp;答辩时间：</p>"+
			"</td><td><p class='text-info'>"+											
			"<input type='text' class='form-control datepicker' placeholder='请输入答辩时间'  name='rec.answerTime' style='width:300px;'/></p></td></tr>"+
			"<tr><td class='text-center' colspan='3'><button type='submit'  class='btn btn-info'><span class=' glyphicon glyphicon-ok-sign'></span>添加</button>&nbsp;&nbsp;<button type='button'  class='btn btn-success' id='cancelAddReply'><span class='glyphicon glyphicon-repeat'></span>取消</button>"
			+"<td></tr></table></form>";
			
			$("#divagain").append(str);
			$(this).attr("disabled", true);
			$("input[name='rec.answerTime']").Zebra_DatePicker();
			
			$("#cancelAddReply").click(function() {
				$(this).parents("table").remove();
				$("#addReply").attr("disabled", false);
			});
		});
	});
	
	//收起
	$("button[name='wqhide']").click(function(){
		
		var $content = $(this).parents("div.panel-heading").next();
		if ($content.is(":visible")) {
			$(this).empty().append("<span class='glyphicon glyphicon-chevron-down'></span>");
			$content.slideUp(300);
		} else {
			$(this).empty().append("<span class='glyphicon glyphicon-chevron-up'></span>");
			$content.slideDown(300);
		}
	});
	
	//全部隐藏
	$("#allShow").click(function() {
		$("div.groupDetail").slideUp(300);
		$("button.closeGroupInfo").empty().append("<span class='glyphicon glyphicon-chevron-down'></span>");
	});
	//全部显示
	$("#allHide").click(function() {
		$("div.groupDetail").slideDown(300);
		$("button.closeGroupInfo").empty().append("<span class='glyphicon glyphicon-chevron-up'></span>");
	});
});