<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro" %> --%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx }/static/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx }/static/font-awesome/css/font-awesome.css" rel="stylesheet">
<link href="${ctx }/static/css/animate.css" rel="stylesheet">
<link href="${ctx }/static/css/style.css" rel="stylesheet">
<link href="${ctx }/static/css/font.css" rel="stylesheet">
<title>协同OA办公管理系统</title>
</head>

<body class="gray-bg">
    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <div style="width: 300px;">
                <h1 class="logo-name" style="font-size: 70px;">新易捷</h1>
            </div>
            <h3>欢迎您</h3>
            <p></p>
            <p></p>
            <form class="m-t" role="form" id="loginSubmitForm" action="${ctx }/login" method="post">
                <div class="form-group">
                    <input id="username" name="username" class="form-control" placeholder="用户名" required="">
                </div>
                <div class="form-group">
                    <input id="password" name="password" type="password" class="form-control" placeholder="密码" required="">
                </div> 
                <%
					Object error = request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
					if(error != null){
				%>
					<div style="color:red;">
						<span style="display:block;">登录失败  ${error}</span>
					</div>
				<%
					}
				%>
                <button type="submit" class="btn btn-primary block full-width m-b">登陆</button>
            </form>
            <p class="m-t"> <small>天津新易捷科技有限公司&copy; 2018</small> </p>
        </div>
    </div>
    <script src="${ctx }/static/js/jquery-2.1.1.js"></script>
    <script src="${ctx }/static/js/bootstrap.min.js"></script>
</body>
</html>