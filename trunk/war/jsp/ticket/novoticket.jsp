<%@include file="/jsp/template/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>

<div id="content">
	<c:set var="ticket" value="${requestScope.item}" scope="page" />
	
	<h3>Criação de Ticket</h3>
	
	<p>Entre com os dados do novo ticket no formulário abaixo:</p>
	
	<form action="/login/salvaTicket.do" method="post">
		<input type="hidden" name="id" value="${ticket.id}"/>

		<table>
		<tr><td colspan="2">
			<label for="titulo">Titulo:</label>
			<input type="text" name="titulo" id="titulo" value="${ticket.titulo}" class="long" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="descricao">Descricao:</label>
			<input type="text" name="descricao" id="descricao" value="${ticket.descricao}" class="long" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="sistema">Sistema:</label>
			<input type="text" name="sistema" id="sistema" value="${ticket.sistema}" class="long" /><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="componente">Componente:</label>
			<input type="text" name="componente" id="componente" value="${ticket.componente}" class="long" /><br>
		</td></tr>
		</table>		

		<input type="submit" name="btSubmit" value="Enviar Dados" class="btn btn-primary"/><br>
	</form>
</div>