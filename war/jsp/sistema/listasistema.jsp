<%@include file="/jsp/template/template.jsp"%>


<div id="content">
	<h3>Sistemas</h3>	
	<a class="btn btn-success" href="/sistema/novoSistema.do"><i class="icon-plus icon-white"></i>&nbsp;&nbsp;Novo sistema</a>
	<br>
		
	<form class="navbar-form pull-right" action="/sistema/filtraSistema.do" method="post">
    	<input name="filtro" id="filtro" type="text" class="search-query" placeholder="Buscar">
    	<button type="submit" class="btn"><i class="icon-search"></i></button>
    </form>
    <br><br><br>
    <c:if test="${requestScope.hasItem}">
	<table class="table table-hover">
	<thead>
		<tr>
		  <th>Nome</th>		
		  <th>#</th>		    		  					
		</tr>
	</thead>
	<tbody>	
		<c:forEach var="item" items="${requestScope.item}" varStatus="stat">
			<c:set var="i" value="confirmacaoRemocao${stat.count}" />
			<tr>
				<td>
					<a href="/componente/listaComponentes.do?sistema=${item.nome}"><c:out value="${item.nome}"/></a>
				</td>
				<td>
					<a style="visibility:visible" href='/sistema/editaSistema.do?id=${item.id}'><i class="icon-pencil"></i></a>
 					&nbsp;
 					<a href="#myModal${item.id}" data-toggle="modal"> 
						<i class="icon-trash"></i>
					</a>
				</td>
			</tr>
			<div id="myModal${item.id}" class="modal hide fade" tabindex="-1">
				<div class="modal-header">
					<h3>Confirmção</h3>
				</div>
				<div class="modal-body">
					<p>Tem certeza de que deseja deletar o sistema?</p>
				</div>

				<div class="modal-footer">
					<a class="btn" href='/sistema/removeSistema.do?id=${item.id}'>Sim</a> 
					<a class="btn btn-primary" data-dismiss="modal">Nao</a>
				</div>
			</div>
		</c:forEach>
	</tbody>
	</table>	
	
	<ul class="pager pull-left">
		<c:if test="${requestScope.hasPriorPage}">	
			<li><a href='/sistema/listaSistemas.do?page=${requestScope.page-1}'>Anterior</a></li>	 
		</c:if>
		<c:if test="${requestScope.noPriorPage}">	
			<li class="disabled"><a href="#">Anterior</a></li>	 
		</c:if>
		<c:if test="${requestScope.hasNextPage}">	
			<li><a href='/sistema/listaSistemas.do?page=${requestScope.page+1}'>Próxima</a></li>	
		</c:if>
		<c:if test="${requestScope.noNextPage}">	
			<li class="disabled"><a href="#">Próxima</a></li>	
		</c:if>
	</ul>
	</c:if>
	<c:if test="${requestScope.noItem}">
		<span class="span5 alert alert-error" style="margin-left:0px">Nehum sistema encontrado.</span>
	</c:if>
</div>
