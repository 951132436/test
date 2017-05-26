<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据表格编辑功能</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<!--调用easyui的API创建datagrid -->
	<table id="myTable"></table>
	<script type="text/javascript">
		var myIndex = -1;  //当前的数据的索引值
		$(function(){
			$("#myTable").datagrid({
				//展示标题列信息
				columns:[[
				          {title:'编号',field:'id',
				        	  editor:{ //只是确定此列可以进行编辑
				        	  type:'validatebox',
				        	  options:{}
				          }},
				          {title:'姓名',field:'name',
					        	  editor:{
					        	  type:'validatebox',
					        	  options:{}
					          }},
				          {title:'年龄',field:'age',
					        	  editor:{
						        	  type:'numberbox',
						        	  options:{}
						          }},
				          ]]
				,
				//发送请求获取数据
				url:'${pageContext.request.contextPath }/json/datagrid_data.json',  
				//工具栏
				toolbar:[
				         {text:'增加',iconCls:'icon-save',handler:function(){
				        	 //添加一条空白记录
				        	 $("#myTable").datagrid('insertRow',{
				        		 index:0,
				        		 row:{}
				        	 });
				        	 //让空白记录进入编辑状态
				        	 $("#myTable").datagrid('beginEdit',0);
				        	 myIndex = 0;
				         }},   //一个对象代表一个按钮
				         {text:'修改',iconCls:'icon-edit',handler:function(){
				        	 //获取到选中记录的索引值
				        	 var rows = $("#myTable").datagrid("getSelections");
				        	 var index = $("#myTable").datagrid('getRowIndex',rows[0]);
				        	 $("#myTable").datagrid('beginEdit',index);
				        	 myIndex = index;
				         }},
				         {text:'删除',iconCls:'icon-remove',handler:function(){
				        	 var rows = $("#myTable").datagrid("getSelections");
				        	 var index = $("#myTable").datagrid('getRowIndex',rows[0]);
				        	 $("#myTable").datagrid('deleteRow',index);
				         }},
				         {text:'查询',iconCls:'icon-search'},
				         {text:'保存',iconCls:'icon-save',handler:function(){
				        	 $("#myTable").datagrid('endEdit',myIndex);
				         }},
				         ]
				,
				//是否分页
				pagination:true,
				striped:true, //条纹显示
				rownumbers:true,//行号
				singleSelect:true,//单选
				pageNumber:4, //初始化页码
				pageSize:5,   //初始化记录数
				pageList:[5,10,15] //选择每页显示数据的列表
			});
		})
	</script>
</body>




</html>