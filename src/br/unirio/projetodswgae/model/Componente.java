package br.unirio.projetodswgae.model;

import br.unirio.simplemvc.gae.datastore.DataObject;

public class Componente implements DataObject{

	private int id;
	private String nome;
	private Sistema sistema;
	private Usuario operadorResponsavel;
	
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
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	public Usuario getOperadorResponsavel() {
		return operadorResponsavel;
	}
	public void setOperadorResponsavel(Usuario operadorResponsavel) {
		this.operadorResponsavel = operadorResponsavel;
	}
	
}
