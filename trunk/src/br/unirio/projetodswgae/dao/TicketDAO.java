package br.unirio.projetodswgae.dao;

import java.util.List;

import br.unirio.projetodswgae.model.StatusTicket;
import br.unirio.projetodswgae.model.Ticket;
import br.unirio.projetodswgae.model.TipoUsuario;
import br.unirio.projetodswgae.model.Usuario;
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
		ticket.setStatusAtual(StatusTicket.get(getStringProperty(e, "statusAtual", StatusTicket.NOVO.getCodigo())));
		ticket.setEmailOperadorResponsavel(getStringProperty(e, "operador", ""));
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
		e.setProperty("statusAtual", ticket.getStatusAtual().getCodigo());
		e.setProperty("emailOperadorResponsavel", ticket.getEmailOperadorResponsavel());
	}
	
	/**
	 * Retorna uma lista de tickets de um usuário
	 */
	public List<Ticket> getTicketsUsuario(Usuario usuario, String filtro, int start, int page_size){
		
		if(usuario.getTipoUsuario().getCodigo().equals(TipoUsuario.USUARIO_FINAL.getCodigo())){
			if (filtro.equals(""))
				return list(start, page_size, exactFilter("id_usuario", FilterOperator.EQUAL, usuario.getId()));
			else
				return list(start, page_size, and(exactFilter("id_usuario", FilterOperator.EQUAL, usuario.getId()), 
					or(exactFilter("titulo", FilterOperator.EQUAL, filtro), exactFilter("sistema", FilterOperator.EQUAL, filtro))));
		}
		else if(usuario.getTipoUsuario().getCodigo().equals(TipoUsuario.OPERADOR.getCodigo())) {
			if (filtro.equals(""))
				return list(start, page_size, exactFilter("emailOperadorResponsavel", FilterOperator.EQUAL, usuario.getEmail()));
			else
				return list(start, page_size, and(exactFilter("emailOperadorResponsavel", FilterOperator.EQUAL, usuario.getEmail()),
					or(exactFilter("titulo", FilterOperator.EQUAL, filtro), exactFilter("sistema", FilterOperator.EQUAL, filtro))));
		}
		else {
			if (filtro.equals(""))
				return list(start, page_size);
			else
				return list(start, page_size, or(exactFilter("titulo", FilterOperator.EQUAL, filtro), exactFilter("sistema", FilterOperator.EQUAL, filtro)));
		}
	}
	
	/**
	 * Retorna uma lista de tickets de um componente de um sistema
	 */
	public List<Ticket> getTicketsComponenteSistema(String componente, String sistema){
		return list(and(exactFilter("componente", FilterOperator.EQUAL, componente),
						exactFilter("sistema", FilterOperator.EQUAL, sistema)));
	}
	
	public int conta(int id_usuario) {
		return count(exactFilter("id_usuario", FilterOperator.EQUAL, id_usuario));
	}
	
}
