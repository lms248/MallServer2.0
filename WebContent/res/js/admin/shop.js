/**
 * 店铺js
 */

var param_shopId = 0;

/**
 * 添加或编辑店铺
 */
function shop_edit() {
	var name = $("#shop_name").val();
	var title = $("#shop_title").val();
	var details = $("#shop_details").val();
	var logoAndThumb = $("#shop_logo").attr("alt");
	var imageAndThumb = $("#shop_image").attr("alt");
	var contactPhone = $("#shop_contactPhone").val();
	
	if (name == "") {
		alert("请输入店铺名称！！！");
	}
	else if (title == "") {
		alert("请输入标题！！！");
	}
	else if (details == "") {
		alert("请输入描述内容！！！");
	}
	else if (logoAndThumb == "") {
		alert("请上传店铺Logo！！！");
	}
	else if (imageAndThumb == "") {
		alert("请上传店铺背景图！！！");
	}
	else {
		if ($("#shop-edit-submit").text()=="添加") {
			if(confirm("你确认添加该店铺吗？")){
				var params = {name:name,title:title,details:details,
						logoAndThumb:logoAndThumb,imageAndThumb:imageAndThumb,contactPhone:contactPhone};
				$.post("/shop/add",params,function(data){
					alert(data.msg);
					if(data.code=="0"){
						getShopDateList(0);
					}
					$("#shop_modalCloseBtn").click();
					$("#shop-edit-submit").html("修改");
				},"json");
			}
		} else {
			if(confirm("你确认修改该店铺吗？")){
				var params = {shopId:param_shopId,name:name,title:title,details:details,
						logoAndThumb:logoAndThumb,imageAndThumb:imageAndThumb,contactPhone:contactPhone};
				$.post("/shop/update",params,function(data){
					alert(data.msg);
					if(data.code=="0"){
						getShopDateList(0);
					}
					$("#shop_modalCloseBtn").click();
					$("#shop-edit-submit").html("修改");
				},"json");
			}
		}
		
	}
}

/**
 * 添加商品
 */
function goods_add() {
	var goods_name = $("#goods_name").val();
	var shopId = $("#shop_name").attr("title");
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
			var params = {name:goods_name,shopId:shopId,curPrice:goods_curPrice,prePrice:goods_prePrice,sortId:sortId,
					tags:goods_tags,title:goods_title,details:goods_details,logo:goods_logo,imageList:goods_imageList,thumbList:goods_thumbList};
			$.post("/goods/add",params,function(data){
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
 * 查看店铺
 * @param shopId
 */
function showShop(shopId) {
	updateShop(shopId);
	
	$("#shop_logo_edit").hide();
	$("#shop_logo_url").hide();
	$("#shop_image_edit").hide();
	$("#shop_image_url").hide();
	$("#shop-edit-reset").hide();
	$("#shop-edit-submit").hide();
	$("#shop_modalCloseBtn").click(function(){
		$("#shop_logo_edit").show();
		$("#shop_logo_url").show();
		$("#shop_image_edit").show();
		$("#shop_image_url").show();
		$("#shop-edit-reset").show();
		$("#shop-edit-submit").show();
	});
}

/**
 * 修改店铺
 * @param shopId
 */
function updateShop(shopId) {
	param_shopId = shopId;
	$.get("/shop/info",{shopId:shopId},function(data){
		if(data.code=="0"){
			$("#shop_name").val(data.data.name);
			$("#shop_title").val(data.data.title);
			$("#shop_details").val(data.data.details);
			$("#shop_logo").attr("src",data.data.logo);
			$("#shop_logo").attr("alt",data.data.logo+";"+data.data.logoThumb);
			$("#shop_image").attr("src",data.data.image);
			$("#shop_image").attr("alt",data.data.image+";"+data.data.thumbnail);
			$("#shop_contactPhone").val(data.data.contactPhone);
			$("#shop-edit-submit").html("修改");
			$("#add_shop_btn").click();
			$("#shop_modalCloseBtn").click(function(){
				shop_resetEdit();
				$("#shop-edit-submit").html("添加");
			});
		} else {
			alert(data.msg);
		}
	},"json");
}

/**
 * 删除店铺
 * @param shopId
 */
function deleteShop(shopId) {
	if (confirm("确定要删除店铺ID为（"+shopId+"）的店铺吗？")) {
		$.post("/shop/delete",{shopId:shopId},function(data){
			alert(data.msg);
			if(data.code=="0"){
				getShopDateList(0);
			}
		},"json");
	}
}


/**
 * 重置编辑
 */
function shop_resetEdit() {
	$("#shop_name").val("");
	$("#shop_title").val("");
	$("#shop_details").val("");
	$("#shop_contactPhone").val("");
	$("#shop_logo").attr("src","");
	$("#shop_logo").attr("alt","");
	$("#shop_image").attr("src","");
	$("#shop_image").attr("alt","");
}

/**
 * 获取店铺数据列表
 */
function getShopDateList(index) {
	var pageSize = $('#pageSize').val();
	var params = {index:index,size:pageSize};
	$.get("/shop/infoList",params,function(data){
		if(data.code=="0"){
			var template = $.templates("#tableTmpl");
			var htmlOutput = template.render(data.data);
			$("#shopTableData").html(htmlOutput);
			$("#shop_size").html(data.count);
		} else {
			alert(data);
		}
	},"json");
}

$("#pageSize").change(function(){
	getShopDateList(0);
});

$("#id_goods_level_1").change(function(){
	$("#id_goods_level_2").html("<option value =\"0\">请选择二级分类</option>");
	if (this.value==0) {
		$("#id_goods_level_2").hide();
	} else {
		$("#id_goods_level_2").show();
	}
	getGoodsSortList(this.value);
});

/**
 * 获取商品类别列表
 */
function getGoodsSortList(pid) {
	var id_goods_level = $("#id_goods_level_1");
	if (pid > 0) {
		id_goods_level = $("#id_goods_level_2");
	}
	$.get("/sort/infoList",{pid:pid},function(data){
		if(data.code=="0"){
			if (data.data=="") {
				$("#id_goods_level_2").hide();
			}
			var template = $.templates("#goodsSortTmpl");
			var htmlOutput = template.render(data.data);
			id_goods_level.append(htmlOutput);
		} else {
			alert(data);
		}
	},"json");
}

