/**
 * 活动js
 */


/**
 * 添加活动
 */
function activity_add() {
	var activity_level_1 = $("#activity_level_1").val();
	var activity_level_2 = $("#activity_level_2").val();
	var activity_title = $("#activity_title").val();
	var activity_goodsId = $("#activity_goodsId").val();
	var activity_mark = $("#activity_mark").val();
	
	var activity_sortId = activity_level_1;
	if (activity_level_1 != 0 && activity_level_2 != 0) {
		activity_sortId = activity_level_2;
	}
	if (activity_title == "") {
		alert("请输入活动标题！！！");
	}
	else if (activity_goodsId == "") {
		alert("请输入商品ID！！！");
	}
	else {
		var tip = "你确认添加该商品活动吗？";
		if(confirm(tip)){
			var params = {sortId:activity_sortId,title:activity_title,goodsId:activity_goodsId,mark:activity_mark};
			$.post("/activity/add",params,function(data){
				alert(data.msg);
				if(data.code=="0"){
					getActivityDateList(0);
				}
			},"json");
		}
	}
}


/**
 * 获取活动数据列表
 */
function getActivityDateList(index) {
	var pageSize = $('#pageSize').val();
	var params = {index:index,size:pageSize};
	$.get("/activity/infoList",params,function(data){
		if(data.code=="0"){
			var template = $.templates("#activity_tableTmpl");
			var htmlOutput = template.render(data.data);
			$("#activityTableData").html(htmlOutput);
			$("#activity_size").html(data.count);
		} else {
			alert(data);
		}
	},"json");
}

$("#pageSize").change(function(){
	getActivityDateList(0);
});


/**
 * 获取活动类别列表，并选择对应值
 */
function getActivitySortList(pid, sortId, type) {
	var activity_level = $("#activity_level_1");
	//$("#activity_level_1").html("<option value =\"0\">请选择活动一级分类</option>");
	if (pid > 0) {
		activity_level = $("#activity_level_2");
		//$("#activity_level_2").html("<option value =\"0\">请选择活动二级分类</option>");
	}
	$.get("/sort/infoList",{pid:pid,type:type},function(data){
		if(data.code=="0"){
			if (data.data=="") {
				$("#activity_level_2").hide();
			}
			var template = $.templates("#activitySortTmpl");
			var htmlOutput = template.render(data.data);
			activity_level.append(htmlOutput);
			
			activity_level.val(sortId);
		} else {
			alert(data);
		}
	},"json");
}

/**
 * 类别一变化时
 */
$("#activity_level_1").change(function(){
	$("#activity_level_2").html("<option value =\"0\">请选择活动二级分类</option>");
	if (this.value==0) {
		$("#activity_level_2").hide();
	} else {
		getActivitySortList(this.value, 0, 2);
		$("#activity_level_2").show();
	}
});


/**
 * 查看活动
 * @param activityId
 */
function showActivity(activityId) {
	
}

/**
 * 修改活动
 * @param activityId
 */
function updateActivity(activityId) {
	
}

/**
 * 删除活动
 * @param activityId
 */
function deleteActivity(activityId) {
	if (confirm("确定要删除活动ID为（"+activityId+"）的活动吗？")) {
		$.post("/activity/delete",{activityId:activityId},function(data){
			alert(data.msg);
			if(data.code=="0"){
				getActivityDateList(0);
			}
		},"json");
	}
}





