<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/assets" var="assets"/>
<c:url value="/app/movimentacao" var="movimentar"/>
<c:url value="/app/movimentacao/historico" var="historico"/>
<c:url value="/app/adm/item/excluir" var="excluirItem"/>
<c:url value="/app" var="urlItens"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Itens</title>
<script type="text/javascript">
window.onload=function(){
	$(document).ready(function() {
	    $('select').material_select();
	});
	}
</script>
<style>
	#classificacao{
		width: 35%;
	}
	.cartao{
		    width: 400px;
	}
	.classificacaoSelect{
		width: 300px;
		 margin-right: 50px;
	}
	.pad{
		padding-top: 10px;
    	padding-bottom: 15px;
	}
</style>
</head>
<body class="backgroundGrey">
	<c:import url="../templates/menu.jsp"></c:import>
	<h1>Itens</h1>	 
	<div class="filtroBusca">
		<div class="classif">
		 	<form action="${urlItens}" method="get" class="flex classif">
		 		<div class="input-field col s12">
		 			<select name="patId" class="classificacaoSelect">
				 		<option value="" disabled selected>Todos</option>
					 	<c:forEach items="${patrimonios}" var="patrimonio">
					 		<option value="${patrimonio.id}">${patrimonio.nome}</option>
					 	</c:forEach>
					</select>
					<label>Classificar por Patrimônio</label>
		 		</div>
			 	<div class="input-field col s12">
					<select name="id" class="classificacaoSelect">
						<option value="" disabled selected>Todos</option>
					 	<c:forEach items="${ambientes}" var="ambiente">
					 		<option value="${ambiente.id}">${ambiente.nome}</option>
					 	</c:forEach>
					</select>
					<label>Classificar por ambiente</label>
				</div>
				<button type="submit" class="btn radiusBtn" style="margin-top: 16px">Pesquisar</button>
			</form>
	  	</div>
  	</div>
	<section id="itens" class="flex">
  		<c:forEach items="${itens }" var="item" >
		  <div class="row">
		    <div class="col s12 m6 cartao">
		      <div class="card hoverable pad">
		      	<h4>${item.patrimonio.nome}</h4>
		        <div class="card-image">
		          <%--<img src="${assets}/patrimonioImagens/imagem_${item.patrimonio.id}"> --%>
		        	<c:if test="${usuarioAutenticado.tipo eq 'ADMINISTRADOR'}">
		          		<a href="${excluirItem}?id=${item.id}" class="btn-floating halfway-fab"><img src="${assets}/images/excluir.png" alt="excluir"></a>
		          	</c:if>
		        </div>
		        <div class="card-content">
		          <p><b>Identificação: </b>${item.id }</p>
		          <p><b>Categoria: </b>${item.patrimonio.categoria.nome } </p>
		          <p><b>Ambiente atual: </b>${item.ambiente.nome } </p>
		          <p><b>Cadastrado por: </b>${item.usuario.nome}</p>
		          <c:if test="${item.dataUltimaMovimentacao != null }">
		          	<p><b>Última atualização: </b><fmt:formatDate value="${item.dataUltimaMovimentacao}" pattern="dd/MM/yyyy"/></p>
		          </c:if>
		        </div>
		        <span style=" margin: 8%;">
			          <a href="${historico}?id=${item.id}" class="btn m20">Histórico</a>
			          <a href="${movimentar}?id=${item.id}" class="btn m20">Movimentar</a>
	        	  </span>
		      </div>
		    </div>
		  </div>
	</c:forEach>
	</section>
</body>
</html>