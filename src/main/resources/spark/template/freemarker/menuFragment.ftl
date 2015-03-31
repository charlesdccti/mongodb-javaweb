<div class="sidebar">
	<div class="well span2">
		<div>
			<ul class="nav nav-list" style="padding-top: 15px;">
				<li><em>Mercadorias</em></li>
				
				<#if (activeLista)?has_content>
					<#assign activeL= "active" />
				<#else>
					<#assign activeL= "" />
				</#if>
				
				<#if (activeIncluir)?has_content>
					<#assign activeI= "active" />
				<#else>
					<#assign activeI= "" />
				</#if>
				
				<#if (activeAbout)?has_content>
					<#assign activeS= "active" />
				<#else>
					<#assign activeS= "" />
				</#if>
				
				<li class="${activeL}">
					<a href="/"><i class="icon-list"></i> Lista</a>
				</li>
				<li class="${activeI}">
					<a href="/new"><i class="icon-pencil"></i> Incluir</a>
		        </li>
				<li class="divider"></li>
				<li class="${activeS}">
					<a href="/about"><i class="icon-book"></i> Sobre</a>
				</li>
			</ul>
		</div>
	</div>
</div>