<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- web文件上传 css-->
<link href="/res/webuploader/webuploader.css" rel="stylesheet">
<link href="/res/webuploader/style.css" rel="stylesheet">

<!--tags input-->
<!-- <link rel="stylesheet" type="text/css" href="/res/AdminEx/js/jquery-tags-input/jquery.tagsinput.css" /> -->

<style>
.webuploader-pick{
	height: 34px;
	width: 100px;
	margin-right: 0px;
	padding: 8px 8px;
}
</style>


<div class="row">
	<div class="col-sm-12">
        <section class="panel">
        <header class="panel-heading">
            	店铺管理
            <span class="pull-right">
                <a id="add_shop_btn" class="btn btn-success fa fa-plus-circle" href="#modal_add_shop" data-toggle="modal">添加店铺</a>
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
        				<label>搜索: <input type="text" class="form-control" aria-controls="hidden-table-info" placeholder="请输入店铺名进行查询"></label>
        			</div>
        		</div>
        	</div>
        <table class="display table table-bordered dataTable" id="hidden-table-info" aria-describedby="hidden-table-info_info">
        <thead>
        <tr role="row">
        	<th class="" role="columnheader" style=" text-align: center;">序号</th>
        	<th class="" role="columnheader" style=" text-align: center;">店铺ID</th>
        	<th class="" role="columnheader" style=" text-align: center;">店铺名称</th>
        	<th class="" role="columnheader" style=" text-align: center;">店铺Logo</th>
        	<th class="" role="columnheader" style=" text-align: center;">标题</th>
        	<!-- <th class="" role="columnheader" style=" text-align: center;">描述</th> -->
        	<th class="" role="columnheader" style=" text-align: center;">创建时间</th>
        	<th class="" role="columnheader" style=" text-align: center;">添加商品</th>
        	<th class="" role="columnheader" style=" text-align: center;">操作</th>
        </tr>
        </thead>
        
        <tbody id="shopTableData" role="alert" aria-live="polite" aria-relevant="all"></tbody>
       </table>
       <div class="row-fluid">
       	<div class="span6">
       		<div class="dataTables_info" id="hidden-table-info_info">总数：<b id="shop_size">0</b>&nbsp;条</div>
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
<div class="modal fade" id="modal_add_shop" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
    	<div class="modal-content">
        	<div class="modal-header">
            	<button id="shop_modalCloseBtn" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">店铺编辑</h4>
            </div>

            <div class="modal-body row">
				<div class="col-md-5 img-modal">
                	<img id="shop_logo" src="" alt="" width="100" height="100" style="padding-left: 50px;padding-right: 50px;">
                    <a id="shop_logo_edit" href="#" class="btn btn-white btn-sm"><i class="fa fa-pencil"></i>编辑Logo</a>
                    <a id="shop_logo_url" href="#" class="btn btn-white btn-sm"><i class="fa fa-eye"></i>查看原图</a>
                    
					<img id="shop_image" src="" alt="" width="220" height="165" style="background-color: #cccccc;">
                    <a id="shop_image_edit" href="#" class="btn btn-white btn-sm"><i class="fa fa-pencil"></i>编辑图片</a>
                    <a id="shop_image_url" href="#" class="btn btn-white btn-sm"><i class="fa fa-eye"></i>查看原图</a>
                    <!-- <p class="mtop10"><strong>文件名:</strong></p> -->
                    <!-- <p><strong>File Type:</strong> jpg</p>
                    <p><strong>Resolution:</strong> 300x200</p>
                    <p><strong>Uploaded By:</strong> <a href="#">ThemeBucket</a></p> -->
                </div>
                <div class="col-md-7">
                	<div class="form-group">
                    	<label> 店铺名 </label>
                        <input id="shop_name" class="form-control" placeholder="请输入店铺名">
                    </div>
                    <div class="form-group">
                        <label> 标题 </label>
                        <input id="shop_title" class="form-control" placeholder="请输入标题">
                    </div>
                         <div class="form-group">
                         <label> 描述 </label>
                         <textarea id="shop_details" rows="2" class="form-control" placeholder="请输入店铺描述"></textarea>
                    </div>
                    <div class="form-group">
                    	<label> 联系客服电话 </label>
                        <input id="shop_contactPhone" class="form-control" placeholder="请输入客服电话">
                    </div>
                    <!-- <div class="form-group">
                         <label> 链接地址URL </label>
                         <input id="link" class="form-control">
                    </div> -->
                    <div class="pull-right">
                         <button id="shop-edit-reset" class="btn btn-danger btn-sm" type="button" style="margin-right: 10px;" onclick="shop_resetEdit()">重置</button>
                         <button id="shop-edit-submit" class="btn btn-success btn-sm" type="button" onclick="shop_edit()">添加</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- Modal -->
