package entidade;

public class Cliente {

	private int codigo;
	private String nome;
	private String cpf;
	private String endereco;
	private String telefone;
	
	public Cliente() {}

	public Cliente(int codigo, String nome, String cpf, String endereco, String telefone) {
		this.codigo = codigo;
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.telefone = telefone;
	}
	
	public Cliente(String nome, String cpf, String endereco, String telefone) {
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public String toString() {
		return "Código: "+this.getCodigo()+" | Nome: "+this.getNome()+" | CPF: "+this.getCpf()+
				" | Endereço: "+this.getEndereco()+" | Telefone: "+this.getTelefone();
	}
		
}
