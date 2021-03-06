package by.training.filmstore.command.util;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class CustomAttrTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private String head;
	private int row;

	public void setHead(String head){
		this.head = head;
	}
	
	public void setRow(Integer row) {
		this.row = row;
	}
	
	public int doStartTag() throws JspTagException {

		try {
			JspWriter out = pageContext.getOut();
			out.write("<table border='1'><colgroup span='2' title='title' />");
			out.write("<thead><tr><th scope='col'>"+head+"</th>"
					+ "</tr></thead>");
			out.write("<tbody><tr><td>");
		} catch (IOException e) {
			throw new JspTagException(e.getMessage());
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doAfterBody() throws JspTagException {
		if (row-- > 1) {
			try {
			pageContext.getOut().write("</td></tr><tr><td>");
			} catch (IOException e) {
				throw new JspTagException(e.getMessage());
			}
			return EVAL_BODY_AGAIN;
		} else {
			return SKIP_BODY;
		}
	}
	
	public int doEndTag() throws JspTagException {
		try {
			pageContext.getOut().write("</td></tr></tbody></table>");
		} catch (IOException e) {
			throw new JspTagException(e.getMessage());
		}
		return EVAL_PAGE;
	}
}
