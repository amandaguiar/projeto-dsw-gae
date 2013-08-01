package br.unirio.projetodswgae.model;

public enum StatusTicket {

	NOVO("novo"),
	RESOLVIDO("resolvido"), 
	INVALIDADO("invalidado"), 
	REABERTO("reaberto"),
	FECHADO("fechado");
	
private String codigo;
	
	StatusTicket(String codigo)
	{
		this.codigo = codigo;
	}
	
	public String getCodigo()
	{
		return codigo;
	}
	
	public static StatusTicket get(String codigo)
	{
		for (StatusTicket status : StatusTicket.values())
			if (status.getCodigo().compareToIgnoreCase(codigo) == 0)
				return status;
		
		return null;
	}
	
	
}
