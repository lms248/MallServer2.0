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
	else if (goodsId == "") {
		alert("无法获取商品ID！！！");
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
		var tip = "你确认修改吗？";
		if(confirm(tip)){
			goods_tags = goods_tagKey+":"+goods_tagValue+";";
			var params = {name:goods_name,goodsId:goodsId,curPrice:goods_curPrice,prePrice:goods_prePrice,sortId:sortId,
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
	$.get("/goods/info",{goodsId:goodsId},function(data){
		if(data.code=="0"){
			$("#goods_shop_name").html(data.data.shopName);
			$("#goods_name").attr("title", data.data.goodsId);
			$("#goods_name").val(data.data.name);
			$("#goods_prePrice").val(data.data.prePrice);
			$("#goods_curPrice").val(data.data.curPrice);
			
			var tags = eval ("(" + data.data.tags + ")");
			for (var key in tags) {
				$("#goods_tagKey").val(key);
				$("#goods_tagValue").val(String(tags[key]).replace(/,/g,"#"));
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
				getGoodsSortList(0,sortIds[0]);
				getGoodsSortList(sortIds[0],sortIds[1]);
				$("#id_goods_level_2").show();
			} else {
				getGoodsSortList(0,0);
				$("#id_goods_level_2").hide();
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

/**
 * 获取商品类别列表，并选择对应值
 */
function getGoodsSortList(pid, sortId) {
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
			
			id_goods_level.val(sortId);
		} else {
			alert(data);
		}
	},"json");
}

/**
 * 类别一变化时
 */
$("#id_goods_level_1").change(function(){
	$("#id_goods_level_2").html("<option value =\"0\">请选择二级分类</option>");
	if (this.value==0) {
		getGoodsSortList(0, 0);
		$("#id_goods_level_2").hide();
	} else {
		getGoodsSortList(this.value, 0);
		$("#id_goods_level_2").show();
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
