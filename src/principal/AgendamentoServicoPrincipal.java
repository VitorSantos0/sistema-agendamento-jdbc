package principal;

import java.util.ArrayList;
import java.util.Scanner;

import entidade.Servico;
import entidade.dao.ServicoDAO;

public class AgendamentoServicoPrincipal {

	public static void main(String[] args) {
		
		ServicoDAO servicoDAO = new ServicoDAO();
		
		ArrayList<Servico> servicos = servicoDAO.selectAll();
		
		System.out.println("Serviços disponiveis:");
		
		Scanner sc = new Scanner(System.in);
		
		for (Servico serv : servicos) {
			System.out.println(serv.getDescricao());
		}
		
		sc.close();

	}

}
