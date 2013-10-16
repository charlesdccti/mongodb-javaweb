<!doctype html>
<html>
	<head>
		<#include "headFragment.ftl">
	</head>

  	<body>
  		<div class="header">
    		<div class="container">
				<div>	
					<div class="logo">
						<img src="http://yawdemo-spring-mvc.appspot.com/resources/img/logo_yaw.png" />
					</div>
					
					<h1 class="title">Projeto: Java <small>(c/ Freemaker, Bootstrap e JQuery)</small> com MongoDB</h1>
				</div>
    		</div>
    	</div>
  		
  		<div class="container">
	   		<div id="wrapper" class="row-fluid show-grid">
			    <#include "menuFragment.ftl">
			    
			    <div class="span8">

					<div>
					
					<#if error?has_content>
					<div class="alert alert-error">
					    ${error}
					</div>
					</#if>
						
					<form id="frmMercadoria" class="form-horizontal" action="/save" method="POST">
						<input type="hidden" name="id" value="${mercadoria["id"]}" />
						<fieldset>
					   		<legend><h3>Mercadoria <small> Incluir</small></h3></legend>
					   		<div class="control-group">
					    		<label class="control-label">Nome</label>
					    		<div class="controls">
					    			<input id="nome" name="nome" class="input-large" type="text" value="${mercadoria["nome"]}"/>
					    			
					    		</div>
					   		</div>
					   		
					   		<div class="control-group">
					    		<label class="control-label">Descrição</label>
					    		<div class="controls">
					    			<input id="descricao" name="descricao" class="input-large" type="text" value="${mercadoria["descricao"]}"/>
					    			
					    		</div>
					   		</div>
					   		
					   		<div class="control-group">
					    		<label class="control-label">Quantidade</label>
					    		<div class="controls">
					    			<input id="quantidade" name="quantidade" class="input-small" type="text" value="${mercadoria["quantidade"]}"/>
					    			
					    		</div>
					   		</div>
					   		
					   		<div class="control-group">
					    		<label class="control-label">Preço</label>
					    		<div class="controls">
					    			<input id="preco" name="preco" class="input-small" type="text" value="${mercadoria["preco"]}"/>
					    			
					    		</div>
					   		</div>
					   	</fieldset>
					</form>
					
					
					<div class="control-group form-horizontal">
						<div class="controls">
							<button id="salvar" class="btn btn-success">Salvar</button>
							<a href="/"><button class="btn">Cancelar</button></a>
							<#if (mercadoria["id"])?has_content>
							<button id="excluir" class="btn btn-danger">Excluir</button>
							</#if>
						</div>
					</div>
					
					<script>
					$(document).ready(function () {
					 	$("#frmMercadoria").validate({
					 		 	rules: {
					 	 		 	nome: { required: true, minlength: 5 },
					 	 		 	quantidade: { required: true, number: true },
									preco: { required: true, moeda: true }
					 		 	}
					 	});
					 	
					 	$("#salvar").click(function () { $("#frmMercadoria").submit(); });
					
					    $.validator.addMethod("moeda",
					    	function(value, element) {
					    		return this.optional(element) || /^-?(?:\d+|\d{1,3}(?:.\d{3})+)?(?:\,\d+)?$/.test(value);
					    	}, 'Valor invalido para moeda');
					});
					</script>
					
					<#if (mercadoria["id"])?has_content>
					<form id="deleteMercadoria" action="/delete/${mercadoria["id"]}" method="post" />
	   	
				   	<script>
				   	$(document).ready(function () {
				   		$("#excluir").click(function () {
				   	   		$("#deleteMercadoria").submit();
				     	});
				   	});
					</script>
					</#if>
						
					</div>
		    		
		    		<footer>
						<#include "footerFragment.ftl">
			    	</footer>
			    </div>
			</div>
		</div>
	</body>
</html>
