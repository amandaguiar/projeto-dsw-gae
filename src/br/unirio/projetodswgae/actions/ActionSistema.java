package br.unirio.projetodswgae.actions;

import br.unirio.projetodswgae.model.Sistema;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.SuccessRedirect;
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
		
		// Pega o identificador do sistema
		int id = getIntParameter("id", -1);

		// Captura ou cria o sistema
		Sistema sistema = (id == -1) ? new Sistema() : DAOFactory.getSistemaDAO().get(id);

		// Disponibiliza os dados para o caso de erros
		setAttribute("item", sistema);
		
		// Captura os dados do formulário
		sistema.setNome(getParameter("nome", ""));
				
		// Verifica as regras de negócio
		checkNonEmpty(sistema.getNome(), "O nome do sistema não pode ser vazio.");

		//TODO Verificar se já existe outro sistema com o mesmo nome 
		
		DAOFactory.getSistemaDAO().put(sistema);
		return addRedirectNotice("Sistema registrado com sucesso.");		
	}
}
