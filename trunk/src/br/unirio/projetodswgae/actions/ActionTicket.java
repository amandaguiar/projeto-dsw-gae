package br.unirio.projetodswgae.actions;

import java.util.List;
import java.util.UUID;

import br.unirio.projetodswgae.dao.DAOFactory;
import br.unirio.projetodswgae.model.HistoricoStatus;
import br.unirio.projetodswgae.model.StatusTicket;
import br.unirio.projetodswgae.model.Ticket;
import br.unirio.projetodswgae.model.TipoUsuario;
import br.unirio.projetodswgae.model.Usuario;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.ErrorRedirect;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.actions.results.SuccessRedirect;

public class ActionTicket extends Action{

	public static final int PAGE_SIZE = 25;
	
	/**
	 * Ação para a criação de um novo ticket
	 */
	@Any("/jsp/ticket/ticketform.jsp")
	public String novoTicket() throws ActionException
	{
		Ticket ticket = new Ticket();
		setAttribute("item", ticket);
		return SUCCESS;
	}
	
	/**
	 * Ação de salvamento de novos tickets
	 */
	@SuccessRedirect("/ticket/listaTickets.do")
	@Error(ACTION_DEPENDENT)
	public String salvaTicket() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		// Pega o identificador do ticket
		int id = getIntParameter("id", -1);

		// Captura ou cria o ticket
		Ticket ticket = (id == -1) ? new Ticket() : DAOFactory.getTicketDAO().get(id);

		// Disponibiliza os dados para o caso de erros
		setAttribute("item", ticket);

		// Captura os dados do formulário
		ticket.setTitulo(getParameter("titulo", ""));
		ticket.setDescricao(getParameter("descricao", ""));
		ticket.setSistema(getParameter("sistema", ""));
		ticket.setComponente(getParameter("componente", ""));
		
		if(!ticket.getComponente().isEmpty())
			ticket.setEmailOperadorResponsavel(DAOFactory.getComponenteDAO().getComponenteEmailOperador(ticket));
		
		String comentario = getParameter("comentario", "");
		StatusTicket statusAntigo = ticket.getStatusAtual();
		StatusTicket novoStatus = StatusTicket.get(getParameter("statusAtual", ""));
		
		setActionDependentURL("/ticket/editaTicket.do");
		
		/* verifica se é um ticket novo, senão é uma edição de ticket */
		if (ticket.getId() <= 0)
		{
			setActionDependentURL("/jsp/ticket/ticketform.jsp");
			ticket.setId_usuario(usuario.getId());
			ticket.setIdentificador(String.valueOf(UUID.randomUUID()));
		}
		/* altera o status atual caso tenha sido escolhido um novo status */
		else if(statusAntigo != novoStatus){
			ticket.setStatusAtual(novoStatus);
		}
		
		// Verifica as regras de negócio
		checkNonEmpty(ticket.getTitulo(), "O titulo do ticket não pode ser vazio.");
		checkLength(ticket.getTitulo(), 80, "O nome do usuário.");
		checkNonEmpty(ticket.getSistema(), "O sistema não pode ser vazio.");		
		checkNonEmpty(ticket.getComponente(), "O componente não pode ser vazio.");		

		/* verifica se o novo status é Resolvido, Invalidado, Reaberto ou Fechado e se existe um comentário e se houve mudança de status */
		if( (ticket.getStatusAtual() ==  StatusTicket.RESOLVIDO || ticket.getStatusAtual() == StatusTicket.INVALIDADO || 
			 ticket.getStatusAtual() ==  StatusTicket.REABERTO || ticket.getStatusAtual() == StatusTicket.FECHADO)  &&
			 comentario.isEmpty() && 
			 statusAntigo != novoStatus){
			return addError("Para alterar o status do ticket, é necessário escrever um comentário.");
		}
		
		// Salva os dados do ticket
		DAOFactory.getTicketDAO().put(ticket);
		
		//Salva os dados de status do ticket		
		DAOFactory.getHistoricoStatusDAO().registraStatus(ticket.getId(), ticket.getStatusAtual(), comentario);
		
