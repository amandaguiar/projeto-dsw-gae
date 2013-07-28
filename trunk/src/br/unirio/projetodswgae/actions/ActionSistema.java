package br.unirio.projetodswgae.actions;

import br.unirio.projetodswgae.model.Sistema;
import br.unirio.projetodswgae.model.Ticket;
import br.unirio.projetodswgae.model.Usuario;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.SuccessRedirect;
import br.unirio.projetodswgae.dao.DAOFactory;

public class ActionSistema extends Action{

	/**
	 * A��o para a cria��o de um novo sistema
	 */
	@Any("/jsp/sistema/sistemaform.jsp")
	public String novoSistema()
	{
		Sistema sistema = new Sistema();
		setAttribute("item", sistema);
		return SUCCESS;
	}	
	
	
	/**
	 * A��o de salvamento de novos componentes
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
		
		// Captura os dados do formul�rio
		sistema.setNome(getParameter("nome", ""));
				
		// Verifica as regras de neg�cio
		checkNonEmpty(sistema.getNome(), "O nome do sistema n�o pode ser vazio.");

		//Verifica se j� existe outro sistema com o mesmo nome 
		Sistema sistema2 = DAOFactory.getSistemaDAO().getNomeSistema(sistema.getNome());
		check(sistema2 == null || sistema2.getId() == sistema.getId(), "J� existe um sistema com esse nome.");
		
		DAOFactory.getSistemaDAO().put(sistema);
		return addRedirectNotice("Sistema registrado com sucesso.");		
	}
}
