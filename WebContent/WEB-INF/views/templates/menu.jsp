<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<c:url value="/assets" var="assets" />
	<c:url value="/assets/css" var="cssRoot" />
	<c:url value="/assets/js" var="jsRoot" />
	<c:url value="/app/sair" var="sair" />
	<c:url value="/app/movimentacao" var="movimentacao"/>
	<c:url value="/app/patrimonio" var="patrimonios"/>
	<c:url value="/app/adm/patrimonio/novo" var="addPatrimonio"/>
	<c:url value="/app/ambiente" var="ambientes"/>
	<c:url value="/app/item/novo" var="addItens"/>
	<c:url value="/app" var="itens"/>
	<c:url value="/app/adm/usuario" var="usuarios"/>
	<c:url value="/app/adm/usuario/novo" var="addUsuario"/>
	<c:url value="/app/usuario/senha/nova" var="alterarSenha"/>
	<link rel="stylesheet" href="${cssRoot}/materialize.min.css" />
	<link rel="stylesheet" href="${cssRoot }/style.css" />
	<script src="${jsRoot}/jquery-3.2.1.min.js"></script>
	<script src="${jsRoot}/materialize.min.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$(".menu").sideNav();
		});
	</script>
	<style>
		.iconDrop{
			width: 15px;
    		height: 15px;
		}
		.sairIcon{
			    width: 30px;
    		height: 30px;
    	margin-top: 10px;
		}
	</style>
</head>
<body>
	<nav class="background"> 
		<div class="nav-wrapper">
      <a href="${itens}" class="brand-logo center">IANES</a>
      <ul id="nav-mobile">
      	<%--<li><a class="menu" data-activates="slide-out"><img src="${assets}/images/menuIcon.jpg" class="menuIcon"></a></li> --%>
      	<li><a class="menu" data-activates="slide-out"><i class="material-icons">menu</i></a></li>
        <li><a href="${addItens}">Adicionar Item</a></li>
        <c:if test="${usuarioAutenticado.tipo eq 'ADMINISTRADOR'}">
        	<li><a href="${addPatrimonio}">Novo Patrimônio</a></li>
        </c:if>
        <li class="right"><a href="${movimentacao}">Movimentar</a></li>
      </ul>
    </div>
	</nav>
	
	<ul id="slide-out" class="side-nav sidenav-fixed">
		<li>
			<div class="user-view">
				<div class="background">
					<img>
				</div>
				<c:choose>
					<c:when test="${not empty usuarioAutenticado.caminhoFoto }">
						<img class="circle" src="${assets}/usuariosFotos/foto_${usuarioAutenticado.id}">
					</c:when>
					<c:otherwise>
						<img class="circle" src="${assets}/images/collaborator.png">
					</c:otherwise>
				</c:choose>
				
				<span class="white-text name">${usuarioAutenticado.nome}</span>
	      		<span class="white-text email">${usuarioAutenticado.email}</span>
	      		<a href="${alterarSenha }" id="linkSenha" class="white-text">Alterar senha</a>
			</div>
		</li>
		<li><a href="${movimentacao }" class="waves-effect">Realizar Movimentação</a></li>
		<li><div class="divider"></div></li>
		
				<li class="no-padding">
					<ul class="collapsible collapsible-accordion">
						<li>
							<a class="collapsible-header">Itens<img src="${assets}/images/arrowDropDown.png" class="iconDrop"></a>
							<div class="collapsible-body">
								<ul>
									<li><a href="${itens}">Itens</a></li>
									<li><a href="${addItens}" class="waves-effect">Adicionar Item</a></li>
								</ul>
							</div>
						</li>
					</ul>
				</li>
		<li><div class="divider"></div></li>
		
		<c:choose>
			<c:when test="${usuarioAutenticado.tipo eq 'ADMINISTRADOR'}">
				<li class="no-padding">
					<ul class="collapsible collapsible-accordion">
						<li>
							<a class="collapsible-header">Patrimônios <img src="${assets}/images/arrowDropDown.png" class="iconDrop"></a>
							<div class="collapsible-body">
								<ul>
									<li><a href="${patrimonios}">Patrimônios Cadastrados</a></li>
									<li><a href="${addPatrimonio}">Cadastrar Patrimônio</a></li>
								</ul>
							</div>
						</li>
					</ul>
				</li>
			</c:when>
			<c:otherwise>
				<li><a href="${patrimonios }" class="waves-effect">Patrimônios</a></li>
			</c:otherwise>
		</c:choose>
		<li><div class="divider"></div></li>
		
		<li><a href="${ambientes }" class="waves-effect">Ambientes</a></li>
		<li><div class="divider"></div></li>
		<c:if test="${usuarioAutenticado.tipo eq 'ADMINISTRADOR' }">
			<li class="no-padding">
				<ul class="collapsible collapsible-accordion">
					<li>
						<a class="collapsible-header">Usuários <img src="${assets}/images/arrowDropDown.png" class="iconDrop"></a>
						<div class="collapsible-body">
							<ul>
								<li><a href="${usuarios }">Usuários</a></li>
								<li><a href="${addUsuario }">Novo Usuário</a></li>
							</ul>
						</div>
					</li>
				</ul>
			</li>
		</c:if>
		<li><div class="divider"></div></li>
		<li id="sair" class="background"><a href="${sair}" class="white-text"><img src="${assets}/images/sair.png" class="sairIcon">Sair</a></li>
	</ul>
</body>
</html>