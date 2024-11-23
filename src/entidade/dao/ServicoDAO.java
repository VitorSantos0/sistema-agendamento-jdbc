package entidade.dao;

import entidade.Servico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexao.ConexaoDB;

public class ServicoDAO {
	
	private Connection conn;
	private PreparedStatement stmt = null;
	
	public ServicoDAO() {
		this.conn = ConexaoDB.getConnection();
	}
	
	public Servico selectById(int id) {
		ResultSet resultSet = null;
		Servico servico = new Servico();
		String sql = "SELECT * FROM servico WHERE id = "+id;
		LogSql.exibirComandoSql(sql);
		try {
			stmt = conn.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			resultSet.next();
			servico.setCodigo(resultSet.getInt("id"));
			servico.setDescricao(resultSet.getString("descricao"));
			servico.setCategoria(resultSet.getString("categoria"));
			servico.setAtivo(resultSet.getBoolean("ativo"));
			servico.setValor(resultSet.getDouble("valor"));

		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
		}
		return servico;
	}
	
	public boolean desativarServico(int id) {
		
		String sql = "update servico set ativo = 0 WHERE id = "+id;
		LogSql.exibirComandoSql(sql);
		try {
			stmt = conn.prepareStatement(sql);

			stmt.executeUpdate();
			System.out.println("Serviço desativado com sucesso");
			return true;

		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
			return false;
		}
	}
	
	public boolean ativarServico(int id) {
		String sql = "update servico set ativo = 1 WHERE id = "+id;
		LogSql.exibirComandoSql(sql);
		try {
			stmt = conn.prepareStatement(sql);

			stmt.executeUpdate();
			System.out.println("Serviço ativado com sucesso");
			return true;

		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
			return false;
		}
	}


	public ArrayList<Servico> selectAll() {
		ResultSet resultado = null;
		ArrayList<Servico> servicos = new ArrayList<>();
		String sql = "SELECT * FROM servico";
		LogSql.exibirComandoSql(sql);
		try {
			
			stmt = conn.prepareStatement(sql);
			resultado = stmt.executeQuery();

			while (resultado.next()) {
				servicos.add(new Servico(resultado.getInt("id"), resultado.getString("descricao"),
						resultado.getDouble("valor"), resultado.getString("categoria"),
						resultado.getBoolean("ativo")));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
		} 
		return servicos;
	}
	
	public ArrayList<Servico> selectView() {
		PreparedStatement stmt = null;
		ResultSet resultado = null;
		ArrayList<Servico> servicos = new ArrayList<>();
		String sql = "SELECT * FROM servicos_ativos_view";
		LogSql.exibirComandoSql(sql);
		try {
			stmt = conn.prepareStatement(sql);
			resultado = stmt.executeQuery();

			while (resultado.next()) {
				servicos.add(new Servico(resultado.getInt("id"), resultado.getString("descricao"),
						resultado.getDouble("valor"), resultado.getString("categoria"),
						resultado.getBoolean("ativo")));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
		} 
		return servicos;
	}
}
