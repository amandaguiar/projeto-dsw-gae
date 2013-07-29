package br.unirio.projetodswgae.dao;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;

import br.unirio.projetodswgae.model.Sistema;
import br.unirio.projetodswgae.model.Ticket;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

public class SistemaDAO extends AbstractDAO<Sistema>  {

	protected SistemaDAO()
	{
		super("Sistema");
	}
	
	@Override
	protected Sistema load(Entity e)
	{
		Sistema sistema = new Sistema();
		sistema.setId((int)e.getKey().getId());
		sistema.setNome(getStringProperty(e, "nome", ""));
		return sistema;
	}
	
	@Override
	protected void save(Sistema sistema, Entity e)
	{
		e.setProperty("nome", sistema.getNome());
	}
	
	public Sistema getNomeSistema(String nome)
	{
		return get("nome", nome);
	}
	
	/**
	 * Retorna os sistemas cadastrados
	 */
	
	public List<Sistema> getSistemas(int page, int page_size){
		return list(page, page_size);
	}

	/**
	 * Retorna a quantidade de sistemas cadastrados
	 */
	public int conta() {
		return count();
	}
	
}
