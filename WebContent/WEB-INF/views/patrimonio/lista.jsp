<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url value="/assets" var="assets" />
<c:url value="/app/adm/patrimonio/novo" var="alterarPatrimonio" />
<c:url value="/app/item/novo" var="adicionarItem" />
<c:url value="/app/adm/patrimonio/deletar" var="excluirPatrimonio" />
<c:url value="/app" var="itens" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patrimônios</title>
<style type="text/css">
	.col.s12.m6 {
    width: 30%;
    }
    h4{
    	color: #00CCCC;
    }
   	.pad{
   		padding-top: 5px;
   	}
</style>
</head>
<body class="backgroundGrey">
	<c:import url="../templates/menu.jsp"></c:import>
	<h1>Patrimônios</h1>
	<section id="patrimonios" class="flex">  
	  <c:forEach items="${patrimonios}" var="patrimonio" >
	  <div class="row">
	      <div class="card hoverable pad">
	      	<a href="${alterarPatrimonio}?id=${patrimonio.id}" class="center"><h4>${patrimonio.nome}</h4></a>
	        <div class="card-image">
	        
			<img src="${assets}/patrimonioImagens/imagem_${patrimonio.id}" class="imgPatrimonio">				
				
	          <c:if test="${usuarioAutenticado.tipo eq 'ADMINISTRADOR'}">
	          	<a href="${excluirPatrimonio}?id=${patrimonio.id}" class="btn-floating halfway-fab waves-effect waves-light "><img src="${assets}/images/excluir.png" alt="excluir" class=""></a>
	          </c:if>
	        </div>
	        <div class="card-content">
	          <p>Categoria: ${patrimonio.categoria.nome}</p>
	          <p>Cadastrado por: ${patrimonio.usuarioCadastrou.nome } </p>
	          <p>Data cadastro: <fmt:formatDate value="${patrimonio.dataCadastro}" pattern="dd/MM/yyyy"/> </p>
	        </div>
	       	<%--<a href="${adicionarItem }" class="btn" style="width: 100%">Adicionar Item</a>  --%>
	       	<a href="${itens}?patId=${patrimonio.id}" class="btn" style="width: 100%">Ver Itens Cadastrados</a>
	      </div>
	  </div>
	  </c:forEach>
	</section>
</body>
</html>