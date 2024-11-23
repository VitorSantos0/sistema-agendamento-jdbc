package entidade.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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

	public boolean insert(Agendamento agendamento) {
		String sql = "INSERT INTO agendamento (servico_id, cliente_id, nome_profissional," 
				+ "data_servico, hora_servico) VALUES (?,?,?,?,?)";

		try {
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, agendamento.getServico().getCodigo());
			stmt.setInt(2, agendamento.getCliente().getCodigo());
			stmt.setString(3, agendamento.getNomeProfissional());
			stmt.setDate(4, agendamento.getDataServico());
			stmt.setTime(5, agendamento.getHoraServico());

			String executedSQL = sql.replaceFirst("\\?", String.valueOf(agendamento.getServico().getCodigo()))
	                .replaceFirst("\\?", String.valueOf(agendamento.getCliente().getCodigo()))
					.replaceFirst("\\?", "'"+agendamento.getNomeProfissional()+"'")
					.replaceFirst("\\?", "'"+agendamento.getDataServico()+"'")
					.replaceFirst("\\?", "'"+agendamento.getHoraServico()+"'");
			
			LogSql.exibirComandoSql(executedSQL);
			
			stmt.executeUpdate();

			System.out.println("Registro salvo com sucesso");
			return true;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
			return false;
		}

	}
	
	public boolean updateCancelamento(Timestamp dataHoraCancelamento, int codigo) {
		String query = "UPDATE agendamento SET data_hora_cancelamento = '"+dataHoraCancelamento+"' WHERE id = "+codigo;
		LogSql.exibirComandoSql(query);
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

	public ArrayList<Agendamento> selectAll() {
		ResultSet resultado = null;
		ArrayList<Agendamento> agendamento = new ArrayList<>();

		try {
			stmt = conn.prepareStatement("SELECT * FROM agendamento");
			resultado = stmt.executeQuery();

			while (resultado.next()) {

				ServicoDAO servicoDAO = new ServicoDAO();
				Servico servico =  servicoDAO.selectById(resultado.getInt("servico_id"));
				
				ClienteDAO clienteDAO = new ClienteDAO();
				Cliente cliente = clienteDAO.selectById(resultado.getInt("cliente_id"));

				agendamento.add(new Agendamento(resultado.getInt("id"), servico, cliente,
						resultado.getString("nome_profissional"),
						resultado.getDate("data_servico"), resultado.getTime("hora_servico"),
						resultado.getTimestamp("data_hora_lancamento"),
						resultado.getTimestamp("data_hora_cancelamento")));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
		}
		return agendamento;
	}
	
	public ArrayList<AgendamentoDia> selectAgendamentosDia() {
		String sql = "SELECT serv.descricao AS descricao_servico, serv.valor AS valor_servico,"
				+"agd.data_servico, agd.hora_servico, agd.nome_profissional, cli.nome AS nome_cliente "
				+"FROM agendamento agd INNER JOIN servico serv ON agd.servico_id = serv.id "
				+"INNER JOIN cliente cli ON agd.cliente_id = cli.id WHERE agd.data_servico = CURRENT_DATE";
		ResultSet resultSet = null;
		ArrayList<AgendamentoDia> agendamento = new ArrayList<>();

		try {
			stmt = conn.prepareStatement(sql);
			resultSet = stmt.executeQuery();
			LogSql.exibirComandoSql(sql);
			while (resultSet.next()) {
				AgendamentoDia agendamentoDia = new AgendamentoDia(resultSet.getString("descricao_servico"), 
						resultSet.getDouble("valor_servico"), resultSet.getDate("data_servico"), 
						resultSet.getTime("hora_servico"), resultSet.getString("nome_profissional"), 
						resultSet.getString("nome_cliente"));
				agendamento.add(agendamentoDia);
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
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
			System.out.println("Não foi possível executar sql: " + ex);
		}
		return agendamentos;
	}
	
	public int getQuantidadeAgendamentoDia() {
		ResultSet resultado = null;
		String query = "SELECT quantidade_agendamentos_dia()";
		LogSql.exibirComandoSql(query);
		try {
			stmt = conn.prepareStatement(query);
			resultado = stmt.executeQuery();
			resultado.next();
			return resultado.getInt("quantidade_agendamentos_dia");
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
		}
		return 0;
	}
	
	public boolean update(String chave, String valor, int codigo) {
		try {
			String sql = "UPDATE agendamento SET "+chave+" = '"+valor+"' WHERE id = "+codigo;
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
	
	public boolean updateData(String chave, Date dataServico, int codigo) {
		try {
			String sql = "UPDATE agendamento SET "+chave+" = '"+dataServico+"' WHERE id = "+codigo;
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
	
	public boolean updateHora(String chave, Time horaServico, int codigo) {
		try {
			String sql = "UPDATE agendamento SET "+chave+" = '"+horaServico+"' WHERE id = "+codigo;
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
	
	public Agendamento selectById(int id) {
		ResultSet resultado = null;
		Agendamento agendamento = new Agendamento();
		try {
			String query = "SELECT * FROM agendamento WHERE id = "+id;
			LogSql.exibirComandoSql(query);
			stmt = conn.prepareStatement(query);
			resultado = stmt.executeQuery();
			if(resultado.next()) {
				ServicoDAO servicoDAO = new ServicoDAO();
				Servico servico =  servicoDAO.selectById(resultado.getInt("servico_id"));
				
				ClienteDAO clienteDAO = new ClienteDAO();
				Cliente cliente = clienteDAO.selectById(resultado.getInt("cliente_id"));
			
				agendamento.setCodigo(resultado.getInt("id"));
				agendamento.setServico(servico);
				agendamento.setCliente(cliente);
				agendamento.setNomeProfissional(resultado.getString("nome_profissional"));
				agendamento.setDataServico(resultado.getDate("data_servico"));
				agendamento.setHoraServico(resultado.getTime("hora_servico"));
				agendamento.setDataHoraLancamento(resultado.getTimestamp("data_hora_lancamento"));
				agendamento.setDataHoraCancelamento(resultado.getTimestamp("data_hora_cancelamento"));
				
				agendamento = new Agendamento(resultado.getInt("id"), servico, cliente,
						resultado.getString("nome_profissional"),
						resultado.getDate("data_servico"), 
						resultado.getTime("hora_servico"),
						resultado.getTimestamp("data_hora_lancamento"),
						resultado.getTimestamp("data_hora_cancelamento"));
			}
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
		}
		return agendamento;
	}
	
	public boolean selectByDataHora(Date dataServico, Time horaServico) {
		ResultSet resultado = null;
		String query = "SELECT count(id) FROM agendamento WHERE data_servico = '"+dataServico
				+"' AND hora_servico = '"+horaServico+"'";
		LogSql.exibirComandoSql(query);
		try {
			stmt = conn.prepareStatement(query);
			resultado = stmt.executeQuery();
			resultado.next();
			return resultado.getInt("count") > 0;
		} catch (SQLException ex) {
			System.out.println("Não foi possível executar sql: " + ex);
		}
		return false;
	}
	
}
