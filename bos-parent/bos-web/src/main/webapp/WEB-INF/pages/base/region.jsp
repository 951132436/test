<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
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
<script src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js" type="text/javascript"></script>
<script type="text/javascript">
	function doAdd(){
		$('#addRegionWindow').window("open");
	}
	
	function doView(){
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length==0){
			$.messager.alert('提示信息','请至少选择一条数据进行修改','warning');
		}else{
			$("#editRegionWindow").window("open");
			$("#editRegion").form('load',rows[0]);
		}
	}
	
	function doDelete(){
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length==0){
			$.messager.alert('提示信息','请至少选择一条数据','warning');
		}else{
			$.messager.confirm('提示信息','确定要删除吗？',function(r){
				if(r){
					var array = new Array();
					for(var i=0;i<rows.length;i++){
						var regionId = rows[i].id;
						array.push(regionId);
					};
					var ids=array.join(",");
					window.location.href="${pageContext.request.contextPath}/regionAction_deleteIds.action?ids="+ids;
				}
			});
		}
	}
	
	function doSearch(){
		$("#searchRegionWindow").window("open");
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, {
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	}, {
		id : 'button-search',
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [30,50,100],
			pagination : true,
			toolbar : toolbar,
			url : "regionAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		//给导入按钮绑定方法
		$("#button-import").upload({
			name:'regionXls',
			action:'regionAction_upload.action'
		});
		
		// 添加、修改区域窗口
		$('#addRegionWindow').window({
	        title: '添加修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		//修改页面隐藏
		$('#editRegionWindow').window({
	        title: '添加修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		//查询页面
		$('#searchRegionWindow').window({
	        title: '查询区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
	});

	function doDblClickRow(){
		alert("双击表格数据...");
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="区域添加修改" id="addRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				$("#save").click(function(){
					var r = $("#addRegion").form("validate");
					if(r){
						$("#addRegion").submit();
						$('#addRegionWindow').window("close");
					}
					
				})
			})
		</script>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addRegion" action="${pageContext.request.contextPath}/regionAction_save.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
			</form>
		</div>
		</div>
		<!-- 修改页面的页面 -->
		<div class="easyui-window" title="区域修改" id="editRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				$("#edit").click(function(){
					var r=$("#editRegion").form("validate");
					if(r){
						$("#editRegion").submit();
					}
				})
			})
		</script>
		<!-- 修改页面 -->
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editRegion" action="${pageContext.request.contextPath}/regionAction_edit.action"   method="post" >
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<input type="hidden" name="id" value="rows[0].id"/>
					<tr>
						<td>省</td>
						<td><input type="text" name="province" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	<!-- 模糊查询（条件查询） -->
	<div class="easyui-window" title="查询区域" id="searchRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="search" icon="icon-search" href="#" class="easyui-linkbutton" plain="true" >查询</a>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				$("#search").click(function(){
					var data = $("#searchRegion").serializeJson();
					$("#grid").datagrid("load",data);
					$("#searchRegionWindow").window("close");
				})
			})
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
		</script>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="searchRegion"  method="post" >
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<!-- <input type="hidden" name="id" value="rows[0].id"/> -->
					<tr>
						<td>省</td>
						<td><input  type="text" name="province"  class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" class="easyui-validatebox" /></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>