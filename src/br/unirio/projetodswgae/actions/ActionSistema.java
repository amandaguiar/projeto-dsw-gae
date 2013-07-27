package br.unirio.projetodswgae.actions;

import br.unirio.projetodswgae.model.Sistema;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.SuccessRedirect;
import br.unirio.simplemvc.utils.Crypto;
import br.unirio.projetodswgae.dao.DAOFactory;

public class ActionSistema extends Action{

	/**
	 * Ação para a criação de um novo sistema
	 */
	@Any("/jsp/sistema/sistemaform.jsp")
	public String novoSistema()
	{
		Sistema sistema = new Sistema();
		setAttribute("item", sistema);
		return SUCCESS;
	}	
	
	
	/**
	 * Ação de salvamento de novos componentes
	 */
	@SuccessRedirect("/login/login.do")
	@Error("/jsp/sistema/sistemaform.jsp")
	public String salvaSistema() throws ActionException
	{
		return addRedirectNotice("Sistema registrado com sucesso.");		
	}
}
