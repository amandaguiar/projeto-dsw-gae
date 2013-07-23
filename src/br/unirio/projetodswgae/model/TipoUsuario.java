package br.unirio.projetodswgae.model;

public enum TipoUsuario {

	USUARIO_FINAL("usuario_final"),
	OPERADOR("operador"),
	ADMINISTRADOR("administrador");
	
private String codigo;
	
	TipoUsuario(String codigo)
	{
		this.codigo = codigo;
	}
	
	public String getCodigo()
	{
		return codigo;
	}
	
	public static TipoUsuario get(String codigo)
	{
		for (TipoUsuario tipo : TipoUsuario.values())
			if (tipo.getCodigo().compareToIgnoreCase(codigo) == 0)
				return tipo;
		
		return null;
	}
	
	
}
