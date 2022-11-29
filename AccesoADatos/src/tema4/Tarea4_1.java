package tema4;

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
			// Lo primero que hace el programa es acceder a la base de datos.
			Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection(servidor,"ad2223_asosa","1234");
	        if ( con != null){
	            st = (Statement) con.createStatement();
	            System.out.println("Conexión a base de datos correcta ");
	            System.out.println(st.toString());
	            }
	        String use = "USE ad2223_asosa";
	        st.execute(use);
	        // Se crean las tablas con los campos correspondientes.
	        String[] Buzon= {"chat VARCHAR(20)", "mensaje VARCHAR(20)", "fecha DATE", "hora TIME", "leido BOOLEAN"};
			String[] Contactos = {"id INT", "nombre VARCHAR(20)", "telefono INT", "Bloqueado BOOLEAN"};
			String tabla = "buzon";
			String tabla1 = "contactos";
			// Creamos ambas tablas con cada uno de sus datos.
			//CrearTablas(st, con, tabla, Buzon);
			//CrearTablas(st, con, tabla1, Contactos);
			//InsertarContactos(st);
			//InsertarBuzon(st);
			// Accedemos al usuario que se ha introducido.
			System.out.println("Usuario: ");
			String cuenta = sc.nextLine();
			if(cuenta == "Ale" || cuenta == "Angel" || cuenta == "Javi" || cuenta == "David") System.out.println("Bienvenido " + cuenta);
			else System.out.println("No existe ninguna cuenta con ese nombre");
			// Enseñamos el listado de conversaciones que tiene el usuario.
			mostrarBuzon(st, con, cuenta);
			int opc;
			do {
				// Le enseñamos al usuario las opciones que puede elegir.
				System.out.println("¿Qué desea hacer?:\n"
						+ "1. Ver un chat.\n"
						+ "2. Iniciar/Continuar una conversación.\n"
						+ "3. Ver la lista de contactos.\n"
						+ "4. Bloquear/Desbloquear un cantacto.");
				opc = sc.nextInt();
				String chat;
				switch (opc) {
				// Mostramos la iformación de un chat que eliga el usuario.
				case 1:
					System.out.println("¿A qué conversación quiere acceder?");
					chat = sc.nextLine();
					mostrarBuzon(st, con, chat);
					break;
				// Permite que el usuario envie mensajes al contacto que él desee escribir.
				case 2:
					System.out.println("¿A qué conversación quiere acceder?");
					chat = sc.nextLine();
					System.out.println("Escriba un mensaje");
					String mensaje = sc.nextLine();
					insertarMensaje(st, mensaje, chat);
					break;
				// Muestra el listado de contactos que tiene el usuario.
				case 3:
					mostrarContactos(st, con, cuenta);
					break;
				// Permite bloquear o desbloquear un contacto seleccionado por el usuario.
				case 4:
					System.out.println("¿A qué contacto quiere bloquear/desbloquear?");
					chat = sc.nextLine();
					actualizarContactos(st, chat);
					break;
				// Si no se introduce ninguno de los valores anteriores se muestra un mensaje de error.
				default:
					System.out.println("Opción no válida");
					break;
				}			
			}while(opc>=1 && opc<5);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Este método es el encargado de bloquear los contactos que desee el usuario.
	public static void actualizarContactos(Statement st, String conver) throws SQLException {
		String mod = "UPDATE Contactos SET Bloqueado=true WHERE nombre=" + conver + ";";
		st.execute(mod);
	}
	
	// Este método se encarga de guardar los mensajes escritos en la base de datos.
	public static void insertarMensaje(Statement st, String mensaje, String chat) {
		try{
			  String sql = "insert into ad2223_asosa.Buzon(mensaje, fecha, hora) values ('" + mensaje + "', GETDATE(), GETTIME) WHERE CHAT LIKE '" + chat + "',";
				st.execute(sql);
				System.out.println();
	      }catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Mostramos informacion de los contactos que tiene guardado(telefono, nombre, si esta bloqueado o no).
	public static void mostrarContactos(Statement st, Connection con, String cuenta) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * from ad2223_asosa.Contactos WHERE nombre NOT LIKE '" + cuenta + "'");
			  ResultSet rs = ps.executeQuery();
		  	while(rs.next()) {
		  		System.out.println("id: " + rs.getInt("id") + " nombre: " + rs.getString("nombre") + 
		  				" telefono: " + rs.getInt("telefono") + " Bloqueado: " + rs.getBoolean("Bloqueado"));
		  	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Te muestra todos los mensajes junto con los contactos que te los han mandado.
	public static void mostrarBuzon(Statement st, Connection con, String nombre) {
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * from ad2223_asosa.Buzon WHERE CHAT NOT LIKE " + nombre);
			  ResultSet rs = ps.executeQuery();
		  	while(rs.next()) {
		  		System.out.println("chat: " + rs.getString("chat") + " mensaje: " + rs.getString("mensaje") + 
		  				" fecha: " + rs.getDate("fecha") + " hora: " + rs.getTime("hora") + "leido: " + rs.getBoolean("leido"));
		  	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Añadimos un listado de cuatro contactos iniciales que tiene guardado el usuario.
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
	
	// Este método crea cuatro contactos iniciales con información de cada uno.
	public static void insertarContactos(Statement st) throws IOException {
		  try{
			  String sql = "insert into ad2223_asosa.Contactos(nombre, telefono, usuariosBloqueados) values ('Angel', '999999999', 'false'),"
			  		+ "('Ale', '666666666', 'true'),"
			  		+ "('Javi', '123456789', 'false'),"
			  		+ "('David', '987654321, 'true')";
				st.execute(sql);
				System.out.println();
	      }catch (SQLException e) {
			e.printStackTrace();
		}
		  
	}
	
	// Crea las tablas Buzon y Contactos la primera vez que se inicie la aplicacion.
	public static void CrearTablas(Statement st, Connection con, String tabla, String[] campos) throws SQLException {
		//String sql2 = "DELETE IF EXISTS TABLE " + tabla + ";";
	      String sql = "CREATE TABLE IF NOT EXISTS " + tabla +"(";
	      for(int i = 0; i < campos.length; i++){

	          if (i == campos.length - 1){
	              sql += campos[i];
	          } else {
	              sql += campos[i] + ",";
	          }
	      }
	      sql += ")";
	    //st.executeUpdate(sql2);
	      System.out.println(sql);
	      System.out.println();
	      st.execute(sql);
	}

}
