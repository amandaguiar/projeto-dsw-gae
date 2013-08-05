package br.unirio.projetodswgae.dao;

import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

import br.unirio.projetodswgae.model.TipoUsuario;
import br.unirio.projetodswgae.model.Usuario;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;

public class UsuarioDAO extends AbstractDAO<Usuario>{
	
	protected UsuarioDAO()
	{
		super("Usuario");
	}

	@Override
	protected Usuario load(Entity e)
	{
		Usuario usuario = new Usuario();
		usuario.setId((int)e.getKey().getId());
		usuario.setTipoUsuario(TipoUsuario.get(getStringProperty(e, "tipoUsuario", TipoUsuario.USUARIO_FINAL.getCodigo())));
		usuario.setNome(getStringProperty(e, "nome", ""));
		usuario.setSobrenome(getStringProperty(e, "sobrenome", ""));
		usuario.setEmail(getStringProperty(e, "email", ""));
		usuario.setSenha(getStringProperty(e, "senha", ""));
		usuario.setAtivo(getBooleanProperty(e, "ativo", false));
		usuario.setForcaResetSenha(getBooleanProperty(e, "forcaTrocaSenha", false));
		usuario.setDeveTrocarSenha(getBooleanProperty(e, "deveTrocarSenha", true));		
		return usuario;
	}
	
	@Override
	protected void save(Usuario usuario, Entity e)
	{
		e.setProperty("tipoUsuario", usuario.getTipoUsuario().getCodigo());
		e.setProperty("nome", usuario.getNome());
		e.setProperty("sobrenome", usuario.getSobrenome());
		e.setProperty("email", usuario.getEmail());
		e.setProperty("senha", usuario.getSenha());
		e.setProperty("ativo", usuario.getAtivo());
		e.setProperty("forcaTrocaSenha", usuario.getForcaResetSenha());
		e.setProperty("deveTrocarSenha", usuario.getDeveTrocarSenha());		
	}	
	
	public Usuario getUsuarioEmail(String email)
	{
		return get("email", email);
	}
	
	/**
	 * Retorna os usuarios cadastrados
	 */
	
	public List<Usuario> getUsuarios(String filtro, int start, int page_size){
		if (filtro.equals(""))
			return list(start, page_size, "nome", SortDirection.ASCENDING);
		else
			return list(start, page_size, exactFilter("nome", FilterOperator.EQUAL, filtro), "nome", SortDirection.ASCENDING);
	}

	/**
	 * Retorna a quantidade de usuarios cadastrados
	 */
	public int conta() {
		return count();
	}
	
	/**
	 * Retorna todos os administradores cadastrados
	 */
	public List<Usuario> getAdministradores() {		
		return list(exactFilter("tipoUsuario", FilterOperator.EQUAL, TipoUsuario.ADMINISTRADOR.getCodigo().toString()));
	}
	
}
