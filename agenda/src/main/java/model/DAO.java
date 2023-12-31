package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	/* M�dulo de conex�o */
	// Par�metros de conex�o
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "root123#";

	// M�todo de conex�o

	private Connection conectar() {
		Connection con;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	// teste de conex�o

	public void testeConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/* CRUD CREATE */
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome,fone,email) values (?,?,?)";
		try {
			// abrir conex�o
			Connection con = conectar();
			// Preparar a query para a execu��o no banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			// Substituir as interroga��es pelos par�metros
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// Executar a query
			pst.executeUpdate();
			// Encerrar a conex�o
			con.close();
		} catch (Exception e) {

		}
	}

	/* CRUD READ */

	public ArrayList<JavaBeans> listarContatos() {
		// Criando um objeto para acessar a classe javabeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "select * from contatos order by nome";
		try {
			// Abrir conex�o
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				// vari�veis de apoio que recebem os dados do banco
				//Observa��o importante: os n�meros do getString(?) correspondem �s colunas da tabela do banco de dados
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// populando o arraylist
				contatos.add(new JavaBeans(idcon, nome, fone, email));

			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		} 
	}
		
		/** CRUD UPDATE **/ 
		//selecionar contato 
		public void selecionarContato(JavaBeans contato) {
			String read2 = "select * from contatos where idcon = ?";
			try {
				Connection con = conectar();
				PreparedStatement pst = con.prepareStatement(read2); 
				pst.setString(1,contato.getIdcon());
				ResultSet rs = pst.executeQuery();
				while(rs.next()) {
					//Observa��o importante: os n�meros do getString(?) correspondem �s colunas da tabela do banco de dados
					contato.setIdcon(rs.getString(1));
					contato.setNome(rs.getString(2)); 
					contato.setFone(rs.getString(3)); 
					contato.setEmail(rs.getString(4));
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} 
		
		//editar contato 
		
		public void alterarContato(JavaBeans contato) {
			String create = "update contatos set nome=?, fone=?, email=? where idcon=?"; 
			try {
				Connection con = conectar();
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, contato.getNome());
				pst.setString(2, contato.getFone()); 
				pst.setString(3, contato.getEmail()); 
				pst.setString(4, contato.getIdcon());
				
			} catch (Exception e) {
				System.out.println(e);
			}
		} 
		
		/** CRUD DELETE **/ 

		 public void deletarContato(JavaBeans contato) {
			 String delete = "delete from contatos where idcon=?"; 
			 
			 try {
				Connection con = conectar(); 
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, contato.getIdcon());
				pst.executeUpdate();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		 }

	
}
