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
							<h3>Ops <small> algo ruim aconteceu</small></h3>
						</div>
					
						<div class="row-fluid" style="padding-top: 10px; padding-bottom: 10px">
							<div class="span1"></div>
							
							<div class="span10">
									<p>${error}</p>
							</div>
						</div>
						
					</div>
		    		
		    		<footer>
						<#include "footerFragment.ftl">
			    	</footer>
			    </div>
			</div>
		</div>
	</body>
</html>
