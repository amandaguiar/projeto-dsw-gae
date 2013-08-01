<%@include file="/jsp/template/template.jsp"%>

<div id="content">
	<h3>Tickets</h3>
	
	<table class="table table-bordered table-hover">
		<tr>			
			<th>Titulo</th>
		  	<th>Descrição</th>		  
		  	<th>Sistema</th>																
		  	<th>Componente</th>
		  	<th>Status</th>			
		  	<th></th>					
		</tr>
		
		<c:forEach var="item" items="${requestScope.item}">
			<tr>				
				<td>
					<c:out value="${item.titulo}"/>
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
					<c:out value="${item.status}"/>
				</td>
				<td>
					<a href='/ticket/editaTicket.do?id=${item.id}'><i class="icon-pencil"></i></a>					
				</td>
			</tr>
		</c:forEach>
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
		
</div>