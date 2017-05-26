<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags"  prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/layer/layer.js"></script>
<script type="text/javascript">
	function doAdd(){
		//alert("增加...");
		$('#addStaffWindow').window("open");
	}
	
	function doView(){
		$('#searchStaffWindow').window("open");
	}
	
	function doDelete(){
		//将选中记录取派员的id获取到,返回数组  
		var rows = $("#grid").datagrid('getSelections');
		//判断是否选中记录
		if(rows.length==0){
			layer.alert('请选择一条数据！',{icon:6});
		}else{
			layer.confirm('是否作废?', {icon: 3, title:'提示'},function(r){
				if(r){
			// 		alert(rows.length);
					var array = new Array();
					console.info(rows);
					for(var i = 0;i<rows.length;i++){
						var staffId = rows[i].id;
						array.push(staffId);
					}
					var ids = array.join(",");//默认使用,拼接
					//发送请求调用服务端,ajax请求不会是刷新页面
			// 		$.post("staffAction_delete.action",{"ids":ids},function(data){
						
			// 		})
					window.location.href="staffAction_deleteBatch.action?ids="+ids;
				}
			});
		}
	}
	
	function doRestore(){

		var rows = $('#grid').datagrid('getSelections');
		if(rows.length==0){
			layer.alert('请选择一条数据！',{icon:6});
		}else{
			for(var i=0;i<rows.length;i++){
				if(rows[i].deltag=="1"){
					layer.confirm('是否还原该取派员?', {icon: 3, title:'提示'},function(r){
						if(r){
							var array = new Array();
							for(var i=0;i<rows.length;i++){
								var staffId = rows[i].id;
								array.push(staffId);
							}
							var ids = array.join(",");
							window.location.href="staffAction_restoreBatch.action?ids="+ids;
						}	
					});
				}else{
					layer.msg('只能还原已作废取派员！', {icon: 5});
					//$.messager.alert("提示信息","只能还原已作废取派员！","warning");
				}
			}
		}

	}
	//工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, 
		{
			id : 'button-delete',
			text : '作废',
			iconCls : 'icon-cancel',
			handler : doDelete
		},
	{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			//data:返回数据，返回的数据是简单类型
			//row:记录行
// 			console.info(data);
// 			console.info(row);
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="0"){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所谓单位',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,//自适应
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [5,10,15],
			pagination : true,//分页条
			toolbar : toolbar,
			url : "staffAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow  //数据行的双击事件
		});
		
		// 添加取派员窗口
		$('#addStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 修改取派员窗口
		$('#editStaffWindow').window({
	        title: '修改取派员',
	        width: 400,
	        modal: true,//遮罩效果
	        shadow: true,//显示阴影效果
	        closed: true,//关闭
	        height: 400,
	        resizable:false
	    });
		// 查询取派员窗口
		$('#searchStaffWindow').window({
	        title: '查询取派员',
	        width: 400,
	        modal: true,//遮罩效果
	        shadow: true,//显示阴影效果
	        closed: true,//关闭
	        height: 200,
	        resizable:false
	    });
		
		//保存按钮单击事件
		$("#save").click(function(){
			//调用form组件方法 validate方法：表单校验
			var r = $("#addStaffForm").form("validate");
			if(r){
// 				$("#addStaffForm").from("submit");//模拟ajax请求，页面不刷新
				$("#addStaffForm").submit();
			}
		})
		
		//更新按钮单击事件
		$("#update").click(function(){
			//调用form组件方法 validate方法：表单校验
			var r = $("#editStaffForm").form("validate");
			if(r){
// 				$("#addStaffForm").from("submit");//模拟ajax请求，页面不刷新
				$("#editStaffForm").submit();
			}
		});
		
		//查询按钮单击事件
		$("#search").click(function(){
			if($("#searchName").val()!="" && $("#searchName").val()!=" " || $("#searchTel").val()!=""){
				
				//先获取查询条件
				var goData=$("#searchStaffForm").serializeJson();//查询窗口表单封装的数据
				$('#grid').datagrid('load',goData);
				$('#searchStaffWindow').window("close");
			}else{

				//alert($("#searchName").val());
					$.messager.show({
						title:'系统消息',
					msg:'请至少输入一个条件!!!',
						timeout:3000,
						showType:'slide'
					});

				return false;
			}
		});	
	});	
	//先将表单序列化为json对象
	$.fn.serializeJson=function(){  
        var serializeObj={};  
        var array=this.serializeArray();
        $(array).each(function(){  
            if(serializeObj[this.name]){  
                if($.isArray(serializeObj[this.name])){  
                    serializeObj[this.name].push(this.value);  
                }else{  
                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
                }  
            }else{  
                serializeObj[this.name]=this.value;   
            }  
        });  
        return serializeObj;  
    }; 	
	function doDblClickRow(rowIndex, rowData){
		// 		rowIndex: 点击的行索引,从0
		// 		rowData: 点击对应的记录行
		//打开编辑取派员窗口
		$("#editStaffWindow").window("open");
		//获取到选中数据:数据回显
		//1、获取到回显数据
		//2、将数据回显到编辑窗口中编辑的Form表单中：调用form组件的load方法
		console.info(rowData);
		$("#editStaffForm").form("load",rowData);
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="对收派员进行添加或者修改" id="addStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addStaffForm" action="staffAction_save.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
<!-- 					<tr> -->
<!-- 						<td>取派员编号</td> -->
<!-- 						<td><input type="text" name="id" class="easyui-validatebox" required="true"/></td> -->
<!-- 					</tr> -->
					<tr>
						<td>姓名</td>
						<td><input type="text"  name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td>
							<input type="text"  name="telephone" data-options="validType:'telvalidate'" class="easyui-validatebox" required="true"/>
							<script type="text/javascript">
								$(function(){
									var reg = /^1[3|4|5|7|8][0-9]{9}$/;
									//扩展手机号校验规则
									$.extend($.fn.validatebox.defaults.rules, { 
										telvalidate: {  //规则名称可以自定义
											validator: function(value,param){ 
											return reg.test(value); //value输入项的值
										}, 
										message: '手机号格式错误！' 
										} 
									}); 
								})
							</script>
						</td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	<div class="easyui-window" title="对收派员修改" id="editStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="update" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >更新</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editStaffForm" action="staffAction_update.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
<!-- 					<tr> -->
<!-- 						<td>取派员编号</td> -->
<!-- 						<td><input type="text" name="id" class="easyui-validatebox" required="true"/></td> -->
<!-- 					</tr> -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td>
							<input type="text" name="telephone" data-options="validType:'telvalidate'" class="easyui-validatebox" required="true"/>
							<script type="text/javascript">
								$(function(){
									var reg = /^1[3|4|5|7|8][0-9]{9}$/;
									//扩展手机号校验规则
									$.extend($.fn.validatebox.defaults.rules, { 
										telvalidate: {  //规则名称可以自定义
											validator: function(value,param){ 
											return reg.test(value); //value输入项的值
										}, 
										message: '手机号格式错误！' 
										} 
									}); 
								})
							</script>
						</td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	<!-- 查找取派员      -->
	<div class="easyui-window" title="查询取派员" id="searchStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="search" icon="icon-search" href="#" class="easyui-linkbutton" plain="true" >查询</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="searchStaffForm" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">请输入取派员信息</td>
					</tr>
					<tr>
						<td>姓名</td>
						<td><input type="text" id="searchName" name="name" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>手机</td>
						<td>
							<input type="text" id="searchTel" name="telephone" data-options="validType:'telvalidate'" class="easyui-validatebox"  />
							<script type="text/javascript">
								$(function(){
									var reg = /^1[3|4|5|7|8][0-9]{9}$/;
									//扩展手机号校验规则
									$.extend($.fn.validatebox.defaults.rules, { 
										telvalidate: {  //规则名称可以自定义
											validator: function(value,param){ 
											return reg.test(value); //value输入项的值
										}, 
										message: '手机号格式错误！' 
										} 
									}); 
								})
							</script>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
</body>
</html>	