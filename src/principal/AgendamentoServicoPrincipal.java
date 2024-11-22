package principal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import conexao.ConexaoDB;
import entidade.Agendamento;
import entidade.Cliente;
import entidade.Servico;
import entidade.dao.AgendamentoDAO;
import entidade.dao.ClienteDAO;
import entidade.dao.ServicoDAO;

public class AgendamentoServicoPrincipal {
	
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		exibirMenuPrincipal();
		
		ConexaoDB.closeConnection();

	}
	
	// MÉTODOS MENU

	public static void exibirMenuPrincipal() {
        while (true) {
            System.out.println("\n======================== MENU PRINCIPAL ========================\n");
            System.out.println("[1] - Serviços");
            System.out.println("[2] - Clientes");
            System.out.println("[3] - Agendamentos");
            System.out.println("[0] - Encerrar");
            System.out.print("Escolha a entidade que deseja manipular: ");
            int opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                	exibirSubmenuServico();
                    break;
                case 2:
                    //exibirSubmenu(clientes);
                    break;
                case 3:
                    //exibirSubmenu(agendamentos);
                    break;
                case 0:
                    System.out.println("\nEncerrando o programa...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
	
	private static void exibirSubmenuServico() {
        while (true) {
        	listarServicosDisponiveis();
        	
            System.out.println("\n================== SUBMENU SERVIÇOS ==================\n");
            System.out.println("[1] - Ativar");
            System.out.println("[2] - Desativar");
            System.out.println("[3] - Visualizar por ID");
            System.out.println("[4] - Visualizar todos");
            System.out.println("[0] - Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                	ativarServico();
                    break;
                case 2:
                	desativarServico();
                    break;
                case 3:
                	visualizarServicoID();
                    break;
                case 4:
                	visualizarServico();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
	
	// MÉTODOS SERVIÇO
	
	public static void listarServicosDisponiveis() {
		ServicoDAO servicoDAO = new ServicoDAO();
		ArrayList<Servico> servicos = servicoDAO.selectView();
		for (Servico serv : servicos) {
			System.out.println(serv.toString());
		}
	}
	
	public static void ativarServico() {
	    ServicoDAO servico = new ServicoDAO();
	    
	    System.out.println("Digite o id do serviço que gostaria de ativar: ");
	    int codigo = sc.nextInt();
	    
	    servico.ativarServico(codigo);
	}
	
	public static void desativarServico() {
	    ServicoDAO servico = new ServicoDAO();
	    
	    System.out.println("Digite o id do serviço que gostaria de desativar: ");
	    int codigo = sc.nextInt();
	    
	    servico.desativarServico(codigo);
	}
	    
	public static void visualizarServicoID() {
	    ServicoDAO servico = new ServicoDAO();
	    
	    System.out.println("Digite o id do serviço que gostaria de visualizar: ");
	    int codigo = sc.nextInt();
	    
	    servico.selectById(codigo);
	    
	}
	
	public static void visualizarServico() {
	    ServicoDAO servico = new ServicoDAO();
	    
	    System.out.println("**** Lista de todos os serviços **** ");
	    
		ArrayList<Servico> servicos = servico.selectAll();
		for (Servico serv : servicos) {
			System.out.println(serv.toString());
		}
	    
	}
	
	// MÉTODOS CLIENTE
	
	public static boolean listarClientes() {
		ClienteDAO clienteDAO = new ClienteDAO();
		ArrayList<Cliente> clientes = clienteDAO.selectAll();
		System.out.println();
		if(clientes.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado\n");
		}else {
			for (Cliente cliente : clientes) {
				System.out.println(cliente.toString()+"\n");
			}
			return true;
		}
		return false;
	}
	
	public static void cadastrarCliente() {
		System.out.print("Informe o nome do cliente: ");
		String nome = sc.nextLine();
		System.out.print("Informe o CPF do cliente: ");
		String cpf = sc.nextLine();
		System.out.print("Informe o endereço do cliente: ");
		String endereco = sc.nextLine();
		System.out.print("Informe o telefone do cliente: ");
		String telefone = sc.nextLine();
		Cliente cliente = new Cliente(nome, cpf, endereco, telefone);
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.insert(cliente);
	}
	
	public static Cliente selecionarClienteOperacao() {
		System.out.print("Informe o código do cliente: ");
		int codigo = sc.nextInt();
		sc.nextLine();
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = clienteDAO.selectById(codigo);
		while(cliente.getCodigo() == 0) {
			System.out.print("Cliente não identificado, informe um código válido: ");
			codigo = sc.nextInt();
			sc.nextLine();
			cliente = clienteDAO.selectById(codigo);
		};
		return cliente;
	}
	
	public static void atualizarCliente() {
		if(listarClientes()) {
			ClienteDAO clienteDAO = new ClienteDAO();
			Cliente cliente = selecionarClienteOperacao();
			System.out.println("[1] - Nome");
			System.out.println("[2] - CPF");
			System.out.println("[3] - Endereço");
			System.out.println("[4] - Telefone");
			System.out.println("[0] - Cancelar");
			System.out.print("Atualizar qual informação: ");
			String atributos[] = {"cancelar", "nome", "cpf", "endereço", "telefone"};
			int opcao = sc.nextInt();
			sc.nextLine();
			if(opcao != 0) {
				while(opcao > 4 || opcao < 0) {
					System.out.print("Informe uma opção válida: ");
					opcao = sc.nextInt();
					sc.nextLine();
				}
				String atributo = atributos[opcao];
				System.out.print("Informe o novo "+atributo+" do cliente: ");
				String valor = sc.nextLine();
				atributo = opcao == 3 ? atributo.replace('ç', 'c') : atributo;
				clienteDAO.update(atributo, valor, cliente.getCodigo());
			}
		}
	}
	
	public static void apagarCliente() {
		if(listarClientes()) {
			ClienteDAO clienteDAO = new ClienteDAO();
			Cliente cliente = selecionarClienteOperacao();
			System.out.print("Deseja realmente apagar o cliente "+cliente.getNome()+"? 1-Sim/0-Não: ");
			if(sc.nextInt() == 1) {
				AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
				ArrayList<Agendamento> agendamentosClienteDia = agendamentoDAO.selectByClienteDia(cliente.getCodigo());
				if(agendamentosClienteDia.isEmpty()) {
					clienteDAO.delete(cliente.getCodigo());
				} else { 
					for (Agendamento agendamento : agendamentosClienteDia) {
						System.out.println(agendamento.toString());
					}
					System.out.print("Existe(m) agendamento(s) para este cliente, apagar mesmo assim? 1-Sim/0-Não");
					if(sc.nextInt() == 1) {
						clienteDAO.delete(cliente.getCodigo());
						for (Agendamento agendamento : agendamentosClienteDia) {
							Timestamp dataHoraAtual = new Timestamp(System.currentTimeMillis());
							agendamentoDAO.updateCancelamento(dataHoraAtual, agendamento.getCodigo());
						}
					}
				}
			}
		}
	}
	
}
