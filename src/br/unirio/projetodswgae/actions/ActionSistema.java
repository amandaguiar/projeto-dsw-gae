package br.unirio.projetodswgae.actions;

import java.util.List;

import br.unirio.projetodswgae.model.Sistema;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.ErrorRedirect;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.actions.results.SuccessRedirect;
import br.unirio.projetodswgae.dao.DAOFactory;

public class ActionSistema extends Action{

	public static final int PAGE_SIZE = 3;
	
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
	@SuccessRedirect("/sistema/listaSistemas.do")
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

		//Verifica se já existe outro sistema com o mesmo nome 
		Sistema sistema2 = DAOFactory.getSistemaDAO().getNomeSistema(sistema.getNome());
		check(sistema2 == null || sistema2.getId() == sistema.getId(), "Já existe um sistema com esse nome.");
		
		DAOFactory.getSistemaDAO().put(sistema);
		return addRedirectNotice("Sistema registrado com sucesso.");		
	}
	
	/**
	 * Ação para listar sistemas cadastrados
	 */
	@Success("/jsp/sistema/listasistema.jsp")
	@Error("/login/login.do")
	public String listaSistemas() throws ActionException{		
		
		int page = getIntParameter("page", 0);

		List<Sistema> sistema = DAOFactory.getSistemaDAO().getSistemas(page, PAGE_SIZE);
		int count = DAOFactory.getSistemaDAO().conta();
		
		boolean hasNext = (count > (page+1) * PAGE_SIZE);
		boolean hasPrior = (page > 0);
		
		setAttribute("item", sistema);
		setAttribute("page", page);
		setAttribute("hasNextPage", hasNext);
		setAttribute("hasPriorPage", hasPrior);
		return SUCCESS;
	}
	
	@ErrorRedirect("/sistema/listaSistemas.do")
	@Success("/jsp/sistema/sistemaform.jsp")
	public String editaSistema() throws ActionException
	{
		int id = getIntParameter("id", -1);
		Sistema sistema = DAOFactory.getSistemaDAO().get(id);
		check(sistema != null, "O sistema não existe.");		
		
		setAttribute("item", sistema);
		return SUCCESS;
	}
	
}
