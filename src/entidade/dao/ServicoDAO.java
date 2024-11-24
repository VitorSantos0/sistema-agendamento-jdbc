package entidade.dao;

import entidade.Servico;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ServicoDAO extends DAO {
	
	private final String ENTIDADE = "servico";
	
	public ArrayList<Servico> select() {
		ArrayList<Servico> servicos = new ArrayList<>();
		try {
			ResultSet resultado = this.select(this.ENTIDADE);
			while(resultado.next()) {
				servicos.add(new Servico(resultado.getInt("id"), resultado.getString("descricao"),
						resultado.getDouble("valor"), resultado.getString("categoria"),
						resultado.getBoolean("ativo")));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return servicos;
	}
	
	public Servico selectById(int identificador) {
		Servico servico = new Servico();
		try {
			ResultSet resultado = this.select(this.ENTIDADE, "id = "+identificador);
			resultado.next();
			servico.setCodigo(resultado.getInt("id"));
			servico.setDescricao(resultado.getString("descricao"));
			servico.setCategoria(resultado.getString("categoria"));
			servico.setAtivo(resultado.getBoolean("ativo"));
			servico.setValor(resultado.getDouble("valor"));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return servico;
	}
	
	public boolean updateAtivo(boolean ativo, int identificador) { 
		Map<String, String> dados = new LinkedHashMap<String, String>();
		Servico servico = this.selectById(identificador);
		if(servico.isAtivo() != ativo) {
			dados.put("ativo", ativo ? "1" : "0");
			return this.update(this.ENTIDADE, dados, identificador);
		}
		System.out.println("Serviço ja esta "+(ativo ? "ativado" : "desativado")+"\n");
		return false;
	}
	
	public ArrayList<Servico> selectAtivosView() { 
		ArrayList<Servico> servicos = new ArrayList<>();
		try {
			ResultSet resultado = this.select("servicos_ativos_view");
			while(resultado.next()) {
				servicos.add(new Servico(resultado.getInt("id"), resultado.getString("descricao"),
						resultado.getDouble("valor"), resultado.getString("categoria"),
						resultado.getBoolean("ativo")));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return servicos;
	}
	
	public int countAtivos() {
		return this.count(this.ENTIDADE, " ativo = 1");
	}
	
}
