<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息框</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
// alert(111);
$(function(){
	//提示框
	//图片样式：info,question,warning,error
// 	$.messager.alert('这是标题','提示的内容。。','error');
	//确认框
// 	$.messager.confirm('提示信息','确认删除吗',function(r){
// 		if(r){
// 			alert('执行操作');
// 		}
// 	});
	
	//欢迎框
	$.messager.show({
		  title:'系统信息',  	
		  msg:'欢迎您【admin】用户',  	
		  timeout:5000,  	
		  showType:'slide'  
	});
})
</script>
</head>
<body>
</body>
</html>