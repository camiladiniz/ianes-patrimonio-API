<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"  uri="http://www.springframework.org/tags/form" %>

<c:url value="/app/item/salvar" var="salvarItem" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar item</title>
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
	<h1>Novo Item</h1>
	<form:form modelAttribute="item" action="${salvarItem }" method="post" class="cadastro">
		<form:hidden path="id"/>
		
		<div class="input-field col s12">
	    <form:select path="patrimonio.id" items="${patrimonios}" itemValue="id" itemLabel="nome">
	    </form:select>
	    <label>Selecione o Patrimônio</label>
	  </div>
	  
	  <div class="input-field col s12">
	   <form:select path="ambiente.id" items="${ambientes}" itemValue="id" itemLabel="nome"> 
	   </form:select>
	    <label>Selecione o ambiente</label>
	  </div>
	  <button type="submit" class="btn" style="margin: 5% 40%;">Adicionar</button>
	</form:form>
</body>
</html>