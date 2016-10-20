<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="row">
	<div class="col-sm-12">
        <section class="panel">
        <header class="panel-heading">
            	订单管理
            <span class="pull-right" style="display: none;">
                <a id="show_order_btn" class="btn btn-success fa fa-plus-circle" href="#modal_order_info" data-toggle="modal">查看订单详情</a>
            </span>
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
        				
        				<label style="margin-left: 100px;">
        				<select id="orderStatus" class="form-control" style="width: 100px;" size="1" name="hidden-table-info_length" aria-controls="hidden-table-info">
        				<option value="">全部</option>
        				<option value="0">待付款</option>
        				<option value="5" selected="selected">待发货</option>
        				<option value="1">待收货</option>
        				<option value="2">已收货</option>
        				<option value="3">已取消</option>
        				<option value="4">申请售后</option>
        				</select> 
        				</label>
        			</div>
        		</div>
        		<div class="span6">
        			<!-- <div class="dataTables_filter" id="hidden-table-info_filter">
        				<label>搜索: <input type="text" class="form-control" aria-controls="hidden-table-info" placeholder="请输入用户ID进行查询"></label>
        			</div> -->
        		</div>
        	</div>
        <table class="display table table-bordered dataTable" id="hidden-table-info" aria-describedby="hidden-table-info_info">
        <thead>
        <tr role="row">
        	<th class="" role="columnheader" style=" text-align: center;">序号</th>
        	<th class="" role="columnheader" style=" text-align: center;">订单ID</th>
        	<th class="" role="columnheader" style=" text-align: center;">用户ID</th>
        	<th class="" role="columnheader" style=" text-align: center;">店铺名</th>
        	<th class="" role="columnheader" style=" text-align: center;">店铺Logo</th>
        	<th class="" role="columnheader" style=" text-align: center;">订单状态</th>
        	<th class="" role="columnheader" style=" text-align: center;">提交时间</th>
        	<th class="" role="columnheader" style=" text-align: center;">订单详情</th>
        	<th class="" role="columnheader" style=" text-align: center;">操作</th>
        </tr>
        </thead>
        
        <tbody id="orderTableData" role="alert" aria-live="polite" aria-relevant="all"></tbody>
       </table>
       <div class="row-fluid">
       	<div class="span6">
       		<div class="dataTables_info" id="hidden-table-info_info">总数：<b id="order_size">0</b>&nbsp;条</div>
       	</div>
       	<div class="span6">
       		<div class="dataTables_paginate paging_bootstrap pagination">
       			<ul>
       			<li class="prev"><a href="#" onclick="firstPage()">首页</a></li>
       			<li class="prev"><a href="#" onclick="pageUp()">上一页</a></li>
       			<li class="active"><a id="pageNum" href="#">1</a></li>
       			<li class="next"><a href="#" onclick="pageDown()">下一页</a></li>
       			<li class="next"><a href="#" onclick="lastPage()">尾页  </a></li>
       			<li class="next" style="margin-left: 10px;">第<input id="pageGo" type="text" style="width: 40px;height: 30px;">页</li>
       			<li class="active"><a href="#" onclick="goPage()">GO  </a></li>
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

<!-- Modal -->
<div class="modal fade" id="modal_order_info" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
    	<div class="modal-content">
        	<div class="modal-content">
            	<div class="modal-header">
                	<button id="activity_modalCloseBtn" aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                    <h4 class="modal-title">订单详情</h4>
                </div>
                <div class="modal-body">
					<form role="form">
                    	<div class="form-group">
                    		<span>订单ID:<b id="modal_orderId" style="margin-left: 10px;"></b></span>
                   		</div>
                    	<div class="form-group">
                    		<span>店铺名:<b id="modal_shopName" style="margin-left: 10px;"></b></span>
                   		</div>
                    	<div class="form-group">
                    		<span>购买商品信息:</span>
                    		<div id="modal_goodsInfo"></div>
                   		</div>
                   		<div class="form-group">
                    		<span>总金额:<b id="modal_totalPrice" style="margin-left: 10px;"></b></span>
                   		</div>
                    	<div class="form-group">
                    		<span>用户信息</span>
                    		<div id="modal_userInfo"></div>
                    	<div class="form-group">
                    		<span>订单状态:<b id="modal_status" style="margin-left: 10px;color: red;"></b></span>
                   		</div>
                   		</div>
                    	<div class="form-group">
                    		<span id="modal_btn"></span>
                         	<!-- <button class="btn btn-danger" type="button" style="margin-right: 10px;" onclick="activity_resetEdit()">重置</button>
                         	<button id="activity-edit-submit" class="btn btn-success" type="button" onclick="activity_edit()">添加</button> -->
                    	</div>
                	</form>
                </div>
        	</div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- 店铺数据表模板 -->
<script id="order_tableTmpl" type="text/x-jsrender">
<tr class="gradeA odd">
	<td class="center ">{{:#index+1}}</td>
    <td class="center ">{{:orderId}}</td>
    <td class="center ">{{:uid}}</td>
    <td class="center ">{{:shopName}}</td>
    <td class="center "><img src="{{:shopLogoThumb}}" width="50" heigth="50"></td>
	<td class="center ">
		{{if status == 0}} 待付款
		{{else status == 1}} 待收货
		{{else status == 2}} 已收货
		{{else status == 3}} 已取消
		{{else status == 4}} <b style="color: #663366;">申请售后</b>
		{{else status == 5}} <b style="color: #009999;">待发货</b>
		{{/if}}
	</td>
	<td class="time center ">{{:createTime2}}</td>
    <td class="center ">
		<a class="btn btn-success" data-toggle="modal" onclick="showOrderInfo('{{:orderId}}')">查看</a>
	</td>
	<td class="center ">
		{{if status == 5}}
		 	<button class="btn btn-info" type="button" onclick="updateOrderStatus('{{:orderId}}',1)">发货</button>
		{{else status == 1}} 
			<button class="btn btn-warning" type="button" onclick="updateOrderStatus('{{:orderId}}',5)">取消发货</button>
		{{/if}}
	</td>
</tr>
</script>

<script src="/res/js/admin/order.js"></script>

<script>
getOrderDateList(0);//活动列表
</script>