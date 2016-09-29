/**
 * 用户反馈js
 */

/**
 * 获取用户反馈数据列表
 */
function getFeedbackDateList(index) {
	var pageSize = $('#pageSize').val();
	var params = {index:index,size:pageSize};
	$.get("/feedback/infoList",params,function(data){
		if(data.code=="0"){
			var template = $.templates("#feedback_tableTmpl");
			var htmlOutput = template.render(data.data);
			$("#feedbackTableData").html(htmlOutput);
			$("#feedback_size").html(data.count);
		} else {
			alert(data);
		}
	},"json");
}

$("#pageSize").change(function(){
	getFeedbackDateList(0);
});