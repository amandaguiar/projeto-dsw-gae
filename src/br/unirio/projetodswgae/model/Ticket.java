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
	private StatusTicket statusAtual;
	private String emailOperadorResponsavel;
	
	public Ticket() {
		this.id = -1;
		this.id_usuario = -1;
		this.titulo = "";
		this.sistema = "";
		this.componente = "";
		this.descricao = "";		
		this.statusAtual = StatusTicket.NOVO;
		this.emailOperadorResponsavel = "";
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
	public StatusTicket getStatusAtual() {
		return statusAtual;
	}
	public void setStatusAtual(StatusTicket statusAtual) {
		this.statusAtual = statusAtual;
	}
	public String getEmailOperadorResponsavel() {
		return emailOperadorResponsavel;
	}
	public void setEmailOperadorResponsavel(String emailOperadorResponsavel) {
		this.emailOperadorResponsavel = emailOperadorResponsavel;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	
}
