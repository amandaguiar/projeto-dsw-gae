package br.unirio.projetodswgae.dao;

import java.util.Date;
import java.util.List;

import br.unirio.projetodswgae.dao.DAOFactory;
import br.unirio.projetodswgae.dao.LoginUsuario;
import br.unirio.projetodswgae.model.Usuario;
import br.unirio.simplemvc.gae.datastore.AbstractDAO;
import br.unirio.simplemvc.gae.datastore.DataObject;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

public class UsuarioLoginDAO extends AbstractDAO<LoginUsuario>
{
	protected UsuarioLoginDAO()
	{
		super("UsuarioLogin");
	}

	@Override
	protected LoginUsuario load(Entity e)
	{
		LoginUsuario login = new LoginUsuario();
		login.setId((int)e.getKey().getId());
		login.setIdUsuario(getIntProperty(e, "idUsuario", -1));
		login.setToken(getBooleanProperty(e, "sucesso", false));
		login.setTimestamp(getDateProperty(e, "timestamp"));
		return login;
	}

	@Override
	protected void save(LoginUsuario login, Entity e)
	{
		e.setProperty("idUsuario", login.getIdUsuario());
		e.setProperty("sucesso", login.getSucesso());
		e.setProperty("timestamp", login.getTimestamp());
	}

	public void registraLoginFalha(int idUsuario)
	{
		this.put(new LoginUsuario(idUsuario, false));

		List<LoginUsuario> logins = list(0, 3, exactFilter("idUsuario", FilterOperator.EQUAL, idUsuario), "timestamp", SortDirection.DESCENDING);
		
		for (LoginUsuario login : logins)
			if (login.getSucesso())
				return;

		if (logins.size() == 3)
		{
			Usuario usuario = DAOFactory.getUsuarioDAO().get(idUsuario);
			usuario.setForcaResetSenha(true);
			DAOFactory.getUsuarioDAO().put(usuario);
		}
	}

	public void registraLoginSucesso(int idUsuario)
	{
		this.put(new LoginUsuario(idUsuario, true));

		Usuario usuario = DAOFactory.getUsuarioDAO().get(idUsuario);
		usuario.setForcaResetSenha(false);
		DAOFactory.getUsuarioDAO().put(usuario);
	}

	public Date pegaDataUltimoLogin(int idUsuario)
	{
		List<LoginUsuario> logins = list(0, 1, exactFilter("idUsuario", FilterOperator.EQUAL, idUsuario), "timestamp", SortDirection.DESCENDING);
		return (logins.size() > 0) ? logins.get(0).getTimestamp() : null;
	}
	
	public Date pegaDataPenultimoLogin(int idUsuario)
	{
		List<LoginUsuario> logins = list(0, 2, exactFilter("idUsuario", FilterOperator.EQUAL, idUsuario), "timestamp", SortDirection.DESCENDING);
		return (logins.size() > 0) ? logins.get(1).getTimestamp() : null;
	}
}

class LoginUsuario implements DataObject
{
	private int id;
	private int idUsuario;
	private boolean sucesso;
	private Date timestamp;
	
	public LoginUsuario(int idUsuario, boolean sucesso)
	{
		this.id = -1;
		this.idUsuario = idUsuario;
		this.sucesso = sucesso;
		this.timestamp = new Date();
	}

	public LoginUsuario()
	{
		this(-1, false);
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

	public boolean getSucesso()
	{
		return sucesso;
	}

	public void setToken(boolean flag)
	{
		this.sucesso = flag;
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