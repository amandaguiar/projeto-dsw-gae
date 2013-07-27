package br.unirio.projetodswgae.dao;

import com.google.appengine.api.datastore.Entity;

import br.unirio.projetodswgae.model.Ticket;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

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
		ticket.setIdentificador(getStringProperty(e, "identificador", ""));
		ticket.setTitulo(getStringProperty(e, "titulo", ""));
		ticket.setDescricao(getStringProperty(e, "descricao", ""));
		ticket.setComponente(getStringProperty(e, "componente", ""));
		ticket.setSistema(getStringProperty(e, "sistema", ""));
		ticket.setStatus(getStringProperty(e, "status", ""));
		ticket.setOperadorResponsavel(getStringProperty(e, "operador", ""));
		return ticket;
	}
	
	@Override
	protected void save(Ticket ticket, Entity e)
	{
		e.setProperty("identificador", ticket.getIdentificador());
		e.setProperty("titulo", ticket.getTitulo());
		e.setProperty("descricao", ticket.getDescricao());
		e.setProperty("sistema", ticket.getSistema());
		e.setProperty("componente", ticket.getComponente());
		e.setProperty("status", ticket.getStatus());
		e.setProperty("operador", ticket.getOperadorResponsavel());
	}
}
