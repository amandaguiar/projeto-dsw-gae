package br.unirio.projetodswgae.dao;

import br.unirio.projetodswgae.model.Componente;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

public class ComponenteDAO extends AbstractDAO<Componente>{

	protected ComponenteDAO()
	{
		super("Componente");
	}
	
	@Override
	protected Componente load(Entity e)
	{
		Componente componente = new Componente();
		componente.setId((int)e.getKey().getId());
		componente.setEmailOperadorResponsavel(getStringProperty(e, "emailOperadorResponsavel", ""));
		componente.setNome(getStringProperty(e, "nome", ""));
		componente.setSistema(getStringProperty(e, "sistema", ""));
		return componente;
	}
	
	@Override
	protected void save(Componente componente, Entity e)
	{
		e.setProperty("emailOperadorResponsavel", componente.getEmailOperadorResponsavel());
		e.setProperty("nome", componente.getNome());
		e.setProperty("sistema", componente.getSistema());
	}
	
	public Iterable<Componente> getComponentesSistema(String sistema)
	{
		return list(exactFilter("sistema", FilterOperator.EQUAL, sistema), "nome", SortDirection.ASCENDING);
	}
	
	
}
