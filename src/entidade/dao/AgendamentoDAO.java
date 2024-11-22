package entidade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import conexao.ConexaoDB;
import entidade.Agendamento;
import entidade.AgendamentoDia;
import entidade.Cliente;
import entidade.Servico;

public class AgendamentoDAO {
	
	private Connection conn;
	private PreparedStatement stmt = null;
	
	public AgendamentoDAO() {
		this.conn = ConexaoDB.getConnection();
	}

	public boolean insert(Agendamento agendamento, Servico servico, Cliente cliente) {
		String sql = "INSERT INTO agendamento (servico_id," + "cliente_id,"
				+ "nome_profissional," + "data_servico," + "hora_servico," + "data_hora_lancamento,"
				+ "data_hora_cancelamento)" + "values (?,?,?,?,?,?,?,?)";

		try {
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, servico.getCodigo());
			stmt.setInt(2, cliente.getCodigo());
			stmt.setString(3, agendamento.getNomeProfissional());
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
		}

	}
	
	public boolean updateCancelamento(Timestamp dataHoraCancelamento, int codigo) {
		String query = "UPDATE agendamento SET data_hora_cancelamento = "+dataHoraCancelamento+" WHERE id = "+codigo;
		LogSql.exibirSql(query);
		try {
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			System.out.println("Registro cancelado com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
			return false;
		}

	}

	public boolean updateAll(Agendamento agendamento, int servico_id, int cliente_id) {
		String sql = "UPDATE agendamento SET servico_id = ?," + "cliente_id = ?,"
				+ "nome_profissional = ?," + "data_servico = ?" + "hora_servico = ?" + "data_hora_lancamento = ?"
				+ "data_hora_cancelamento = ?" + "where servico_id = ?";

		try {
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, servico_id);
			stmt.setInt(2, cliente_id);
			stmt.setString(3, agendamento.getNomeProfissional());
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
		}

	}

	public boolean delete(Agendamento agendamento) {
		String sql = "DELETE FROM agendamento where id = ?";

		try {
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, agendamento.getCodigo());

			stmt.executeUpdate();
			System.out.println("Registro alterados com sucesso");
			return true;

		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
			return false;
		}
	}

	public ArrayList<Agendamento> selectAll() {
		ResultSet resultado = null;
		ArrayList<Agendamento> agendamento = new ArrayList<>();

		try {
			stmt = conn.prepareStatement("SELECT * FROM agendamento");
			resultado = stmt.executeQuery();

			while (resultado.next()) {

				ResultSet resultSet = null;

				stmt = conn.prepareStatement("SELECT * FROM servico WHERE id = ?");
				stmt.setInt(1, resultado.getInt("servico_id"));
				resultSet = stmt.executeQuery();

				Servico servico = new Servico(resultSet.getInt("id"), resultSet.getString("descricao"),
						resultSet.getDouble("valor"), resultSet.getString("categoria"),
						resultSet.getBoolean("status"));
				

				stmt = conn.prepareStatement("SELECT * FROM cliente WHERE id = ?");
				stmt.setInt(1, resultado.getInt("cliente_id"));
				resultSet = stmt.executeQuery();

				Cliente cliente = new Cliente(resultSet.getInt("id"), resultSet.getString("nome"),
						resultSet.getString("cpf"), resultSet.getString("endereco"),
						resultSet.getString("telefone"));
				

				agendamento.add(new Agendamento(resultado.getInt("id"), servico, cliente,
						resultado.getString("nome_profissional"),
						resultado.getDate("data_servico"), resultado.getTime("hora_servico"),
						resultado.getTimestamp("data_hora_lancamento"),
						resultado.getTimestamp("data_hora_cancelamento")));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
		}
		return agendamento;
	}
	
	public ArrayList<AgendamentoDia> selectAgendamentosDia() {
		String sql = "SELECT serv.descricao AS descricao_servico, serv.valor AS valor_servico,"
				+"agd.data_servico, agd.hora_servico, agd.nome_profissional, cli.nome AS nome_cliente"
				+"FROM agendamento agd INNER JOIN servico serv ON agd.servico_id = serv.id"
				+"INNER JOIN cliente cli ON agd.cliente_id = cli.id WHERE agd.data_servico = CURRENT_DATE";
		ResultSet resultSet = null;
		ArrayList<AgendamentoDia> agendamento = new ArrayList<>();

		try {
			stmt = conn.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				AgendamentoDia agendamentoDia = new AgendamentoDia(resultSet.getString("descricao_servico"), 
						resultSet.getDouble("valor_servico"), resultSet.getDate("data_servico"), 
						resultSet.getTime("hora_servico"), resultSet.getString("nome_profissional"), 
						resultSet.getString("nome_cliente"));
				agendamento.add(agendamentoDia);
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
		}
		return agendamento;
	}
	
	public ArrayList<Agendamento> selectByClienteDia(int clienteId) {
		ResultSet resultado = null;
		ArrayList<Agendamento> agendamentos = new ArrayList<>();
		String query = "SELECT * FROM agendamento "
				+ "WHERE cliente_id = "+clienteId+" AND data_servico >= CURRENT_DATE"
				+ " AND hora_servico >= CURRENT_TIME";
		try {
			stmt = conn.prepareStatement(query);
			resultado = stmt.executeQuery();
			while (resultado.next()) { 
				
				ServicoDAO servicoDAO = new ServicoDAO();
				Servico servico =  servicoDAO.selectById(resultado.getInt("servico_id"));
				
				ClienteDAO clienteDAO = new ClienteDAO();
				Cliente cliente = clienteDAO.selectById(resultado.getInt("cliente_id"));
			
				Agendamento agendamento = new Agendamento(resultado.getInt("id"), servico, cliente,
						resultado.getString("nome_profissional"),
						resultado.getDate("data_servico"), 
						resultado.getTime("hora_servico"),
						resultado.getTimestamp("data_hora_lancamento"),
						resultado.getTimestamp("data_hora_cancelamento"));
				agendamentos.add(agendamento);
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar " + ex);
		}
		return agendamentos;
	}
	
}
