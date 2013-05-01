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
			
			
			<form action="/shiro_hai/app/auth/authenticate" method="post" class="form-horizontal"  >
				<div class="control-group">
					<label class="control-label">Username</label>
					<div class="controls">
						<input type="text" name="username" value="" placeholder="username" id="username"/>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label">Password</label>
					<div class="controls">
						<input type="password" name="password" value="" placeholder="****" id="password">	
					</div>
				</div>


				<input type="submit" class="btn" id="login" value="Login"/>
			</form>	
		</div>	
	</div>
	
	
	


</body>
</html>