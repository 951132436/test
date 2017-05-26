<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"></script>
</head>
<body>
<h4>传统文件上传</h4>
<iframe name="abc" style="display: none"></iframe>
<form action="xxxx.action" target="abc" method="post" enctype="multipart/form-data">
	<input type="file" name="myFile">
	<input type="submit" value="上传">
</form>

<hr>


<input id="upload" type="button" value="一键上传">
<script type="text/javascript">
	$(function(){
		$("#upload").upload({
			name:'myFile',
			action:'${pageContext.request.contextPath }/staffAction_test.action'
		});
	})
</script>
</body>
</html>