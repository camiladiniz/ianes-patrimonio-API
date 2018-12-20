<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:url value="/assets" var="assets" />
<c:url value="/app/adm/categoria/deletar" var="excluirCategoria" />
<c:url value="/app/adm/categoria/editar" var="editarCategoria" />
<c:url value="/app/adm/patrimonio/salvar" var="salvarPatrimonio" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adicionar Patrimônio</title>
<script>
window.onload=function(){
	$(document).ready(function() {
	    $('select').material_select();
	});
	
	let $selectCategorias = $("#selectCategorias");
	let $addNovaCategoria = $('#addNovaCategoria');
	
	$addNovaCategoria.hide();
	  $selectCategorias.change(function() {
		if($selectCategorias.val() == 'OUTRA_CATEGORIA'){
			$addNovaCategoria.show();
		}else{
			$addNovaCategoria.hide();
		}
	  });
	}
</script>
</head>
<body>
	<c:import url="../templates/menu.jsp"></c:import>
    	<form:form modelAttribute="patrimonio" action="${salvarPatrimonio }" method="post" class="cadastro" enctype="multipart/form-data">
    		<form:hidden path="id"/>
    		<h1>Cadastrar Patrimônio</h1>
    		<div class="input-field col s6">
          		<form:input path="nome" placeholder="Nome do Patrimônio" id="nomeId" type="text" />
          		<label for="nomeId">Patrimônio</label>
          		<form:errors path="nome" class="error" />
       	 	</div>
        	<div class="input-field col s6" id="selecionar">
				<select name="categoria.id" id="selectCategorias">
					<c:forEach items="${categorias}" var="categoria">
						<option value="${categoria.id}">${categoria.nome}</option>
				 	</c:forEach>
				 	<option value="OUTRA_CATEGORIA">Outra Categoria</option>
				</select>
				<label>Categoria</label>
				<a id="linkDeletarCategoria" href="${excluirCategoria }?id=categoria.id"style="display: none;"><img src="${assets}/images/trash.png" width="30px" height="30px" alt="excluir"></a>
		  		<form:errors path="categoria" class="error"/>
		  	</div>
			<div class="input-field col s6" id="addNovaCategoria">
          		<input path="categoria" placeholder="Nova categoria" id="categoria" type="text" name="novaCategoria" />
          		<label for="categoria">Nova categoria</label>
			</div>
			<br/>
			<div>
				<div>
					<label for="inputImagem">Foto do Patrimônio</label>
					<input type="file" name="imagem" accept=".png, .jpg, .jpeg">
				</div>
			</div>
		  	<button type="submit" class="btn" style="margin: 5% 40%;">Adicionar</button>
		</form:form>
		
		<script>
			let selectCategorias = document.getElementById("selectCategorias");
			let linkDeletarCategoria = document.getElementById('linkDeletarCategoria');

			selectCategorias.onchange = () => {
				let valor = selectCategorias.options[selectCategorias.selectedIndex].value;
				console.log(valor);
				if(!isNaN(Number(valor))){
					linkDeletarCategoria.style.display = "block";
					linkDeletarCategoria.setAttribute('href', "${excluirCategoria}?id=" + valor);	
				}else{
					linkDeletarCategoria.style.display = "none";
				}
				
			};
		</script>
		
		<script>
		
		$("#selecionar").change(function() {
		    var $this, secao, atual, campo;
		  
		    campo = $("div[data-name]");
		    
		    campo.addClass("hide");

		    if (this.value !== "") {
		        secao = $('option[data-section][value="' + this.value + '"]', this).attr("data-section");
		      
		        atual = campo.filter("[data-name=" + secao + "]");
		      
		        if (atual.length !== 0) {
		            atual.removeClass("hide");
		        }
		    }
		});
		</script>
</body>
</html>