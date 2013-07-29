<%@include file="/jsp/template/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>

<div id="content">
	<h3>Meus Tickets</h3>
	
	<table>
		<tr>
		  <th>Titulo</th>
		  <th>Sistema</th>																
		  <th>Componente</th>
		  <th>Status</th>							
		</tr>
		
		<c:forEach var="item" items="${requestScope.item}">
			<tr>
				<td colspan="2">
					<c:out value="${item.titulo}"/>&nbsp;
				</td>
				<td colspan="2">
					<c:out value="${item.sistema}"/>
				</td>
				<td colspan="2">
					<c:out value="${item.componente}"/>
				</td>
				<td colspan="2">
					<c:out value="${item.status}"/>
				</td>
			</tr>
		</c:forEach>
	</table>
		
</div>