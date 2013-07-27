package br.unirio.projetodswgae.dao;

import com.google.appengine.api.datastore.Entity;

import br.unirio.projetodswgae.model.Sistema;

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
		return sistema;
	}
	
	@Override
	protected void save(Sistema sistema, Entity e)
	{
		
	}
	
}