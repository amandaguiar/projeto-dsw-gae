package br.unirio.projetodswgae.dao;

import br.unirio.projetodswgae.dao.UsuarioDAO;
import br.unirio.projetodswgae.dao.UsuarioLoginDAO;
import br.unirio.projetodswgae.dao.UsuarioTokenSenhaDAO;

public class DAOFactory {
	private static UsuarioDAO usuarioDAO;
	private static UsuarioLoginDAO loginUsuarioDAO;
	private static UsuarioTokenSenhaDAO tokenSenhaUsuarioDAO;
	private static TicketDAO ticketDAO;
	private static SistemaDAO sistemaDAO;
	private static ComponenteDAO componenteDAO;
	
	public static UsuarioDAO getUsuarioDAO()
	{
		if (usuarioDAO == null)
			usuarioDAO = new UsuarioDAO();
		
		return usuarioDAO;
	}
	
	public static UsuarioLoginDAO getUsuarioLoginDAO()
	{
		if (loginUsuarioDAO == null)
			loginUsuarioDAO = new UsuarioLoginDAO();
		
		return loginUsuarioDAO;
	}
	
	public static UsuarioTokenSenhaDAO getUsuarioTokenSenhaDAO()
	{
		if (tokenSenhaUsuarioDAO == null)
			tokenSenhaUsuarioDAO = new UsuarioTokenSenhaDAO();
		
		return tokenSenhaUsuarioDAO;
	}
	
	public static TicketDAO getTicketDAO()
	{
		if (ticketDAO == null)
			ticketDAO = new TicketDAO();
		
		return ticketDAO;
	}
	
	public static SistemaDAO getSistemaDAO()
	{
		if (sistemaDAO == null)
			sistemaDAO = new SistemaDAO();
		
		return sistemaDAO;
	}

	public static ComponenteDAO getComponenteDAO() {
		if (componenteDAO == null)
			componenteDAO = new ComponenteDAO();
		
		return componenteDAO;
	}
}
