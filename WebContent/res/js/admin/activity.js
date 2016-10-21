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
	var searchContent = $('#activity_search').val();
	var params = {index:index,size:pageSize,searchContent:searchContent};
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

/**
 * 活动查询
 */
$('#activity_search').bind('input propertychange', function() {  
	getActivityDateList(0);  
}); 

$("#pageSize").change(function(){
	getActivityDateList(0);
});

function firstPage(){//首页
	getActivityDateList(0);
	$("#pageNum").html("1");
}
function pageUp(){//上一页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	if(parseInt(pageNum)>1){
		var nextPage = parseInt(pageNum)-1;
		getActivityDateList((nextPage-1)*pageSize);
		$("#pageNum").html(nextPage);
	}	
}
function pageDown(){//下一页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	var nextPage = parseInt(pageNum)+1;
	var pageAmount = $("#activity_size").text() / pageSize;
	if ($("#activity_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (nextPage > pageAmount) {
		alert("已经到达尾页！");
	} else {
		getActivityDateList((nextPage-1)*pageSize);
		$("#pageNum").html(nextPage);
	}
}
function lastPage(){//尾页
	var pageNum = $("#pageNum").text();
	var pageSize = $('#pageSize').val();
	var nextPage = parseInt(pageNum)+1;
	var pageAmount = $("#activity_size").text() / pageSize;
	if ($("#activity_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (nextPage < pageAmount) {
		getActivityDateList((pageAmount-1)*pageSize);
		$("#pageNum").html(pageAmount);
	} 
}
function goPage(){//指定目标页面
	var pageNum = $("#pageNum").text();
	var pageGo = $("#pageGo").val();
	var pageSize = $('#pageSize').val();
	var pageAmount = $("#activity_size").text() / pageSize;
	if ($("#activity_size").text() % pageSize != 0) {
		pageAmount += 1;
	}
	
	if (isNaN(pageGo) || pageGo < 1 || pageGo > pageAmount) {
		alert("您输入的页面区间不对呀~");
	} else if (pageGo != pageNum) {
		getActivityDateList((pageGo-1)*pageSize);
		$("#pageNum").html(pageAmount);
	}
}



