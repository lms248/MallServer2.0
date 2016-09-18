/**
 * 商品js
 */

/**
 * 提交编辑
 */
function goods_edit() {
	var goods_name = $("#goods_name").val();
	var goodsId = $("#goods_name").attr("title");
	var goods_curPrice = $("#goods_curPrice").val();
	var goods_prePrice = $("#goods_prePrice").val();
	var goods_tagKey = $("#goods_tagKey").val();
	var goods_tagValue = $("#goods_tagValue").val();
	var goods_title = $("#goods_title").val();
	var goods_details = $("#goods_details").val();
	var goods_logo = $("#goods_logo").attr("alt");
	
	var sortId = $("#id_goods_level_1").val();
	if (sortId == 0) {
		alert("请选择商品的一级类别！！！");
		return;
	} else if ($("#id_goods_level_1").val()!=0) {
		sortId = $("#id_goods_level_2").val();
	}
		
	if (goods_name == "") {
		alert("请输入商品名称！！！");
	}
	else if (shopId == "") {
		alert("请输入店铺ID！！！");
	}
	else if(goods_curPrice==""){
		alert("请输入商品原价！！！");
	}
	else if(isNaN(parseFloat($("#goods_curPrice").val()))){
		alert("商品原价类型不正确！！！");
	}
	else if(goods_prePrice==""){
		alert("请输入商品当前售价！！！");
	}
	else if(isNaN(parseFloat($("#goods_prePrice").val()))){
		alert("商品当前售价类型不正确！！！");
	}
	else if (goods_tagKey == "") {
		alert("请输入标签名字！！！");
	}
	else if (goods_tagValue == "") {
		alert("请输入标签值！！！");
	}
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
	else {
		var tip = "你确认添加吗？";
		if(confirm(tip)){
			goods_tags = goods_tagKey+":"+goods_tagValue+";";
			alert(goods_tags);
			var params = {name:goods_name,goodsId:goodsId,curPrice:goods_curPrice,prePrice:goods_prePrice,sortId:sortId,
					tags:goods_tags,title:goods_title,details:goods_details,logo:goods_logo,imageList:goods_imageList,thumbList:goods_thumbList};
			$.post("/goods/update",params,function(data){
				if(data.code=="0"){
					alert("添加商品成功！！！");
					$("#goods_modalCloseBtn").click();
				} else {
					alert(data.msg);
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
	var params = {index:index,size:pageSize};
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

$("#pageSize").change(function(){
	getGoodsDateList(0)
});

/**
 * 查看商品
 * @param goodsId
 */
function showGoods(goodsId) {
	updateGoods(goodsId);
	
	/*$("#shop_edit_logo").hide();
	$("#shop_logo_url").hide();
	$("#shop_edit_image").hide();
	$("#shop_image_url").hide();
	$("#shop-edit-reset").hide();
	$("#shop-edit-submit").hide();
	$("#shop_modalCloseBtn").click(function(){
		$("#shop_edit_logo").show();
		$("#shop_logo_url").show();
		$("#shop_edit_image").show();
		$("#shop_image_url").show();
		$("#shop-edit-reset").show();
		$("#shop-edit-submit").show();
	});*/
}

/**
 * 修改商品
 * @param goodsId
 */
function updateGoods(goodsId) {
	$.get("/goods/info",{goodsId:goodsId},function(data){
		if(data.code=="0"){
			$("#goods_name").val(data.data.name);
			$("#goods_prePrice").val(data.data.prePrice);
			$("#goods_curPrice").val(data.data.curPrice);
			$("#goods_logo").attr("src",data.data.logo);
			$("#shop-edit-submit").html("修改");
			$("#add_goods_btn").click();
			$("#goods_modalCloseBtn").click(function(){
				
			});
		} else {
			alert(data.msg);
		}
	},"json");
}

/**
 * 删除店铺
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


