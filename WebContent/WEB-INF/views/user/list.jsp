<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/common.jsp" %><%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
		<title>图片管理列表</title>
		<link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="${ctx}/static/css/user-list.css"/>
	</head>
	<body>
		<%@include file="/commons/head.jsp" %>

    <div class="container-fluid">
      <div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
		  <ul class="nav nav-sidebar">
		    <li class="active"><a href="${ctx}/user/imgList">图片列表 <span class="sr-only">(current)</span></a></li>
		    <li><a href="${ctx}/user/toUpload">上传文件</a></li>
		  </ul>
		</div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h2 class="sub-header">上传图片列表</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
	                <th>ID</th>
					<th>图片</th>
					<th>上传时间</th>
					<th>操作</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${page.content}" var="img">
					<tr>
						<td>${img.id}</td>
						<td><img style="max-width:200px; max-height:100px"  src="http://img.alinetgo.com${img.path}" /></td>
						<td><tags:time time="${img.uploadTime}"></tags:time></td>
						<td><a href="${ctx}/user/remove/${img.id}?path=${img.path}">删除</a></td>
					</tr>
				</c:forEach>
              </tbody>
            </table>
          </div>
          <tags:page page="${page}" queryString="${queryString}"></tags:page>
        </div>
      </div>
    </div>
	</body>
</html>