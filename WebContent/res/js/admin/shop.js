/**
 * 商店js
 */

/**
 * 提交编辑
 */
function submitEdit() {
	var name = $("#name").val();
	var title = $("#title").val();
	var details = $("#details").val();
	var images = $("#show_image").attr("alt");
	var contactPhone = $("#contactPhone").val();
	
	if (name == "") {
		alert("请输入商店名称！！！");
	}
	else if (title == "") {
		alert("请输入标题！！！");
	}
	else if (details == "") {
		alert("请输入描述内容！！！");
	}
	else if (images == "") {
		alert("请上传商店Logo！！！");
	}
	else {
		var tip = "你确认添加吗？";
		if(confirm(tip)){
			var params = {name:name,title:title,details:details,images:images,contactPhone:contactPhone};
			$.post("/shop/add",params,function(data){
				if(data.code=="0"){
					alert("添加商店成功！！！");
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
	$("#contact_phone").val("");
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

