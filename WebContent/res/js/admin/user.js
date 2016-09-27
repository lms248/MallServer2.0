/**
 * 用户js
 */

/**
 * 获取用户数据列表
 */
function getUserDateList(index) {
	var pageSize = $('#pageSize').val();
	var params = {index:index,size:pageSize};
	$.post("/user/infoList",params,function(data){
		if(data.code=="0"){
			var template = $.templates("#user_tableTmpl");
			var htmlOutput = template.render(data.data);
			$("#userTableData").html(htmlOutput);
			$("#user_size").html(data.count);
		} else {
			alert(data);
		}
	},"json");
}

$("#pageSize").change(function(){
	getUserDateList(0);
});