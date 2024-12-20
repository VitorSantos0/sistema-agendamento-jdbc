package entidade.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import entidade.Agendamento;
import entidade.AgendamentoDia;
import entidade.Cliente;
import entidade.Servico;

public class AgendamentoDAO extends DAO{
	
	private final String ENTIDADE = "agendamento";
	
	public ArrayList<Agendamento> select() {
		ArrayList<Agendamento> agendamentos = new ArrayList<>();
		try {
			ResultSet resultado = this.select(this.ENTIDADE);
			while(resultado.next()) {
				ServicoDAO servicoDAO = new ServicoDAO();
				Servico servico =  servicoDAO.selectById(resultado.getInt("servico_id"));
				ClienteDAO clienteDAO = new ClienteDAO();
				Cliente cliente = clienteDAO.selectById(resultado.getInt("cliente_id"));
				Agendamento agendamento = new Agendamento(resultado.getInt("id"), servico, cliente,
						resultado.getString("nome_profissional"),
						resultado.getDate("data_servico").toLocalDate(), 
						resultado.getTime("hora_servico").toLocalTime(),
						resultado.getTimestamp("data_hora_lancamento").toLocalDateTime());
				if(resultado.getTimestamp("data_hora_cancelamento") != null) {
					agendamento.setDataHoraCancelamento(resultado.getTimestamp("data_hora_cancelamento").toLocalDateTime());
				}
				agendamentos.add(agendamento);
			}
		} catch (SQLException e) {
			System.out.println("Não foi possível acessar os dados: " + e);
		}
		return agendamentos;
	}
	
	public Agendamento selectById(int identificador) {
		Agendamento agendamento = new Agendamento();
		try {
			ResultSet resultado = this.select(this.ENTIDADE, "id = "+identificador);
			if(resultado.next()) {
				ServicoDAO servicoDAO = new ServicoDAO();
				Servico servico =  servicoDAO.selectById(resultado.getInt("servico_id"));
				ClienteDAO clienteDAO = new ClienteDAO();
				Cliente cliente = clienteDAO.selectById(resultado.getInt("cliente_id"));
				agendamento.setCodigo(resultado.getInt("id"));
				agendamento.setServico(servico);
				agendamento.setCliente(cliente);
				agendamento.setNomeProfissional(resultado.getString("nome_profissional"));
				agendamento.setDataServico(resultado.getDate("data_servico").toLocalDate());
				agendamento.setHoraServico(resultado.getTime("hora_servico").toLocalTime());
				agendamento.setDataHoraLancamento(resultado.getTimestamp("data_hora_lancamento").toLocalDateTime());
				if(resultado.getTimestamp("data_hora_cancelamento") != null) {					
					agendamento.setDataHoraCancelamento(resultado.getTimestamp("data_hora_cancelamento").toLocalDateTime());
				}
			}
		} catch (SQLException e) {
			System.out.println("Não foi possível acessar os dados: " + e);
		}
		return agendamento;
	}
	
	public ArrayList<AgendamentoDia> selectByDia() {
		ArrayList<AgendamentoDia> agendamentosDia = new ArrayList<>();
		try {
			String query = "SELECT serv.descricao AS descricao_servico, serv.valor AS valor_servico,"
					+"agd.data_servico, agd.hora_servico, agd.nome_profissional, cli.nome AS nome_cliente "
					+"FROM agendamento agd INNER JOIN servico serv ON agd.servico_id = serv.id "
					+"INNER JOIN cliente cli ON agd.cliente_id = cli.id WHERE agd.data_servico = CURRENT_DATE "
					+ "AND agd.data_hora_cancelamento IS NULL";
			ResultSet resultado = this.selectQuery(query);
			while(resultado.next()) {
				AgendamentoDia agendamentoDia = new AgendamentoDia(resultado.getString("descricao_servico"), 
						resultado.getDouble("valor_servico"), resultado.getDate("data_servico").toLocalDate(), 
						resultado.getTime("hora_servico").toLocalTime(), resultado.getString("nome_profissional"), 
						resultado.getString("nome_cliente"));
				agendamentosDia.add(agendamentoDia);
			}
		} catch (SQLException e) {
			System.out.println("Não foi possível acessar os dados: " + e);
		}
		return agendamentosDia;
	}
	
