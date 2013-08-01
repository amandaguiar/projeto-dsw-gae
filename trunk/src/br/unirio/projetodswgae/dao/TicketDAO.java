package br.unirio.projetodswgae.dao;

import java.util.List;

import br.unirio.projetodswgae.model.StatusTicket;
import br.unirio.projetodswgae.model.Ticket;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class TicketDAO extends AbstractDAO<Ticket> {
	
	protected TicketDAO()
	{
		super("Ticket");
	}

	@Override
	protected Ticket load(Entity e)
	{
		Ticket ticket = new Ticket();
		ticket.setId((int)e.getKey().getId());
		ticket.setId_usuario(getIntProperty(e, "id_usuario"));
		ticket.setIdentificador(getStringProperty(e, "identificador", ""));
		ticket.setTitulo(getStringProperty(e, "titulo", ""));
		ticket.setDescricao(getStringProperty(e, "descricao", ""));
		ticket.setComponente(getStringProperty(e, "componente", ""));
		ticket.setSistema(getStringProperty(e, "sistema", ""));
		ticket.setStatus(StatusTicket.get(getStringProperty(e, "status", StatusTicket.NOVO.getCodigo())));
		ticket.setOperadorResponsavel(getStringProperty(e, "operador", ""));
		return ticket;
	}
	
	@Override
	protected void save(Ticket ticket, Entity e)
	{
		e.setProperty("id_usuario", ticket.getId_usuario());
		e.setProperty("identificador", ticket.getIdentificador());
		e.setProperty("titulo", ticket.getTitulo());
		e.setProperty("descricao", ticket.getDescricao());
		e.setProperty("sistema", ticket.getSistema());
		e.setProperty("componente", ticket.getComponente());
		e.setProperty("status", ticket.getStatus().getCodigo());
		e.setProperty("operador", ticket.getOperadorResponsavel());
	}
	
	/**
	 * 
	 * Retorna uma lista de tickets de um usuário
	 * 
	 */
	public List<Ticket> getTickets(int id_usuario, int start, int page_size){
		return list(start, page_size, exactFilter("id_usuario", FilterOperator.EQUAL, id_usuario));
	}
	
	public int conta(int id_usuario) {
		return count(exactFilter("id_usuario", FilterOperator.EQUAL, id_usuario));
	}
	
}
