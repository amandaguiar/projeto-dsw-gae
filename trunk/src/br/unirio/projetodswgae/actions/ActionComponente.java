package br.unirio.projetodswgae.actions;

import br.unirio.projetodswgae.model.Componente;
import br.unirio.projetodswgae.model.Sistema;
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
		checkNonEmpty(componente.getNome(), "O nome do componente não pode ser vazio.");
		
		DAOFactory.getComponenteDAO().put(componente);
		return addRedirectNotice("Componente registrado com sucesso.");		
	}
}
