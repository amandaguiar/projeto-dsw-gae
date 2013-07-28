package br.unirio.projetodswgae.actions;

import br.unirio.projetodswgae.dao.DAOFactory;
import br.unirio.projetodswgae.model.Usuario;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.actions.results.SuccessRedirect;

public class ActionUsuario extends Action {
	
	/**
	 * A��o para preparar para a edi��o dos dados de um usuario
	 */
	@DisableUserVerification
	@Error("/login/login.do")
	@Success("/jsp/usuario/usuarioform.jsp")
	public String preparaEdicaoDadosUsuario() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();
		setAttribute("item", usuario);
		return SUCCESS;
	}
	
	/**
	 * A��o de edi��o dos dados de um usu�rio
	 */
	@SuccessRedirect("/login/login.do")
	@Error("/jsp/usuario/usuarioform.jsp")
	public String editaDadosUsuario() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();

		// Captura ou cria o usu�rio
		//Usuario usuario = (id == -1) ? new Usuario() : DAOFactory.getUsuarioDAO().get(id);

		// Disponibiliza os dados para o caso de erros
		setAttribute("item", usuario);

		// Captura os dados do formul�rio
		usuario.setNome(getParameter("nome", ""));
		usuario.setSobrenome(getParameter("sobrenome", ""));
		
		// Verifica as regras de neg�cio
		checkNonEmpty(usuario.getNome(), "O nome do usu�rio n�o pode ser vazio.");
		checkLength(usuario.getNome(), 80, "O nome do usu�rio.");

		checkNonEmpty(usuario.getSobrenome(), "O Sobrenome do usu�rio n�o pode ser vazio.");
		checkLength(usuario.getSobrenome(), 80, "O Sobrenome do usu�rio.");

		// Salva os dados do usu�rio
		DAOFactory.getUsuarioDAO().put(usuario);
		return addRedirectNotice("Dados alterados com sucesso.");
	}
}
