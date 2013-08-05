package br.unirio.projetodswgae.model;

import java.util.Date;

import br.unirio.simplemvc.gae.datastore.DataObject;

public class HistoricoStatus implements DataObject {
	
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
