package br.unirio.projetodswgae.actions;

import br.unirio.projetodswgae.model.Componente;
import br.unirio.projetodswgae.model.TipoUsuario;
import br.unirio.projetodswgae.model.Usuario;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.SuccessRedirect;
import br.unirio.projetodswgae.dao.DAOFactory;

public class ActionComponente extends Action{
	
	/**
	 * Ação para a criação de um novo componente
	 */
	@Any("/jsp/componente/componenteform.jsp")
	public String novoComponente()
	{
		Componente componente = new Componente();
		setAttribute("item", componente);
		return SUCCESS;
	}	

	
	/**
	 * Ação de salvamento de novos componentes
	 */
	@SuccessRedirect("/login/login.do")
	@Error("/jsp/componente/componenteform.jsp")
	public String salvaComponente() throws ActionException
	{
		
		// Pega o identificador do componente
		int id = getIntParameter("id", -1);

		// Captura ou cria o componente
		Componente componente = (id == -1) ? new Componente() : DAOFactory.getComponenteDAO().get(id);

		// Disponibiliza os dados para o caso de erros
		setAttribute("item", componente);
		
		// Captura os dados do formulário
		componente.setNome(getParameter("nome", ""));
		componente.setSistema(getParameter("sistema", ""));
		componente.setEmailOperadorResponsavel(getParameter("email", ""));
				
		// Verifica as regras de negócio
		checkNonEmpty(componente.getSistema(), "O sistema do componente não pode ser vazio.");
		
		checkNonEmpty(componente.getNome(), "O nome do componente não pode ser vazio.");
		
		boolean componenteExistente = false;
		Iterable<Componente> componentes = DAOFactory.getComponenteDAO().getComponentesSistema(componente.getSistema());
		for (Componente comp : componentes){
			if(comp.getNome().equalsIgnoreCase(componente.getNome())){
				componenteExistente = true;
				break;
			}
		}
		check(!componenteExistente, "O sistema escolhido possui um componente com este nome.");
		
		
		
		checkNonEmpty(componente.getEmailOperadorResponsavel(), "O email do operador não pode ser vazio.");
		checkEmail(componente.getEmailOperadorResponsavel(), "O e-mail do operador não está seguindo um formato válido.");
		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(componente.getEmailOperadorResponsavel());
		check(usuario != null, "E-mail do usuário não encontrado");
		check(usuario.getTipoUsuario() == TipoUsuario.OPERADOR, "O usuario não é operador");
		
		DAOFactory.getComponenteDAO().put(componente);
		return addRedirectNotice("Componente registrado com sucesso.");
	}
}
