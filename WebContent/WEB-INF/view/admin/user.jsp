<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-sm-12">
        <section class="panel">
        <header class="panel-heading">
            	用户管理
            <!-- <span class="pull-right">
                <a id="add_user_btn" class="btn btn-success fa fa-plus-circle" href="#modal_add_user" data-toggle="modal">添加活动</a>
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
        				<label>搜索: <input type="text" class="form-control" aria-controls="hidden-table-info" placeholder="请输入用户ID进行查询"></label>
        			</div>
        		</div>
        	</div>
        <table class="display table table-bordered dataTable" id="hidden-table-info" aria-describedby="hidden-table-info_info">
        <thead>
        <tr role="row">
        	<th class="" role="columnheader" style=" text-align: center;">序号</th>
        	<th class="" role="columnheader" style=" text-align: center;">用户ID</th>
        	<th class="" role="columnheader" style=" text-align: center;">账号</th>
        	<th class="" role="columnheader" style=" text-align: center;">昵称</th>
        	<th class="" role="columnheader" style=" text-align: center;">头像</th>
        	<th class="" role="columnheader" style=" text-align: center;">最近登录时间</th>
        	<th class="" role="columnheader" style=" text-align: center;">注册时间</th>
        </tr>
        </thead>
        
        <tbody id="userTableData" role="alert" aria-live="polite" aria-relevant="all"></tbody>
       </table>
       <div class="row-fluid">
       	<div class="span6">
       		<div class="dataTables_info" id="hidden-table-info_info">总数：<b id="user_size">0</b>&nbsp;条</div>
       	</div>
       	<div class="span6">
       		<div class="dataTables_paginate paging_bootstrap pagination">
       			<ul>
       			<li class="prev"><a href="#">首页</a></li>
       			<li class="prev"><a href="#">上一页</a></li>
       			<li class="active"><a href="#">1</a></li>
       			<li class="next"><a href="#">下一页</a></li>
       			<li class="next"><a href="#">尾页  </a></li>
       			<li class="next">第<input type="text" style="width: 40px;height: 30px;">页</li>
       			<li class="active"><a href="#">GO  </a></li>
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
<script id="user_tableTmpl" type="text/x-jsrender">
<tr class="gradeA odd">
	<td class="center ">{{:#index+1}}</td>
    <td class="center ">{{:uid}}</td>
    <td class="center ">{{:username}}</td>
    <td class="center ">{{:nickname}}</td>
    <td class="center "><img src="{{:thumbnail}}" width="50" heigth="50"></td>
    <td class="center ">{{:loginTime2}}</td>
    <td class="time center ">{{:registerTime2}}</td>
</tr>
</script>

<script src="/res/js/admin/user.js"></script>

<script>
getUserDateList(0);//活动列表
</script>