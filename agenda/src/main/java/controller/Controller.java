package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}

	}

	// Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.sendRedirect("agenda.jsp");
		// Criando um objeto que ir� receber os dados javaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		// teste de recebimento da lista
		System.out.println(lista);
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i).getNome());
			System.out.println(lista.get(i).getFone());
			System.out.println(lista.get(i).getEmail());
		}

	}

	// Novo contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste d recebimento dos dados do formul�rio

		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));

		// setar as variaveis javabeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// invocar o m�todo inserirContato passando os par�metros
		dao.inserirContato(contato);
		// redirecionar para o documento agenda.jsp
		response.sendRedirect("main");

	}

	// Editar contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recebimento do id do contato que ser� editado
		String idcon = request.getParameter("idcon");
		contato.setIdcon(idcon);
		// Executar o m�todo selecionarContato(DAO)
		dao.selecionarContato(contato);
		// Setar os atributos do formul�rio com o conte�do JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
		// teste de recebimento
		System.out.println(contato.getIdcon());
		System.out.println(contato.getNome());
		System.out.println(contato.getFone());
		System.out.println(contato.getEmail());
	}

	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setar as vari�veis JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setIdcon(request.getParameter("nome"));
		contato.setIdcon(request.getParameter("fone"));
		contato.setIdcon(request.getParameter("email"));

		// teste de recebimento
		System.out.println(request.getParameter("idcon"));
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));

		dao.alterarContato(contato);
		// redirecionar para o documento agenda.jsp atualizando as altera��es
		response.sendRedirect("main");
	}

	// Remover um contato
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recebimento do id do contato a ser removido validador.js
		String idcon = request.getParameter("idcon");
		// setar a variavel idcon Javabeans
		contato.setIdcon(idcon);
		// executar o m�todo deletarContato
		dao.deletarContato(contato);
		// redirecionar para a agenda.jsp com lista atualizada
		response.sendRedirect("main");

	} 
	
	// Gerar relat�rio em pdf
	
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Document documento = new Document();
		try {
			//tipo de conte�do 
			response.setContentType("application/pdf");
			//nome do documento 
			response.addHeader("Content-Disposition", "inline; filename="+"contatos.pdf");
			//criar o documento
			PdfWriter.getInstance(documento, response.getOutputStream()); 
			//abrir o documento 
			documento.open();
			documento.add(new Paragraph("Lista de Contatos")); 
			documento.add(new Paragraph(" ")); 
			//criar uma tabela 
			PdfPTable table = new PdfPTable(3);
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			table.addCell(col1);
			table.addCell(col2); 
			table.addCell(col3);
			documento.add(table);
			//popular a tabela com os contatos 
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for(int i = 0; i < lista.size(); i++) {
				table.addCell(lista.get(i).getNome());
				table.addCell(lista.get(i).getFone()); 
				table.addCell(lista.get(i).getEmail());
				System.out.println(lista.get(i).getNome());
			}
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
}
