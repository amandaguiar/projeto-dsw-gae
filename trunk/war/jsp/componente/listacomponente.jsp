<%@include file="/jsp/template/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>

<div id="content">
	<h3>Componentes</h3>
	<a class="btn btn-success" href="/componente/novoComponente.do">Cadastrar novo componente</a>
	<br><br>
	<table class="table table-bordered table-hover">
		<tr>
		  <th>Nome</th>
		  <th>Sistema</th>
		  <th>E-mail Responsável</th>		
		  <th></th>					
		</tr>
		<c:forEach var="item" items="${requestScope.item}">
			<tr>
				<td>
					<c:out value="${item.nome}"/>&nbsp;
				</td>
				<td>
					<c:out value="${item.sistema}"/>&nbsp;
				</td>
				<td>
					<c:out value="${item.emailOperadorResponsavel}"/>&nbsp;
				</td>
				<td>
					<a href='/componente/editaComponente.do?id=${item.id}'><i class="icon-pencil"></i></a>					
					&nbsp;
					<a href="#"><i class="icon-trash"></i></a>					
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<ul class="pager">
		<c:if test="${requestScope.hasPriorPage}">	
			<li><a href='/componente/listaComponentes.do?page=${requestScope.page-1}'>Anterior</a></li>	 
		</c:if>
		<c:if test="${requestScope.hasNextPage}">	
			<li><a href='/componente/listaComponentes.do?page=${requestScope.page+1}'>Próxima</a></li>	
		</c:if>
	</ul>
</div>