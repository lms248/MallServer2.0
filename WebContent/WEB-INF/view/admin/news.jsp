<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- web文件上传 css-->
<link href="/res/webuploader/webuploader.css" rel="stylesheet">

<style>
.webuploader-pick{
	height: 34px;
	width: 100px;
	padding: 8px 8px;
}
</style>

<div class="container">
  <div class="hero-unit" style="width: 1130px;">
	<table>
	<tr>
	<td><font size="5" face="华文隶书">☞ 公告编辑 ☜</font></td>
	</tr>
	</table>
	<br>
	<div id="">
		<table>
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
		<br>
		<table>
		<tr>
			<td width="120px"><div id="logo_upload">选择图片</div></td>
			<td id="logo_show"></td>
			<td id="logo_url"></td>
		</tr>
		<tr>
			<td colspan="4">
				<small>(温馨提示：为了得到更好的图片压缩效果，请上传 <b style="color: blue;">.jpg</b> 或 <b style="color: blue;">.jpeg</b> 格式的图片。)</small>
			</td>
		</tr>
		</table>
		<label for="title" style="margin-top: 6px;margin-bottom: 0px;">标题：</label>
		<input id="title" type="text" class="form-control" placeholder="请输入文章标题" style="width: 500px; height: 35px;margin-top: 0px;color: #000;" />
		<label for="brief" style="margin-top: 6px;margin-bottom: 0px;">简述：</label>
		<textarea id="brief" class="form-control p-text-area" style="width: 600px;height: 80px;color: #000;" placeholder="请输入文章简述"></textarea>
		<br>
	</div>
      
	<div>
	<script id="editor" name="content" type="text/plain" style="width:1010px;height:300px;"></script>
    </div>	    
    
    <hr>
    <font size="5" face="华文隶书">☞ 公告管理 ☜</font>
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
  	 
	<div id="newsdb" style="height: 600px;width: 1200px;"></div>
    
  </div>
</div>

<!-- web文件上传 js-->
<script src="/res/webuploader/webuploader.js"></script>

<!-- UEditor编辑框 -->
<script src="/res/ueditor/ueditor.config.js"></script>
<script src="/res/ueditor/ueditor.all.min.js"></script>

<script type="text/javascript">
var uploader;
/** 初始化Web Uploader */
uploader = WebUploader.create({
	method: 'post',
    // swf文件路径
    swf: '/res/webuploader/Uploader.swf',
    // 文件接收服务端。
    server: '/service/uploadfile/image4compress.do',
    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
    pick: {
    	id: '#logo_upload',
    	innerHTML: '上传Logo图片',
    	multiple: false
    },
 	// 自动上传。
    auto: true,
    // 不进行图片压缩
    compress: null,
    formData: { },
 	// 只允许选择文件，可选。
    accept: {
        title: 'Images',
        extensions: 'gif,jpg,jpeg,bmp,png',
        mimeTypes: 'image/*'
    }
});

uploader.on( 'uploadSuccess', function( file, response ) {
    var logo = "/upload/image/"+response._raw;
    var img = "<img src='"+logo+"' style='width: 34px;height: 34px;margin-left: 30px;margin-right: 10px;'>";
	$("#logo_show").html(img);
	$("#logo_url").html(response._raw);
});

uploader.on( 'uploadError', function( file ) {
	alert('上传出错了...');
});
</script>

<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    ue.ready(function() {
        //设置编辑器的内容
        ue.setContent('');
        //获取html内容，返回: <p>hello</p>
        var html = ue.getContent();
        //获取纯文本内容，返回: hello
        var txt = ue.getContentTxt();
    });
</script>

<!-- 新闻公告js -->
<script src="/res/js/admin/news.js"></script>
<script type="text/javascript">
/* 加载新闻公告列表数据 */
getNewsDB(1,10);
</script>

