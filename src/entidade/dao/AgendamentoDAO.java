package entidade.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
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
		ArrayList<Agendamento> agendamento = new ArrayList<>();
		try {
			ResultSet resultado = this.select(this.ENTIDADE);
			while(resultado.next()) {
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
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return agendamento;
	}
	
	public Agendamento selectById(int identificador) {
		Agendamento agendamento = new Agendamento();
		try {
			ResultSet resultado = this.select(this.ENTIDADE, "id = "+identificador);
			resultado.next();
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
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return agendamento;
	}
	
	public ArrayList<AgendamentoDia> selectByDia() {
		ArrayList<AgendamentoDia> agendamentosDia = new ArrayList<>();
		try {
			String query = "SELECT serv.descricao AS descricao_servico, serv.valor AS valor_servico,"
					+"agd.data_servico, agd.hora_servico, agd.nome_profissional, cli.nome AS nome_cliente "
					+"FROM agendamento agd INNER JOIN servico serv ON agd.servico_id = serv.id "
					+"INNER JOIN cliente cli ON agd.cliente_id = cli.id WHERE agd.data_servico = CURRENT_DATE";
			ResultSet resultado = this.selectQuery(query);
			while(resultado.next()) {
				AgendamentoDia agendamentoDia = new AgendamentoDia(resultado.getString("descricao_servico"), 
						resultado.getDouble("valor_servico"), resultado.getDate("data_servico"), 
						resultado.getTime("hora_servico"), resultado.getString("nome_profissional"), 
						resultado.getString("nome_cliente"));
				agendamentosDia.add(agendamentoDia);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return agendamentosDia;
	}
	
	public ArrayList<Agendamento> selectByClienteDia(int identificador) { 
		ArrayList<Agendamento> agendamentosClienteDia = new ArrayList<>();
		String condicao = "WHERE cliente_id = "+identificador+" AND data_servico >= CURRENT_DATE"
				+ " AND hora_servico >= CURRENT_TIME";
		try {
			ResultSet resultado = this.select(this.ENTIDADE, condicao);
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
				agendamentosClienteDia.add(agendamento);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
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
	
	public boolean updateCancelamento(Timestamp dataHoraCancelamento, int identificador) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put("data_hora_cancelamento", String.valueOf(dataHoraCancelamento));
		return this.update(this.ENTIDADE, dados, identificador);
	}

	public boolean update(String chave, String valor, int identificador) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put(chave, valor);
		return this.update(this.ENTIDADE, dados, identificador);
	}
	
	public boolean update(String chave, Date dataServico, int identificador) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put(chave, String.valueOf(dataServico));
		return this.update(this.ENTIDADE, dados, identificador);
	}
	
	public boolean update(String chave, Time horaServico, int identificador) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put(chave, String.valueOf(horaServico));
		return this.update(this.ENTIDADE, dados, identificador);
	}
	
	public int getQuantidadeAgendamentoDia() {
		try {
			ResultSet resultado = this.selectQuery("SELECT quantidade_agendamentos_dia()");
			resultado.next();
			return resultado.getInt("quantidade_agendamentos_dia");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return 0;
	}
	
	public int countDataHora(Date dataServico, Time horaServico) {
		String condicao = " data_servico = '"+dataServico+"' AND hora_servico = '"+horaServico+"'";
		return this.count(this.ENTIDADE, condicao);
	}
	
}
