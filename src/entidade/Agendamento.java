package entidade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Agendamento {
	
	private int codigo;
	private Servico servico;
	private Cliente cliente;
	private String nomeProfissional;
	private LocalDate dataServico;
	private LocalTime horaServico;
	private LocalDateTime dataHoraLancamento;
	private LocalDateTime dataHoraCancelamento;
	
	public Agendamento() {}
	
	public Agendamento(Servico servico, Cliente cliente, String nomeProfissional, LocalDate dataServico, LocalTime horaServico) {
		this.servico = servico;
		this.cliente = cliente;
		this.nomeProfissional = nomeProfissional;
		this.dataServico = dataServico;
		this.horaServico = horaServico;
	}
	
	public Agendamento(int codigo, Servico servico, Cliente cliente, String nomeProfissional,
			LocalDate dataServico, LocalTime horaServico, LocalDateTime dataHoraLancamento) {
		this.codigo = codigo;
		this.servico = servico;
		this.cliente = cliente;
		this.nomeProfissional = nomeProfissional;
		this.dataServico = dataServico;
		this.horaServico = horaServico;
		this.dataHoraLancamento = dataHoraLancamento;
	}
	
	public Agendamento(int codigo, Servico servico, Cliente cliente, String nomeProfissional,
			LocalDate dataServico, LocalTime horaServico, LocalDateTime dataHoraLancamento, LocalDateTime dataHoraCancelamento) {
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

	public LocalDate getDataServico() {
		return dataServico;
	}

	public void setDataServico(LocalDate dataServico) {
		this.dataServico = dataServico;
	}

	public LocalTime getHoraServico() {
		return horaServico;
	}

	public void setHoraServico(LocalTime dataServico) {
		this.horaServico = dataServico;
	}

	public LocalDateTime getDataHoraLancamento() {
		return dataHoraLancamento;
	}

	public void setDataHoraLancamento(LocalDateTime dataHoraLancamento) {
		this.dataHoraLancamento = dataHoraLancamento;
	}

	public LocalDateTime getDataHoraCancelamento() {
		return dataHoraCancelamento;
	}

	public void setDataHoraCancelamento(LocalDateTime dataHoraCancelamento) {
		this.dataHoraCancelamento = dataHoraCancelamento;
	}
	
	@Override
	public String toString() {
		String cancelamento = this.getDataHoraCancelamento() != null ?  
				" | Cancelamento: "+this.getDataHoraCancelamento()
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "";
		return "Código: "+this.getCodigo()
				+" | Cliente: "+this.getCliente().getNome()
				+" | Serviço: "+this.getServico().getDescricao().toUpperCase()
				+" | Profissional: "+this.getNomeProfissional()
				+" | Data: "+this.getDataServico().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				+" | Hora: "+this.getHoraServico().format(DateTimeFormatter.ofPattern("HH:mm"))
				+cancelamento;
	}
	
}
