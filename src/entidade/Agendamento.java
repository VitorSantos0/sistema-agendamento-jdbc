package entidade;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Agendamento {
	
	private int codigo;
	private Servico servico;
	private Cliente cliente;
	private String nomeProfissional;
	private Date dataServico;
	private Time horaServico;
	private Timestamp dataHoraLancamento;
	private Timestamp dataHoraCancelamento;
	
	public Agendamento() {}
	
	public Agendamento(int codigo, Servico servico, Cliente cliente, String nomeProfissional,
			Date dataServico, Time horaServico, Timestamp dataHoraLancamento, Timestamp dataHoraCancelamento) {
		this.codigo = codigo;
		this.servico = servico;
		this.cliente = cliente;
		this.nomeProfissional = nomeProfissional;
		this.dataServico = dataServico;
		this.horaServico = horaServico;
		this.dataHoraLancamento = dataHoraLancamento;
		this.dataHoraCancelamento = dataHoraCancelamento;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNomeProfissional() {
		return nomeProfissional;
	}

	public void setNomeProfissional(String nomeProfissional) {
		this.nomeProfissional = nomeProfissional;
	}

	public Date getDataServico() {
		return dataServico;
	}

	public void setDataServico(Date dataServico) {
		this.dataServico = dataServico;
	}

	public Time getHoraServico() {
		return horaServico;
	}

	public void setHoraServico(Time horaServico) {
		this.horaServico = horaServico;
	}

	public Timestamp getDataHoraLancamento() {
		return dataHoraLancamento;
	}

	public void setDataHoraLancamento(Timestamp dataHoraLancamento) {
		this.dataHoraLancamento = dataHoraLancamento;
	}

	public Timestamp getDataHoraCancelamento() {
		return dataHoraCancelamento;
	}

	public void setDataHoraCancelamento(Timestamp dataHoraCancelamento) {
		this.dataHoraCancelamento = dataHoraCancelamento;
	}
	
	@Override
	public String toString() {
		return "Código: "+this.getCodigo()+"| Cliente: "+this.getCliente().getNome()+" | Serviço: "+this.getServico().getDescricao()
				+" | Profissional: "+this.getNomeProfissional()+" | Data/Hora: "+this.getDataServico()+" às "+this.getHoraServico();
	}
	
}
