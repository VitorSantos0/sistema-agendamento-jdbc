package entidade.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import entidade.Cliente;

public class ClienteDAO extends DAO {

	private final String ENTIDADE = "cliente";
	
	public ArrayList<Cliente> select() {
		ArrayList<Cliente> clientes = new ArrayList<>();
		try {
			ResultSet resultado = this.select(this.ENTIDADE);
			while(resultado.next()) {
				clientes.add(new Cliente(resultado.getInt("id"), resultado.getString("nome"),
						resultado.getString("cpf"), resultado.getString("endereco"),
						resultado.getString("telefone")));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return clientes;
	}
	
	public Cliente selectById(int identificador) { 
		Cliente cliente = new Cliente();
		try {
			ResultSet resultado = this.select(this.ENTIDADE, "id = "+identificador);
			resultado.next();
			cliente.setCodigo(resultado.getInt("id"));
			cliente.setNome(resultado.getString("nome"));
			cliente.setCpf(resultado.getString("cpf"));
			cliente.setEndereco(resultado.getString("endereco"));
			cliente.setTelefone(resultado.getString("telefone"));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return cliente;
	}
	
	public boolean insert(Cliente cliente) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put("nome", cliente.getNome());
		dados.put("cpf", cliente.getCpf());
		dados.put("endereco", cliente.getEndereco());
		dados.put("telefone", cliente.getTelefone());
		return this.insert(this.ENTIDADE, dados);
	}

	public boolean update(String chave, String valor, int identificador) {
		Map<String, String> dados = new LinkedHashMap<String, String>();
		dados.put(chave, valor);
		return this.update(this.ENTIDADE, dados, identificador);
	}
	
	public boolean delete(int identificador) {
		return this.delete(this.ENTIDADE, identificador);
	}
	
	public int count() {
		return this.count(this.ENTIDADE);
	}

}
