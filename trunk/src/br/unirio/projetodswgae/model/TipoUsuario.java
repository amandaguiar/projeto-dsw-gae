package br.unirio.projetodswgae.model;

public enum TipoUsuario {

	USUARIO_FINAL("final", "USUARIO_FINAL"),
	OPERADOR("operador", "OPERADOR"),
	ADMINISTRADOR("adm", "ADMINISTRADOR");
	
private String codigo;
private String nome;
	
	TipoUsuario(String codigo, String nome)
	{
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public String getCodigo()
	{
		return codigo;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public static TipoUsuario get(String codigo)
	{
		for (TipoUsuario tipo : TipoUsuario.values())
			if (tipo.getCodigo().compareToIgnoreCase(codigo) == 0)
				return tipo;
		
		return null;
	}
	
	
}
