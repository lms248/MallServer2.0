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

function firstPage(){//首页
	getUserDateList(0);
	$("#pageNum").html("1");
}
function pageUp(){//上一页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	if(parseInt(pageNum)>1){
		var nextPage = parseInt(pageNum)-1;
		getUserDateList((nextPage-1)*pageSize);
		$("#pageNum").html(nextPage);
	}	
}
function pageDown(){//下一页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	var nextPage = parseInt(pageNum)+1;
	var pageAmount = $("#user_size").text() / pageSize;
	if ($("#user_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (nextPage > pageAmount) {
		alert("已经到达尾页！");
	} else {
		getUserDateList((nextPage-1)*pageSize);
		$("#pageNum").html(nextPage);
	}
}
function lastPage(){//尾页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	var nextPage = parseInt(pageNum)+1;
	var pageAmount = $("#user_size").text() / pageSize;
	if ($("#user_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (nextPage < pageAmount) {
		getUserDateList((pageAmount-1)*pageSize);
		$("#pageNum").html(pageAmount);
	} 
}
function goPage(){//指定目标页面
	var pageNum = $("#pageNum").text();
	var pageGo = $("#pageGo").val();
	var pageSize = $('#pageSize').val();
	var pageAmount = $("#user_size").text() / pageSize;
	if ($("#user_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (isNaN(pageGo) || pageGo < 1 || pageGo > pageAmount) {
		alert("您输入的页面区间不对呀~");
	} else if (pageGo != pageNum) {
		getUserDateList((pageGo-1)*pageSize);
		$("#pageNum").html(pageAmount);
	}
}