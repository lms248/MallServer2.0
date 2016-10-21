/**
 * 商品js
 */

/**
 * 提交编辑
 */
function goods_edit() {
	var goods_name = $("#goods_name").val();
	var goodsId = $("#goods_name").attr("title");
	var goods_prePrice = $("#goods_prePrice").val();
	var goods_curPrice = $("#goods_curPrice").val();
	var goods_tagKey_1 = $("#goods_tagKey_1").val();
	var goods_tagValue_1 = $("#goods_tagValue_1").val();
	var goods_tagKey_2 = $("#goods_tagKey_2").val();
	var goods_tagValue_2 = $("#goods_tagValue_2").val();
	var goods_tagKey_3 = $("#goods_tagKey_3").val();
	var goods_tagValue_3 = $("#goods_tagValue_3").val();
	var goods_title = $("#goods_title").val();
	var goods_details = $("#goods_details").val();
	var goods_logo = $("#goods_logo").attr("alt");
	var goods_stock = $("#goods_stock").val();
	
	var goods_sortId = $("#goods_level_1").val();
	/*if (goods_sortId == 0) {
		alert("请选择商品的一级类别！！！");
		return;
	} else if ($("#goods_level_1").val()!=0) {
		goods_sortId = $("#goods_level_2").val();
	}*/
	if (goods_sortId!=0 && $("#goods_level_2").val()!=0) {
		goods_sortId = $("#goods_level_2").val();
	}
		
	if (goods_name == "") {
		alert("请输入商品名称！！！");
	}
	else if (goodsId == "") {
		alert("无法获取商品ID！！！");
	}
	else if(goods_prePrice==""){
		alert("请输入商品原价！！！");
	}
	else if(isNaN(parseFloat(goods_prePrice))){
		alert("商品原价类型不正确！！！");
	}
	else if(goods_curPrice==""){
		alert("请输入商品当前售价！！！");
	}
	else if(isNaN(parseFloat(goods_curPrice))){
		alert("商品当前售价类型不正确！！！");
	}
	/*else if (goods_tagKey_1 == "") {
		alert("请输入标签一名字！！！");
	}
	else if (goods_tagValue_1 == "") {
		alert("请输入标签一值！！！");
	}*/
	else if (goods_title == "") {
		alert("请输入标题！！！");
	}
	else if (goods_details == "") {
		alert("请输入描述内容！！！");
	}
	else if (goods_logo == "") {
		alert("请上传商品Logo！！！");
	}
	else if (goods_imageList == "") {
		alert("请上传商品图片列表！！！");
	}
	else if (goods_stock == "") {
		alert("请输入商品库存！！！");
	}
	else if(isNaN(goods_stock)){
		alert("商品库存类型不正确！！！");
	}
	else {
		var tip = "你确认修改吗？";
		if(confirm(tip)){
			goods_tags = goods_tagKey_1+":"+goods_tagValue_1+";"+goods_tagKey_2+":"+goods_tagValue_2+";"+goods_tagKey_3+":"+goods_tagValue_3+";";
			var params = {name:goods_name,goodsId:goodsId,curPrice:goods_curPrice,prePrice:goods_prePrice,sortId:goods_sortId,stock:goods_stock,
					tags:goods_tags,title:goods_title,details:goods_details,logo:goods_logo,imageList:goods_imageList,thumbList:goods_thumbList};
			$.post("/goods/update",params,function(data){
				alert(data.msg);
				if(data.code=="0"){
					$("#goods_modalCloseBtn").click();
					getGoodsDateList(0);
				}
			},"json");
		}
	}
}


/**
 * 重置编辑
 */
function resetEdit() {
	$("#name").val("");
	$("#title").val("");
	$("#details").val("");
}

/**
 * 获取数据列表
 */
function getGoodsDateList(index) {
	var pageSize = $('#pageSize').val();
	var searchContent = $('#goods_search').val();
	var params = {index:index,size:pageSize,goodsId:searchContent,goodsName:searchContent};
	$.get("/goods/infoList",params,function(data){
		if(data.code=="0"){
			var template = $.templates("#tableTmpl");
			var htmlOutput = template.render(data.data);
			$("#goodsTableData").html(htmlOutput);
			$("#goods_size").html(data.count);
		} else {
			alert(data);
		}
	},"json");
}

/**
 * 查看商品
 * @param goodsId
 */
