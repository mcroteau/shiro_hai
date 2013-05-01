<%@ page import="java.lang.String" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
	<%@include file="/jsp/common/head.jsp" %>
    <sitemesh:write property='head'/>
</head>
<body>
	<div class="container">
		
		
		<div class="row">
			<div class="span12">
				<%@include file="/jsp/common/navigation.jsp" %>
			</div>
		</div>
	
		<div class="row">
			<div class="span12">
				<a href="/shiro_hai/app/auth/login">Login</a>
			</div>
		</div>

		<div class="row">
			<div class="span12">
				<h2>subject : <shiro:notAuthenticated>not</shiro:notAuthenticated></h2>
			</div>
		</div>
		

			
		<sitemesh:write property='body'></sitemesh:write>
	
	</div>
	
</body>
</html>