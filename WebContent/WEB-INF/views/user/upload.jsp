<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/common.jsp" %><%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
		<title>图片上传</title>
		<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="${ctx}/static/css/user-list.css"/>
		<link href="${ctx}/static/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />	
	</head>
	<body>
		<%@include file="/commons/head.jsp" %>

    <div class="container-fluid">
      <div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
		  <ul class="nav nav-sidebar">
		    <li><a href="${ctx}/user/imgList">图片列表 <span class="sr-only">(current)</span></a></li>
		    <li class="active"><a href="${ctx}/user/toUpload">上传文件</a></li>
		  </ul>
		</div>
		<div class="col-sm-3 col-sm-offset-3 col-md-3 col-md-offset-3 main" style="margin-top: 50px;">
            <form action="${ctx}/user/upload" method="post" enctype="multipart/form-data">
                <input id="file-0a" class="file" type="file" name="img" multiple data-min-file-count="1">
                <br>
                <button type="submit" class="btn btn-primary">提交</button>
                <button type="reset" class="btn btn-default">重置</button>
            </form>
        </div>
        <div class="col-sm-5 col-sm-offset-3 col-md-7 col-md-offset-2"></div>
      </div>
    </div>
    <script src="${ctx}/static/script/fileinput.js" type="text/javascript"></script>
    <script src="${ctx}/static/script/fileinput_locale_zh.js" type="text/javascript"></script>
	</body>
</html>