function showGoods(goodsId) {
	$.get("/goods/info",{goodsId:goodsId},function(data){
		if(data.code=="0"){
			$("#goods_shop_name").html(data.data.shopName);
			$("#goods_name").attr("title", data.data.goodsId);
			$("#goods_name").val(data.data.name);
			$("#goods_prePrice").val(data.data.prePrice);
			$("#goods_curPrice").val(data.data.curPrice);
			$("#goods_stock").val(data.data.stock);
			
			var tags = eval ("(" + data.data.tags + ")");
			var i = 1;
			for (var key in tags) {
				$("#goods_tagKey_"+i).val(key);
				$("#goods_tagValue_"+i).val(String(tags[key]).replace(/,/g,"#"));
				i++;
			}
			
			$("#goods_title").val(data.data.title);
			$("#goods_details").val(data.data.details);
			$("#goods_logo").attr("src",data.data.logo);
			$("#goods_logo").attr("alt",data.data.logo+";"+data.data.logoThumb);
			
			var thumbList = eval ("(" + data.data.thumbList + ")");
			var thumbListHtml = "";
			thumbList.forEach(function(data){  
				thumbListHtml += "<img src='"+data+"' style='width: 80px; height: 80px; margin: 5px;'>";
			});  
			$("#fileList").html(thumbListHtml);
			
			goods_imageList = eval (data.data.imageList)+"";
			goods_thumbList = eval (data.data.thumbList)+"";
			
			var sortIds = String(data.data.sortIds).split(":");
			if (sortIds[0] != 0) {
				getGoodsSortList(0,sortIds[0],1);
				getGoodsSortList(sortIds[0],sortIds[1],1);
				$("#goods_level_2").show();
			} else {
				getGoodsSortList(0,0,1);
				$("#goods_level_2").hide();
			}
			
			$("#add_goods_btn").click();
		} else {
			alert(data.msg);
		}
	},"json");
}

/**
 * 修改商品
 * @param goodsId
 */
function updateGoods(goodsId) {
	showGoods(goodsId);
	$("#shop-edit-submit").html("修改");
	$("#goods_modalCloseBtn").click(function(){
		
	});
}

/**
 * 删除商品
 * @param goodsId
 */
function deleteGoods(goodsId) {
	if (confirm("确定要删除商品ID为（"+goodsId+"）的商品吗？")) {
		$.post("/goods/delete",{goodsId:goodsId},function(data){
			alert(data.msg);
			if(data.code=="0"){
				getGoodsDateList(0);
			}
		},"json");
	}
}


/**
 * 获取商品类别列表
 */
/*function getGoodsSortList(pid, type) {
	var goods_level = $("#goods_level_1");
	if (pid > 0) {
		goods_level = $("#goods_level_2");
	}
	$.get("/sort/infoList",{pid:pid,type:type},function(data){
		if(data.code=="0"){
			if (data.data=="") {
				$("#goods_level_2").hide();
			}
			var template = $.templates("#goodsSortTmpl");
			var htmlOutput = template.render(data.data);
			goods_level.append(htmlOutput);
		} else {
			alert(data);
		}
	},"json");
}*/

/**
 * 获取商品类别列表，并选择对应值
 */
function getGoodsSortList(pid, sortId, type) {
	var goods_level = $("#goods_level_1");
	if (pid > 0) {
		goods_level = $("#goods_level_2");
		$("#goods_level_2").html("<option value =\"0\">请选择二级分类</option>");
	}
	$.get("/sort/infoList",{pid:pid,type:type},function(data){
		if(data.code=="0"){
			if (data.data=="") {
				$("#goods_level_2").hide();
			}
			var template = $.templates("#goodsSortTmpl");
			var htmlOutput = template.render(data.data);
			goods_level.append(htmlOutput);
			
			goods_level.val(sortId);
		} else {
			alert(data);
		}
	},"json");
}

/**
 * 类别一变化时
 */
$("#goods_level_1").change(function(){
	$("#goods_level_2").html("<option value =\"0\">请选择二级分类</option>");
	if (this.value==0) {
		$("#goods_level_2").hide();
	} else {
		getGoodsSortList(this.value, 0, 1);
		$("#goods_level_2").show();
	}
});

/**
 * 清空商品编辑图片列表
 */
function clearImageList() {
	goods_imageList = "";
	goods_thumbList = "";
	$("#fileList").html("");
}

/**
 * 商品查询
 */
$('#goods_search').bind('input propertychange', function() {  
	getGoodsDateList(0);  
}); 

$("#pageSize").change(function(){
	getGoodsDateList(0);
});

function firstPage(){//首页
	getGoodsDateList(0);
	$("#pageNum").html("1");
}
function pageUp(){//上一页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	if(parseInt(pageNum)>1){
		var nextPage = parseInt(pageNum)-1;
		getGoodsDateList((nextPage-1)*pageSize);
		$("#pageNum").html(nextPage);
	}	
}
function pageDown(){//下一页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	var nextPage = parseInt(pageNum)+1;
	var pageAmount = $("#goods_size").text() / pageSize;
	if ($("#goods_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (nextPage > pageAmount) {
		alert("已经到达尾页！");
	} else {
		getGoodsDateList((nextPage-1)*pageSize);
		$("#pageNum").html(nextPage);
	}
}
function lastPage(){//尾页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	var nextPage = parseInt(pageNum)+1;
	var pageAmount = $("#goods_size").text() / pageSize;
	if ($("#goods_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (nextPage < pageAmount) {
		getGoodsDateList((pageAmount-1)*pageSize);
		$("#pageNum").html(pageAmount);
	} 
}
function goPage(){//指定目标页面
	var pageNum = $("#pageNum").text();
	var pageGo = $("#pageGo").val();
	var pageSize = $('#pageSize').val();
	var pageAmount = $("#goods_size").text() / pageSize;
	if ($("#goods_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (isNaN(pageGo) || pageGo < 1 || pageGo > pageAmount) {
		alert("您输入的页面区间不对呀~");
	} else if (pageGo != pageNum) {
		getGoodsDateList((pageGo-1)*pageSize);
		$("#pageNum").html(pageAmount);
	}
}


