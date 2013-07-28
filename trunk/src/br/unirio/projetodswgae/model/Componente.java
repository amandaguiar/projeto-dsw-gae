package br.unirio.projetodswgae.model;

import br.unirio.simplemvc.gae.datastore.DataObject;

public class Componente implements DataObject{

	private int id;
	private String nome;
	private String sistema;
	private String emailOperadorResponsavel;
	
	public Componente()
	{
		this.id = -1;
		this.nome = "";
		this.sistema = "";
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public String getEmailOperadorResponsavel() {
		return emailOperadorResponsavel;
	}
	public void setEmailOperadorResponsavel(String emailOperadorResponsavel) {
		this.emailOperadorResponsavel = emailOperadorResponsavel;
	}
	
}
