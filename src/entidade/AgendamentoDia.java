package entidade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AgendamentoDia {
	
	private String descricao_servico;
	private double valor_servico;
	private LocalDate data_servico;
	private LocalTime hora_servico;
	private String nome_profissional;
	private String nome_cliente;
	
	public AgendamentoDia(String descricao_servico, double valor_servico, LocalDate data_servico, LocalTime hora_servico,
			String nome_profissional, String nome_cliente) {
		this.descricao_servico = descricao_servico;
		this.valor_servico = valor_servico;
		this.data_servico = data_servico;
		this.hora_servico = hora_servico;
		this.nome_profissional = nome_profissional;
		this.nome_cliente = nome_cliente;
	}

	public String getDescricao_servico() {
		return descricao_servico;
	}
	
	public void setDescricao_servico(String descricao_servico) {
		this.descricao_servico = descricao_servico;
	}
	
	public double getValor_servico() {
		return valor_servico;
	}
	
	public void setValor_servico(double valor_servico) {
		this.valor_servico = valor_servico;
	}
	
	public LocalDate getData_servico() {
		return data_servico;
	}
	
	public void setData_servico(LocalDate data_servico) {
		this.data_servico = data_servico;
	}
	
	public LocalTime getHora_servico() {
		return hora_servico;
	}
	
	public void setHora_servico(LocalTime hora_servico) {
		this.hora_servico = hora_servico;
	}
	
	public String getNome_profissional() {
		return nome_profissional;
	}
	
	public void setNome_profissional(String nome_profissional) {
		this.nome_profissional = nome_profissional;
	}
	
	public String getNome_cliente() {
		return nome_cliente;
	}
	
	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}
	
	@Override
	public String toString() {
		return "Serviço: "+this.getDescricao_servico().toUpperCase()
		+" | Valor: "+this.getValor_servico()
		+" | Cliente: "+this.getNome_cliente()
		+" | Profissional: "+this.getNome_profissional()
		+" | Data: "+this.getData_servico().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
		+" | Hora: "+this.getHora_servico().format(DateTimeFormatter.ofPattern("HH:mm"));
	}

}
