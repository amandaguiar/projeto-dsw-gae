package br.unirio.projetodswgae.model;

import br.unirio.simplemvc.gae.datastore.DataObject;

public class Sistema implements DataObject{

	private int id;
	private String nome;
	
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
	
	
	
}