<div class="modal fade" id="modal_add_goods" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
    	<div class="modal-content">
        	<div class="modal-header">
            	<button id="goods_modalCloseBtn" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">商品编辑<span style="margin-left: 10px; color: #FFEB3B;">(店铺：<b id="goods_shop_name" title=""></b>)</span></h4>
            </div>

            <div class="modal-body row">
				<div class="col-md-5 img-modal">
                	<img id="goods_logo" src="" alt="" width="100" height="100" style="padding-left: 50px;padding-right: 50px;">
                    <a id="goods_logo_edit" href="#" class="btn btn-white btn-sm" style="margin-left: 50px;"><i class="fa fa-pencil"></i>编辑图片</a>
                    <a id="goods_logo_url" href="#" class="btn btn-white btn-sm" style="display: none;"><i class="fa fa-eye"></i>查看原图</a>
					
                    <!-- <p class="mtop10"><strong>文件名:</strong></p> -->
                    <!-- <p><strong>File Type:</strong> jpg</p>
                    <p><strong>Resolution:</strong> 300x200</p>
                    <p><strong>Uploaded By:</strong> <a href="#">ThemeBucket</a></p> -->
                    <div style="width=100%; height: auto;margin-top: 10px;padding-bottom: 10px; background-color: #cccccc;">
                		<!--用来存放item-->
    					<div id="fileList" class="uploader-list"></div>
    					<div id="filePicker" style="margin: 20px;margin-left: 60px;">添加图片列表</div>
                	</div>
                </div>
                <div class="col-md-7">
                	<div class="form-group">
                		<div class="form-group">
                			<label> <span style="color: red;margin-right: 2px;">*</span>商品名 </label>
                        	<input id="goods_name" class="form-control" placeholder="请输入商品名">
                		</div>
                    	<div class="form-group">
                    		<label> <span style="color: red;margin-right: 2px;">*</span>原价（单位：元） </label>
                        	<input id="goods_prePrice" class="form-control" placeholder="原价 ">
                        	<label> <span style="color: red;margin-right: 2px;">*</span>当前售价（单位：元） </label>
                        	<input id="goods_curPrice" class="form-control" placeholder="当前售价">
                    	</div>
                    	<div class="form-group">
                			<label> <span style="color: red;margin-right: 2px;">*</span>库存 </label>
                        	<input id="goods_stock" class="form-control" placeholder="请输入库存数量">
                		</div>
                    	<div class="form-group">
                            <span style="color: red;margin-right: 2px;">*</span>
                            <select id="goods_level_1" name="goods_level_1" style="height: 30px;">
  								<option value ="0">请选择一级分类</option>
							</select>
                            <select id="goods_level_2" name="goods_level_2" style="height: 30px; display: none;">
  								<option value ="0">请选择二级分类</option>
							</select>
                    	</div>
                    	<div class="form-group">
                    		<label> <span style="color: red;margin-right: 2px;">*</span>标题 </label>
                        	<input id="goods_title" class="form-control" placeholder="请输入标题">
                    	</div>
                    	<div class="form-group">
                    		<label> <span style="color: red;margin-right: 2px;">*</span>描述 </label>
                        	<textarea id="goods_details" rows="1" class="form-control" placeholder="请输入商品描述"></textarea>
                    	</div>
                    	<div class="form-group">
                    		<label>标签一名字（例如：颜色）</label>
                        	<input id="goods_tagKey_1" class="form-control" placeholder="标签名字">
                        	<label style="font-size: 12px;"> 标签一值（各值用英文字符“#”分隔开，例如：黑色#白色）</label>
                        	<textarea id="goods_tagValue_1" rows="1" class="form-control" placeholder="标签值"></textarea>
                    	</div>
                    	<div class="form-group">
                    		<label> 标签二名字（例如：颜色）</label>
                        	<input id="goods_tagKey_2" class="form-control" placeholder="标签名字">
                        	<label style="font-size: 12px;"> 标签二值（各值用英文字符“#”分隔开，例如：黑色#白色）</label>
                        	<textarea id="goods_tagValue_2" rows="1" class="form-control" placeholder="标签值"></textarea>
                    	</div>
                    	<div class="form-group">
                    		<label> 标签三名字（例如：颜色）</label>
                        	<input id="goods_tagKey_3" class="form-control" placeholder="标签名字">
                        	<label style="font-size: 12px;"> 标签三值（各值用英文字符“#”分隔开，例如：黑色#白色）</label>
                        	<textarea id="goods_tagValue_3" rows="2" class="form-control" placeholder="标签值"></textarea>
                    	</div>
                        
                    </div>
                    <!-- <div class="form-group">
                         <label> 链接地址URL </label>
                         <input id="link" class="form-control">
                    </div> -->
                    <div class="pull-right">
                         <button class="btn btn-danger btn-sm" type="button" style="margin-right: 10px;" onclick="resetEdit()">重置</button>
                         <button class="btn btn-success btn-sm" type="button" onclick="goods_add()">添加</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- 店铺数据表模板 -->
