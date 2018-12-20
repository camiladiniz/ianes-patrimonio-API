<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/" var="raiz" />
<c:url value="/app/adm/usuario/salvar" var="salvarUsuario" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Novo usuário</title>
</head>
<body>	
	<main>
		<c:import url="../templates/menu.jsp"/>
		<h1>Cadastro de Usuário</h1>
		<form:form modelAttribute="usuario" action="${salvarUsuario}" method="post" class="cadastro" enctype="multipart/form-data">
			<form:hidden path="id"/>
			<label for="inputNome">Nome</label>
			<form:input path="nome" id="inputNome" />
			<form:errors path="nome" element="div" cssClass="error"/>
			
			<label for="inputSobrenome">Sobrenome</label>
			<form:input path="sobrenome" id="inputSobrenome"/>
			<form:errors path="sobrenome" element="div" cssClass="error" />			
			
			<div>
			<label for="inputEmail">Email</label>
			<c:choose>
			<c:when test="${usuario.id != null && not empty usuario.email }">
				<form:input path="email" type="email" id="inputEmail" readonly="true"/>
			</c:when>
			<c:otherwise>
				<form:input path="email" type="email" id="inputEmail"/>
			</c:otherwise>
			</c:choose>
			<form:errors path="email" element="div" cssClass="error"/>
			</div>
			
			<c:if test="${empty usuario.id }">
				<label for="inputSenha">Senha</label>
				<form:password path="senha" id="inputSenha"/>
				<form:errors path="senha" element="div" cssClass="error"/>
								
				<label for="inputConfirmaSenha">Confirmar Senha</label>
				<input type="password" id="inputConfirmaSenha" name="confirmacaoSenha"/>
				<form:errors path="senha" element="div" cssClass="error"/>
			</c:if>
			<input type="checkbox" name="isAdministrador" ${usuario.tipo eq 'ADMINISTRADOR' ? 'checked' : ''} id="inputAdministrador">
				<label for="inputAdministrador">
					<span>Administrador</span>
				</label>
				<br/>
				<div>
					<label for="inputFoto">Foto de Perfil</label>
					<input type="file" name="foto" accept=".png, .jpg, .jpeg">
				</div>
			<br/><br/>
			<button type="submit" class="waves-light btn" style="margin: 5% 40%;">Salvar</button>
		</form:form>
	</main>
	
	
</body>
</html>