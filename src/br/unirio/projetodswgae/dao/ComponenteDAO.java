package br.unirio.projetodswgae.dao;

import java.util.List;

import br.unirio.projetodswgae.model.Componente;
import br.unirio.projetodswgae.model.Sistema;
import br.unirio.projetodswgae.model.Ticket;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
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
	
	/**
	 * 
	 * Retorna o email do operador de um componente de um sistema
	 * 
	 */
	public String getComponenteEmailOperador(Ticket ticket)
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter sistema = new FilterPredicate("sistema", FilterOperator.EQUAL, ticket.getSistema());
		Filter nomeComponente = new FilterPredicate("nome", FilterOperator.EQUAL, ticket.getComponente());

		Filter sistemaEcomponente = CompositeFilterOperator.and(sistema, nomeComponente);

		Query q = new Query("Componente").setFilter(sistemaEcomponente);
		PreparedQuery pq = datastore.prepare(q);
		
		Entity result = pq.asSingleEntity();
		
		return (String) result.getProperty("emailOperadorResponsavel");
	}
	
	/**
	 * Retorna os sistemas cadastrados
	 */
	
	public List<Componente> getComponentes(int page, int page_size){
		return list(page, page_size);
	}

	/**
	 * Retorna a quantidade de sistemas cadastrados
	 */
	public int conta() {
		return count();
	}
	
}
