<%@include file="/jsp/template/template.jsp"%>


<div id="content">
	<h3>Componentes</h3>
	<a class="btn btn-success" href="/componente/novoComponente.do"><i class="icon-plus icon-white"></i>&nbsp;&nbsp;Novo componente</a> 
	<br><br>
	
    <c:if test="${requestScope.hasItem}">
	<table class="table table-hover">
	<thead>
		<tr>
			<th>Nome</th>
			<th>Sistema</th>
			<th>E-mail Responsavel</th>
			<th>#</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="item" items="${requestScope.item}" varStatus="stat">
			<tr>
				<td><c:out value="${item.nome}" />&nbsp;</td>
				<td><c:out value="${item.sistema}" />&nbsp;</td>
				<td><c:out value="${item.emailOperadorResponsavel}" />&nbsp;</td>
				<td>				
					<a href='/componente/editaComponente.do?id=${item.id}'> 
						<i class="icon-pencil"></i>
					</a> 
					&nbsp;
					<a href="#myModal${item.id}" data-toggle="modal"> 
						<i class="icon-trash"></i>
					</a>					
				</td>
			</tr>
			<div id="myModal${item.id}" class="modal hide fade" tabindex="-1">
				<div class="modal-header">
					<h3>Confirmacao</h3>
				</div>
				<div class="modal-body">
					<p>Tem certeza de que deseja deletar o componente?</p>
				</div>

				<div class="modal-footer">
					<a class="btn" href='/componente/removeComponente.do?id=${item.id}'>Sim</a> 
					<a class="btn btn-primary" data-dismiss="modal">Nao</a>
				</div>
			</div>
		</c:forEach>
	</tbody>
	</table>

	<ul class="pager pull-left">
		<c:if test="${requestScope.hasPriorPage}">
			<li><a
				href='/componente/listaComponentes.do?page=${requestScope.page-1}'>Anterior</a></li>
		</c:if>
		<c:if test="${requestScope.noPriorPage}">
			<li class="disabled"><a href="#">Anterior</a></li>
		</c:if>
		<c:if test="${requestScope.hasNextPage}">
			<li><a
				href='/componente/listaComponentes.do?page=${requestScope.page+1}'>Proxima</a></li>
		</c:if>
		<c:if test="${requestScope.noNextPage}">
			<li class="disabled"><a href="#">Proxima</a></li>
		</c:if>
	</ul>
	</c:if>
	<c:if test="${requestScope.noItem}">
		<span class="span5 alert alert-error" style="margin-left:0px">Nehum componente encontrado.</span>
	</c:if>
</div>