	public ArrayList<Agendamento> selectByClienteDia(int identificador) { 
		ArrayList<Agendamento> agendamentosClienteDia = new ArrayList<>();
		String condicao = "cliente_id = "+identificador+" AND data_servico >= CURRENT_DATE"
				+ " AND hora_servico >= CURRENT_TIME AND data_hora_cancelamento IS NULL";
		try {
			ResultSet resultado = this.select(this.ENTIDADE, condicao);
			while (resultado.next()) { 
				ServicoDAO servicoDAO = new ServicoDAO();
				Servico servico =  servicoDAO.selectById(resultado.getInt("servico_id"));
				ClienteDAO clienteDAO = new ClienteDAO();
				Cliente cliente = clienteDAO.selectById(resultado.getInt("cliente_id"));
				Agendamento agendamento = new Agendamento(resultado.getInt("id"), servico, cliente,
						resultado.getString("nome_profissional"),
						resultado.getDate("data_servico").toLocalDate(), 
						resultado.getTime("hora_servico").toLocalTime(),
						resultado.getTimestamp("data_hora_lancamento").toLocalDateTime());
				if(resultado.getTimestamp("data_hora_cancelamento") != null) {
					agendamento.setDataHoraCancelamento(resultado.getTimestamp("data_hora_cancelamento").toLocalDateTime());
				}
				agendamentosClienteDia.add(agendamento);
			}
		} catch (SQLException e) {
			System.out.println("Não foi possível acessar os dados: " + e);
		}
		return agendamentosClienteDia;
	}
	
	public boolean insert(Agendamento agendamento) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put("servico_id", String.valueOf(agendamento.getServico().getCodigo()));
		dados.put("cliente_id", String.valueOf(agendamento.getCliente().getCodigo()));
		dados.put("nome_profissional", agendamento.getNomeProfissional());
		dados.put("data_servico", String.valueOf(agendamento.getDataServico()));
		dados.put("hora_servico", String.valueOf(agendamento.getHoraServico()));
		return this.insert(this.ENTIDADE, dados);
	}
	
	public boolean updateCancelamento(LocalDateTime dataHoraCancelamento, int identificador) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put("data_hora_cancelamento", String.valueOf(dataHoraCancelamento));
		return this.update(this.ENTIDADE, dados, identificador);
	}

	public boolean update(String chave, String valor, int identificador) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put(chave, valor);
		return this.update(this.ENTIDADE, dados, identificador);
	}
	
	public boolean update(String chave, LocalDate dataServico, int identificador) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put(chave, String.valueOf(dataServico));
		return this.update(this.ENTIDADE, dados, identificador);
	}
	
	public boolean update(String chave, LocalTime horaServico, int identificador) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put(chave, String.valueOf(horaServico));
		return this.update(this.ENTIDADE, dados, identificador);
	}
	
	public int getQuantidadeAgendamentoDia() {
		try {
			ResultSet resultado = this.selectQuery("SELECT quantidade_agendamentos_dia()");
			if(resultado.next()) {
				return resultado.getInt("quantidade_agendamentos_dia");
			}
			return 0;
		} catch (SQLException e) {
			System.out.println("Não foi possível acessar os dados: " + e);
		}
		return 0;
	}
	
	public int countDataHora(LocalDate dataServico, LocalTime horaServico) {
		String condicao = "data_servico = '"+dataServico+"' AND hora_servico = '"+horaServico+"' AND data_hora_cancelamento IS NULL";
		return this.count(this.ENTIDADE, condicao);
	}
	
}
