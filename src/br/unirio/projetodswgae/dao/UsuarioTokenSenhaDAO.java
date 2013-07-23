package br.unirio.projetodswgae.dao;

import java.util.Date;
import java.util.List;

import br.unirio.projetodswgae.dao.TokenSenhaUsuario;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;
import br.unirio.simplemvc.gae.datastore.DataObject;
import br.unirio.simplemvc.utils.DateUtils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class UsuarioTokenSenhaDAO extends AbstractDAO<TokenSenhaUsuario>
{
	protected UsuarioTokenSenhaDAO()
	{
		super("UsuarioTokenSenha");
	}

	@Override
	protected TokenSenhaUsuario load(Entity e)
	{
		TokenSenhaUsuario token = new TokenSenhaUsuario();
		token.setId((int)e.getKey().getId());
		token.setIdUsuario(getIntProperty(e, "idUsuario", -1));
		token.setToken(getStringProperty(e, "token", ""));
		token.setTimestamp(getDateProperty(e, "timestamp"));
		return token;
	}

	@Override
	protected void save(TokenSenhaUsuario token, Entity e)
	{
		e.setProperty("idUsuario", token.getIdUsuario());
		e.setProperty("token", token.getToken());
		e.setProperty("timestamp", token.getTimestamp());
	}

	public void armazenaTokenTrocaSenha(int idUsuario, String token)
	{
		TokenSenhaUsuario tokenSenha = new TokenSenhaUsuario();
		tokenSenha.setIdUsuario(idUsuario);
		tokenSenha.setToken(token);
		this.put(tokenSenha);
	}

	public boolean verificaTokenTrocaSenha(int idUsuario, String token, int numeroHoras)
	{
		Filter filter1 = exactFilter("idUsuario", FilterOperator.EQUAL, idUsuario);
		Filter filter2 = exactFilter("token", FilterOperator.EQUAL, token);
		List<TokenSenhaUsuario> tokensSenha = this.list(and(filter1, filter2));
		
		if (tokensSenha.size() > 0)
		{
			TokenSenhaUsuario tokenSenha = tokensSenha.get(0);
			long seconds = (DateUtils.now().getTime() - tokenSenha.getTimestamp().getTime()) / 1000;
			double hours = seconds / 3600.0;
			return hours <= numeroHoras;
		}
		return false;
	}
}

class TokenSenhaUsuario implements DataObject
{
	private int id;
	private int idUsuario;
	private String token;
	private Date timestamp;
	
	public TokenSenhaUsuario()
	{
		this.id = -1;
		this.idUsuario = -1;
		this.token = "";
		this.timestamp = new Date();
	}

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public void setId(int id)
	{
		this.id = id;
	}

	public int getIdUsuario()
	{
		return idUsuario;
	}

	public void setIdUsuario(int id)
	{
		this.idUsuario = id;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public Date getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(Date timestamp)
	{
		this.timestamp = timestamp;
	}
}
