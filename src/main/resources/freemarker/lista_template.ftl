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
						<div style="border-bottom: 1px solid #E5E5E5;">
							<h3>Mercadorias <small> Listagem</small></h3>
						</div>
						
						<#if error?has_content>
						<div class="alert alert-error">
						    ${error}
						</div>
						</#if>
						
						<table class="table table-hover">
							<thead>
								<tr>
									<th>#</th>
									<th>Nome</th>
									<th>Descrição</th>
									<th>Quantidade</th>
									<th>Preço</th>
								</tr>
							</thead>
							<#list mercadorias as m>
							<tr>
								<td>${m_index+1}</td>
								<td>
									<a href="/edit/${m["id"]}" title="Editar ${m["nome"]}">${m["nome"]}</a>
								</td>
								<td>${m["descricao"]}</td>
								<td>${m["quantidade"]}</td>
								<td>${m["precoFmt"]}</td>
							</tr>
							</#list>
							</table>
							<form id="atualizaMercadoria" action="/" method="GET">
								<div class="control-group">
							   		<div class="controls">
							   			<button id="salvar" class="btn btn-success">Atualizar</button>
							   		</div>
							   	</div>
							</form>
					</div>
		    		
		    		<footer>
						<#include "footerFragment.ftl">
			    	</footer>
			    </div>
			</div>
		</div>
	</body>
</html>
