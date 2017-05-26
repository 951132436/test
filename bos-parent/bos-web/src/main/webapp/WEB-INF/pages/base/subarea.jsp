<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理分区</title>
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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/highcharts/highcharts.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/highcharts/highcharts-3d.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/highcharts/modules/exporting.js"></script>
<script type="text/javascript">
	function doAdd() {
		$('#addSubareaWindow').window("open");
	}

	function doEdit() {
		var rows = $('#grid').datagrid('getSelections');
		if (rows != null && rows.length == 1) {
			$("#editSubareaWindow").window("open");
			$('#editSubareaForm').form('load', rows[0]);
			$("#regionId").combobox("select", rows[0].region.id);
		} else {
			$.messager.alert("提示信息", "请选择一条数据！", "warning");
		}

	}

	function doDelete() {
		//将选中分区的id获取到,返回数组  
		var rows = $("#grid").datagrid('getSelections');
		//判断是否选中记录
		if (rows.length == 0) {
			//请至少选中一条记录
			$.messager.alert('提示信息', '请至少选择一条记录操作！', 'warning');
		} else {
			$.messager
					.confirm(
							'提示信息',
							'确定删除吗？',
							function(r) {
								//确认删除
								if (r) {
									var array = new Array();
									console.info(rows);
									for (var i = 0; i < rows.length; i++) {
										var subareaId = rows[i].id;
										array.push(subareaId);
									}
									var ids = array.join(",");//默认使用,拼接
									window.location.href = "subareaAction_deleteBatch.action?ids="
											+ ids;
								}
							});
		}
	}

	function doSearch() {
		$('#searchWindow').window("open");
	}

	function doExport() {
		//发送请求：注意：不能发送ajax请求：下载文件响应的数据二进制
		location.href = "subareaAction_exportExls.action"
	}

	function doImport() {
		alert("导入");
	}

	//工具栏
	var toolbar = [ {
		id : 'button-search',
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : doEdit
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, {
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo',
		handler : doImport
	}, {
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	}, {
		id : 'button-showPie',
		text : '查询分区分布图',
		iconCls : 'icon-search',
		handler : doShowPie
	} ];

	//展示窗口显示分区信息
	function doShowPie() {
		$("#showPieWindow").window("open");
		//发送请求获取分区分布数据
		$
				.post(
						'subareaAction_findListGroupByProvince.action',
						null,
						function(data) {
							$('#subareaPie')
									.highcharts(
											{
												chart : {
													plotBackgroundColor : null,
													plotBorderWidth : null,
													plotShadow : false
												},
												title : {
													text : '分区分布占比'
												},
												tooltip : {
													headerFormat : '{series.name}<br>',
													pointFormat : '{point.name}: <b>{point.percentage:.1f}%</b>'
												},
												plotOptions : {
													pie : {
														allowPointSelect : true,
														cursor : 'pointer',
														dataLabels : {
															enabled : true,
															format : '<b>{point.name}</b>: {point.percentage:.1f} %',
															style : {
																color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
																		|| 'black'
															}
														}
													}
												},
												series : [ {
													type : 'pie',
													data : data
												// 		            data: [
												// 		                ['Firefox',   45.0],
												// 		                ['IE',       26.8],
												// 		                {
												// 		                    name: 'Chrome',
												// 		                    y: 12.8,
												// 		                    sliced: true,
												// 		                    selected: true
												// 		                },
												// 		                ['Safari',    8.5],
												// 		                ['Opera',     6.2],
												// 		                ['其他',   0.7]
												// 		            ]
												} ]
											});
						})
	}

	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	}, {
		field : 'showid',
		title : '分拣编号',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.id;
		}
	}, {
		field : 'province',
		title : '省',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			console.info(row);
			return row.region.province;
		}
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.region.city;
		}
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			return row.region.district;
		}
	}, {
		field : 'addresskey',
		title : '关键字',
		width : 120,
		align : 'center'
	}, {
		field : 'startnum',
		title : '起始号',
		width : 100,
		align : 'center'
	}, {
		field : 'endnum',
		title : '终止号',
		width : 100,
		align : 'center'
	}, {
		field : 'single',
		title : '单双号',
		width : 100,
		align : 'center'
	}, {
		field : 'position',
		title : '位置',
		width : 200,
		align : 'center'
	} ] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 收派标准数据表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList : [ 10 ],
			pagination : true,
			toolbar : toolbar,
			url : "subareaAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});

		// 添加、修改分区
		$('#addSubareaWindow').window({
			title : '添加修改分区',
			width : 600,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		// 修改分区
		$('#editSubareaWindow').window({
			title : '修改分区',
			width : 600,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

		// 查询分区
		$('#searchWindow').window({
			title : '查询分区',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

		// 查询分区
		$('#showPieWindow').window({
			title : '查询分区分布图',
			width : 600,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

		$("#btn").click(function() {
			//将输入查询条件获取到
			var data = $("#searchForm").serializeJson();
			console.info(data);
			//将条件提交到服务端，根据条件查询，返回数据还要进行分页（发送请求pageQuery.action）
			// 			$("#grid").datagrid('load',{
			// 				id:0001,
			// 				name:'jack'
			// 			});
			$("#grid").datagrid('load', data);
			//将查询的窗口关闭
			$('#searchWindow').window("close");
		});

		$("#save").click(function() {
			//表单校验
			var r = $("#addSubareaForm").form('validate');
			if (r) {
				$("#addSubareaForm").submit();
			}
		});
		$("#update").click(function() {
			//表单校验
			var r = $("#editSubareaForm").form('validate');
			if (r) {
				$("#editSubareaForm").submit();
			}
		});

	});

	//将表单序列化为json对象
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};

	function doDblClickRow() {
		alert("双击表格数据...");
	}
</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<table id="grid"></table>
	</div>
	<!-- 添加 修改分区 -->
	<div class="easyui-window" title="分区添加修改" id="addSubareaWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>

		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="addSubareaForm" action="subareaAction_save.action"
				method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<!-- 					<tr> -->
					<!-- 						<td>分区编码</td> -->
					<!-- 						<td><input type="text" name="id" class="easyui-validatebox" required="true"/></td> -->
					<!-- 					</tr> -->
					<tr>
						<td>选择区域</td>
						<td><input class="easyui-combobox" name="region.id"
							data-options="mode:'remote',valueField:'id',textField:'name',url:'regionAction_listajax.action'" />
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>起始号</td>
						<td><input type="text" name="startnum"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>终止号</td>
						<td><input type="text" name="endnum"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>单双号</td>
						<td><select class="easyui-combobox" name="single"
							style="width: 150px;">
								<option value="0">单双号</option>
								<option value="1">单号</option>
								<option value="2">双号</option>
						</select></td>
					</tr>
					<tr>
						<td>位置信息</td>
						<td><input type="text" name="position"
							class="easyui-validatebox" required="true" style="width: 250px;" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div class="easyui-window" title="分区修改" id="editSubareaWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false"
			border="false">
			<div class="datagrid-toolbar">
				<a id="update" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
		</div>

		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="editSubareaForm" action="subareaAction_update.action"
				method="post">
				<input type="hidden" name="id" />
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">分区信息</td>
					</tr>
					<!-- 					<tr> -->
					<!-- 						<td>分区编码</td> -->
					<!-- 						<td><input type="text" name="id" class="easyui-validatebox" required="true"/></td> -->
					<!-- 					</tr> -->
					<tr>
						<td>选择区域</td>
						<td><input class="easyui-combobox" name="region.id"
							id="regionId"
							data-options="mode:'remote',valueField:'id',textField:'name',url:'regionAction_listajax.action'" />
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>起始号</td>
						<td><input type="text" name="startnum"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>终止号</td>
						<td><input type="text" name="endnum"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>单双号</td>
						<td><select class="easyui-combobox" name="single"
							style="width: 150px;">
								<option value="0">单双号</option>
								<option value="1">单号</option>
								<option value="2">双号</option>
						</select></td>
					</tr>
					<tr>
						<td>位置信息</td>
						<td><input type="text" name="position"
							class="easyui-validatebox" required="true" style="width: 250px;" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 查询分区 -->
	<div class="easyui-window" title="查询分区窗口" id="searchWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top: 20px; left: 200px">
		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="region.province" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="region.city" /></td>
					</tr>
					<tr>
						<td>区（县）</td>
						<td><input type="text" name="region.district" /></td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="addresskey" /></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="#"
							class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 查询分区分区图 -->
	<div class="easyui-window" title="查询分区分区图" id="showPieWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top: 20px; left: 200px">
		<div id="subareaPie"></div>
	</div>
</body>
</html>