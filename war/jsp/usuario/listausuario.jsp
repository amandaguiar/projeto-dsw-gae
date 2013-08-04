<%@include file="/jsp/template/template.jsp"%>

<div id="content">
	<h3>Usuários</h3>
	<table class="table table-bordered table-hover">
		<tr>
		  <th>Nome</th>		
		  <th>Sobrenome</th>
		  <th>E-mail</th>
		  <th>Tipo Usuário</th>	
		  <th></th>	  	    		  					
		</tr>
		
		<c:forEach var="item" items="${requestScope.item}">
			<tr>
				<td>				
					<c:out value="${item.nome}"/>
				</td>
				<td>				
					<c:out value="${item.sobrenome}"/>
				</td>
				<td>
					<c:out value="${item.email}"/>
				</td>
				<td>
					<c:out value="${item.tipoUsuario}"/>
				</td>
				<td>
				<c:if test="${item.tipoUsuario == 'USUARIO_FINAL'}">				
					<a class="btn btn-small btn-warning" href="/usuario/promoverOperador.do?email=${item.email}">Promover a operador</a>				
				</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>	
	
	<ul class="pager pull-left">
		<c:if test="${requestScope.hasPriorPage}">	
			<li><a href='/usuario/listaUsuarios.do?page=${requestScope.page-1}'>Anterior</a></li>	 
		</c:if>
		<c:if test="${requestScope.noPriorPage}">	
			<li class="disabled"><a href="#">Anterior</a></li>	 
		</c:if>
		<c:if test="${requestScope.hasNextPage}">	
			<li><a href='/usuario/listaUsuarios.do?page=${requestScope.page+1}'>Próxima</a></li>	
		</c:if>
		<c:if test="${requestScope.noNextPage}">	
			<li class="disabled"><a href="#">Próxima</a></li>	
		</c:if>
	</ul>
</div>
	