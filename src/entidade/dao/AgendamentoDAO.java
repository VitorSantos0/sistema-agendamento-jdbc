package entidade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import conexao.ConexaoDB;
import entidade.Agendamento;
import entidade.Servico;

public class AgendamentoDAO {

	private ConexaoDB conexaoDB = null;

	public AgendamentoDAO() {
		conexaoDB = new ConexaoDB("sistema_agendamento");
	}

	public boolean insert(Agendamento agendamento, Servico servico) {
		Connection conexao = conexaoDB.getConnection();
		PreparedStatement stmt = null;
		String sql = "INSERT INTO agendamento (servico_id," + "nome_cliente," + "telefone_cliente,"
				+ "nome_profissional," + "data_servico" + "hora_servico" + "data_hora_lancamento"
				+ "data_hora_cancelamento)" + "values (?,?,?,?,?,?,?,?)";

		try {
			stmt = conexao.prepareStatement(sql);

			stmt.setInt(1, servico.getCodigo());
			stmt.setString(2, agendamento.getNomeCliente());
			stmt.setString(3, agendamento.getTelefoneCliente());
			stmt.setString(4, agendamento.getNomeProfissional());
			stmt.setDate(4, agendamento.getDataServico());
			stmt.setTime(5, agendamento.getHoraServico());
			stmt.setTimestamp(6, agendamento.getDataHoraLancamento());
			stmt.setTimestamp(7, agendamento.getDataHoraCancelamento());

			stmt.executeUpdate();

			System.out.println("Registro salvo com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar" + ex);
			return false;
		} finally {
			conexaoDB.closeConnection();
		}

	}

	public boolean update(Agendamento agendamento, Servico servico) {
		Connection conexao = conexaoDB.getConnection();
		PreparedStatement stmt = null;
		String sql = "UPDATE agendamento SET servico_id = ?," + "nome_cliente = ?," + "telefone_cliente = ?,"
				+ "nome_profissional = ?," + "data_servico = ?" + "hora_servico = ?" + "data_hora_lancamento = ?"
				+ "data_hora_cancelamento = ?" + "where servico_id = ?";

		try {
			stmt = conexao.prepareStatement(sql);

			stmt.setInt(1, servico.getCodigo());
			stmt.setString(2, agendamento.getNomeCliente());
			stmt.setString(3, agendamento.getTelefoneCliente());
			stmt.setString(4, agendamento.getNomeProfissional());
			stmt.setDate(4, agendamento.getDataServico());
			stmt.setTime(5, agendamento.getHoraServico());
			stmt.setTimestamp(6, agendamento.getDataHoraLancamento());
			stmt.setTimestamp(7, agendamento.getDataHoraCancelamento());

			stmt.executeUpdate();
			System.out.println("Registro alterados com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
			return false;
		} finally {
			conexaoDB.closeConnection();
		}

	}

	public boolean delete(Agendamento agendamento) {
		Connection conexao = conexaoDB.getConnection();
		PreparedStatement stmt = null;
		String sql = "DELETE FROM agendamento where id = ?";

		try {
			stmt = conexao.prepareStatement(sql);

			stmt.setInt(1, agendamento.getCodigo());

			stmt.executeUpdate();
			System.out.println("Registro alterados com sucesso");
			return true;

		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
			return false;
		} finally {
			conexaoDB.closeConnection();
		}

	}

	public ArrayList<Agendamento> selectAll() {
		Connection con = conexaoDB.getConnection();
		PreparedStatement stmt = null;
		ResultSet resultado = null;
		ArrayList<Agendamento> agendamento = new ArrayList<>();

		try {
			stmt = con.prepareStatement("SELECT * FROM agendamento");
			resultado = stmt.executeQuery();

			while (resultado.next()) {

				ResultSet resultServico = null;

				stmt = con.prepareStatement("SELECT * FROM servico WHERE id = ?");
				stmt.setInt(1, resultado.getInt("servico_id"));
				resultServico = stmt.executeQuery();

				Servico servico = new Servico(resultServico.getInt("id"), resultServico.getString("descricao"),
						resultServico.getDouble("valor"), resultServico.getString("categoria"),
						resultServico.getBoolean("status"));

				agendamento.add(new Agendamento(resultado.getInt("id"), servico, resultado.getString("nome_cliente"),
						resultado.getString("telefone_cliente"), resultado.getString("nome_profissional"),
						resultado.getDate("data_servico"), resultado.getTime("hora_servico"),
						resultado.getTimestamp("data_hora_lancamento"),
						resultado.getTimestamp("data_hora_cancelamento")));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
		} finally {
			conexaoDB.closeConnection();
		}
		return agendamento;
	}
}
