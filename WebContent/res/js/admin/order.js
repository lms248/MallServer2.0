/**
 * 订单js
 */

/**
 * 获取订单数据列表
 */
function getOrderDateList(index) {
	var pageSize = $('#pageSize').val();
	var params = {index:index,size:pageSize};
	$.get("/order/infoList2",params,function(data){
		if(data.code=="0"){
			var template = $.templates("#order_tableTmpl");
			var htmlOutput = template.render(data.data);
			$("#orderTableData").html(htmlOutput);
			$("#order_size").html(data.count);
		} else {
			alert(data);
		}
	},"json");
}

/**
 * 查看订单详情
 * @param orderId
 */
function showOrderInfo(orderId) {
	$.get("/order/info",{orderId:orderId},function(data){
		if(data.code=="0"){
			$("#modal_shopName").html(data.data.shopName);
			var goodsInfo = "";
			for (var i = 0; i < data.data.goodsList.length; i++) {
				goodsInfo += "&emsp;&emsp;（"+(i+1)+"）商品名："+data.data.goodsList[i].goodsName+";"+
					"商品ID："+data.data.goodsList[i].goodsId+";"+
					"标签："+data.data.goodsList[i].tags+";"+
					"单价："+data.data.goodsList[i].curPrice+";"+
					"购买数量："+data.data.goodsList[i].amount+"<br>";
			}
			$("#modal_goodsInfo").html(goodsInfo);
			$("#show_order_btn").click();
		} else {
			alert(data);
		}
	},"json");
}

$("#pageSize").change(function(){
	getOrderDateList(0);
});

function firstPage(){//首页
	getOrderDateList(0);
	$("#pageNum").html("1");
}
function pageUp(){//上一页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	if(parseInt(pageNum)>1){
		var nextPage = parseInt(pageNum)-1;
		getOrderDateList((nextPage-1)*pageSize);
		$("#pageNum").html(nextPage);
	}	
}
function pageDown(){//下一页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	var nextPage = parseInt(pageNum)+1;
	var pageAmount = $("#order_size").text() / pageSize;
	if ($("#order_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (nextPage > pageAmount) {
		alert("已经到达尾页！");
	} else {
		getOrderDateList((nextPage-1)*pageSize);
		$("#pageNum").html(nextPage);
	}
}
function lastPage(){//尾页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	var nextPage = parseInt(pageNum)+1;
	var pageAmount = $("#order_size").text() / pageSize;
	if ($("#order_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (nextPage < pageAmount) {
		getOrderDateList((pageAmount-1)*pageSize);
		$("#pageNum").html(pageAmount);
	} 
}
function goPage(){//指定目标页面
	var pageNum = $("#pageNum").text();
	var pageGo = $("#pageGo").val();
	var pageSize = $('#pageSize').val();
	var pageAmount = $("#order_size").text() / pageSize;
	if ($("#order_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (isNaN(pageGo) || pageGo < 1 || pageGo > pageAmount) {
		alert("您输入的页面区间不对呀~");
	} else if (pageGo != pageNum) {
		getOrderDateList((pageGo-1)*pageSize);
		$("#pageNum").html(pageAmount);
	}
}

