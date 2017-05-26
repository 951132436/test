<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆页面</title>
<script src="${pageContext.request.contextPath }/js/jquery-1.8.3.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/style.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style_grey.css" />
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
<style>
input[type=text] {
	width: 80%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

input[type=password] {
	width: 80%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

#loginform\:codeInput {
	margin-left: 1px;
	margin-top: 1px;
}

#loginform\:vCode {
	margin: 0px 0 0 60px;
	height: 34px;
}
</style>
<script type="text/javascript">
	if(window.self != window.top){
		window.top.location = window.location;
	}
	
	<%
		String username = request.getParameter("username");
		request.setAttribute("username", username);
	%>
	$(function(){
		//给账号input赋值
		var a = "${username}";
		$("#username").val(a);
		
		/* $("#btn").click(function(){
			alert(1);
			//from表单提交 获取手机号 然后给手机号发送随机数 
		//	var num = $("#mobileId").val();
			//var url="${pageContext.request.contextPath}/userAction_sendMessage.action";
			//$.post(url,{"mobileNum":num},function(data){});
		}); */
		
		 
		/* document.getElementById("btn").onclick=function(){
			var url="${pageContext.request.contextPath}/userAction_sendMessage.action";
			$.post(url,{"mobileNum":num},function(data){});
			time(this);
		}  */
		$("#btn").click(function(){
			var url="${pageContext.request.contextPath}/userAction_sendMessage.action";
			$.post(url,null,function(data){});
			//alert(222);
			time(this);
		});
		
		
		//找到保存的按钮
		$("#bbtn").click(function(){
			var num = $("#saveId").val();
			var url="${pageContext.request.contextPath}/userAction_saveMessage.action";
			$.post(url,{"mobileNum":num},function(data){
				//alert(data);
				if(data == "0"){
					//提示错误的信息
					$.messager.alert('提示消息','手机验证码不匹配！','info');
					$("#saveId").focus();
					//将那个按钮设置成不能使用的的的  
					return false;
				}else{ 
					//窗口打开
					$("#editPwdWindow").window('open');
			 	}
			},"json"); 
		});
		
		// extend the 'equals' rule    
		$.extend($.fn.validatebox.defaults.rules, {    
		    equals: {    
		        validator: function(value,param){    
		            return value == $(param[0]).val();    
		        },    
		        message: '两次密码不一致'   
		    }    
		}); 
		
		//修改密码
		$("#btnEp").click(function(){
			var f = $("#editPwdForm").form("validate");
			if(f){
				//提交form表单
				$.ajax({
					url : '${pageContext.request.contextPath}/userAction_updatePwdByUsername.action',
					type : 'POST',
					dataType : 'json',
					async:false,
					data:{"username":$("#username").val(),"password":$("#newPwd").val()},
					success : function(data) {
					},
				});

				$("#editPwdWindow").window('close');
				$.messager.confirm('提示信息','密码修改成功！请点击任意按钮进行登录！',function(r){    
				    if (r){    
				    	location.href="login.jsp";   
				    }else{
				    	location.href="login.jsp";   
				    }    
				}); 
			}
		});
		
		
	});
	var wait=60;  
	function time(o) {  
        if (wait == 0) {  
            o.removeAttribute("disabled");            
            o.value="免费获取验证码";  
            wait = 60;  
        } else {  
            o.setAttribute("disabled", true);  
            o.value="重新发送(" + wait + ")";  
            wait--;  
            setTimeout(function() {  
                time(o)  
            },  
            1000)  
        }  
    }; 
</script>
</head>
<body>
	<div
		style="width: 900px; height: 50px; position: absolute; text-align: left; left: 50%; top: 50%; margin-left: -450px;; margin-top: -280px;">
		<span style="float: right; margin-top: 35px; color: #488ED5;">新BOS系统，致力于便捷、安全、稳定等方面的客户体验</span>
	</div>

	
	<div class="main-inner" id="mainCnt"
		style="width: 900px; height: 440px; overflow: hidden; position: absolute; left: 50%; top: 50%; margin-left: -450px; margin-top: -220px; background-image: url('${pageContext.request.contextPath }/images/bg_login.jpg')">
		<div id="loginBlock" class="login"
			style="margin-top: 80px; height: 255px;">
			<div class="loginFunc">
				<div id="lbNormal" class="loginFuncMobile">找回密码</div>
			</div>
			<div class="loginForm">
				<form >
					<div id="idInputLine" class="loginFormIpt showPlaceholder"
						style="margin-top: 5px;">
						<input style="width:58%;margin-left:40%" type="text" name="mobileNum" value="" id="mobileId" />
						<label for="idInput" class="placeholder" id="idPlaceholder">请输入手机号：</label>
					</div>
					<div class="forgetPwdLine"></div>
					<div id="pwdInputLine" class="loginFormIpt showPlaceholder">
						<input style="width:58%;margin-left:40%" type="text" name="mobileNum" id="saveId" />
						 
						<label for="pwdInput" class="placeholder" id="pwdPlaceholder">请输入验证码：</label>
						
					</div>
					<div class="loginFormIpt loginFormIptWiotTh"
						style="margin-top:58px;">
						<div id="codeInputLine" class=""
							style="margin-left:0px;margin-top:-40px;width:100px;">
							
							<input type="button" id="btn" value="免费获取验证码" />  
						</div>
						<%-- <a href="javascript:document.getElementById('loginform').submit()" id="loginform:j_id19" name="loginform:j_id19">
						<span
							id="loginform:loginBtn" class="btn btn-login"
							style="margin-top:-36px;">登录</span>
						</a> --%>
						<a style="margin-top:-34px;float:right;" id="bbtn">
						<input  value="保存" class="btn btn-login"/>
						</a>
						<!--忘记密码的功能  跳转到新的页面-->
					</div>
					<div style="color: red" align="center">
						<s:actionerror/>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	
	<div
		style="width: 900px; height: 50px; position: absolute; text-align: left; left: 50%; top: 50%; margin-left: -450px;; margin-top: 220px;">
		<span style="color: #488ED5;">Powered By www.itcast.cn</span><span
			style="color: #488ED5;margin-left:10px;">推荐浏览器（右键链接-目标另存为）：<a
			href="http://download.firefox.com.cn/releases/full/23.0/zh-CN/Firefox-full-latest.exe">Firefox</a>
		</span>
	</div>
	<!--修改密码窗口-->
	
    <div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
        maximizable="false" icon="icon-save"  style="width: 400px; height: 200px; padding: 5px;
        background: #fafafa">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
               <form id="editPwdForm" action="userAction_updatePwdByUsername.action" method="post">
               <input type="hidden" id="username" name="username"/>
               <input type="hidden" id="password" name="password"/>
               
                <table cellpadding=3>
                    <tr>
                        <td>新密码：</td>
                        <td><input id="newPwd" name="newPwd" type="Password" data-options="validType:'length[4,6]',required:true" class="txt01 easyui-validatebox" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td>	
                        	<!-- url:校验地址规则
                        		 length:校验输入项长度
                        		 emil:校验邮箱规则 -->
                        	<input id="rePwd" name="rePwd" type="password" validType="equals['#newPwd']"  data-options="validType:'length[4,6]',required:true" class="txt01 easyui-validatebox"/>
                        </td>
                    </tr>
                </table>
                </form>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
</body>
