package br.unirio.projetodswgae.dao;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import br.unirio.projetodswgae.model.Componente;
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
	
	public List<Sistema> getSistemas(int start, int page_size){
		return list(start, page_size);
	}

	/**
	 * Retorna a quantidade de sistemas cadastrados
	 */
	public int conta() {
		return count();
	}
	
	/**
	 * Retorna os componentes desse sistema
	 */
	public List<Componente> getComponentesSistema(String nome) {
		List<Componente> componentes = DAOFactory.getComponenteDAO().getComponentes(nome);
		return componentes;
	}
	
}
