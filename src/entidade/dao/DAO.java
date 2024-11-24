package entidade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import conexao.ConexaoDB;

public class DAO {
	
	private Connection conn;
	private PreparedStatement stmt = null;
	
	public DAO() {
		this.conn = ConexaoDB.getConnection();
	}

	public ResultSet select(String entidade) throws Exception {
		ResultSet rs = null;
		try {
			String query = "SELECT * FROM "+entidade;
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			rs = stmt.executeQuery();
			return rs;
		} catch (SQLException ex) {
			throw new Exception("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
	}
	
	public ResultSet select(String entidade, String condicao) throws Exception {
		ResultSet rs = null;
		try {
			String query = "SELECT * FROM "+entidade+" WHERE "+condicao;
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			rs = stmt.executeQuery();
			return rs;
		} catch (SQLException ex) {
			throw new Exception("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
	}
	
	public ResultSet selectQuery(String query) throws Exception {
		ResultSet rs = null;
		try {;
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			rs = stmt.executeQuery();
			return rs;
		} catch (SQLException ex) {
			throw new Exception("Não foi possível executar a query: " + ex);
		} finally {
			ConexaoDB.closeConnection();
		}
	}
	
	public boolean update(String entidade, Map<String, String> dados, int identificador) {
		try {
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
			stmt = conn.prepareStatement(query);
			LogSql.exibirComandoSql(query);
			stmt.executeUpdate();
			System.out.println("Registro atualizado com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
			return false;
		} finally {
			ConexaoDB.closeConnection();
		}
	}
	
	public boolean update(String entidade, Map<String, String> dados, String condicao) {
		try {
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
			System.out.println("Registro atualizado com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar a query: " + ex);
			return false;
		} finally {
			ConexaoDB.closeConnection();
		}
	}
	
	public boolean insert(String entidade, Map<String, String> dados) {
		try {
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
			System.out.println("Registro inserido com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
			return false;
		} finally {
			ConexaoDB.closeConnection();
		}
	}
	
	public boolean delete(String entidade, int identificador) {
		try {
			String query = "DELETE FROM "+entidade+" WHERE id = "+identificador;
			LogSql.exibirComandoSql(query);
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			System.out.println("Registro excluído com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
			return false;
		} finally {
			ConexaoDB.closeConnection();
		}
	}
	
	public boolean delete(String entidade, String condicao) {
		try {
			String query = "DELETE FROM "+entidade+" WHERE "+condicao;
			LogSql.exibirComandoSql(query);
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			System.out.println("Registro excluído com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
			return false;
		} finally {
			ConexaoDB.closeConnection();
		}
	}
	
	public int count(String entidade) {
		ResultSet rs = null;
		try {
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