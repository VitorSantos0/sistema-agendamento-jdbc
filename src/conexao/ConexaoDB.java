package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

	protected Connection con = null;
	protected String hostName = "localhost";
	protected String userName = "postgres";
	protected String password = "postgres";
  	protected String url;
  	protected String jdbcDriver = "org.postgresql.Driver";
  	protected String dataBaseName;
  	protected String dataBasePrefix = "jdbc:postgresql://";
	
  	public ConexaoDB(String dataBaseName) {
  		this.dataBaseName = dataBaseName;
  		this.url = this.dataBasePrefix+this.hostName+"/"+this.dataBaseName;
  	}
  	
  	public Connection getConnection() {
  	  try {
  	    if (con == null) {
  	      Class.forName(jdbcDriver);
  	      con = DriverManager.getConnection(url, userName, password);
  	      if(con != null) {
  	    	  //System.out.println("Conexão iniciada");
  	      }
  	    } else if (con.isClosed()) {
  	      con = null;
  	      return getConnection();
  	    }
  	  } catch (ClassNotFoundException e) {
  		  System.out.println("A classe do driver do banco de dados não foi localizada, instale o driver do javapath");
//  		  e.printStackTrace();
  	  } catch (SQLException e) {
  		  e.printStackTrace();
  	  }
  	  return con;
  	}
	
  	public void closeConnection() {
  	  if (con != null) {
  	    try {
  	      con.close();
  	      //System.out.println("Conexão encerrada");
  	    } catch (SQLException e) {
  	    	e.printStackTrace();
  	    }
  	  }
  	}
  	
}
