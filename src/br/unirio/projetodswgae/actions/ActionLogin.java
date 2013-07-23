package br.unirio.projetodswgae.actions;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.unirio.projetodswgae.dao.DAOFactory;
import br.unirio.projetodswgae.model.Usuario;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.results.AnyRedirect;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.utils.Crypto;

public class ActionLogin extends Action {

	/**
	 * Per�odo de validade do token de troca de senha
	 *//*
	private static final int VALIDATE_TOKEN_SENHA = 72;
	
	*//**
	 * A��o de login
	 *//*
	@DisableUserVerification
	@Error("/jsp/homepage.jsp")
	@Success("/login/homepage.do")
	public String login() throws ActionException
	{
		if (testLogged() != null)
			return SUCCESS;

		String email = getParameter("email");
		String password = getParameter("pwd");

		if (email == null || password == null)
			return ERROR;

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);

		if (usuario == null)
			return addError("Usu�rio e senha incorretos.");

		if (!usuario.isActive())
			return addError("O usu�rio est� desativado no sistema.");

		if (usuario.getForcaResetSenha())
			return addError("O sistema indica que voc� precisa fazer um reset de senha. Isto pode ter ocorrido porque voc� pediu o reset e ainda n�o recebemos a confirma��o ou porque houve tr�s tentativas mal-sucedidas de acesso � sua conta. Se necess�rio, clique na op��o \"Esqueceu sua senha?\" para enviarmos novamente o e-mail de reset de senha.");

		String hashSenha = Configuracao.getAmbienteHomologacao() ? password : Crypto.hash(password);

		if (usuario.getSenhaCodificada().compareTo(hashSenha) != 0)
		{
			DAOFactory.getUsuarioLoginDAO().registraLoginFalha(usuario.getId());
			return addError("Usu�rio e senha incorretos.");
		}

		Date dataUltimoLogin = DAOFactory.getUsuarioLoginDAO().pegaDataUltimoLogin(usuario.getId());

		if (dataUltimoLogin != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			addNotice("Seu �ltimo login no sistema foi realizado em " + sdf.format(dataUltimoLogin) + " hr.");
		}

		DAOFactory.getUsuarioLoginDAO().registraLoginSucesso(usuario.getId());
		setCurrentUser(usuario);
		return SUCCESS;
	}*/
	
	/**
	 * A��o de logout
	 */
	@DisableUserVerification
	@AnyRedirect("/jsp/homepage.jsp")
	public String teste12343()
	{
		//invalidateCurrentUser();
		return SUCCESS;
	}
}
