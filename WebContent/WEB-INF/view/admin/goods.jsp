<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- web文件上传 css-->
<link href="/res/webuploader/webuploader.css" rel="stylesheet">
<link href="/res/webuploader/style.css" rel="stylesheet">

<style>
.webuploader-pick{
	height: 34px;
	width: 120px;
	padding: 8px 8px;
}
</style>


<div class="row">
	<div class="col-sm-12">
        <section class="panel">
        <header class="panel-heading">
            	商品管理
            <span class="pull-right">
                <a class="btn btn-success fa fa-plus-circle" href="#myModal" data-toggle="modal">添加商品</a>
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
        			</div>
        		</div>
        		<div class="span6">
        			<div class="dataTables_filter" id="hidden-table-info_filter">
        				<label>搜索: <input type="text" class="form-control" aria-controls="hidden-table-info" placeholder="请输入商品名进行查询"></label>
        			</div>
        		</div>
        	</div>
        <table class="display table table-bordered dataTable" id="hidden-table-info" aria-describedby="hidden-table-info_info">
        <thead>
        <tr role="row">
        	<th class="" role="columnheader" style=" text-align: center;">序号</th>
        	<th class="" role="columnheader" style=" text-align: center;">商品ID</th>
        	<th class="" role="columnheader" style=" text-align: center;">商品名称</th>
        	<th class="" role="columnheader" style=" text-align: center;">商品Logo</th>
        	<th class="" role="columnheader" style=" text-align: center;">标题</th>
        	<th class="" role="columnheader" style=" text-align: center;">描述</th>
        	<th class="" role="columnheader" style=" text-align: center;">创建时间</th>
        	<th class="" role="columnheader" style=" text-align: center;">操作</th>
        </tr>
        </thead>
        
        <tbody id="shopTableData" role="alert" aria-live="polite" aria-relevant="all"></tbody>
       </table>
       <div class="row-fluid">
       	<div class="span6">
       		<div class="dataTables_info" id="hidden-table-info_info">总数：<b id="allCount">0</b>&nbsp;条</div>
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

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
    	<div class="modal-content">
        	<div class="modal-header">
            	<button id="modalCloseBtn" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">商品编辑</h4>
            </div>

            <div class="modal-body row">
				<div class="col-md-5 img-modal">
                	<img id="show_image" src="" alt="" width="220" height="220" style="background-color: #cccccc;">
                    <a id="edit_image" href="#" class="btn btn-white btn-sm"><i class="fa fa-pencil"></i>编辑图片</a>
                    <a id="image_url" href="#" class="btn btn-white btn-sm"><i class="fa fa-eye"></i>查看原图</a>

                    <!-- <p class="mtop10"><strong>文件名:</strong></p> -->
                    <!-- <p><strong>File Type:</strong> jpg</p>
                    <p><strong>Resolution:</strong> 300x200</p>
                    <p><strong>Uploaded By:</strong> <a href="#">ThemeBucket</a></p> -->
                </div>
                <div class="col-md-7">
                	<div class="form-group">
                    	<label> 商品名 </label>
                        <input id="name" class="form-control" placeholder="请输入商品名">
                    	<label> 商店ID </label>
                        <input id="shopId" class="form-control" placeholder="请输入商店ID">
                    	<label> 原价（单位：元） </label>
                        <input id="curPrice" class="form-control" placeholder="原价 ">
                    	<label> 当前售价（单位：元） </label>
                        <input id="prePrice" class="form-control" placeholder="当前售价">
                        <label> 颜色 </label>
                        <input id="color" class="form-control" placeholder="颜色">
                        <label> 尺寸 </label>
                        <input id="size" class="form-control" placeholder="尺寸">
                        <label> 标题 </label>
                        <input id="title" class="form-control" placeholder="请输入标题">
                        <label> 描述 </label>
                        <textarea id="details" rows="2" class="form-control" placeholder="请输入商品描述"></textarea>
                    </div>
                    <!-- <div class="form-group">
                         <label> 链接地址URL </label>
                         <input id="link" class="form-control">
                    </div> -->
                    <div class="pull-right">
                         <button class="btn btn-danger btn-sm" type="button" style="margin-right: 10px;" onclick="resetEdit()">重置</button>
                         <button class="btn btn-success btn-sm" type="button" onclick="submitEdit()">添加</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- 数据表模板 -->
<script id="tableTmpl" type="text/x-jsrender">
<tr class="gradeA odd">
	<td class="center ">{{:#index+1}}</td>
    <td class="center ">{{:shopId}}</td>
    <td class="center ">{{:name}}</td>
    <td class="center "><img src="{{:thumbnail}}" width="50" heigth="50"></td>
    <td class="center ">{{:title}}</td>
    <td class="center ">****</td>
    <td class="center ">{{:time}}</td>
    <td class="center ">
		 <button class="btn btn-info" type="button" onclick="showData({{:shopId}})">查看</button>
         <button class="btn btn-warning" type="button" onclick="updateData({{:shopId}})">修改</button>
         <button class="btn btn-danger" type="button" onclick="deleteData({{:shopId}})">删除</button>
	</td>
</tr>
</script>

<script src="/res/js/admin/goods.js"></script>

<!-- web文件上传 js-->
<script src="/res/webuploader/webuploader.js"></script>

<script type="text/javascript">
var uploader;
/** 初始化Web Uploader */
uploader = WebUploader.create({
	method: 'post',
    // swf文件路径
    swf: '/res/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: '/servlet/upload',
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
    	id: '#edit_image',
    	innerHTML: '上传商品Logo',
    	multiple: true
    },
 	// 自动上传。
    auto: true,
    // 不进行图片压缩
    compress: null,
    formData: {width:200, height:200, aspectRatio:"true", width_thumb:50, height_thumb:50, aspectRatio_thumb:"false"},
 	// 只允许选择文件，可选。
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
    }
});

uploader.on( 'uploadSuccess', function( file, response ) {
    var image = response._raw.split(";")[0];
    var thumb = response._raw.split(";")[1];
    $("#show_image").attr("src", image);
    $("#show_image").attr("alt", response._raw);
    $("#image_url").attr("href", image);
    /* var img = "<img src='"+logo+"' title='"+response._raw+"' style='width: 34px;height: 34px;margin-left: 30px;margin-right: 10px;'>";
	$("#logo_show").html(img); */
	//$("#logo_url").html(response._raw);
});

uploader.on( 'uploadError', function( file ) {
	alert('上传出错了...');
});
</script>

<!-- <script type="text/javascript">
    $(function() {
        var $container = $('#edit');
        $container.isotope({
            itemSelector: '.item',
            animationOptions: {
                duration: 750,
                easing: 'linear',
                queue: false
            }
        });

        // filter items when filter link is clicked
        $('#submit').click(function() {
            var selector = $(this).attr('data-filter');
            $container.isotope({filter: selector});
            return false;
        });
    });
</script> -->

<script>
getDateList(0);//数据列表显示
</script>

