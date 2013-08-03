<%@include file="/jsp/template/template.jsp"%>


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
		<c:forEach var="item" items="${requestScope.item}" varStatus="stat">
			<c:set var="i" value="confirmacaoRemocao${stat.count}" />
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
					<div id="${i}" style="visibility:hidden">
						<a style="visibility:visible" href='/componente/editaComponente.do?id=${item.id}'><i class="icon-pencil"></i></a>					
						&nbsp;
						<a href="#" style="visibility:visible" onclick="confirmacaoRemover('${i}')"><i class="icon-trash"></i></a>
						&nbsp; Tem certeza de que deseja deletar o componente? &nbsp; &nbsp;
						<a href="/componente/removeComponente.do?id=${item.id}">Sim</a> 
						&nbsp; 
						&nbsp; 
						&nbsp;
						<a href="#" onclick="cancelarRemocao('${i}')" >Nao</a>
					</div>				
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<ul class="pager pull-left">
		<c:if test="${requestScope.hasPriorPage}">	
			<li><a href='/componente/listaComponentes.do?page=${requestScope.page-1}'>Anterior</a></li>	 
		</c:if>
		<c:if test="${requestScope.noPriorPage}">	
			<li class="disabled"><a href="#">Anterior</a></li>	 
		</c:if>
		<c:if test="${requestScope.hasNextPage}">	
			<li><a href='/componente/listaComponentes.do?page=${requestScope.page+1}'>Próxima</a></li>	
		</c:if>
		<c:if test="${requestScope.noNextPage}">	
			<li class="disabled"><a href="#">Próxima</a></li>	
		</c:if>
	</ul>
</div>

<script>
function confirmacaoRemover(id)
{
	document.getElementById(id).style.visibility="visible";
}

function cancelarRemocao(id)
{
	document.getElementById(id).style.visibility="hidden";
}
</script>