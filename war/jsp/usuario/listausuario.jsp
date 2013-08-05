<%@include file="/jsp/template/template.jsp"%>

<div id="content">
	<h3>Usuários</h3>
	<form class="navbar-form pull-right" action="/usuario/filtraUsuario.do" method="post">
    	<input name="filtro" id="filtro" type="text" class="search-query" placeholder="Buscar">
    	<button type="submit" class="btn"><i class="icon-search"></i></button>
    </form>
    <br><br><br>
    <c:if test="${requestScope.hasItem}">
		<table class="table table-hover">
		<thead>
			<tr>
			  <th>Nome</th>		
			  <th>Sobrenome</th>
			  <th>E-mail</th>
			  <th>Tipo Usuário</th>	
			  <th>#</th>	  	    		  					
			</tr>
		</thead>
		<tbody>
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
		</tbody>
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
	</c:if>
	<c:if test="${requestScope.noItem}">
		<span class="span5 alert alert-error" style="margin-left:0px">Nehum usuário encontrado.</span>
	</c:if>
</div>
	