package br.unirio.projetodswgae.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import br.unirio.projetodswgae.services.GerenciadorEmail;
import br.unirio.projetodswgae.config.Configuracao;
import br.unirio.projetodswgae.dao.DAOFactory;
import br.unirio.projetodswgae.model.Usuario;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.AnyRedirect;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.actions.results.SuccessRedirect;
import br.unirio.simplemvc.utils.Crypto;

/**
 * Classe com ações de login e tratamento de usuários
 */
public class ActionLogin extends Action {

	/**
	 * Período de validade do token de troca de senha
	 */
	private static final int VALIDATE_TOKEN_SENHA = 72;
	
	/**
	 * Ação de login
	 */
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
			return addError("Usuário e senha incorretos.");

		if (!usuario.isActive())
			return addError("O usuário está desativado no sistema.");

		if (usuario.getForcaResetSenha())
			return addError("O sistema indica que você precisa fazer um reset de senha. Isto pode ter ocorrido porque você pediu o reset e ainda não recebemos a confirmação ou porque houve três tentativas mal-sucedidas de acesso à sua conta. Se necessário, clique na opção \"Esqueceu sua senha?\" para enviarmos novamente o e-mail de reset de senha.");

		String hashSenha = Crypto.hash(password);

		if (usuario.getSenha().compareTo(hashSenha) != 0)
		{
			DAOFactory.getUsuarioLoginDAO().registraLoginFalha(usuario.getId());
			return addError("Usuário e senha incorretos.");
		}

		Date dataUltimoLogin = DAOFactory.getUsuarioLoginDAO().pegaDataUltimoLogin(usuario.getId());

		if (dataUltimoLogin != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			addNotice("Seu último login no sistema foi realizado em " + sdf.format(dataUltimoLogin) + " hr.");
		}

