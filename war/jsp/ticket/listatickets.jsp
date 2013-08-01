<%@include file="/jsp/template/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>

<div id="content">
	<h3>Meus Tickets</h3>
	
	<table class="table table-bordered table-hover" style="width:auto">
		<tr>
		  <th>Titulo</th>
		  <th>Descrição</th>		  
		  <th>Sistema</th>																
		  <th>Componente</th>
		  <th>Status</th>							
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
			</tr>
		</c:forEach>
	</table>
		
</div>