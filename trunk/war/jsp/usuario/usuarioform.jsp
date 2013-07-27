<%@include file="/jsp/template/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>
<div id="content">
	<c:set var="usuario" value="${requestScope.item}" scope="page" />
	
	<h3>Usuário</h3>
	
	<form action="/usuario/salvaUsuario.do" method="post">
		<input type="hidden" name="id" value="${usuario.id}"/>

		<table>
		<tr><td colspan="2">
			<label for="nome">Nome:</label>
			<input type="text" name="nome" id="nome" value="${usuario.nome}" class="long" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="sobrenome">Sobrenome:</label>
			<input type="text" name="sobrenome" id="sobrenome" value="${usuario.sobrenome}" class="long" /><br>
		</td></tr>
		</table>		

		<input type="submit" name="btSubmit" value="Enviar Dados" class="btn btn-primary"/><br>
	</form>
</div>