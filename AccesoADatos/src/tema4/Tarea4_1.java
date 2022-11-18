package tema4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Tarea4_1 {
	public static Scanner sc = new Scanner(System.in);
	private static String servidor = "jdbc:mysql://dns11036.phdns11.es";
    private static Connection con;
    private static Statement st;
	public static void main(String[] args) throws ClassNotFoundException {
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection(servidor,"ad2223_asosa","1234");
	        if ( con != null){
	            st = (Statement) con.createStatement();
	            System.out.println("Conexión a base de datos correcta ");
	            System.out.println(st.toString());
	            }
	        String use = "USE ad2223_asosa";
	        st.execute(use);
	        String[] Buzon= {"chat VARCHAR(20)", "mensaje VARCHAR(20)", "fecha DATE", "hora TIME", "leido BOOLEAN"};
			String[] Contactos = {"id INT", "nombre VARCHAR(20)", "telefono INT", "UsuariosBloqueados VARCHAR(20)"};
			String tabla = "buzon";
			String tabla1 = "contactos";
			//CrearTablas(st, con, tabla, Buzon);
			//CrearTablas(st, con, tabla1, Contactos);
			//InsertarContactos(Statement st);
			//InsertarBuzon(statement st);
			//mostrarBuzon()
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void mostrarContactos(Statement st, Connection con) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * from ad2223_asosa.Contactos");
			  ResultSet rs = ps.executeQuery();
		  	while(rs.next()) {
		  		System.out.println("id: " + rs.getInt("id") + " nombre: " + rs.getString("nombre") + 
		  				" telefono: " + rs.getInt("telefono") + " UsuariosBloqueados: " + rs.getString("UsuariosBloqueados"));
		  	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void mostrarBuzon(Statement st, Connection con) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * from ad2223_asosa.Buzon");
			  ResultSet rs = ps.executeQuery();
		  	while(rs.next()) {
		  		System.out.println("chat: " + rs.getString("chat") + " mensaje: " + rs.getString("mensaje") + 
		  				" fecha: " + rs.getDate("fecha") + " hora: " + rs.getTime("hora") + "leido: " + rs.getBoolean("leido"));
		  	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void insertarBuzon(Statement st) throws IOException {
		  try{
			  String sql = "insert into ad2223_asosa.Buzon(chat) values ('Angel'),"
			  		+ "('Ale'),"
			  		+ "('Javi'),"
			  		+ "('David')";
				st.execute(sql);
				System.out.println();
	      }catch (SQLException e) {
			e.printStackTrace();
		}
		  
	}
	public static void insertarContactos(Statement st) throws IOException {
		  try{
			  String sql = "insert into ad2223_asosa.Contactos(nombre, telefono, usuariosBloqueados) values ('Angel', '999999999', 'Javi'),"
			  		+ "('Ale', '666666666', 'David'),"
			  		+ "('Javi', '123456789', 'Ale'),"
			  		+ "('David', '987654321, 'Angel')";
				st.execute(sql);
				System.out.println();
	      }catch (SQLException e) {
			e.printStackTrace();
		}
		  
	}
	public static void CrearTablas(Statement st, Connection con, String tabla, String[] campos) throws SQLException {
		//String sql2 = "DELETE IF EXISTS TABLE " + tabla + ";";
	      String sql="CREATE TABLE IF NOT EXISTS " + tabla +"(";
	      for(int i = 0; i < campos.length; i++){

	          if (i == campos.length - 1){
	              sql += campos[i];
	          } else {
	              sql += campos[i] + ",";
	          }
	      }
	      sql += ")";
	      System.out.println(sql);
	      System.out.println();
	      //st.executeUpdate(sql2);
	      st.execute(sql);
	}

}
