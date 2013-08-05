<!DOCTYPE html>
<%@taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@taglib uri="/WEB-INF/pdsw.tld" prefix="pdsw"%>
<%@taglib uri="/WEB-INF/simplemvc.tld" prefix="mvc"%>

<%@ page import="br.unirio.simplemvc.servlets.AuthenticationService" %>
<%@ page import="br.unirio.projetodswgae.model.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<html>
<head>
	<meta charset="utf-8">
	<title>Projeto DSW</title>
	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="Projeto da disciplina DSW">
	<meta name="author" content="Amanda Aguiar e Jean Freitas">
		
	<link href="/css/jquery/jquery-ui-1.8.4.custom.css" rel="stylesheet">
	<link href="/css/twitter/bootstrap.min.css" rel="stylesheet">
	<link href="/css/twitter/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="/css/template.css" rel="stylesheet">
	
	<script src="/js/jquery/jquery.min.js"></script>
	<script src="/js/twitter/bootstrap.min.js"></script>
	
	<link rel="shortcut icon" href="/favicon.ico">
	
</head>
<body>

<%
	pageContext.setAttribute("usuarioLogado", AuthenticationService.getUser(request));	
	//int id = 0;
	//if (AuthenticationService.getUser(request) != null) {
	//	id = AuthenticationService.getUser(request).getId();
	//}
	//pageContext.setAttribute("ultimoLogin", Usuario.getPenultimoLogin(id));
	
	pageContext.setAttribute("ultimoLogin", "");
%>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			
			<a class="brand" href="../../index.html">Projeto DSW</a>	
					
			<mvc:checkLogged>
			<div class="nav-collapse collapse navbar-responsive-collapse">
				<ul class="nav pull-right">
					<li class="dropdown">											
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">
							<c:out value="${usuarioLogado.nome}"/>&nbsp;<c:out value="${usuarioLogado.sobrenome}"/>
							<b class="caret"></b>						
						</a>
						<ul class="dropdown-menu">
						 <li><a href="/usuario/preparaEdicaoDadosUsuario.do">Editar Perfil</a></li>
						 <li><a href="/login/preparaTrocaSenha.do">Trocar Senha</a></li> 
						 <li class="divider"></li>
						 <li><a href="/login/logout.do"><i class="icon-off"></i> Sair</a></li>
						</ul>					
					</li>
				</ul>
			
				<!-- 
				<div class="navbar-text pull-right"> 
					Último login em: ${ultimoLogin}
				</div>
				-->
			</div>
			</mvc:checkLogged>
		</div>
	</div>
</div>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span3 bs-docs-sidebar">
			<mvc:checkLogged>
				<div class="well" style="padding: 15px 0px;">
					<ul class="nav nav-list" id="menu">
					
						<mvc:checkUserLevel level="final">
						<li class="nav-header">Tickets</li>
						<li><a href="/ticket/listaTickets.do">Meus tickets</a></li>
						<li><a href="/ticket/novoTicket.do">Novo ticket</a></li>
						<li class="nav-header">Perfil</li>
						<li><a href="/usuario/preparaEdicaoDadosUsuario.do">Editar perfil</a></li>												
						<li><a href="/login/preparaTrocaSenha.do">Trocar senha</a></li>
						<li class="divider"></li>
						<li><a href="/login/logout.do">Sair</a></li>
						</mvc:checkUserLevel>
						
						<mvc:checkUserLevel level="operador">
						<li class="nav-header">Tickets</li>
						<li><a href="/ticket/listaTickets.do">Meus tickets</a></li>
						<li class="nav-header">Perfil</li>
						<li><a href="/usuario/preparaEdicaoDadosUsuario.do">Editar perfil</a></li>												
						<li><a href="/login/preparaTrocaSenha.do">Trocar senha</a></li>
						<li class="divider"></li>
						<li><a href="/login/logout.do">Sair</a></li>						
						</mvc:checkUserLevel>
						
						<mvc:checkUserLevel level="adm">				
						<li class="nav-header">Administração</li>
						<li><a href="/sistema/listaSistemas.do">Sistemas</a></li>
						<li><a href="/componente/listaComponentes.do">Componentes</a></li>
						<li><a href="/ticket/listaTickets.do">Tickets</a></li>						
						<li><a href="/usuario/listaUsuarios.do">Usuários</a></li>
						<li class="nav-header">Perfil</li>
						<li><a href="/usuario/preparaEdicaoDadosUsuario.do">Editar perfil</a></li>												
						<li><a href="/login/preparaTrocaSenha.do">Trocar senha</a></li>
						<li class="divider"></li>
						<li><a href="/login/logout.do">Sair</a></li>
						</mvc:checkUserLevel>
						
					</ul>
				</div>
			</mvc:checkLogged>

			<mvc:checkUnlogged>
				<div class="well well-large bs-docs-sidenav">
					<h3>Login</h3>
					<form action="/login/login.do" method="post">
						<label for="email">E-mail:</label>
						<input type="text" name="email" id="emailLoginField" class="span" placeholder="E-mail" />
						
						<label for="pwd">Senha:</label>
						<input type="password" name="pwd" class="span" placeholder="Senha" /><br />
						<input type="submit" name="Submit" value="Login" class="btn btn-primary">
					</form>
					<ul>
						<li><a href="/jsp/login/esqueceuSenha.jsp">Esqueci minha senha</a></li>
						<li><a href="/login/novo.do">Criar conta no sistema</a></li>
					</ul>
				</div>
			</mvc:checkUnlogged>
		</div>		
		<div class="span9">
			<div class="row-fluid error" id="pnErro">
				<mvc:error/>
			</div>
			
			<div class="row-fluid notice" id="pnAviso">
				<mvc:notice/>
			</div>
			
			<div class="row-fluid" id="pnCentral">
			</div>
		</div>
	</div>

	<hr style="margin-bottom: 0px;">

	<footer style="font-size: 10px;">
		<p>&copy; 2013: Projeto DSW</p>
	</footer>
</div>

<script type="text/javascript">
$(document).ready(function() {
	$("#emailLoginField").focus();
	var content = $("#content");
	if (content) $("#pnCentral").html("").append(content);
	
	var divError = $("div.error");
	if (divError.find("p").length > 0) divError.show();
	
	var divNotice = $("div.notice");
	var divNoticeP = divNotice.find("p");
	if (divNoticeP.length > 0) divNotice.html(divNoticeP.text()).show();
	
	var url = window.location;	
    $('#menu > li > a[href="'+ url +'"]').parent().addClass('active');
    $('#menu > li > a').filter(function() {
         return this.href == url;
    }).parent().addClass('active');
});

</script>

</body>
</html>