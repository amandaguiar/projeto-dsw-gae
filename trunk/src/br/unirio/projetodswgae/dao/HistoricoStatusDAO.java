package br.unirio.projetodswgae.dao;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;

import br.unirio.projetodswgae.model.StatusTicket;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;
import br.unirio.simplemvc.gae.datastore.DataObject;

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
	
	
}

class HistoricoStatus implements DataObject {
	
	private int id;
	private int idTicket;
	private Date dataRegistro;
	private StatusTicket status;
	private String comentario;
	
	public HistoricoStatus(int id, int idTicket, StatusTicket status, String comentario) {
		this.id = -1;
		this.idTicket = idTicket;
		this.dataRegistro = new Date();
		this.status = status;
		this.comentario = comentario;
	}
	
	public HistoricoStatus() {
		this(-1, -1, StatusTicket.NOVO, "");
	}
	
	@Override
	public int getId() {		
		return this.id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}

	public int getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(int idTicket) {
		this.idTicket = idTicket;
	}

	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public StatusTicket getStatus() {
		return status;
	}

	public void setStatus(StatusTicket status) {
		this.status = status;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}	