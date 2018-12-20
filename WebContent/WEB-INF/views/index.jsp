<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/usuario/autenticar" var="autenticarUsuario" />
<c:url value="/" var="raiz" />
<c:url value="/assets/css" var="cssRoot" />
<c:url value="/assets/js" var="jsRoot" />
<link rel="stylesheet" href="${cssRoot}/materialize.min.css"/>
<script src="${jsRoot}/jquery-3.2.1.min.js"></script>
<script src="${jsRoot}/materialize.min.js"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ianes-Patrimônio</title>
<style>
	*{
		background-color: #f2f2f2;
		text-align: center;
	}
	body{
		margin-left: 20%;
		margin-right: 20%;
		margin-top: 5%;
	}
	h1{
		text-align: center;
		color: #26a69a;
	}
	#botao{
		border-radius: 20px;	
		padding: 0px 80px;
	}
	button#botao:hover {
	    background-color: white;
	    color: #26a69a;
	}
</style>
</head>
<body>
	<h1>IANES</h1>
	<h3>Gerenciamento de Patrimônios</h3>
	<p><strong>O sistema que gerencia funcionários, patrimônios e empresa de forma inteligente!</b></p>
	<div class="row">
    <form action="${autenticarUsuario}" method="post" class="col s12">
    	<div class="row">
       		<div class="input-field col s12">
	          <input name="email" id="email" maxlength="120" type="email" />
	          <label for="email">Email</label>
        	</div>
      	</div>
	      <div class="row">
	      	<div class="input-field col s12">
	          <input name="senha" maxlength="20" id="password" type="password" />
	          <label for="senha">Senha</label>
	        </div>
	      </div>
	      <button id="botao" class="btn" type="submit">Entrar</button>
    </form>
  </div>
</body>
</html>