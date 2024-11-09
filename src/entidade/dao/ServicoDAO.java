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
	
	public ServicoDAO() {
		this.conn = ConexaoDB.getConnection();
	}
	
	public Servico selectById(int id) {
		PreparedStatement stmt = null;
		ResultSet resultSet = null;
		Servico servico = new Servico();
		try {
			stmt = conn.prepareStatement("SELECT * FROM servico WHERE id = ?");
			stmt.setInt(1, id);
			resultSet = stmt.executeQuery();
			
			servico.setCodigo(resultSet.getInt("id"));
			servico.setDescricao(resultSet.getString("descricao"));
			servico.setCategoria(resultSet.getString("categoria"));
			servico.setAtivo(resultSet.getBoolean("ativo"));
			servico.setValor(resultSet.getDouble("valor"));

		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
		}
		return servico;
	}

	public ArrayList<Servico> selectAll() {
		PreparedStatement stmt = null;
		ResultSet resultado = null;
		ArrayList<Servico> servicos = new ArrayList<>();

		try {
			stmt = conn.prepareStatement("SELECT * FROM servico");
			resultado = stmt.executeQuery();

			while (resultado.next()) {
				servicos.add(new Servico(resultado.getInt("id"), resultado.getString("descricao"),
						resultado.getDouble("valor"), resultado.getString("categoria"),
						resultado.getBoolean("ativo")));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
		} 
		return servicos;
	}
	
	public ArrayList<Servico> selectView() {
		PreparedStatement stmt = null;
		ResultSet resultado = null;
		ArrayList<Servico> servicos = new ArrayList<>();

		try {
			stmt = conn.prepareStatement("SELECT * FROM servicos_ativos");
			resultado = stmt.executeQuery();

			while (resultado.next()) {
				servicos.add(new Servico(resultado.getInt("id"), resultado.getString("descricao"),
						resultado.getDouble("valor"), resultado.getString("categoria"),
						resultado.getBoolean("ativo")));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
		} 
		return servicos;
	}
}
