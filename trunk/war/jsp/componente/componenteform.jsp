<%@include file="/jsp/template/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>
<div id="content">
	<c:set var="componente" value="${requestScope.item}" scope="page" />
	
	<h3>Componente</h3>
	
	<form action="/componente/salvaComponente.do" method="post">
		<input type="hidden" name="id" value="${componente.id}"/>

		<table>
		<tr>
			<td colspan="2">
				<label for="nome">Nome:</label>
				<input type="text" name="nome" id="nome" value="${componente.nome}" class="long" /><br>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<label for="sistema">Sistema:</label>
				<pdsw:seletorSistema name="sistema" value="${componente.sistema}" id="sistema" blankOption="Selecione o sistema ..."/><br>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<label for="email">E-mail operador responsável:</label>
				<input type="text" name="email" id="email" value="${componente.emailOperadorResponsavel}" class="long" /><br>
			</td>			
		</tr>
		</table>

		<input type="submit" name="btSubmit" value="Enviar Dados" class="btn btn-primary"/><br>
	</form>
</div>