<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人工调度</title>
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
<script type="text/javascript">
	$(function() {
		$("#grid").datagrid({
			singleSelect : true,
			toolbar : [ {
				id : 'diaodu',
				text : '人工调度',
				iconCls : 'icon-edit',
				handler : function() {
					
					var row = $('#grid').datagrid('getSelected');
					var id = row.id;
					 $.post('noticebill_findnoticebillById.action',{"id":row.id},function(data){
						 	$("#diaoduForm").form("load",data);
						},"json");
					// 弹出窗口
					$("#diaoduWindow").window('open');
				}
			} ],
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 100
			}, {
				field : 'delegater',
				title : '联系人',
				width : 100
			}, {
				field : 'telephone',
				title : '电话',
				width : 100
			}, {
				field : 'pickaddress',
				title : '取件地址',
				width : 100
			}, {
				field : 'product',
				title : '商品名称',
				width : 100
			}, {
				field : 'pickdateStr',
				title : '取件日期',
				width : 100,
				formatter : function(data, row, index) {
					return data.replace("T", " ");
				}
			} ] ],
			url : 'noticebill_findnoassociations.action'
		});

		// 点击保存按钮，为通知单 进行分单 --- 生成工单
		$("#save").click(function() {
			$('#diaoduForm').submit(); 
			$("#diaoduWindow").window('close');
		});
	});
	
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<table id="grid"></table>
		
		<div class="easyui-window" title="人工调度" id="diaoduWindow" closed="true"
			collapsible="false" minimizable="false" maximizable="false"
			style="width: 800px; height: 600px">
			<div region="north" style="height:31px;overflow:hidden;" split="false"
				border="false">
				<div class="datagrid-toolbar">
					<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
						plain="true">保存</a>
				</div>
			</div>
			<div region="center" style="overflow:auto;padding:5px;" border="false">
				<form id="diaoduForm" action="noticebill_save.action" method="post">
				<input  name="id" type="hidden" />
					<table class="table-edit" width="95%" align="center">
					<tr class="title">
						<td colspan="4">客户信息</td>
					</tr>
					<tr>
					<td>选择取派员</td>
						<td>
							<input class="easyui-combobox" name="staffId"  id="staff"
    							data-options="valueField:'id',textField:'name',url:'staffAction_listajax.action'" />  
						</td>
					</tr>
					<tr>
						<td>来电号码:</td>
						<td><input type="text" class="easyui-validatebox" name="telephone" required="true" />
						</td>
						<td>客户编号:</td>
						<td><input type="text" class="easyui-validatebox"  name="customerId"
							 /></td>
					</tr>
					<tr>
						<td>客户姓名:</td>
						<td><input type="text" class="easyui-validatebox" name="customerName"
							required="true" /></td>
						<td>联系人:</td>
						<td><input type="text" class="easyui-validatebox" name="delegater"
							required="true" /></td>
					</tr>
					<tr class="title">
						<td colspan="4">货物信息</td>
					</tr>
					<tr>
						<td>品名:</td>
						<td><input type="text" class="easyui-validatebox" name="product"
							 /></td>
						<td>件数:</td>
						<td><input type="text" class="easyui-numberbox" name="num"
							 /></td>
					</tr>
					<tr>
						<td>重量:</td>
						<td><input type="text" class="easyui-numberbox" name="weight"
							 /></td>
						<td>体积:</td>
						<td><input type="text" class="easyui-validatebox" name="volume"
							 /></td>
					</tr>
					<tr>
						<td>取件地址</td>
						<td colspan="3"><input type="text" class="easyui-validatebox" name="pickaddress"
							required="true" size="144"/></td>
					</tr>
					<tr>
						<td>到达城市:</td>
						<td><input type="text" class="easyui-validatebox" name="arrivecity"
							 /></td>
						<td>预约取件时间:</td>
						<td><input type="text" class="easyui-validatebox" name="pickdateStr"
							 /></td>
					</tr>
					<tr>
						<td>备注:</td>
						<td colspan="3"><textarea rows="5" cols="80" type="text" class="easyui-validatebox" name="remark"
							 ></textarea></td>
					</tr>
				</table>
			</form>
			</div>
		</div>
	</div>
</body>
</html>