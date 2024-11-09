package principal;

import java.util.ArrayList;
import java.util.Scanner;

import conexao.ConexaoDB;
import entidade.Cliente;
import entidade.Servico;
import entidade.dao.ClienteDAO;
import entidade.dao.ServicoDAO;

public class AgendamentoServicoPrincipal {

	public static void main(String[] args) {
		
		ClienteDAO clienteDAO = new ClienteDAO();
		
		Cliente cliente = new Cliente("Yuri", "00000000000", "Rua 0", "40028922"); 
		
		clienteDAO.insert(cliente);
		
		ConexaoDB.closeConnection();

	}

	
	public static void servicosDisponiveis() {
		ServicoDAO servicoDAO = new ServicoDAO();
		ArrayList<Servico> servicos = servicoDAO.selectView();
		for (Servico serv : servicos) {
			System.out.println(serv.getDescricao());
		}
	}
}
