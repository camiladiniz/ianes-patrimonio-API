<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url value="/app/movimentacao/historico" var="urlHistorico"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Histórico</title>
<%--Script responsável por executar o select --%>
<script type="text/javascript">
	window.onload=function(){
		$(document).ready(function() { 
		    $('select').material_select();
		});
	}
</script>
</head>
<body>
	<c:import url="../templates/menu.jsp"/>
	<h1>Histórico de movimentações</h1>
	<form method="get" action="${urlHistorico}" class="classif flex">
		<div class="input-field col s12">
			<select name="id" class="filtro">
			<option value="">Todos</option>
				<c:forEach items="${itens}" var="item">
					<option value="${item.id}">${item.patrimonio.nome} ${item.id}</option>
				 </c:forEach>
			</select>
			<label>Filtrar histórico por:</label>
		</div>	 
		<button type="submit" class="btn radiusBtn" style="margin-top: 23px; margin-left: 15px">Buscar</button>
	</form>
	<table class="striped centered" id="historicoTabela">
		<thead>
			<tr>
				<th>Movimentação</th>
				<th>Item</th>
				<th>Data</th>
				<th>Horário</th>
				<th>Origem</th>
				<th>Destino</th>
				<th>Responsável</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${movimentacoes}" var="movimentacao">
				<tr>
					<td>${movimentacao.id}</td>
					<td>${movimentacao.item.patrimonio.nome}</td>
					<td><fmt:formatDate value="${movimentacao.dataMovimentacao}" pattern="dd/MM/yyyy"/></td>
					<td><fmt:formatDate value="${movimentacao.dataMovimentacao}" pattern="hh:mm:ss"/></td>
					<td>${movimentacao.ambienteOrigem.nome}</td>
					<td>${movimentacao.ambienteDestino.nome}</td>
					<td>${movimentacao.usuario.nome}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>