<%@include file="/jsp/template/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>

<div id="content">
	<c:set var="usuario" value="${requestScope.item}" scope="page" />
	
	<h3>Criação de Conta no Sistema</h3>
	
	<p>Entre com os seus dados no formulário abaixo:</p>
	
	<form action="/login/salva.do" method="post">
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
		
		<tr><td colspan="2">
			<label for="email">E-mail:</label>
			<input type="text" name="email" id="email" value="${usuario.email}" class="long" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="senha">Senha:</label>
			<input type="text" name="senha" id="senha" value="${usuario.senha}" class="long" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="confirmacao_senha">Confirmação da Senha:</label>
			<input type="text" name="confirmacao_senha" id="confirmacao_senha" class="long" /><br>
		</td></tr>
		</table>		

		<input type="submit" name="btSubmit" value="Enviar Dados" class="btn btn-primary"/><br>
	</form>
</div>