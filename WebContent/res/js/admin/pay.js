/**
 * 支付清单js
 */

/**
 * 获取支付清单数据列表
 */
function getPayDateList(index) {
	var pageSize = $("#pageSize").val();
	var payStatus = $("#payStatus").val();
	var params = {index:index,size:pageSize,status:payStatus};
	$.get("/pay/infoList",params,function(data){
		if(data.code=="0"){
			var template = $.templates("#pay_tableTmpl");
			var htmlOutput = template.render(data.data);
			$("#payTableData").html(htmlOutput);
			$("#pay_size").html(data.count);
		} else {
			alert(data.msg);
		}
	},"json");
}

/**
 * 导出支付清单Excel
 */
function exportPayExcel() {
	if (confirm("确定导出支付清单Excel表吗？")) {
		$.get("/excel/exportPay",{},function(data){
			$("#exportPayExcel").click();
		},"json");
	}
}

$("#pageSize").change(function(){
	getPayDateList(0);
});

$("#payStatus").change(function(){
	getPayDateList(0);
});

function firstPage(){//首页
	getPayDateList($("#pageNum").text());
	$("#pageNum").html("1");
}
function pageUp(){//上一页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	if(parseInt(pageNum)>1){
		var nextPage = parseInt(pageNum)-1;
		getPayDateList((nextPage-1)*pageSize);
		$("#pageNum").html(nextPage);
	}	
}
function pageDown(){//下一页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	var nextPage = parseInt(pageNum)+1;
	var pageAmount = $("#pay_size").text() / pageSize;
	if ($("#pay_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (nextPage > pageAmount) {
		alert("已经到达尾页！");
	} else {
		getPayDateList((nextPage-1)*pageSize);
		$("#pageNum").html(nextPage);
	}
}
function lastPage(){//尾页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	var nextPage = parseInt(pageNum)+1;
	var pageAmount = $("#pay_size").text() / pageSize;
	if ($("#pay_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (nextPage < pageAmount) {
		getPayDateList((pageAmount-1)*pageSize);
		$("#pageNum").html(pageAmount);
	} 
}
function goPage(){//指定目标页面
	var pageNum = $("#pageNum").text();
	var pageGo = $("#pageGo").val();
	var pageSize = $('#pageSize').val();
	var pageAmount = $("#pay_size").text() / pageSize;
	if ($("#pay_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (isNaN(pageGo) || pageGo < 1 || pageGo > pageAmount) {
		alert("您输入的页面区间不对呀~");
	} else if (pageGo != pageNum) {
		getPayDateList((pageGo-1)*pageSize);
		$("#pageNum").html(pageAmount);
	}
}

