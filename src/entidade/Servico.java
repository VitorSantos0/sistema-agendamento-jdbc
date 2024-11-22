package entidade;

public class Servico {

	private int codigo;
	private String descricao;
	private double valor;
	private String categoria;
	private boolean ativo;
	
	public Servico() {}
	
	public Servico(int codigo,String descricao, double valor, String categoria, boolean ativo) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.valor = valor;
		this.categoria = categoria;
		this.ativo = ativo;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		if(valor > 0)
			this.valor = valor;
		else 
			System.out.println("O valor deve ser maior que 0");
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public String toString() {
		return "Código: "+this.getCodigo()+" | Serviço: "+this.getDescricao().toUpperCase()+" | Valor: R$"+this.getValor();
	}
	
}
