package br.unirio.projetodswgae.model;

import br.unirio.simplemvc.gae.datastore.DataObject;
import br.unirio.simplemvc.servlets.IUser;

public class Usuario implements IUser, DataObject {
	private int id;
	private String nome;
	private String sobrenome;
	private String email;
	private String senha;
	private TipoUsuario tipoUsuario;
	private boolean ativo;
	private boolean forcaResetSenha;
	private boolean deveTrocarSenha;
	
	
	
	public Usuario() {
		this.id = -1;
		this.nome = "";
		this.sobrenome = "";
		this.email = "";
		this.senha = "";
		this.tipoUsuario = TipoUsuario.USUARIO_FINAL;
		this.ativo = true;
		this.setForcaResetSenha(false);
		this.deveTrocarSenha = false;
	}

	@Override
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	@Override
	public String getName()
	{
		return nome;
	}

	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getSobrenome() {
		return sobrenome;
	}



	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getSenha() {
		return senha;
	}



	public void setSenha(String senha) {
		this.senha = senha;
	}



	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	
	public boolean isAtivo() {
		return ativo;
	}

	public boolean getAtivo()
	{
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isForcaResetSenha() {
		return forcaResetSenha;
	}

	public boolean getForcaResetSenha() {
		return this.forcaResetSenha;
	}
	
	public void setForcaResetSenha(boolean forcaResetSenha) {
		this.forcaResetSenha = forcaResetSenha;
	}

	public boolean isDeveTrocarSenha() {
		return deveTrocarSenha;
	}

	public boolean getDeveTrocarSenha() {
		return this.deveTrocarSenha;
	}
	
	public void setDeveTrocarSenha(boolean deveTrocarSenha) {
		this.deveTrocarSenha = deveTrocarSenha;
	}

	@Override
	public boolean checkLevel(String nivel) {
		return (tipoUsuario.getCodigo().compareToIgnoreCase(nivel) == 0);
	}



	@Override
	public boolean isActive() {
		return ativo;
	}



	@Override
	public boolean mustChangePassword() {
		return deveTrocarSenha;
	}

	
	
	
	
}