<script id="shop_tableTmpl" type="text/x-jsrender">
<tr class="gradeA odd">
	<td class="center ">{{:#index+1}}</td>
    <td class="center ">{{:shopId}}</td>
    <td class="center ">{{:name}}</td>
    <td class="center "><img src="{{:logoThumb}}" width="50" heigth="50"></td>
    <td class="center ">{{:title}}</td>
    <td class="time center ">{{:createTime2}}</td>
	<td class="center ">
		<a class="btn btn-success" href="#modal_add_goods" data-toggle="modal" onclick="$('#goods_shop_name').html('{{:name}}');$('#shop_name').attr('title','{{:shopId}}');">添加商品</a>
	</td>
    <td class="center ">
		 <button class="btn btn-info" type="button" onclick="showShop('{{:shopId}}')">查看</button>
         <button class="btn btn-warning" type="button" onclick="updateShop('{{:shopId}}')">修改</button>
         <button class="btn btn-danger" type="button" onclick="deleteShop('{{:shopId}}')">删除</button>
	</td>
</tr>
</script>

<!-- 商品类别模板 -->
<script id="goodsSortTmpl" type="text/x-jsrender">
<option value ="{{:id}}">{{:name}}</option>
</script>

<script src="/res/js/admin/shop.js"></script>

<!--tags input-->
<!-- <script src="/res/AdminEx/js/jquery-tags-input/jquery.tagsinput.js"></script>
<script src="/res/AdminEx/js/tagsinput-init.js"></script> -->

<!-- web文件上传 js-->
<script src="/res/webuploader/webuploader.js"></script>

<script>
getShopDateList(0);//数据列表显示
getGoodsSortList(0,1);//商品分类
</script>

<!-- 上传Logo -->
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
    	id: '#shop_logo_edit',
    	innerHTML: '上传店铺Logo',
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
    $("#shop_logo").attr("src", image);
    $("#shop_logo").attr("alt", response._raw);
    $("#shop_logo_url").attr("href", image);
    /* var img = "<img src='"+logo+"' title='"+response._raw+"' style='width: 34px;height: 34px;margin-left: 30px;margin-right: 10px;'>";
	$("#logo_show").html(img); */
	//$("#logo_url").html(response._raw);
});

