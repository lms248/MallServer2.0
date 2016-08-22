<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- web文件上传 css-->
<link href="/res/webuploader/webuploader.css" rel="stylesheet">

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
            	商店管理
            <span class="pull-right">
                <a class="btn btn-success fa fa-plus-circle" href="#myModal" data-toggle="modal">添加商店</a>
            </span>
        </header>
        <div class="panel-body" style="display: block;">
        <div class="adv-table">
        <div id="hidden-table-info_wrapper" class="dataTables_wrapper form-inline" role="grid"><div class="row-fluid"><div class="span6"><div id="hidden-table-info_length" class="dataTables_length"><label><select class="form-control" size="1" name="hidden-table-info_length" aria-controls="hidden-table-info"><option value="10" selected="selected">10</option><option value="25">25</option><option value="50">50</option><option value="100">100</option></select> records per page</label></div></div><div class="span6"><div class="dataTables_filter" id="hidden-table-info_filter"><label>Search: <input type="text" class="form-control" aria-controls="hidden-table-info"></label></div></div></div><table class="display table table-bordered dataTable" id="hidden-table-info" aria-describedby="hidden-table-info_info">
        <thead>
        <tr role="row"><th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1" aria-label="" style="width: 7px;"></th><th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="hidden-table-info" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending" style="width: 179px;">Rendering engine</th><th class="sorting" role="columnheader" tabindex="0" aria-controls="hidden-table-info" rowspan="1" colspan="1" aria-label="Browser: activate to sort column ascending" style="width: 258px;">Browser</th><th class="hidden-phone sorting" role="columnheader" tabindex="0" aria-controls="hidden-table-info" rowspan="1" colspan="1" aria-label="Platform(s): activate to sort column ascending" style="width: 235px;">Platform(s)</th><th class="hidden-phone sorting" role="columnheader" tabindex="0" aria-controls="hidden-table-info" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 151px;">Engine version</th><th class="hidden-phone sorting" role="columnheader" tabindex="0" aria-controls="hidden-table-info" rowspan="1" colspan="1" aria-label="CSS grade: activate to sort column ascending" style="width: 103px;">CSS grade</th></tr>
        </thead>
        
        <tbody role="alert" aria-live="polite" aria-relevant="all">
        <tr class="gradeA odd"><td class="center "><img src="/res/AdminEx/images/details_open.png"></td>
            <td class=" sorting_1">Gecko</td>
            <td class="">Camino 1.0</td>
            <td class="hidden-phone ">OSX.2+</td>
            <td class="center hidden-phone ">1.8</td>
            <td class="center hidden-phone ">A</td>
        </tr><tr class="gradeA even"><td class="center "><img src="/res/AdminEx/images/details_open.png"></td>
            <td class=" sorting_1">Gecko</td>
            <td class="">Camino 1.5</td>
            <td class="hidden-phone ">OSX.3+</td>
            <td class="center hidden-phone ">1.8</td>
            <td class="center hidden-phone ">A</td>
        </tr><tr class="gradeA odd"><td class="center "><img src="/res/AdminEx/images/details_open.png"></td>
            <td class=" sorting_1">Gecko</td>
            <td class="">Epiphany 2.20</td>
            <td class="hidden-phone ">Gnome</td>
            <td class="center hidden-phone ">1.8</td>
            <td class="center hidden-phone ">A</td>
        </tr><tr class="gradeA even"><td class="center "><img src="/res/AdminEx/images/details_open.png"></td>
            <td class=" sorting_1">Gecko</td>
            <td class="">Firefox 1.0</td>
            <td class="hidden-phone ">Win 98+ / OSX.2+</td>
            <td class="center hidden-phone ">1.7</td>
            <td class="center hidden-phone ">A</td>
        </tr><tr class="gradeA odd"><td class="center "><img src="/res/AdminEx/images/details_open.png"></td>
            <td class=" sorting_1">Gecko</td>
            <td class="">Firefox 1.5</td>
            <td class="hidden-phone ">Win 98+ / OSX.2+</td>
            <td class="center hidden-phone ">1.8</td>
            <td class="center hidden-phone ">A</td>
        </tr><tr class="gradeA even"><td class="center "><img src="/res/AdminEx/images/details_open.png"></td>
            <td class=" sorting_1">Gecko</td>
            <td class="">Firefox 2.0</td>
            <td class="hidden-phone ">Win 98+ / OSX.2+</td>
            <td class="center hidden-phone ">1.8</td>
            <td class="center hidden-phone ">A</td>
        </tr><tr class="gradeA odd"><td class="center "><img src="/res/AdminEx/images/details_open.png"></td>
            <td class=" sorting_1">Gecko</td>
            <td class="">Firefox 3.0</td>
            <td class="hidden-phone ">Win 2k+ / OSX.3+</td>
            <td class="center hidden-phone ">1.9</td>
            <td class="center hidden-phone ">A</td>
        </tr><tr class="gradeA even"><td class="center "><img src="/res/AdminEx/images/details_open.png"></td>
            <td class=" sorting_1">Gecko</td>
            <td class="">Mozilla 1.0</td>
            <td class="hidden-phone ">Win 95+ / OSX.1+</td>
            <td class="center hidden-phone ">1</td>
            <td class="center hidden-phone ">A</td>
        </tr><tr class="gradeA odd"><td class="center "><img src="/res/AdminEx/images/details_open.png"></td>
            <td class=" sorting_1">Gecko</td>
            <td class="">Mozilla 1.1</td>
            <td class="hidden-phone ">Win 95+ / OSX.1+</td>
            <td class="center hidden-phone ">1.1</td>
            <td class="center hidden-phone ">A</td>
        </tr><tr class="gradeA even"><td class="center "><img src="/res/AdminEx/images/details_open.png"></td>
            <td class=" sorting_1">Gecko</td>
            <td class="">Mozilla 1.2</td>
            <td class="hidden-phone ">Win 95+ / OSX.1+</td>
            <td class="center hidden-phone ">1.2</td>
            <td class="center hidden-phone ">A</td>
        </tr></tbody></table><div class="row-fluid"><div class="span6"><div class="dataTables_info" id="hidden-table-info_info">Showing 1 to 10 of 57 entries</div></div><div class="span6"><div class="dataTables_paginate paging_bootstrap pagination"><ul><li class="prev disabled"><a href="#">← Previous</a></li><li class="active"><a href="#">1</a></li><li><a href="#">2</a></li><li><a href="#">3</a></li><li><a href="#">4</a></li><li><a href="#">5</a></li><li class="next"><a href="#">Next → </a></li></ul></div></div></div></div>

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
            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">商店编辑</h4>
            </div>

            <div class="modal-body row">
				<div class="col-md-5 img-modal">
                	<img id="show_image" src="" alt="" width="220" height="220">
                    <a id="edit_image" href="#" class="btn btn-white btn-sm"><i class="fa fa-pencil"></i>编辑图片</a>
                    <a id="image_url" href="#" class="btn btn-white btn-sm"><i class="fa fa-eye"></i>查看原图</a>

                    <!-- <p class="mtop10"><strong>文件名:</strong></p> -->
                    <!-- <p><strong>File Type:</strong> jpg</p>
                    <p><strong>Resolution:</strong> 300x200</p>
                    <p><strong>Uploaded By:</strong> <a href="#">ThemeBucket</a></p> -->
                </div>
                <div class="col-md-7">
                	<div class="form-group">
                    	<label> 商店名 </label>
                        <input id="name" class="form-control">
                    </div>
                    <div class="form-group">
                        <label> 标题 </label>
                        <input id="title" class="form-control">
                    </div>
                         <div class="form-group">
                         <label> 描述 </label>
                         <textarea id="content" rows="2" class="form-control"></textarea>
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

<script src="/res/js/admin/shop.js"></script>

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
    	innerHTML: '上传商店Logo',
    	multiple: true
    },
 	// 自动上传。
    auto: true,
    // 不进行图片压缩
    compress: null,
    formData: {width:100, height:100, aspectRatio:"true", width_thumb:50, height_thumb:50, aspectRatio_thumb:"false"},
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

