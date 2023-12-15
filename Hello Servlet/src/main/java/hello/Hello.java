package hello;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Hello
 */
@WebServlet("/Hello")
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		 // Adiciona uma estrutura HTML básica
	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Exemplo de Servlet</title>");
	    out.println("</head>");
	    out.println("<body>");

	    // Adiciona conteúdo HTML
	    out.println("<h1>Olá, mundo!</h1>");
	    out.println("<p>Esta é uma página de exemplo gerada pelo Servlet.</p>");

	    // Fecha a estrutura HTML
	    out.println("</body>");
	    out.println("</html>");
	    }

}
