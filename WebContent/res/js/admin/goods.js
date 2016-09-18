/**
 * 商品js
 */

/**
 * 提交编辑
 */
function submitEdit() {
	var name = $("#name").val();
	var shopId = $("#shopId").val();
	var curPrice = $("#curPrice").val();
	var prePrice = $("#prePrice").val();
	var color = $("#color").val();
	var size = $("#size").val();
	var title = $("#title").val();
	var details = $("#details").val();
	var images = $("#show_image").attr("alt");
	
	if (name == "") {
		alert("请输入商品名称！！！");
	}
	else if (shopId == "") {
		alert("请输入商店ID！！！");
	}
	else if(curPrice==""){
		alert("请输入商品原价！！！");
	}
	else if(isNaN(parseFloat($("#curPrice").val()))){
		alert("商品原价类型不正确！！！");
	}
	else if(prePrice==""){
		alert("请输入商品当前售价！！！");
	}
	else if(isNaN(parseFloat($("#prePrice").val()))){
		alert("商品当前售价类型不正确！！！");
	}
	else if (color == "") {
		alert("请输入颜色！！！");
	}
	else if (size == "") {
		alert("请输入尺寸！！！");
	}
	else if (title == "") {
		alert("请输入标题！！！");
	}
	else if (details == "") {
		alert("请输入描述内容！！！");
	}
	else if (images == "") {
		alert("请上传商品Logo！！！");
	}
	else {
		var tip = "你确认添加吗？";
		if(confirm(tip)){
			var params = {name:name,shopId:shopId,curPrice:curPrice,prePrice:prePrice,
					color:color,size:size,title:title,details:details,images:images};
			$.post("/goods/add",params,function(data){
				if(data.code=="0"){
					alert("添加商品成功！！！");
					$("#modalCloseBtn").click();
					getGoodsDateList(0);
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
			$("#add_shop_btn").click();
			$("#shop_modalCloseBtn").click(function(){
				
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


