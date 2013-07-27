package br.unirio.projetodswgae.dao;

import br.unirio.projetodswgae.dao.UsuarioDAO;
import br.unirio.projetodswgae.dao.UsuarioLoginDAO;
import br.unirio.projetodswgae.dao.UsuarioTokenSenhaDAO;

public class DAOFactory {
	private static UsuarioDAO usuarioDAO;
	private static UsuarioLoginDAO loginUsuarioDAO;
	private static UsuarioTokenSenhaDAO tokenSenhaUsuarioDAO;
	private static TicketDAO ticketDAO;

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
}
