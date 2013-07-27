package br.unirio.projetodswgae.actions;

import br.unirio.projetodswgae.model.Componente;
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
		return addRedirectNotice("Componente registrado com sucesso.");		
	}
}
