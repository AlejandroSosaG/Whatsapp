package tema3;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Tarea3_1 {
	private static String servidor = "jdbc:mysql://dns11036.phdns11.es";
    private static Connection con;
    private static Statement st = null;
    public static Scanner sc = new Scanner(System.in);
  public static void main(String[] args) throws SQLException, IOException {
	  String []player = {"idPlayer INT PRIMARY KEY", "Nick VARCHAR(45)", "password VARCHAR(128)", "email VARCHAR(100)"};
      String []compras = {"idCompra INT PRIMARY KEY", "idPlayer INT", "idGames INT", "cosa VARCHAR(25)", "Precio DECIMAL(6,2)", "FechaCompra DATE"};
      String []games= {"idGames INT", "Nombre VARCHAR(45)", "tiempoJugado TIME"};
      String tabla = null;
      int opc;
      do{
    	  System.out.println("Elija una opción: \n"
      		+ "1. Conectar con la base de datos.\n"
      		+ "2. Crear una tabla.\n"
      		+ "3. Insertar datos a una tabla.\n"
      		+ "4. Mostrar datos de una tabla\n"
      		+ "5. Modificar una tabla.\n"
      		+ "6. Eliminar una tabla.");
      opc = sc.nextInt();
      
      if(opc>1) tabla = sc.next();
      switch (opc) {
	case 1:
		con = conectar(con, st, servidor);
		st = con.createStatement();
		break;
	case 2:
		crearTablas(st, tabla, games);
		break;
	case 3:
		insertar(st, tabla);
		break;
	case 4:
		listarGames(con);
		break;
	case 5:
		modificar(st, tabla);
		break;
	case 6:
		borrar(st, tabla);
		break;
	default:
		System.out.println("Programa finalizado");
	}
      }while(opc<7);
    }
  /*
   * Este método se conecta con la base de datos.
   */
  public static Connection conectar(Connection connection, Statement st, String servidor) {
	  try {
          Class.forName("com.mysql.cj.jdbc.Driver");
          connection = DriverManager.getConnection(servidor,"asosa","asosa");
          String use = "USE ad2223_asosa";
         
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }catch (SQLException e){
          e.printStackTrace();
      }
	return connection;
}
  /*
   * Este método crea la tabla que nos pasa el usuario por parámetro.
   */
  public static void crearTablas(Statement st, String tabla, String []campos) throws SQLException {
      //String sql2 = "DELETE from " + tabla + ";";
      String sql="CREATE TABLE " + tabla +"(";
      for(int i = 0; i < campos.length; i++){

          if (i == campos.length - 1){
              sql += campos[i];
          } else {
              sql += campos[i] + ",";
          }
      }
      sql += ")";
      System.out.println(sql);
      //st.executeUpdate(sql2);
      st.executeUpdate(sql);
      
}
  /*
   * Este método inserta los datos en la tabla que nos pasa el usuario por parámetro.
   */
  public static void insertar(Statement st, String tabla) throws IOException {
	  try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\asosa\\git\\repository3\\Acceso_A_Datos\\src\\tema3\\"+tabla+".sql"))){
		  String linea;
		  while ((linea = br.readLine()) != null) {
			System.out.println(linea);
			st.execute(linea);
		}
      }catch (SQLException e) {
		e.printStackTrace();
	}catch (IOException e) {
		throw new RuntimeException(e);
	}
	  if(tabla!=null) System.out.println("Inserción de datos ejecutado correctamente");
		else System.out.println("Inserción fallida");
}
  /*
   * Este método muestra los datos de la tabla Player.
   */
  public static void listarPlayer(Connection con) throws SQLException {
	  PreparedStatement ps = con.prepareStatement("SELECT * from ad2223_asosa.Player");
	  ResultSet rs = ps.executeQuery();
  	while(rs.next()) {
  		System.out.println("idPlayer: " + rs.getString("idPlayer") + " Nick: " + rs.getString("Nick") + 
  				" password: " + rs.getString("password") + " email: " + rs.getString("email"));
  	}
}
  /*
   * Este método muestra los datos de la tabla Compras.
   */
  public static void listarCompras(Connection con) throws SQLException {
	  PreparedStatement ps = con.prepareStatement("SELECT * from ad2223_asosa.Compras");
	  ResultSet rs = ps.executeQuery();
  	while(rs.next()) {
  		System.out.println("idCompra: " + rs.getString("idCompra") + " idPlayer: " + rs.getString("idPlayer") + " idGames: " + rs.getString("idGames") +
  				" cosa: " + rs.getString("cosa") + " precio: " + rs.getString("precio") + " FechaCompra: " + rs.getString("FechaCompra"));
  	}
}
  /*
   * Este método muestra los datos de la tabla Games.
   */
  public static void listarGames(Connection con) throws SQLException {
	  PreparedStatement ps = con.prepareStatement("SELECT * from ad2223_asosa.Games");
	  ResultSet rs = ps.executeQuery();
  	while(rs.next()) {
  		System.out.println("idGames: " + rs.getString("idGames") + " Nombre: " + rs.getString("Nombre") + 
  				" tiempoJugado: " + rs.getString("tiempoJugado"));
  	}
}
  /*
   * Este método modifica los datos de la tabla que nos pasa el usuario por parámetro.
   */
  public static void modificar(Statement st, String tabla) throws SQLException {
		String mod = "UPDATE " + tabla + " SET nombre_columna =valor, nombre_columna=valor2 WHERE nombre_columna =valor;";
		st.execute(mod);
	}
  /*
   * Este método elimina los datos de la tabla que nos pasa el usuario por parámetro.
   */
  public static void borrar(Statement st, String tabla) throws SQLException {
	String borrar = "DELETE FROM " + tabla;
	st.execute(borrar);
	if(tabla==null) System.out.println("Borrado ejectutado correctamente");
	else System.out.println("Borrado fallido");
}
}
