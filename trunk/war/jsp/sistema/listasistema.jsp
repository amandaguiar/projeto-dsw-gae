<%@include file="/jsp/template/template.jsp"%>


<div id="content">
	<h3>Sistemas</h3>	
	<a class="btn btn-success" href="/sistema/novoSistema.do">Cadastrar novo sistema</a>
	<br><br>
	<table class="table table-bordered table-hover">
		<tr>
		  <th>Nome</th>		
		  <th></th>		    		  					
		</tr>
		
		<c:forEach var="item" items="${requestScope.item}">
			<tr>
				<td>
					<a href="/componente/listaComponentes.do?sistema=${item.nome}"><c:out value="${item.nome}"/></a>
				</td>
				<td>
					<a href='/sistema/editaSistema.do?id=${item.id}'><i class="icon-pencil"></i></a>					
					&nbsp;
					<a href="#"><i class="icon-trash"></i></a>					
				</td>
			</tr>
		</c:forEach>
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

</div>