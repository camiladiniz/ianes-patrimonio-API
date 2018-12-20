<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/" var="raiz" />
<c:url value="/assets" var="assets" />
<c:url value="/app/adm/usuario/deletar" var="excluirUsuario"/>
<c:url value="/app/adm/usuario/editar" var="editarUsuario" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Usuários</title>
<style>
	card-content a{
		color: #00CCCC;
	}
	.users{
		height: 191px;
		width: 350px;
	}
</style>
</head>
<body class="backgroundGrey">
	<c:import url="../templates/menu.jsp"></c:import>
	<h1>Usuários</h1>
	
	<section class="flex">	 
  		<c:forEach items="${usuarios}" var="usuario">
	  		<div class="row">
	      		<div class="card horizontal users hoverable">
	        		<div class="card-image">
	        			
								<img src="${assets}/usuariosFotos/foto_${usuario.id}" alt="Foto do usuário">
							
	        		</div>
			        <div class="card-stacked">
			        	<div class="card-content">
	          				<a href="${editarUsuario}?id=${usuario.id}" class="corPadrao">${usuario.nome} ${usuario.sobrenome}</a>
	          				<p>${usuario.email }</p>
	          				<c:if test="${usuario.tipo eq 'ADMINISTRADOR' }">
	          					<p style="color: red">${usuario.tipo}</p>
	          				</c:if>
	        			</div>
	        			<div class="card-action">
	          				<a href="${excluirUsuario}?id=${usuario.id}">Excluir</a>
	        			</div>
	        		</div>
	      		</div>    
	  		</div>
  		</c:forEach>
	</section>
</body>
</html>