		DAOFactory.getUsuarioLoginDAO().registraLoginSucesso(usuario.getId());
		setCurrentUser(usuario);
		return SUCCESS;
	}
	
	/**
	 * Ação de ida para a homepage e login
	 */
	@Error("/login/login.do")
	@Success("/jsp/homepage.jsp")
	public String homepage() throws ActionException
	{
		checkLogged();
		return SUCCESS;
	}

	/**
	 * Ação de logout
	 */
	@DisableUserVerification
	@AnyRedirect("/login/homepage.do")
	public String logout()
	{
		invalidateCurrentUser();
		return SUCCESS;
	}

	/**
	 * Ação para preparar para a troca de senha
	 */
	@DisableUserVerification
	@Error("/login/login.do")
	@Success("/jsp/login/trocaSenha.jsp")
	public String preparaTrocaSenha() throws ActionException
	{
		checkLogged();
		return SUCCESS;
	}

	/**
	 * Ação de troca de senha
	 */
	@DisableUserVerification
	@SuccessRedirect("/login/homepage.do")
	@Error("/jsp/login/trocaSenha.jsp")
	public String trocaSenha() throws ActionException
	{
		Usuario usuario = (Usuario) checkLogged();

		String senhaAtual = getParameter("oldpassword");
		check(senhaAtual != null, "Entre com sua senha antiga.");

		String hashSenha = Crypto.hash(senhaAtual);
		check(usuario.getSenha().compareTo(hashSenha) == 0, "A senha antiga está incorreta.");

		String novaSenha = getParameter("newpassword");
		check(novaSenha != null, "Entre com sua nova senha.");
		check(senhaAceitavel(novaSenha), "A nova senha deve conter pelo menos 8 caracteres. Entre eles, uma letra e um número.");

		String novaSenha2 = getParameter("newpassword2");
		check(novaSenha2 != null, "Repita sua nova senha.");

		check(novaSenha.length() != 0, "A nova senha não pode ser em branco.");
		check(novaSenha.compareTo(novaSenha2) == 0, "Sua senha nova e a repetição estão diferentes.");

		String hashNovaSenha = Crypto.hash(novaSenha);
		usuario.setSenha(hashNovaSenha);
		usuario.setDeveTrocarSenha(false);
		usuario.setForcaResetSenha(false);
		
		DAOFactory.getUsuarioDAO().put(usuario);
		return addRedirectNotice("Senha alterada com sucesso.");
	}

	/**
	 * Verifica se uma senha é aceitável, checando se ela tem pelo menos 8
	 * caracteres. Entre eles uma letra e um número.
	 */
	private boolean senhaAceitavel(String senha)
	{
		return (senha.length() >= 8) && senha.matches(".*[a-zA-Z].*") && senha.matches(".*[0-9].*");
	}

	/**
	 * Ação de envio de email para reset de senha
	 */
	@DisableUserVerification
	@SuccessRedirect("/login/homepage.do")
	@Error("/jsp/login/esqueceuSenha.jsp")
	public String enviaSenha() throws ActionException
	{
		String email = getParameter("email");
		checkNonEmpty(email, "Entre com seu e-mail.");
		setAttribute("email", email);

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
		check(usuario != null, "Usuario não reconhecido.");

		String token = geraTokenTrocaSenha();
		DAOFactory.getUsuarioTokenSenhaDAO().armazenaTokenTrocaSenha(usuario.getId(), token);

		String url = Configuracao.getHostname() + "/login/preparaResetSenha.do?token=" + token + "&email=" + usuario.getEmail();
		String corpo = "<p>Você está recebendo este e-mail porque pediu a reinicializa\u00E7\u00E3o da senha de acesso ao ";
		corpo += "Projeto DSW. Clique <a href='" + url + "'>aqui</a> para acessar a página de troca de senha.</p>";
		boolean envioOK = GerenciadorEmail.getInstance().envia(usuario.getNome(), usuario.getEmail(), "Reinicializacao de senha de acesso ao Projeto DSW", corpo);
		check(envioOK, "Ocorreu um erro ao enviar um e-mail com sua senha.");

		return addRedirectNotice("Senha enviada com sucesso.");
	}

	/**
	 * Gera um token para troca de senha
	 */
	private String geraTokenTrocaSenha()
	{
		StringBuilder sb = new StringBuilder();
		Random r = Crypto.createSecureRandom();

		for (int i = 0; i < 256; i++)
		{
			char c = (char) ('A' + r.nextInt(26));
			sb.append(c);
		}

		return sb.toString();
	}

	/**
	 * Prepara o formulário de reset de senha
	 */
	@DisableUserVerification
	@Success("/jsp/login/resetSenha.jsp")
	@Error("/jsp/login/esqueceuSenha.jsp")
	public String preparaResetSenha() throws ActionException
	{
		String token = getParameter("token", "");
		checkNonEmpty(token, "O token de reset de senha não foi encontrado.");

		String email = getParameter("email", "");
		checkNonEmpty(email, "O e-mail do usuário que requisitou o reset de senha não foi encontrado.");

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
		check(usuario != null, "O usuário que requisitou o reset de senha não está registrado no sistema.");

		boolean valido = DAOFactory.getUsuarioTokenSenhaDAO().verificaTokenTrocaSenha(usuario.getId(), token, VALIDATE_TOKEN_SENHA);
		check(valido, "O token de troca de senha não é válido.");

		setAttribute("token", token);
		setAttribute("email", email);
		return SUCCESS;
	}

	/**
	 * Executa uma troca de senha baseada em reinicialização
	 */
	@DisableUserVerification
	@SuccessRedirect("/login/homepage.do")
	@Error("/jsp/login/resetSenha.jsp")
	public String executaResetSenha() throws ActionException
	{
		String token = getParameter("token", "");
		checkNonEmpty(token, "O token de reset de senha não foi encontrado.");

		String email = getParameter("email", "");
		checkNonEmpty(email, "O e-mail do usuário que requisitou o reset de senha não foi encontrado.");

		setAttribute("token", token);
		setAttribute("email", email);

		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(email);
		check(usuario != null, "O usuário que requisitou o reset de senha não está registrado no sistema.");

		boolean valido = DAOFactory.getUsuarioTokenSenhaDAO().verificaTokenTrocaSenha(usuario.getId(), token, VALIDATE_TOKEN_SENHA);
		check(valido, "O token de troca de senha não é válido");

		String novaSenha = getParameter("newpassword");
		check(novaSenha != null, "Entre com sua nova senha.");
		check(senhaAceitavel(novaSenha), "A nova senha deve conter pelo menos 8 caracteres. Entre eles, uma letra e um número.");

		String novaSenha2 = getParameter("newpassword2");
		check(novaSenha2 != null, "Repita sua nova senha.");
		check(novaSenha.compareTo(novaSenha2) == 0, "Sua senha nova e a repetição estão diferentes.");

		usuario.setSenha(Crypto.hash(novaSenha));
		usuario.setDeveTrocarSenha(false);
		usuario.setForcaResetSenha(false);
		
		DAOFactory.getUsuarioDAO().put(usuario);
		return addRedirectNotice("Senha alterada com sucesso.");
	}
	
	/**
	 * Ação para a criação de um novo usuário
	 */
	@DisableUserVerification
	@Any("/jsp/login/novo.jsp")
	public String novo()
	{
		Usuario usuario = new Usuario();
		setAttribute("item", usuario);
		return SUCCESS;
	}


	/**
	 * Ação de salvamento dos dados de um usuário
	 */
	@DisableUserVerification
	@SuccessRedirect("/login/login.do")
	@Error("/jsp/login/novo.jsp")
	public String salva() throws ActionException
	{
		// Pega o identificador do usuário
		int id = getIntParameter("id", -1);

		// Captura ou cria o usuário
		Usuario usuario = (id == -1) ? new Usuario() : DAOFactory.getUsuarioDAO().get(id);

		// Disponibiliza os dados para o caso de erros
		setAttribute("item", usuario);

		// Captura os dados do formulário
		usuario.setNome(getParameter("nome", ""));
		usuario.setSobrenome(getParameter("sobrenome", ""));
		usuario.setEmail(getParameter("email", ""));

		String senha = getParameter("senha", "");
		
		if (usuario.getId() <= 0 && !senha.isEmpty())
		{
			senha = getParameter("senha", "");
			usuario.setSenha(Crypto.hash(senha));
			
		}
		
		// Verifica as regras de negócio
		checkNonEmpty(usuario.getNome(), "O nome do usuário não pode ser vazio.");
		checkLength(usuario.getNome(), 80, "O nome do usuário.");

		checkNonEmpty(usuario.getSobrenome(), "O Sobrenome do usuário não pode ser vazio.");
		checkLength(usuario.getSobrenome(), 80, "O Sobrenome do usuário.");
		
		checkNonEmpty(usuario.getEmail(), "O e-mail do usuário não pode ser vazio.");
		checkLength(usuario.getEmail(), 80, "O e-mail do usuário.");
		checkEmail(usuario.getEmail(), "O e-mail do usuário não está seguindo um formato válido.");

		Usuario usuario2 = DAOFactory.getUsuarioDAO().getUsuarioEmail(usuario.getEmail());
		check(usuario2 == null || usuario2.getId() == usuario.getId(), "Já existe um usuário com esse e-mail.");

		checkNonEmpty(usuario.getSenha(), "A senha do usuário não pode ser vazia.");
		String confirmacao_senha = getParameter("confirmacao_senha", "");
		check(senha.equals(confirmacao_senha), "As senhas digitadas não são iguais.");

		// Salva os dados do usuário
		DAOFactory.getUsuarioDAO().put(usuario);
		return addRedirectNotice("Usuario cadastrado com sucesso.");
	}
}
