<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/assets" var="assets" />
<c:url value="/app/ambiente" var="ambientesUrl" />
<c:url value="/app/adm/ambiente/salvar" var="salvarAmbiente" />
<c:url value="/app/adm/ambiente/excluir" var="excluirAmbiente" />
<c:url value="/app" var="itens" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ambientes</title>
</head>
<body>
	<c:import url="../templates/menu.jsp"></c:import>
	
	<c:if test="${usuarioAutenticado.tipo eq 'ADMINISTRADOR' }">
		<section class="row">
			<div class="row">
			    <form:form modelAttribute="ambiente" action="${salvarAmbiente}" method="post" class="col s12">
			    	<div id="addAmbiente">
				      	<form:hidden path="id"/>
				        	<span class="input-field col s12" id="ambInput">
				        		<form:input path="nome" id="ambiente" type="text"/>
				          		<label for="ambiente">Ambiente</label>
				          		<form:errors path="nome" class="error" />
				        	</span>
				        	<c:choose>
					        	<c:when test="${empty ambiente.id }">
					        		<button id="btnAddAmbiente" type="submit" class="waves-light btn" >Adicionar</button>
					      		</c:when>
				      			<c:otherwise>
						      		<button id="btnAddAmbiente" type="submit" class="waves-light btn" >Salvar</button>
					      		</c:otherwise>
					      	</c:choose>
				       </div>
				    </form:form>
				  </div>
			</section>
		</c:if>
		<section id="listaAmbientes">
			<h1>Ambientes</h1>
			<ul class="collection">
				<c:forEach items="${ambientes}" var="ambiente">
					<li class="collection-item">
						<!-- link vai para itens com busca de pesquisa de itens do ambiente selecionado -->
						<a href="${itens}?id=${ambiente.id}">
					    	<span class="title">${ambiente.nome}</span>
						</a>
						<c:if test="${usuarioAutenticado.tipo eq 'ADMINISTRADOR' }">
				       		<a href="${excluirAmbiente}?id=${ambiente.id}" class="secondary-content"><img src="${assets}/images/trash.png" width="30px" height="30px" alt="excluir"></a>
				        	<a href="${ambientesUrl}?id=${ambiente.id}" class="secondary-content"><img src="${assets}/images/update.png" width="30px" height="30px" style="margin-right: 80px" alt="alterar"></a>
				    	</c:if>
				    </li>
			    </c:forEach>
			  </ul>
		</section>
</body>
</html>