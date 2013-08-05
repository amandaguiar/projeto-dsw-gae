package br.unirio.projetodswgae.dao;

import java.util.List;

import br.unirio.projetodswgae.model.Sistema;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

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
	
	public List<Sistema> getSistemas(String filtro, int start, int page_size){
		if (filtro.equals(""))
			return list(start, page_size, "nome", SortDirection.ASCENDING);
		else
			return list(start, page_size, exactFilter("nome", FilterOperator.EQUAL, filtro));
	}

	/**
	 * Retorna a quantidade de sistemas cadastrados
	 */
	public int conta() {
		return count();
	}
	
}
