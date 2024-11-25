package entidade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import conexao.ConexaoDB;

public class DAO {

	private PreparedStatement stmt = null;

	public ResultSet select(String entidade) {
		ResultSet rs = null;
		try {
			Connection conn = ConexaoDB.getConnection();
			String query = "SELECT * FROM "+entidade;
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			rs = stmt.executeQuery();
			return rs;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
		return null;
	}
	
	public ResultSet select(String entidade, String condicao) {
		ResultSet rs = null;
		try {
			Connection conn = ConexaoDB.getConnection();
			String query = "SELECT * FROM "+entidade+" WHERE "+condicao;
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			rs = stmt.executeQuery();
			return rs;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
		return null;
	}
	
	public ResultSet selectQuery(String query) {
		ResultSet rs = null;
		try {
			Connection conn = ConexaoDB.getConnection();
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			rs = stmt.executeQuery();
			return rs;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
		return null;
	}
	
	public boolean update(String entidade, Map<String, String> dados, int identificador) {
		try {
			Connection conn = ConexaoDB.getConnection();
			StringBuilder setUpdate = new StringBuilder();
			for (Map.Entry<String, String> entry : dados.entrySet()) {
	            if (setUpdate.length() > 0) {
	            	setUpdate.append(", ");
	            }
	            setUpdate.append(entry.getKey())
	                  .append(" = '")
	                  .append(entry.getValue())
	                  .append("'");
	        }
			String query = "UPDATE "+entidade+" SET "+setUpdate.toString()+" WHERE id = "+identificador;
			LogSql.exibirComandoSql(query);
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			System.out.println("Registro atualizado com sucesso\n");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
		return false;
	}
	
	public boolean update(String entidade, Map<String, String> dados, String condicao) {
		try {
			Connection conn = ConexaoDB.getConnection();
			StringBuilder setUpdate = new StringBuilder();
			for (Map.Entry<String, String> entry : dados.entrySet()) {
	            if (setUpdate.length() > 0) {
	            	setUpdate.append(", ");
	            }
	            setUpdate.append(entry.getKey())
	                  .append(" = '")
	                  .append(entry.getValue())
	                  .append("'");
	        }
			String query = "UPDATE "+entidade+" SET "+setUpdate.toString()+" WHERE "+condicao;
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			stmt.executeUpdate();
			System.out.println("Registro atualizado com sucesso\n");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
		return false;
	}
	
	public boolean insert(String entidade, Map<String, String> dados) {
		try {
			Connection conn = ConexaoDB.getConnection();
			String query = "INSERT INTO "+entidade+" (";
			StringBuilder campos = new StringBuilder();
			StringBuilder valores = new StringBuilder();
			for (Map.Entry<String, String> entry : dados.entrySet()) {
	            if (campos.length() > 0) {
	            	campos.append(", ");
	            	valores.append(", ");
	            }
	            campos.append(entry.getKey());
	            valores.append("'").append(entry.getValue()).append("'");
	        }
			query += campos.toString()+") VALUES ("+valores+")";
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			stmt.executeUpdate();
			System.out.println("Registro inserido com sucesso\n");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
		return false;
	}
	
	public boolean delete(String entidade, int identificador) {
		try {
			Connection conn = ConexaoDB.getConnection();
			String query = "DELETE FROM "+entidade+" WHERE id = "+identificador;
			LogSql.exibirComandoSql(query);
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			System.out.println("Registro excluído com sucesso\n");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
		return false;
	}
	
	public boolean delete(String entidade, String condicao) {
		try {
			Connection conn = ConexaoDB.getConnection();
			String query = "DELETE FROM "+entidade+" WHERE "+condicao;
			LogSql.exibirComandoSql(query);
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			System.out.println("Registro excluído com sucesso\n");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
		return false;
	}
	
	public int count(String entidade) {
		ResultSet rs = null;
		try {
			Connection conn = ConexaoDB.getConnection();
			String query = "SELECT count(id) FROM "+entidade;
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			rs = stmt.executeQuery();
			rs.next();
			return rs.getInt("count");
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
		return 0;
	}
	
	public int count(String entidade, String condicao) {
		ResultSet rs = null;
		try {
			Connection conn = ConexaoDB.getConnection();
			String query = "SELECT count(id) FROM "+entidade+" WHERE "+condicao;
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			rs = stmt.executeQuery();
			rs.next();
			return rs.getInt("count");
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
		return 0;
	}
	
}