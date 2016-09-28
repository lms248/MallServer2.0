/**
 * 活动js
 */

var param_activityId = 0;

/**
 * 活动编辑
 */
function activity_edit() {
	var activity_level_1 = $("#activity_level_1").val();
	var activity_level_2 = $("#activity_level_2").val();
	var activity_title = $("#activity_title").val();
	var activity_goodsId = $("#activity_goodsId").val();
	var activity_mark = $("#activity_mark").val();
	
	var activity_sortId = activity_level_1;
	if (activity_level_1 != 0 && activity_level_2 != 0) {
		activity_sortId = activity_level_2;
	} 
	
	if (activity_sortId == 0) {
		alert("请选择活动类型！！！");
	}
	else if (activity_title == "") {
		alert("请输入活动标题！！！");
	}
	else if (activity_goodsId == "") {
		alert("请输入商品ID！！！");
	}
	else {
		if ($("#activity-edit-submit").text()=="添加") {
			var tip = "你确认添加该商品活动吗？";
			var editType = "add";
		} else {
			var tip = "你确认修改该商品活动吗？";
			var editType = "update";
		}
		if(confirm(tip)){
			var params = {editType:editType,activityId:param_activityId,sortId:activity_sortId,
					title:activity_title,goodsId:activity_goodsId,mark:activity_mark};
			$.post("/activity/edit",params,function(data){
				alert(data.msg);
				if(data.code=="0"){
					getActivityDateList(0);
					$("#activity_modalCloseBtn").click();
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
	if (pid > 0) {
		activity_level = $("#activity_level_2");
		$("#activity_level_2").html("<option value =\"0\">请选择活动二级分类</option>");
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
	param_activityId = activityId;
	$.get("/activity/info",{activityId:activityId},function(data){
		if(data.code=="0"){
			
			$("#activity_title").val(data.data.title);
			$("#activity_goodsId").val(data.data.goodsId);
			$("#activity_mark").val(data.data.mark);
			
			var sortIds = String(data.data.sortIds).split(":");
			if (sortIds[0] != 0) {
				getActivitySortList(0,sortIds[0],2);
				getActivitySortList(sortIds[0],sortIds[1],2);
				$("#activity_level_2").show();
			} else {
				getActivitySortList(0,0,2);
				$("#activity_level_2").hide();
			}
			$("#activity-edit-submit").html("修改");
			$("#add_activity_btn").click();
			$("#activity_modalCloseBtn").click(function(){
				$("#activity-edit-submit").html("添加");
				activity_resetEdit();
			});
		} else {
			alert(data.msg);
		}
	},"json");
}

/**
 * 修改活动
 * @param activityId
 */
function updateActivity(activityId) {
	showActivity(activityId);
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

/**
 * 重置活动编辑
 */
function activity_resetEdit() {
	$("#activity_title").val("");
	$("#activity_goodsId").val("");
	$("#activity_mark").val("");
	getActivitySortList(0,0,2);
	$("#activity_level_2").hide();
}



