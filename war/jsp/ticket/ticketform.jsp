<%@include file="/jsp/template/template.jsp"%>

<style>
table {
	width: 600px;
}

td {
	width: 350px;
}

.ui-datepicker-trigger {
	margin-bottom: 10px;
}
</style>

<div id="content">
	<c:set var="ticket" value="${requestScope.item}" scope="page" />
	<c:set var="qtdecomentarios" value="${requestScope.qtdecomentarios}"
		scope="page" />

	<h3>Formul�rio de Ticket</h3>

	<div class="span5" style="margin-left: 0px;">

		<form action="/ticket/salvaTicket.do" method="post">
			<input type="hidden" name="id" value="${ticket.id}" />
			<table>
				<tr>
					<td colspan="2"><label for="titulo">T�tulo:</label> <input
						type="text" name="titulo" id="titulo" value="${ticket.titulo}"
						class="long" /><br></td>
				</tr>
				<tr>
					<td colspan="2"><label for="descricao">Descricao:</label> <textarea
							name="descricao" id="descricao" rows="3" style="width: 90%">${ticket.descricao}</textarea><br>
					</td>
				</tr>
				<tr>
					<td><label for="sistema">Sistema:</label> <pdsw:seletorSistema
							name="sistema" value="${ticket.sistema}" id="sistema"
							blankOption="Selecione o sistema ..." /><br></td>
					<td><label for="componente">Componente:</label> <select
						name="componente" id="componente"><option value="-1">Selecione
								o componente ...</option></select><br></td>
				</tr>

			</table>
			<!-- 	Verifica se � um ticket novo -->
			<c:if test="${ticket.id != -1}">
				<table>
					<tr>
						<td colspan="2">
							<hr class="divider" style="width: 90%">
						</td>
					</tr>
					<tr>
						<td><label for="status">Status Atual:</label> <input
							id="statusAtual" type="text" name="statusAtual"
							value="${ticket.statusAtual}" disabled /><br></td>
						<td><label for="status">Novo Status:</label> <pdsw:seletorStatus
								id="statusAtual" idticket="${ticket.id}"
								tipoUsuario="${usuario.tipoUsuario}"
								value="${ticket.statusAtual}" /><br></td>
					</tr>
					<tr>
						<td colspan="2"><label for="comentario">Coment�rio:</label> <textarea
								rows="3" cols="2" style="width: 90%" name="comentario"
								id="comentario"></textarea></td>
					</tr>
				</table>
			</c:if>
			<br> <input type="submit" name="btSubmit" value="Enviar Dados"
				class="btn btn-primary" /><br>
		</form>
	</div>
	
	<!-- 	Area de comentarios dos tickets -->
	<!-- 	Verifica se � um ticket novo -->
	<div class="span6">
		<c:if test="${qtdecomentarios > 0}">
		<legend>Altera��es de status</legend>
			<table class="table table-bordered">			
			<thead>
				<tr>
					<th>Data de altera��o</th>
					<th>Status</th>
					<th>Coment�rio</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${requestScope.historico}">
					<tr>
						<td><c:out value="${item.dataRegistro}" /></td>
						<td><c:out value="${item.status}" /></td>
						<td><c:out value="${item.comentario}" /></td>
					</tr>
				</c:forEach>
			</tbody>
			</table>
		</c:if>
	</div>
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