uploader.on( 'uploadError', function( file ) {
	alert('上传出错了...');
});
</script>

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
    	id: '#shop_image_edit',
    	innerHTML: '上传店铺Logo',
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
    $("#shop_image").attr("src", image);
    $("#shop_image").attr("alt", response._raw);
    $("#shop_image_url").attr("href", image);
    /* var img = "<img src='"+logo+"' title='"+response._raw+"' style='width: 34px;height: 34px;margin-left: 30px;margin-right: 10px;'>";
	$("#logo_show").html(img); */
	//$("#logo_url").html(response._raw);
});

uploader.on( 'uploadError', function( file ) {
	alert('上传出错了...');
});
</script>



<!-- 商品Logo -->
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
    	id: '#goods_logo_edit',
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
    $("#goods_logo").attr("src", image);
    $("#goods_logo").attr("alt", response._raw);
    $("#goods_logo__url").attr("href", image);
    /* var img = "<img src='"+logo+"' title='"+response._raw+"' style='width: 34px;height: 34px;margin-left: 30px;margin-right: 10px;'>";
	$("#logo_show").html(img); */
	//$("#logo_url").html(response._raw);
});

uploader.on( 'uploadError', function( file ) {
	alert('上传出错了...');
});
</script>


<!-- 商品图片列表上传 -->
<script>
//图片列表数据
var goods_imageList = "";
var goods_thumbList = "";

//初始化Web Uploader
var uploader = WebUploader.create({

    // 选完文件后，是否自动上传。
    auto: true,

    // swf文件路径
    swf: '/res/webuploader/Uploader.swf',

    // 文件接收服务端。
    server: '/servlet/upload',

    // 选择文件的按钮。可选。
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: '#filePicker',

    // 只允许选择图片文件。
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
    }
});

//当有文件添加进来的时候
uploader.on( 'fileQueued', function( file ) {
    var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail">' +
                '<img>' +
                '<div class="info">' + file.name + '</div>' +
            '</div>'
            ),
        $img = $li.find('img');


    $("#fileList").append( $li );

    // 创建缩略图
    // 如果为非图片文件，可以不用调用此方法。
    // thumbnailWidth x thumbnailHeight 为 100 x 100
    uploader.makeThumb( file, function( error, src ) {
        if ( error ) {
            $img.replaceWith('<span>不能预览</span>');
            return;
        }

        $img.attr( 'src', src );
    }, 80, 80 );
});



//文件上传过程中创建进度条实时显示。
uploader.on( 'uploadProgress', function( file, percentage ) {
    var $li = $( '#'+file.id ),
        $percent = $li.find('.progress span');

    // 避免重复创建
    if ( !$percent.length ) {
        $percent = $('<p class="progress"><span></span></p>')
                .appendTo( $li )
                .find('span');
    }

    $percent.css( 'width', percentage * 100 + '%' );
});



// 文件上传成功，给item添加成功class, 用样式标记上传成功。
uploader.on( 'uploadSuccess', function( file, response) {
    $( '#'+file.id ).addClass('upload-state-done');
    var image = response._raw.split(";")[0];
    var thumb = response._raw.split(";")[1];
    goods_imageList += image+","; 
    goods_thumbList += thumb+","; 
});

// 文件上传失败，显示上传出错。
uploader.on( 'uploadError', function( file ) {
    var $li = $( '#'+file.id ),
        $error = $li.find('div.error');

    // 避免重复创建
    if ( !$error.length ) {
        $error = $('<div class="error"></div>').appendTo( $li );
    }

    $error.text('上传失败');
});

// 完成上传完了，成功或者失败，先删除进度条。
uploader.on( 'uploadComplete', function( file ) {
    $( '#'+file.id ).find('.progress').remove();
});
</script>
