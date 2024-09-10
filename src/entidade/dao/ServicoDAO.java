package entidade.dao;

import entidade.Servico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexao.ConexaoDB;

public class ServicoDAO {

	private ConexaoDB conexao = null;

	public ServicoDAO() {
		conexao = new ConexaoDB("sistema_agendamento");
	}

	public ArrayList<Servico> selectAll() {
		Connection con = conexao.getConnection();
		PreparedStatement stmt = null;
		ResultSet resultado = null;
		ArrayList<Servico> servicos = new ArrayList<>();

		try {
			stmt = con.prepareStatement("SELECT * FROM servico");
			resultado = stmt.executeQuery();

			while (resultado.next()) {
				servicos.add(new Servico(resultado.getInt("id"), resultado.getString("descricao"),
						resultado.getDouble("valor"), resultado.getString("categoria"),
						resultado.getBoolean("status")));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
		} finally {
			conexao.closeConnection();
		}
		return servicos;
	}
}
