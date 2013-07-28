<%@include file="/jsp/template/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>

<div id="content">
	<c:set var="sistema" value="${requestScope.item}" scope="page" />
	<h3>Sistema</h3>
	
	<form action="/sistema/salvaSistema.do" method="post">
		<input type="hidden" name="id" value="${sistema.id}"/>

		<table>
		<tr>
			<td colspan="2">
				<label for="nome">Nome:</label>
				<input type="text" name="nome" id="nome" value="${sistema.nome}" class="long" /><br>
			</td>
		</tr>
		</table>

		<input type="submit" name="btSubmit" value="Enviar Dados" class="btn btn-primary"/><br>
	</form>
</div>