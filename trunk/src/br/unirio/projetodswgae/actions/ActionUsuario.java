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
	 * Ação para preparar para a edição dos dados de um usuario
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
	 * Ação de edição dos dados de um usuário
	 */
	@SuccessRedirect("/login/login.do")
	@Error("/jsp/usuario/usuarioform.jsp")
	public String editaDadosUsuario() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();

		// Captura ou cria o usuário
		//Usuario usuario = (id == -1) ? new Usuario() : DAOFactory.getUsuarioDAO().get(id);

		// Disponibiliza os dados para o caso de erros
		setAttribute("item", usuario);

		// Captura os dados do formulário
		usuario.setNome(getParameter("nome", ""));
		usuario.setSobrenome(getParameter("sobrenome", ""));
		
		// Verifica as regras de negócio
		checkNonEmpty(usuario.getNome(), "O nome do usuário não pode ser vazio.");
		checkLength(usuario.getNome(), 80, "O nome do usuário.");

		checkNonEmpty(usuario.getSobrenome(), "O Sobrenome do usuário não pode ser vazio.");
		checkLength(usuario.getSobrenome(), 80, "O Sobrenome do usuário.");

		// Salva os dados do usuário
		DAOFactory.getUsuarioDAO().put(usuario);
		return addRedirectNotice("Dados alterados com sucesso.");
	}
}
