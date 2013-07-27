package br.unirio.projetodswgae.dao;

import br.unirio.projetodswgae.model.Componente;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

import com.google.appengine.api.datastore.Entity;

public class ComponenteDAO extends AbstractDAO<Componente>{

	protected ComponenteDAO()
	{
		super("Componente");
	}
	
	@Override
	protected Componente load(Entity e)
	{
		Componente componente = new Componente();
		return componente;
	}
	
	@Override
	protected void save(Componente componente, Entity e)
	{
		
	}
	
}
