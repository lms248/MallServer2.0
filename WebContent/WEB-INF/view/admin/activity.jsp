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
                    	<input id="activity_title" type="text" class="form-control" placeholder="请输入活动标题">
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

<div class="row">
	<div class="col-sm-12">
        <section class="panel">
        <header class="panel-heading">
            	活动管理
            <!-- <span class="pull-right">
                <a id="add_activity_btn" class="btn btn-success fa fa-plus-circle" href="#modal_add_activity" data-toggle="modal">添加活动</a>
            </span> -->
        </header>
        <div class="panel-body" style="display: block;">
        <div class="adv-table">
        <div id="hidden-table-info_wrapper" class="dataTables_wrapper form-inline" role="grid">
        	<div class="row-fluid">
        		<div class="span6">
        			<div id="hidden-table-info_length" class="dataTables_length">
        				<label>
        				<select id="pageSize" class="form-control" size="1" name="hidden-table-info_length" aria-controls="hidden-table-info">
        				<option value="5">5</option>
        				<option value="10" selected="selected">10</option>
        				<option value="25">25</option>
        				<option value="50">50</option>
        				<option value="100">100</option>
        				</select> 
        				条<b>/</b>每页</label>
        			</div>
        		</div>
        		<div class="span6">
        			<div class="dataTables_filter" id="hidden-table-info_filter">
        				<label>搜索: <input type="text" class="form-control" aria-controls="hidden-table-info" placeholder="请输入商品ID进行查询"></label>
        			</div>
        		</div>
        	</div>
        <table class="display table table-bordered dataTable" id="hidden-table-info" aria-describedby="hidden-table-info_info">
        <thead>
        <tr role="row">
        	<th class="" role="columnheader" style=" text-align: center;">序号</th>
        	<th class="" role="columnheader" style=" text-align: center;">活动ID</th>
        	<th class="" role="columnheader" style=" text-align: center;">活动标题</th>
        	<th class="" role="columnheader" style=" text-align: center;">商品ID</th>
        	<th class="" role="columnheader" style=" text-align: center;">商品名</th>
        	<th class="" role="columnheader" style=" text-align: center;">类别</th>
        	<th class="" role="columnheader" style=" text-align: center;">备注</th>
        	<th class="" role="columnheader" style=" text-align: center;">创建时间</th>
        	<th class="" role="columnheader" style=" text-align: center;">操作</th>
        </tr>
        </thead>
        
        <tbody id="activityTableData" role="alert" aria-live="polite" aria-relevant="all"></tbody>
       </table>
       <div class="row-fluid">
       	<div class="span6">
       		<div class="dataTables_info" id="hidden-table-info_info">总数：<b id="activity_size">0</b>&nbsp;条</div>
       	</div>
       	<div class="span6">
       		<div class="dataTables_paginate paging_bootstrap pagination">
       			<ul><li class="prev disabled"><a href="#">← 上一页</a></li>
       			<li class="active"><a href="#">1</a></li>
       			<li><a href="#">2</a></li>
       			<li><a href="#">3</a></li>
       			<li><a href="#">4</a></li>
       			<li><a href="#">5</a></li>
       			<li class="next"><a href="#">下一页 → </a></li>
       			</ul>
       		</div>
       	</div>
       </div>
       </div>

       </div>
       </div>
       </section>
	</div>
</div>

<!-- 店铺数据表模板 -->
<script id="activity_tableTmpl" type="text/x-jsrender">
<tr class="gradeA odd">
	<td class="center ">{{:#index+1}}</td>
	<td class="center ">{{:activityId}}</td>
    <td class="center ">{{:title}}</td>
    <td class="center ">{{:goodsId}}</td>
    <td class="center ">{{:goodsName}}</td>
    <td class="center ">{{:sortId}}</td>
    <td class="center ">{{:mark}}</td>
    <td class="time center ">{{:createTime2}}</td>
    <td class="center ">
		 <button class="btn btn-info" type="button" onclick="showActivity('{{:activityId}}')">查看</button>
         <button class="btn btn-warning" type="button" onclick="updateActivity('{{:activityId}}')">修改</button>
         <button class="btn btn-danger" type="button" onclick="deleteActivity('{{:activityId}}')">删除</button>
	</td>
</tr>
</script>

<script src="/res/js/admin/activity.js"></script>

<!-- 活动类别模板 -->
<script id="activitySortTmpl" type="text/x-jsrender">
<option value ="{{:id}}">{{:name}}</option>
</script>

<script>
getActivityDateList(0);//活动列表
getActivitySortList(0,0,2);//分类列表
</script>