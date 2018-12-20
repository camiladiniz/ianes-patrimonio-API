<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/app/usuario/senha/alterar" var="alterarSenha" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nova senha</title>
</head>
<body>
	<c:import url="../templates/menu.jsp"></c:import>
	<h2>Alterar Senha</h2>
	
	<form:form modelAttribute="usuario" action="${alterarSenha}" method="post" class="cadastro">
		<form:hidden path="id" />
		
		<label for="senhaAtual">Senha Atual</label>
		<form:password path="senha" id="senhaAtual" />
		<%--<form:errors path="senha" element="div" cssClass="error" />  --%>
		
		<label for="novaSenha">Nova Senha</label>
		<input name="novaSenha" type="password" id="novaSenha">
		<form:errors path="senha" element="div" cssClass="error" />
		<button type="submit" class="waves-light btn" style="margin: 5% 40%;">Alterar Senha</button>
	</form:form>
</body>
</html>