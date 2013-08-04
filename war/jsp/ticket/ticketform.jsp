<%@include file="/jsp/template/template.jsp"%>

<style>
table { width: 600px; }
td { width: 350px; }
.ui-datepicker-trigger { margin-bottom: 10px; }
</style>

<div id="content">
	<c:set var="ticket" value="${requestScope.item}" scope="page" />
	<c:set var="usuario" value="${requestScope.usuario}" scope="page" />
	
	<h3>Formulário de Ticket</h3>
	
	<p>Entre com os dados do ticket no formulário abaixo:</p>
	
	<form action="/ticket/salvaTicket.do" method="post">
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
				
		<tr><td>		
			<label for="sistema">Sistema:</label>
			<pdsw:seletorSistema name="sistema" value="${ticket.sistema}" id="sistema" blankOption="Selecione o sistema ..."/><br>
		</td><td>
			<label for="componente">Componente:</label>
			<select name="componente" id="componente"><option value="-1">Selecione o componente ...</option></select><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="status">Status:</label>
			<input id="statusAtual" type="text" name="statusAtual" value="${ticket.statusAtual}" disabled/><br>
		</td></tr>
		
		<tr><td colspan="2">
			<label for="status">Novo Status:</label>
			<pdsw:seletorStatus id="statusAtual" idticket="${ticket.id}" tipoUsuario="${usuario.tipoUsuario}" value="${ticket.statusAtual}" statusantigo="${ticket.statusAntigo}"/><br>
		</td></tr>
		</table>

		<input type="submit" name="btSubmit" value="Enviar Dados" class="btn btn-primary"/><br>
	</form>	
</div>

<script src="/js/jquery/jquery.ui.min.js"></script>
<script src="/js/jquery/jquery.maskedinput.js"></script>
<script src="/js/jquery/jquery.meiomask.js"></script>
<script src="/js/simplemvc/simplemvc.datefield.js"></script>
<script src="/js/simplemvc/simplemvc.phonefield.js"></script>
<script src="/js/simplemvc/simplemvc.intfield.js"></script>
<script src="/js/forms.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	
	$("#sistema").change(function() {
		capturaComponentes("sistema", "componente", "${item.componente}");
	});
	$("#sistema").trigger("change");
});
</script>