<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="z" tagdir="/WEB-INF/tags" %>
<html>
<head>
	<title>${title}</title>
</head>
<body>
	
	<div class="row">
		<div class="span12">
			<h1>${title}</h1>
		</div>
	</div>
	
	<div class="row" id="form">
		<div class="span12">
			<h5>Creds</h5>
			<p>${creds}</p>
			<p>username : ${username}</p>
		</div>	
	</div>
	

</body>
</html>