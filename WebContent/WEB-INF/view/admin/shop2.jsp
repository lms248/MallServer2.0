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

<div style="margin-left: 30px;">
  <div class="hero-unit" style="width: 1130px;">
	<table>
	<tr>
	<td><font size="5" face="华文隶书">☞ 商店编辑 ☜</font></td>
	</tr>
	</table>
	<br>
	<div id="">
		<!-- <table>
		<tr>
		<td style="padding-right: 20px;">
		<select id="game" Style="width: 100px;height: 30px;">
			<option value="hrl" selected>红刃</option>
		</select>
		</td>
		<td width="150px">
			<select id="type" Style="width: 100px;height: 30px;">
			<option value="1" selected>新闻</option>
			<option value="2">公告</option>
			<option value="3">活动</option>
			</select>
		</td>
		<td width="70px"><input id="reset" class="btn btn-danger" type="reset" value="  重  置  " onclick="reset()" /></td>
		<td><input id="submit" name="add" class="btn btn-primary" type="submit" value="  发  布  " onclick="addNewsDB(0)"></td>
		</tr>
		</table>
		<br> -->
		<table>
		<tr>
			<td width="120px"><div id="logo_upload">选择图片</div></td>
			<td id="logo_show"></td>
			<td id="logo_url"></td>
		</tr>
		<!-- <tr>
			<td colspan="4">
				<small>(温馨提示：为了得到更好的图片压缩效果，请上传 <b style="color: blue;">.jpg</b> 或 <b style="color: blue;">.jpeg</b> 格式的图片。)</small>
			</td>
		</tr> -->
		</table>
		<label for="name" style="margin-top: 6px;margin-bottom: 0px;">商店名：</label>
		<input id="name" type="text" class="form-control" placeholder="请输入商店名" style="width: 300px; height: 35px;margin-top: 0px;color: #000;" />
		<label for="title" style="margin-top: 6px;margin-bottom: 0px;">标题：</label>
		<input id="title" type="text" class="form-control" placeholder="请输入文章标题" style="width: 500px; height: 35px;margin-top: 0px;color: #000;" />
		<label for="brief" style="margin-top: 6px;margin-bottom: 0px;">介绍：</label>
		<textarea id="brief" class="form-control p-text-area" style="width: 600px;height: 80px;color: #000;" placeholder="请输入文章简述"></textarea>
		<br>
		<input id="reset" class="btn btn-danger" type="reset" value="  重  置  " onclick="reset()" style="margin-right: 30px;" />
		<input id="submit" name="add" class="btn btn-primary" type="submit" value="  发  布  " onclick="addNewsDB(0)">
	</div>
      
	<div>
	<script id="editor" name="content" type="text/plain" style="width:1010px;height:300px;"></script>
    </div>	    
    
    <hr>
    <font size="5" face="华文隶书">☞ 商店管理 ☜</font>
    <table  border="0" >
  	<tr>
  	<!-- <td>
    	<input id="title_search" type="text" size="25" value="" style="height: 30px;margin-top: 10px;"  placeholder="……"  />
  	 	<input type="submit" onclick="" style="cursor: pointer;" value="查询数据" name="search" />
  	</td> -->
  		<td>
  			<div class="black"> 
     			<!-- <span class="disabled">上一页 </span> -->
     			<a onclick="firstPage()" style="cursor: pointer;">首页</a>
  	 			<a onclick="pageUp()" style="cursor: pointer;">上一页</a>
    			<span id="pageNum" class="current">1</span>
    			<a onclick="pageDown()" style="cursor: pointer;">下一页</a>
    			<a onclick="lastPage()" style="cursor: pointer;">尾页</a>
    			第<input id="target_page" type="text" style="margin-top: 5px;width: 30px;height: 25px;">页
    			<input type="button" style="width: 40px;height: 25px;" value="GO" onclick="targetPage()"> 
    			&nbsp;&nbsp;〖共&nbsp;<span id="pageCount">0</span>&nbsp;页〗&nbsp;&nbsp;
			</div>
  		</td>
  	</tr>
	</table>
  	 
  	<!--  <form method="post" action="/service/upload/image" enctype="multipart/form-data">  
            <input type="text" name="name"/>  
            <input type="file" name="imageList"/>  
            <input type="submit"/>  
        </form>   -->
  	 
	<div id="newsdb"></div>
    
  </div>
</div>

<!-- <div id="edit" class="media-gal"></div> -->
<div id="edit" class="media-gal">
	<a href="#myModal" data-toggle="modal">sssssssss</a>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
    	<div class="modal-content">
        	<div class="modal-header">
            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Edit Media Gallery</h4>
            </div>

            <div class="modal-body row">
				<div class="col-md-5 img-modal">
                	<img src="/res/img/user1.png" alt="">
                    	<a href="#" class="btn btn-white btn-sm"><i class="fa fa-pencil"></i> Edit Image</a>
                        <a href="#" class="btn btn-white btn-sm"><i class="fa fa-eye"></i> View Full Size</a>

                        <p class="mtop10"><strong>File Name:</strong> img01.jpg</p>
                        <p><strong>File Type:</strong> jpg</p>
                        <p><strong>Resolution:</strong> 300x200</p>
                        <p><strong>Uploaded By:</strong> <a href="#">ThemeBucket</a></p>
                </div>
                <div class="col-md-7">
                	<div class="form-group">
                    	<label> Name</label>
                        <input id="name" value="img01.jpg" class="form-control">
                    </div>
                    <div class="form-group">
                        <label> Tittle Text</label>
                        <input id="title" value="awesome image" class="form-control">
                    </div>
                         <div class="form-group">
                         <label> Description</label>
                         <textarea rows="2" class="form-control"></textarea>
                    </div>
                    <div class="form-group">
                         <label> Link URL</label>
                         <input id="link" value="/res/img/user1.png" class="form-control">
                    </div>
                    <div class="pull-right">
                         <button class="btn btn-danger btn-sm" type="button">Delete</button>
                         <button class="btn btn-success btn-sm" type="button">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

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
    	id: '#logo_upload',
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
    var logo = response._raw.split(";")[1];
    var img = "<img src='"+logo+"' title='"+response._raw+"' style='width: 34px;height: 34px;margin-left: 30px;margin-right: 10px;'>";
	$("#logo_show").html(img);
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

