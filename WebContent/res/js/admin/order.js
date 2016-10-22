/**
 * 订单js
 */

/**
 * 获取订单数据列表
 */
function getOrderDateList(index) {
	var pageSize = $("#pageSize").val();
	var orderStatus = $("#orderStatus").val();
	var params = {index:index,size:pageSize,status:orderStatus};
	$.get("/order/infoList2",params,function(data){
		if(data.code=="0"){
			var template = $.templates("#order_tableTmpl");
			var htmlOutput = template.render(data.data);
			$("#orderTableData").html(htmlOutput);
			$("#order_size").html(data.count);
		} else {
			alert(data.msg);
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
			$("#modal_orderId").html(data.data.orderId);
			$("#modal_shopName").html(data.data.shopName);
			var goodsInfo = "";
			for (var i = 0; i < data.data.goodsList.length; i++) {
				var tags = "";
				for (var key in data.data.goodsList[i].tags) {
					tags += "【"+key+"："+data.data.goodsList[i].tags[key]+"】";
			    }
				goodsInfo += "&emsp;&emsp;（"+(i+1)+"）"+
					"<img src='"+data.data.goodsList[i].goodsLogoThumb+"' width='50' heigth='50'>;&emsp;<br>"+
					"&emsp;&emsp;&emsp;商品名：<b>"+data.data.goodsList[i].goodsName+"</b>;&emsp;"+
					"商品ID：<b>"+data.data.goodsList[i].goodsId+"</b>;&emsp;<br>"+
					"&emsp;&emsp;&emsp;标签：<b>"+tags+"</b>;&emsp;<br>"+
					"&emsp;&emsp;&emsp;单价：<b>"+data.data.goodsList[i].curPrice+"</b>;&emsp;"+
					"购买数量：<b>"+data.data.goodsList[i].amount+"</b><br>";
			}
			$("#modal_goodsInfo").html(goodsInfo);
			$("#modal_totalPrice").html(data.data.totalPrice);
			var userInfo = "&emsp;&emsp;联系人：<b>"+data.data.address.contact+"</b><br>"+
				"&emsp;&emsp;手机号：<b>"+data.data.address.phone+"</b><br>"+
				"&emsp;&emsp;所在区域：<b>"+data.data.address.area+"</b><br>"+
				"&emsp;&emsp;详细地址：<b>"+data.data.address.address+"</b><br>";
			$("#modal_userInfo").html(userInfo);
			switch (data.data.status) {
			case 0:
				$("#modal_status").html("待付款");
				$("#modal_btn").html("");
				break;
			case 1:
				$("#modal_status").html("待收货");
				$("#modal_btn").html("<button class='btn btn-danger' onclick='updateOrderStatus(\""+data.data.orderId+"\",5)'>取消发货状态</button>");
				break;
			case 2:
				$("#modal_status").html("已收货");
				$("#modal_btn").html("");
				break;
			case 3:
				$("#modal_status").html("已取消");
				$("#modal_btn").html("");
				break;
			case 4:
				$("#modal_status").html("申请售后");
				$("#modal_btn").html("");
				break;
			case 5:
				$("#modal_status").html("待发货");
				$("#modal_btn").html("<button class='btn btn-success' onclick='updateOrderStatus(\""+data.data.orderId+"\",1)'>设为已发货状态</button>");
				break;
			default:
				break;
			}
			$("#show_order_btn").click();
		} else {
			alert(data.msg);
		}
	},"json");
}

/**
 * 导出订单Excel
 */
function exportOrderExcel() {
	if (confirm("确定导出订单Excel表吗？")) {
		$.get("/excel/exportOrders",{},function(data){
			alert(data.msg);
		},"json");
	}
}

$("#pageSize").change(function(){
	getOrderDateList(0);
});

$("#orderStatus").change(function(){
	getOrderDateList(0);
});

/**
 * 更新订单状态
 */
function updateOrderStatus(orderId, newStatus) {
	var tip = "";
	if (newStatus == 1) {
		tip = "您确定要把该订单设为已发货状态吗？"
	} else if (newStatus == 5) {
		tip = "您确定要把该订单设为未发货状态吗？"
	}
	if(confirm(tip)) {
		$.post("/order/updateStatus",{orderId:orderId, newStatus:newStatus},function(data){
			alert(data.msg);
			getOrderDateList(0);
			$("#activity_modalCloseBtn").click();
		},"json");
	}
}

function firstPage(){//首页
	getOrderDateList($("#pageNum").text());
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

