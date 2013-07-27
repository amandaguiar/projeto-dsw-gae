package br.unirio.projetodswgae.model;

import br.unirio.simplemvc.gae.datastore.DataObject;

public class Ticket implements DataObject{

	private int id;
	private String identificador;
	private String titulo;
	private Sistema sistema;
	private Componente componente;
	private String descricao;
	private StatusTicket status;
	private Usuario operadorResponsavel;
	
	
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
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	public Componente getComponente() {
		return componente;
	}
	public void setComponente(Componente componente) {
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
	public Usuario getOperadorResponsavel() {
		return operadorResponsavel;
	}
	public void setOperadorResponsavel(Usuario operadorResponsavel) {
		this.operadorResponsavel = operadorResponsavel;
	}
	
	
	
}
