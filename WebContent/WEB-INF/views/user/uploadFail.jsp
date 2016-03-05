<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/common.jsp" %><%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
		<title>上传失败</title>
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
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" style="margin-top: 50px;">
            <div class="alert alert-danger" role="alert">
			  文件上传失败, <a href="${ctx}/user/imgList" class="alert-link">点击返回列表页</a>
			</div>
        </div>
      </div>
    </div>
    <script src="${ctx}/static/script/fileinput.js" type="text/javascript"></script>
    <script src="${ctx}/static/script/fileinput_locale_zh.js" type="text/javascript"></script>
	</body>
</html>