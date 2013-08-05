package br.unirio.projetodswgae.actions;

import br.unirio.projetodswgae.dao.DAOFactory;
import br.unirio.projetodswgae.model.Componente;
import br.unirio.simplemvc.actions.Action;
import br.unirio.simplemvc.actions.ActionException;
import br.unirio.simplemvc.actions.authentication.DisableUserVerification;
import br.unirio.simplemvc.actions.qualifiers.Ajax;
import br.unirio.simplemvc.json.JSONArray;

public class ActionCommon extends Action
{
	/**
	 * Ação que lista os componentes de um sistema
	 */
	@DisableUserVerification
	@Ajax
	public String listaComponentes() throws ActionException
	{
		String sistema = getParameter("sistema");
		Iterable<Componente> componentes = DAOFactory.getComponenteDAO().getComponentesSistema(sistema);
		JSONArray result = new JSONArray();

		if (componentes != null)
		{
			for (Componente componente : componentes)
				result.add(componente.getNome());
		}
		else
		{
			result.add(sistema);
		}

		return ajaxSuccess(result);
	}
	
	
}
