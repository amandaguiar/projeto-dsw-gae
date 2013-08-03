package br.unirio.projetodswgae.actions;

import java.util.List;

import br.unirio.projetodswgae.config.Configuracao;
import br.unirio.projetodswgae.dao.DAOFactory;
import br.unirio.projetodswgae.model.TipoUsuario;
import br.unirio.projetodswgae.model.Usuario;
import br.unirio.projetodswgae.services.GerenciadorEmail;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.actions.results.SuccessRedirect;

public class ActionUsuario extends Action {
	
	public static final int PAGE_SIZE = 25;
	
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
	
	/**
	 * Ação para listar usuários 
	 */

	//TODO verificar se é administrador
	@Success("/jsp/usuario/listausuario.jsp")
	@Error("/login/login.do")
	public String listaUsuarios() throws ActionException{		
		
		checkUserLevel(TipoUsuario.ADMINISTRADOR.getCodigo().toString());
		
		int page = getIntParameter("page", 0);
		int start = (PAGE_SIZE * page);
		
		List<Usuario> usuarios = DAOFactory.getUsuarioDAO().getUsuarios(start, PAGE_SIZE);
		int count = DAOFactory.getUsuarioDAO().conta();
		
		boolean hasNext = (count > (page+1) * PAGE_SIZE);
		boolean hasPrior = (page > 0);
		
		setAttribute("item", usuarios);
		setAttribute("page", page);
		setAttribute("hasNextPage", hasNext);
		setAttribute("hasPriorPage", hasPrior);
		setAttribute("noPriorPage", !hasPrior);
		setAttribute("noNextPage", !hasNext);
		
		return SUCCESS;
	}
	
	@SuccessRedirect("/usuario/preparaEdicaoDadosUsuario.do")
	@Error("/jsp/usuario/usuarioform.jsp")
	public String solicitaSerOperador() throws ActionException {
		
		Usuario usuario = (Usuario) checkLogged();
		
		String url = Configuracao.getHostname() + "/usuario/listaUsuarios.do"; 
		String corpo = "<p>Você está recebendo este e-mail porque o usuário de e-mail " + usuario.getEmail() + " solicitou sua promoção a operador no sistema ";		
		corpo += "Projeto DSW. Clique <a href='" + url + "'>aqui</a> para acessar a página de usuários.</p>";
		
		boolean envioOK;
		Iterable<Usuario> usuarios = DAOFactory.getUsuarioDAO().getAdministradores();
		for (Usuario usu : usuarios) {
			envioOK = GerenciadorEmail.getInstance().envia(usu.getNome(), usu.getEmail(), "Solicitacao de promocao a operador", corpo);
			check(envioOK, "Ocorreu um erro ao enviar sua solicitação");
		}
		
		return addRedirectNotice("E-mail enviado com sucesso.");
	}
}
