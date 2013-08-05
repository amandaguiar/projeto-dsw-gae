<%@include file="/jsp/template/template.jsp"%>

<div id="content">
	<h3>Tickets</h3>
		
	<form class="navbar-form pull-right" action="/ticket/filtraTicket.do" method="post">
    	<input name="filtro" id="filtro" type="text" class="search-query" placeholder="Buscar">
    	<button type="submit" class="btn"><i class="icon-search"></i></button>
    </form>
    <br><br><br>
    <c:if test="${requestScope.hasItem}">
	<table class="table table-hover">
	<thead>
		<tr>			
			<th>Titulo</th>
		  	<th>Descrição</th>		  
		  	<th>Sistema</th>																
		  	<th>Componente</th>
		  	<th>Status</th>	
		</tr>
	</thead>
	<tbody>	
		<c:forEach var="item" items="${requestScope.item}">
			<tr>				
				<td>
					<a href='/ticket/editaTicket.do?id=${item.id}'><c:out value="${item.titulo}"/></a>		
				</td>
				<td>
					<c:out value="${item.descricao}"/>&nbsp;
				</td>
				<td>
					<c:out value="${item.sistema}"/>
				</td>
				<td>
					<c:out value="${item.componente}"/>
				</td>
				<td>
					<c:out value="${item.statusAtual}"/>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	
	<ul class="pager pull-left">
		<c:if test="${requestScope.hasPriorPage}">	
			<li><a href='/ticket/listaTickets.do?page=${requestScope.page-1}'>Anterior</a></li>	 
		</c:if>
		<c:if test="${requestScope.noPriorPage}">	
			<li class="disabled"><a href="#">Anterior</a></li>	 
		</c:if>
		<c:if test="${requestScope.hasNextPage}">	
			<li><a href='/ticket/listaTickets.do?page=${requestScope.page+1}'>Próxima</a></li>	
		</c:if>
		<c:if test="${requestScope.noNextPage}">	
			<li class="disabled"><a href="#">Próxima</a></li>	
		</c:if>
	</ul>
	</c:if>
	<c:if test="${requestScope.noItem}">
		<span class="span5 alert alert-error" style="margin-left:0px">Nehum ticket encontrado.</span>
	</c:if>
</div>