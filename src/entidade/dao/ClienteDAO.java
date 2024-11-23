package entidade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexao.ConexaoDB;
import entidade.Cliente;

public class ClienteDAO {

	private Connection conn;
	private PreparedStatement stmt = null;
	
	public ClienteDAO() {
		this.conn = ConexaoDB.getConnection();
	}
	
	public Cliente selectById(int id) {
		ResultSet resultSet = null;
		Cliente cliente = new Cliente();
		try {
			String query = "SELECT * FROM cliente WHERE id = "+id;
			LogSql.exibirComandoSql(query);
			stmt = conn.prepareStatement(query);
			resultSet = stmt.executeQuery();
			if(resultSet.next()) {
				cliente.setCodigo(resultSet.getInt("id"));
				cliente.setNome(resultSet.getString("nome"));
				cliente.setCpf(resultSet.getString("cpf"));
				cliente.setEndereco(resultSet.getString("endereco"));
				cliente.setTelefone(resultSet.getString("telefone"));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
		}
		return cliente;
	}

	public ArrayList<Cliente> selectAll() {
		ResultSet resultado = null;
		ArrayList<Cliente> cliente = new ArrayList<>();

		try {
			String query = "SELECT * FROM cliente";
			LogSql.exibirComandoSql(query);
			stmt = conn.prepareStatement(query);
			resultado = stmt.executeQuery();

			while (resultado.next()) {
				cliente.add(new Cliente(resultado.getInt("id"), resultado.getString("nome"),
						resultado.getString("cpf"), resultado.getString("endereco"),
						resultado.getString("telefone")));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
		} 
		return cliente;
	}
	
	public boolean insertId(Cliente cliente) {
		String sql = "INSERT INTO cliente (id, nome, cpf, endereco, telefone) VALUES (?,?,?,?,?)";
		LogSql.exibirComandoSql(sql);
		try {
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, cliente.getCodigo());
			stmt.setString(2, cliente.getNome());
			stmt.setString(3, cliente.getCpf());
			stmt.setString(4, cliente.getEndereco());
			stmt.setString(5, cliente.getTelefone());
			
			stmt.executeUpdate();

			System.out.println("Registro salvo com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
			return false;
		}

	}
	
	public boolean insert(Cliente cliente) {
		String sql = "INSERT INTO cliente (nome, cpf, endereco, telefone) VALUES (?,?,?,?)";
		
		try {
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getCpf());
			stmt.setString(3, cliente.getEndereco());
			stmt.setString(4, cliente.getTelefone());
			

			String executedSQL = sql
	                .replaceFirst("\\?", "'"+cliente.getNome()+"'")
	                .replaceFirst("\\?", "'"+cliente.getCpf()+"'")
					.replaceFirst("\\?", "'"+cliente.getEndereco()+"'")
					.replaceFirst("\\?", "'"+cliente.getTelefone()+"'");
			
			LogSql.exibirComandoSql(executedSQL);

			stmt.executeUpdate();

			System.out.println("Registro salvo com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
			return false;
		}

	}

	public boolean update(String chave, String valor, int codigo) {
		try {
			String sql = "UPDATE cliente SET "+chave+" = '"+valor+"' WHERE id = "+codigo;
			LogSql.exibirComandoSql(sql);
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			System.out.println("Registro atualizado com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
			return false;
		}

	}

	public boolean delete(int id) {
		try {
			String sql = "DELETE FROM cliente WHERE id = "+id;
			LogSql.exibirComandoSql(sql);
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			
			System.out.println("Registro apagado com sucesso");
			return true;

		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
			return false;
		}
	}

}