		return addRedirectNotice("Ticket registrado com sucesso.");
	}
		
	/**
	 * Ação para listar tickets de um usuário
	 */
	@Success("/jsp/ticket/listatickets.jsp")
	@Error("/login/login.do")
	public String listaTickets() throws ActionException{
		Usuario usuario = (Usuario) checkLogged();
		
		int page = getIntParameter("page", 0);
		int start = (PAGE_SIZE * page);
		String filtro = getAttribute("filtro") != null ? getAttribute("filtro").toString() : "";
		
		List<Ticket> tickets = DAOFactory.getTicketDAO().getTicketsUsuario(usuario, filtro, start, PAGE_SIZE);
		
		int count = DAOFactory.getTicketDAO().conta(usuario.getId());
		
		boolean hasNext = (count > (page+1) * PAGE_SIZE);
		boolean hasPrior = (page > 0);
		boolean hasItem = tickets.size() > 0 ? true : false;
				
		setAttribute("page", page);
		setAttribute("hasItem", hasItem);
		setAttribute("noItem", !hasItem);
		setAttribute("hasNextPage", hasNext);
		setAttribute("hasPriorPage", hasPrior);
		setAttribute("noPriorPage", !hasPrior);
		setAttribute("noNextPage", !hasNext);
		
		setAttribute("item", tickets);
		return SUCCESS;
	}
	
	/**
	 * Ação para editar um ticket
	 */
	@ErrorRedirect("/ticket/listaTickets.do")
	@Success("/jsp/ticket/ticketform.jsp")
	public String editaTicket() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		int id = getIntParameter("id", -1);
		Ticket ticket = DAOFactory.getTicketDAO().get(id);
		check(ticket != null, "O ticket não existe.");		
		
		int page = getIntParameter("page", 0);
		int start = (PAGE_SIZE * page);
		
		List<HistoricoStatus> historico = DAOFactory.getHistoricoStatusDAO().getComentariosTicket(ticket, start, PAGE_SIZE);
		
		int count = DAOFactory.getHistoricoStatusDAO().conta(ticket.getId());
		
		boolean hasNext = (count > (page+1) * PAGE_SIZE);
		boolean hasPrior = (page > 0);
				
		setAttribute("page", page);
		setAttribute("hasNextPage", hasNext);
		setAttribute("hasPriorPage", hasPrior);
		setAttribute("noPriorPage", !hasPrior);
		setAttribute("noNextPage", !hasNext);
		
		
		setAttribute("item", ticket);
		setAttribute("usuario", usuario);
		setAttribute("historico", historico);
		setAttribute("qtdecomentarios", count);
		return SUCCESS;
	}
	
	/**
	 * Verifica quais status devem aparecer no form para o usuario	 
	 */
	public static void verificaRegrasStatus(String statusAtual, String tipoUsuario, List<String> statusTicket){
		statusTicket.add(statusAtual);
		if( (tipoUsuario.equalsIgnoreCase(TipoUsuario.OPERADOR.getNome())) && 
		    (statusAtual.equalsIgnoreCase(StatusTicket.NOVO.toString()) || statusAtual.equalsIgnoreCase(StatusTicket.REABERTO.toString())) ){
			statusTicket.add(StatusTicket.RESOLVIDO.toString());
			statusTicket.add(StatusTicket.INVALIDADO.toString());
		}
		else if( (tipoUsuario.equalsIgnoreCase(TipoUsuario.USUARIO_FINAL.getNome())) &&
				(statusAtual.equalsIgnoreCase(StatusTicket.RESOLVIDO.toString()) || statusAtual.equalsIgnoreCase(StatusTicket.INVALIDADO.toString())) ){
			statusTicket.add(StatusTicket.REABERTO.toString());
			statusTicket.add(StatusTicket.FECHADO.toString());
		}
		else if(tipoUsuario.equalsIgnoreCase(TipoUsuario.ADMINISTRADOR.getNome())){
			statusTicket.add(StatusTicket.NOVO.toString());
			statusTicket.add(StatusTicket.RESOLVIDO.toString());
			statusTicket.add(StatusTicket.INVALIDADO.toString());
			statusTicket.add(StatusTicket.REABERTO.toString());
			statusTicket.add(StatusTicket.FECHADO.toString());
		}
	}
	
	/**
	 * Ação para filtrar tickets
	 */
	@Any("/ticket/listaTickets.do")
	public String filtraTicket() throws ActionException {
		
		String filtro = getParameter("filtro", "");
		setAttribute("filtro", filtro);
		return SUCCESS;		
	}
}
