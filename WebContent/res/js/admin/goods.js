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
					getDateList(0);
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
function getDateList(index) {
	var pageSize = $('#pageSize').val();
	var params = {index:index,size:pageSize};
	$.get("/shop/infoList",params,function(data){
		if(data.code=="0"){
			var template = $.templates("#tableTmpl");
			var htmlOutput = template.render(data.data);
			$("#shopTableData").html(htmlOutput);
			$("#allCount").html(data.count);
		} else {
			alert(data);
		}
	},"json");
}

$("#pageSize").change(function(){
	getDateList(0)
});

