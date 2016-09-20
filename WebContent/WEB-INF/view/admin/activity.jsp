<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-lg-12">
    	<section class="panel">
        	<header class="panel-heading">活动编辑</header>
            <div class="panel-body">
            	<div class="form-inline">
                	<div class="form-group">
                    	<select class="form-control" id="activity_level_1" name="activity_level_1" size="1" name="hidden-table-info_length" aria-controls="hidden-table-info">
        				<option value="0" selected="selected">请选择活动一级分类</option>
        				</select> 
                    </div>
                    <div class="form-group">
                    	<select class="form-control" id="activity_level_2" name="activity_level_2" size="1" name="hidden-table-info_length" aria-controls="hidden-table-info" style="display: none;">
        				<option value="0" selected="selected">请选择活动二级分类</option>
        				</select> 
                    </div>
                    <div class="form-group">
                    	<input id="activity_goodsId" type="text" class="form-control" placeholder="请输入商品ID">
                    </div>
                    <div class="form-group">
                    	<input id="activity_mark" type="text" class="form-control" placeholder="备注">
                    </div>
                    	<button class="btn btn-primary" onclick="activity_add()">添加</button>
                </div>
            </div>
    	</section>
	</div>
</div>

<script src="/res/js/admin/activity.js"></script>

<!-- 活动类别模板 -->
<script id="activitySortTmpl" type="text/x-jsrender">
<option value ="{{:id}}">{{:name}}</option>
</script>

<script>
getActivitySortList(0,0,2);//分类列表
</script>