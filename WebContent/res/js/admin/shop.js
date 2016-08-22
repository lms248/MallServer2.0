/**
 * 商店js
 */

/**
 * 提交编辑
 */
function submitEdit() {
	var name = $("#name").val();
	var title = $("#title").val();
	var content = $("#content").val();
	var images = $("#show_image").attr("alt");
	
	if (name == "") {
		alert("请输入商店名称！！！");
	}
	else if (title == "") {
		alert("请输入标题！！！");
	}
	else if (content == "") {
		alert("请输入描述内容！！！");
	}
	else if (images == "") {
		alert("请上传商店Logo！！！");
	}
	else {
		var tip = "你确认添加吗？";
		if(confirm(tip)){
			var params = {name:name,title:title,content:content,images:images};
			$.post("/shop/add",params,function(data){
				if(data.code=="0"){
					alert("添加商店成功！！！");
				} else {
					alert(data);
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
	$("#content").val("");
}