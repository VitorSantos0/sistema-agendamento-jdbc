package conexao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoDB {

	protected static Connection con = null;
  	
  	public static Connection getConnection() {
	  try {
		  Class.forName("org.postgresql.Driver");
	  FileInputStream dbConfig = new FileInputStream("db.properties");
	  Properties properfies = new Properties();
	  properfies.load(dbConfig);
	  String url = properfies.getProperty("url")+properfies.getProperty("database");
	  String user = properfies.getProperty("user");
	  String pwd = properfies.getProperty("password");
	      con = DriverManager.getConnection(url, user, pwd);
	  } catch (ClassNotFoundException e) {
		  System.out.println("A classe do driver do banco de dados não foi localizada, instale o driver do javapath");
	  } catch (SQLException e) {
		  System.out.println("Falha ao conectar ao banco de dados, verifique as configurações");
	  } catch(IOException e) {
		  System.out.println("Falha na leitura das configurações do banco de dados");
	  }
	  return con;
  	}
	
  	public static void closeConnection() {
  		try {
  	    	con.close();
  	    } catch (SQLException e) {
  	    	System.out.println("Falha ao desconectar ao banco de dados, verifique as configurações");
  	    }
  	}
  	
}
