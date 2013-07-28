package br.unirio.projetodswgae.actions;

import br.unirio.projetodswgae.dao.DAOFactory;
import br.unirio.projetodswgae.model.Ticket;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.SuccessRedirect;
import br.unirio.simplemvc.utils.Crypto;

public class ActionTicket extends Action{

	/**
	 * A��o para a cria��o de um novo ticket
	 */
	@Any("/jsp/ticket/novoticket.jsp")
	public String novoTicket()
	{
		Ticket ticket = new Ticket();
		setAttribute("item", ticket);
		return SUCCESS;
	}
	
	/**
	 * A��o de salvamento de novos tickets
	 */
	@SuccessRedirect("/login/login.do")
	@Error("/jsp/ticket/novoticket.jsp")
	public String salvaTicket() throws ActionException
	{
		// Pega o identificador do usu�rio
		int id = getIntParameter("id", -1);

		// Captura ou cria o usu�rio
		Ticket ticket = (id == -1) ? new Ticket() : DAOFactory.getTicketDAO().get(id);

		// Disponibiliza os dados para o caso de erros
		setAttribute("item", ticket);

		// Captura os dados do formul�rio
		ticket.setTitulo(getParameter("titulo", ""));
		ticket.setDescricao(getParameter("descricao", ""));
		ticket.setSistema(getParameter("sistema", ""));
		ticket.setComponente(getParameter("componente", ""));
		
		if (ticket.getId() <= 0)
		{
			ticket.setIdentificador(Crypto.hash(String.valueOf(id)));
			
		}
		
		// Verifica as regras de neg�cio
		checkNonEmpty(ticket.getTitulo(), "O titulo do ticket n�o pode ser vazio.");
		checkLength(ticket.getTitulo(), 80, "O nome do usu�rio.");

		checkNonEmpty(ticket.getSistema(), "O sistema n�o pode ser vazio.");
		
		checkNonEmpty(ticket.getComponente(), "O componente n�o pode ser vazio.");
		// implementear verificacao para quando o componente selecionado n�o perten�e ao sistema selecionado

		// Salva os dados do usu�rio
		DAOFactory.getTicketDAO().put(ticket);
		return addRedirectNotice("Ticket registrado com sucesso.");
	}
}
