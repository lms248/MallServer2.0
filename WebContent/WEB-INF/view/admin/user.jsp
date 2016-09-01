<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- web文件上传 css-->
<link href="/res/webuploader/webuploader.css" rel="stylesheet">
<link href="/res/webuploader/style.css" rel="stylesheet">
<link href="/res/webuploader/demo.css" rel="stylesheet">

<div id="uploader" class="wu-example">
    <div class="queueList">
        <div id="dndArea" class="placeholder">
            <div id="filePicker" class="webuploader-container"><div class="webuploader-pick">点击选择图片</div><div id="rt_rt_1arhpllrl1q9h1bos1f7e1h3b1gm31" style="position: absolute; top: 0px; left: 448px; width: 168px; height: 44px; overflow: hidden; bottom: auto; right: auto;"><input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label></div></div>
            <p>或将照片拖到这里，单次最多可选300张</p>
        </div>
    <ul class="filelist"></ul></div>
    <div class="statusBar" style="display:none;">
        <div class="progress" style="display: none;">
            <span class="text">0%</span>
            <span class="percentage" style="width: 0%;"></span>
        </div><div class="info">共0张（0B），已上传0张</div>
        <div class="btns">
            <div id="filePicker2" class="webuploader-container"><div class="webuploader-pick">继续添加</div><div id="rt_rt_1arhpllrq11pe1kig1g06c6g1h5g6" style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;"><input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*"><label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label></div></div><div class="uploadBtn state-pedding">开始上传</div>
        </div>
    </div>
</div>

<!-- web文件上传 js-->
<script src="/res/webuploader/webuploader.js"></script>
<script src="/res/webuploader/demo.js"></script>
