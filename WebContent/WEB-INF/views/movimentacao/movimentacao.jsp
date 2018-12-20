<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>

<c:url value="/app/movimentacao/salvar" var="movimentar" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movimentação</title>
<script type="text/javascript">
window.onload=function(){
	$(document).ready(function() { 
	    $('select').material_select();
	});
	}
</script>
</head>
<body>
	<c:import url="../templates/menu.jsp"></c:import>
	
	<div>
		<h1>Movimentação</h1>
	
		<form:form modelAttribute="movimentacao" action="${movimentar}" method="post" class="cadastro">
			<div class="input-field col s12">
		    <form:select path="item.id" items="${itens}" itemValue="id" itemLabel="patrimonio.nome"/>
		    <label>Selecione o Item</label>
		    <form:errors path="item" class="error" />
		  </div>
		  <div class="input-field col s12">
		    <form:select path="ambienteDestino.id" items="${ambientes}" itemValue="id" itemLabel="nome"/>
		    <label>Selecione o Destino</label>
		    <form:errors path="ambienteDestino" class="error" />
		  </div>
		  <button type="submit" class="btn " style="margin: 5% 40%;">Movimentar</button>
		</form:form>
	</div>
</body>
</html>