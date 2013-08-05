package br.unirio.projetodswgae.actions;

import java.util.List;

import br.unirio.projetodswgae.dao.DAOFactory;
import br.unirio.projetodswgae.model.Componente;
import br.unirio.projetodswgae.model.TipoUsuario;
import br.unirio.projetodswgae.model.Usuario;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.results.Any;
import br.unirio.simplemvc.actions.results.Error;
import br.unirio.simplemvc.actions.results.ErrorRedirect;
import br.unirio.simplemvc.actions.results.Success;
import br.unirio.simplemvc.actions.results.SuccessRedirect;

public class ActionComponente extends Action{
	
	public static final int PAGE_SIZE = 25;
	
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
	 * Ação de salvar componentes
	 */
	@SuccessRedirect("/componente/listaComponentes.do")
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
		
		if (id == -1)
			check(!componenteExistente, "O sistema escolhido possui um componente com este nome.");	
		
		checkNonEmpty(componente.getEmailOperadorResponsavel(), "O email do operador não pode ser vazio.");
		checkEmail(componente.getEmailOperadorResponsavel(), "O e-mail do operador não está seguindo um formato válido.");
		
		Usuario usuario = DAOFactory.getUsuarioDAO().getUsuarioEmail(componente.getEmailOperadorResponsavel());
		check(usuario != null, "E-mail do usuário não encontrado");
		check(usuario.getTipoUsuario() == TipoUsuario.OPERADOR, "Este e-mail não é de um usuário operador.");
		
		DAOFactory.getComponenteDAO().put(componente);
		return addRedirectNotice("Componente registrado com sucesso.");
	}
	
	/**
	 * Ação para listar componentes 
	 */
	@Success("/jsp/componente/listacomponente.jsp")
	@Error("/login/login.do")
	public String listaComponentes() throws ActionException{		
		
		int page = getIntParameter("page", 0);
		String sistema = getParameter("sistema", "");		
		//String filtro = getAttribute("filtro") != null ? getAttribute("filtro").toString() : "";
		
		int start = (PAGE_SIZE * page);
		
		List<Componente> componente = DAOFactory.getComponenteDAO().getComponentes(sistema, start, PAGE_SIZE);
		int count = DAOFactory.getComponenteDAO().conta();
		
		boolean hasNext = (count > (page+1) * PAGE_SIZE);
		boolean hasPrior = (page > 0);
		boolean hasItem = componente.size() > 0 ? true : false;
		
		setAttribute("item", componente);
		setAttribute("page", page);
		setAttribute("hasItem", hasItem);
		setAttribute("noItem", !hasItem);
		setAttribute("hasNextPage", hasNext);
		setAttribute("hasPriorPage", hasPrior);
		setAttribute("noPriorPage", !hasPrior);
		setAttribute("noNextPage", !hasNext);
		
		return SUCCESS;
	}
		
	/**
	 * Ação para editar componentes 
	 */
	@ErrorRedirect("/componente/listaComponentes.do")
	@Success("/jsp/componente/componenteform.jsp")
	public String editaComponente() throws ActionException
	{
		int id = getIntParameter("id", -1);
		Componente componente = DAOFactory.getComponenteDAO().get(id);
		check(componente != null, "O componente não existe.");		
		
		setAttribute("item", componente);
		return SUCCESS;
	}
	
	/**
	 * Ação para remover um componente cadastrado
	 */
	@Any("/componente/listaComponentes.do")
	public String removeComponente() throws ActionException{
		
		int id = getIntParameter("id", -1);
		
		Componente componente = DAOFactory.getComponenteDAO().get(id);
		check(componente != null, "O componente não existe");
		check(DAOFactory.getTicketDAO().getTicketsComponenteSistema(componente.getNome(), componente.getSistema()).size() == 0, 
				"O componente não foi deletado, pois possui um ou mais tickets.");
		
		DAOFactory.getComponenteDAO().remove((long)id);
		addRedirectNotice("O componente selecionado foi removido com sucesso");
		return SUCCESS;
	}
	
//	@Any("/componente/listaComponentes.do")
//	public String filtraComponente() throws ActionException {
//		
//		String filtro = getParameter("filtro", "");
//		setAttribute("filtro", filtro);
//		return SUCCESS;		
//	}
	
}
