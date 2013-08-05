package br.unirio.projetodswgae.dao;

import java.util.List;

import br.unirio.projetodswgae.model.HistoricoStatus;
import br.unirio.projetodswgae.model.StatusTicket;
import br.unirio.projetodswgae.model.Ticket;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class HistoricoStatusDAO extends AbstractDAO<HistoricoStatus>{

	protected HistoricoStatusDAO() {
		super("HistoricoStatus");
	}

	@Override
	protected HistoricoStatus load(Entity e) {
		HistoricoStatus histStatus = new HistoricoStatus();
		histStatus.setId((int)e.getKey().getId());
		histStatus.setIdTicket(getIntProperty(e, "idTicket", -1));		
		histStatus.setStatus(StatusTicket.get(getStringProperty(e, "status", null)));
		histStatus.setComentario(getStringProperty(e, "comentario", ""));
		histStatus.setDataRegistro(getDateProperty(e, "dataRegistro"));
		return histStatus;
	}

	@Override
	protected void save(HistoricoStatus t, Entity e) {
		e.setProperty("idTicket", t.getIdTicket());
		e.setProperty("status", t.getStatus().getCodigo());
		e.setProperty("comentario", t.getComentario());
		e.setProperty("dataRegistro", t.getDataRegistro());
	}

	public void registraStatus(int idTicket, StatusTicket status, String comentario) {
		this.put(new HistoricoStatus(-1, idTicket, status, comentario));
	}
	
	/**
	 * Retorna uma lista de comentarios de um ticket
	 * @param ticket
	 * @param start
	 * @param page_size
	 * @return
	 */
	public List<HistoricoStatus> getComentariosTicket(Ticket ticket, int start, int page_size){
		return list(start, page_size, and(exactFilter("idTicket", FilterOperator.EQUAL, ticket.getId()),
										  exactFilter("comentario", FilterOperator.NOT_EQUAL, "") ) );
	}
	
	public int conta(int idTicket) {
		return count(and(exactFilter("idTicket", FilterOperator.EQUAL, idTicket),
				         exactFilter("comentario", FilterOperator.NOT_EQUAL, "") ) );
	}
	
}	