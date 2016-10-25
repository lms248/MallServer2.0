<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link href="/res/AdminEx/js/bootstrap-datetimepicker/css/datetimepicker-custom.css" rel="stylesheet" />
<link href="/res/AdminEx/css/style.css" rel="stylesheet">

<div class="row">
	<div class="col-sm-12">
        <section class="panel">
        <header class="panel-heading">
            	支付清单管理
            <span class="pull-right" style="display: none;">
                <a id="show_pay_btn" class="btn btn-success fa fa-plus-circle" href="#modal_pay_info" data-toggle="modal">查看支付清单详情</a>
            </span>
        </header>
        <div class="panel-body" style="display: block;">
        <div class="adv-table">
        <div id="hidden-table-info_wrapper" class="dataTables_wrapper form-inline" role="grid">
        	<div class="row-fluid">
        		<div class="span6">
        			<div id="hidden-table-info_length" class="dataTables_length" style="width: 100%;">
        				<label>
        				<select id="pageSize" class="form-control" size="1" name="hidden-table-info_length" aria-controls="hidden-table-info">
        				<option value="5">5</option>
        				<option value="10" selected="selected">10</option>
        				<option value="25">25</option>
        				<option value="50">50</option>
        				<option value="100">100</option>
        				<option value="200">200</option>
        				<option value="500">500</option>
        				<option value="1000">1000</option>
        				</select> 
        				条<b>/</b>每页</label>
        				
        				<label style="margin-left: 50px;">
        				<select id="payStatus" class="form-control" style="width: 100px;" size="1" name="hidden-table-info_length" aria-controls="hidden-table-info">
        				<option value="">全部</option>
        				<option value="0">未支付</option>
        				<option value="1" selected="selected">已支付</option>
        				</select> 
        				</label>
        				
        				<label style="margin-left: 10px;">
        				<div class="input-group date form_datetime">
                        	<input type="text" class="form-control" readonly="" size="14">
                            <div class="input-group-btn">
                            	<button type="button" class="btn btn-primary date-reset"><i class="fa fa-times"></i></button>
                                <button type="button" class="btn btn-success date-set"><i class="fa fa-calendar"></i></button>
                            </div>
                        </div>
                        	—
        				<div class="input-group date form_datetime">
                        	<input type="text" class="form-control" readonly="" size="14">
                            <div class="input-group-btn">
                            	<button type="button" class="btn btn-primary date-reset"><i class="fa fa-times"></i></button>
                                <button type="button" class="btn btn-success date-set"><i class="fa fa-calendar"></i></button>
                            </div>
                        </div>
        				</label>
        				
        				<label style="margin-left: 20px;">
        				<a class="btn btn-success" onclick="exportPayExcel()"><span>导出支付清单EXCEL</span></a>
        				<a href="/download/file/temp/payOrders.xls" class="btn btn-success" style="display: none;" >
        					<span id="exportPayExcel">导出支付清单EXCEL</span>
        				</a>
        				</label>
        			</div>
        		</div>
        		<div class="span6">
        			<!-- <div class="dataTables_filter" id="hidden-table-info_filter">
        				<label>搜索: <input type="text" class="form-control" aria-controls="hidden-table-info" placeholder="请输入用户ID进行查询"></label>
        			</div> -->
        		</div>
        		
        	</div>
        <table id="pay_table" class="display table table-bordered dataTable" aria-describedby="hidden-table-info_info">
        <thead>
        <tr role="row">
        	<th class="" role="columnheader" style=" text-align: center;">序号</th>
        	<th class="" role="columnheader" style=" text-align: center;">用户ID</th>
        	<th class="" role="columnheader" style=" text-align: center;">客户支付ID</th>
        	<th class="" role="columnheader" style=" text-align: center;">支付订单号</th>
        	<th class="" role="columnheader" style=" text-align: center;">总金额(分)</th>
        	<th class="" role="columnheader" style=" text-align: center;">商品描述</th>
        	<th class="" role="columnheader" style=" text-align: center;">支付方式</th>
        	<th class="" role="columnheader" style=" text-align: center;">支付状态</th>
        	<th class="" role="columnheader" style=" text-align: center;">错误代码</th>
        	<th class="" role="columnheader" style=" text-align: center;">错误描述</th>
        	<th class="" role="columnheader" style=" text-align: center;">创建时间</th>
        	<th class="" role="columnheader" style=" text-align: center;">支付时间</th>
        </tr>
        </thead>
        
        <tbody id="payTableData" role="alert" aria-live="polite" aria-relevant="all"></tbody>
       </table>
       <div class="row-fluid">
       	<div class="span6">
       		<div class="dataTables_info" id="hidden-table-info_info">总数：<b id="pay_size">0</b>&nbsp;条</div>
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


<!-- 店铺数据表模板 -->
<script id="pay_tableTmpl" type="text/x-jsrender">
<tr class="gradeA odd">
	<td class="center ">{{:#index+1}}</td>
    <td class="center ">{{:uid}}</td>
    <td class="center ">{{:payId}}</td>
    <td class="center ">{{:trade_no}}</td>
    <td class="center ">{{:total_fee}}</td>
    <td class="center ">{{:body}}</td>
    <td class="center ">{{:payWay}}</td>
    <td class="center ">{{:status}}</td>
    <td class="center ">{{:err_code}}</td>
    <td class="center ">{{:err_code_des}}</td>
    <td class="center ">{{:createTime2}}</td>
    <td class="center ">{{:payTime2}}</td>
</tr>
</script>

<script src="/res/js/admin/pay.js"></script>

<!--pickers plugins-->
<script src="/res/AdminEx/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>

<!--pickers initialization-->
<script src="/res/AdminEx/js/pickers-init.js"></script>

<script>
getPayDateList(0);//支付清单列表
</script>