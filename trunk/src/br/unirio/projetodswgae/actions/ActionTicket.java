package br.unirio.projetodswgae.actions;

import java.util.List;
import java.util.UUID;

import br.unirio.projetodswgae.dao.DAOFactory;
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
	@Error("/jsp/ticket/ticketform.jsp")
	public String salvaTicket() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		// Pega o identificador do usuário
		int id = getIntParameter("id", -1);

		// Captura ou cria o usuário
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
		
		StatusTicket novoStatus = StatusTicket.get(getParameter("statusAtual", ""));
		
		/* verifica se é um ticket novo, senão é uma edição de ticket */
		if (ticket.getId() <= 0)
		{
			ticket.setId_usuario(usuario.getId());
			ticket.setIdentificador(String.valueOf(UUID.randomUUID()));
		}
		else if(novoStatus != null){
			ticket.setStatusAtual(novoStatus);
		}
		
		// Verifica as regras de negócio
		checkNonEmpty(ticket.getTitulo(), "O titulo do ticket não pode ser vazio.");
		checkLength(ticket.getTitulo(), 80, "O nome do usuário.");

		checkNonEmpty(ticket.getSistema(), "O sistema não pode ser vazio.");
		
		checkNonEmpty(ticket.getComponente(), "O componente não pode ser vazio.");		

		// Salva os dados do usuário
		DAOFactory.getTicketDAO().put(ticket);
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
		
		List<Ticket> tickets = DAOFactory.getTicketDAO().getTicketsUsuario(usuario, start, PAGE_SIZE);
		
		int count = DAOFactory.getTicketDAO().conta(usuario.getId());
		
		boolean hasNext = (count > (page+1) * PAGE_SIZE);
		boolean hasPrior = (page > 0);
				
		setAttribute("page", page);
		setAttribute("hasNextPage", hasNext);
		setAttribute("hasPriorPage", hasPrior);
		setAttribute("noPriorPage", !hasPrior);
		setAttribute("noNextPage", !hasNext);
		
		setAttribute("item", tickets);
		return SUCCESS;
	}
	
	@ErrorRedirect("/ticket/listaTickets.do")
	@Success("/jsp/ticket/ticketform.jsp")
	public String editaTicket() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		int id = getIntParameter("id", -1);
		Ticket ticket = DAOFactory.getTicketDAO().get(id);
		check(ticket != null, "O ticketnão existe.");		
		
		setAttribute("item", ticket);
		setAttribute("usuario", usuario);
		return SUCCESS;
	}
	
	/**
	 * Verifica quais status devem aparecer no form para o usuario
	 * @param statusAntigo
	 * @param statusAtual
	 * @param tipoUsuario
	 * @param statusTicket
	 */
	public static void verificaRegrasStatus(String statusAntigo, String statusAtual, String tipoUsuario, List<String> statusTicket){
		
		if( (tipoUsuario.equalsIgnoreCase(TipoUsuario.OPERADOR.getNome())) && 
		    (statusAtual.equalsIgnoreCase(StatusTicket.NOVO.getCodigo()) || statusAtual.equalsIgnoreCase(StatusTicket.REABERTO.getCodigo())) ){
			statusTicket.add(StatusTicket.RESOLVIDO.getCodigo());
			statusTicket.add(StatusTicket.INVALIDADO.getCodigo());
		}
		else if( (tipoUsuario.equalsIgnoreCase(TipoUsuario.USUARIO_FINAL.getNome())) &&
				(statusAtual.equalsIgnoreCase(StatusTicket.RESOLVIDO.getCodigo()) || statusAtual.equalsIgnoreCase(StatusTicket.INVALIDADO.getCodigo())) ){
			statusTicket.add(StatusTicket.REABERTO.getCodigo());
			statusTicket.add(StatusTicket.FECHADO.getCodigo());
		}
		else if(tipoUsuario.equalsIgnoreCase(TipoUsuario.ADMINISTRADOR.getNome())){
			statusTicket.add(StatusTicket.NOVO.getCodigo());
			statusTicket.add(StatusTicket.RESOLVIDO.getCodigo());
			statusTicket.add(StatusTicket.INVALIDADO.getCodigo());
			statusTicket.add(StatusTicket.REABERTO.getCodigo());
			statusTicket.add(StatusTicket.FECHADO.getCodigo());
		}
		
		
	}
}
