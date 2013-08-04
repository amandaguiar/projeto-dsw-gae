package br.unirio.projetodswgae.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringEscapeUtils;

import br.unirio.projetodswgae.actions.ActionTicket;

public class SeletorStatusTag extends TagSupport
{
	private static final long serialVersionUID = 3816062477060938015L;

	private String id;
	private String idTicket;
	private String valor;	
	private String tipoUsuario;
	private String classe;
	private String estilo;

	public SeletorStatusTag()
	{
		this.valor = "";
		this.tipoUsuario = "";
		this.classe = "";
		this.estilo = "";
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setIdticket(String idTicket)
	{
		this.idTicket = idTicket;
	}
	
	public void setValue(String valor)
	{
		this.valor = valor;
	}
	
	public void setTipoUsuario(String tipoUsuario)
	{
		this.tipoUsuario = tipoUsuario;
	}

	public void setClasse(String classe)
	{
		this.classe = classe;
	}

	public void setStyle(String estilo)
	{
		this.estilo = estilo;
	}
	
	@Override
	public int doStartTag() throws JspException
	{
		JspWriter out = pageContext.getOut();
		
		try
		{
			if (idTicket.equals("-1")) {
				
				out.write("<select disabled " + StringEscapeUtils.escapeHtml4(id) + "' size='1' id='" + StringEscapeUtils.escapeHtml4(id) + "'");
				out.write(">\n");
				String codigo = "";
				String selecionado = " SELECTED";
				out.write("<option value='" + codigo + "'" + selecionado + ">" + StringEscapeUtils.escapeHtml4(codigo) + "</option>\n");
				out.write("</select>\n");
			}
			/* Verifica se o usuario escolheu editar um ticket existente */
			else {
				
				out.write("<select name='" + StringEscapeUtils.escapeHtml4(id) + "' size='1' id='" + StringEscapeUtils.escapeHtml4(id) + "'");

				if (estilo.length() > 0)
					out.write(" style='" + StringEscapeUtils.escapeHtml4(estilo) + "'");
	
				if (classe.length() > 0)
					out.write(" class='" + StringEscapeUtils.escapeHtml4(classe) + "'");
	
				out.write(">\n");
				List<String> statusTickets = new ArrayList<String>();
				ActionTicket.verificaRegrasStatus(valor, tipoUsuario, statusTickets);
				
				for (String codigo : statusTickets) {
					
					String selecionado = (valor.compareToIgnoreCase(codigo) == 0) ? " SELECTED" : "";
					out.write("<option value='" + codigo + "'" + selecionado + ">" + StringEscapeUtils.escapeHtml4(codigo) + "</option>\n");
				}
				out.write("</select>\n");
			}
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
	
	
}
