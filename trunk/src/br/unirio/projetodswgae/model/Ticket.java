package br.unirio.projetodswgae.model;

import br.unirio.simplemvc.gae.datastore.DataObject;

public class Ticket implements DataObject{

	private int id;
	private int id_usuario;
	private String identificador;
	private String titulo;
	private String sistema;
	private String componente;
	private String descricao;
	private StatusTicket status;
	private String operadorResponsavel;
	
	public Ticket() {
		this.id = -1;
		this.id_usuario = -1;
		this.titulo = "";
		this.sistema = "";
		this.componente = "";
		this.descricao = "";
		this.status = StatusTicket.NOVO;
		this.operadorResponsavel = "";
	}
	
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id = id;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public String getComponente() {
		return componente;
	}
	public void setComponente(String componente) {
		this.componente = componente;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public StatusTicket getStatus() {
		return status;
	}
	public void setStatus(StatusTicket status) {
		this.status = status;
	}
	public String getOperadorResponsavel() {
		return operadorResponsavel;
	}
	public void setOperadorResponsavel(String operadorResponsavel) {
		this.operadorResponsavel = operadorResponsavel;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	
}
