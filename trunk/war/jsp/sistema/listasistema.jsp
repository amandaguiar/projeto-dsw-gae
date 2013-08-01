<%@include file="/jsp/template/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>

<div id="content">
	<h3>Sistemas</h3>
	<a class="btn btn-success" href="/sistema/novoSistema.do">Cadastrar novo sistema</a>
	<br><br>
	<table class="table table-bordered table-hover" style="width:auto">
		<tr>
		  <th>Nome</th>		
		  <th style="width:15%"></th>		    		  					
		</tr>
		
		<c:forEach var="item" items="${requestScope.item}">
			<tr>
				<td>
					<c:out value="${item.nome}"/>
				</td>
				<td style="width:15%">
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
		<c:if test="${requestScope.hasNextPage}">	
			<li><a href='/sistema/listaSistemas.do?page=${requestScope.page+1}'>Pr�xima</a></li>	
		</c:if>
	</ul>

</div>