package principal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import entidade.Agendamento;
import entidade.AgendamentoDia;
import entidade.Cliente;
import entidade.Servico;
import entidade.dao.AgendamentoDAO;
import entidade.dao.ClienteDAO;
import entidade.dao.LogSql;
import entidade.dao.ServicoDAO;

public class AgendamentoServicoPrincipal {
	
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		exibirMenuPrincipal();

	}
	
	// MÉTODOS MENU

	public static void exibirMenuPrincipal() {
        while (true) {
            System.out.println("\n======================== MENU PRINCIPAL ========================\n");
            System.out.println("[1] - Serviço");
            System.out.println("[2] - Cliente");
            System.out.println("[3] - Agendamento");
            System.out.println("[0] - Encerrar");
            System.out.print("Escolha a entidade que deseja manipular: ");
            String opcao = sc.nextLine();
            if(!opcao.equals("0")) {
            	 System.out.print("Digite 1 para exibir os comandos SQL executados: ");
                 LogSql.exibicao(sc.nextLine().equals("1"));
            }
            switch (opcao) {
                case "1":
                	exibirSubmenuServico();
                    break;
                case "2":
                    exibirSubmenuCliente();
                    break;
                case "3":
                    exibirSubmenuAgendamento();
                    break;
                case "0":
                    System.out.println("\nPrograma encerrado");
                    sc.close();
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }
	
	private static void exibirSubmenuServico() {
        while (true) {
            System.out.println("\n================== SUBMENU SERVIÇO ==================\n");
            exibirQuantidadeServicosAtivos();
            System.out.println("[1] - Desativar Serviço");
            System.out.println("[2] - Visualizar Todos");
            System.out.println("[0] - Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = sc.nextLine();
    		System.out.println();
            switch (opcao) {
                case "1":
                	desativarServico();
                    break;
                case "2":
                	visualizarTodosServico();
                	exibirMenuAtivarServico();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }
	
	public static void exibirSubmenuCliente() {
		while (true) {
            System.out.println("\n================== SUBMENU CLIENTE ==================\n");
            exibirQuantidadeClientes();
            System.out.println("[1] - Novo");
            System.out.println("[2] - Editar");
            System.out.println("[3] - Excluir");
            System.out.println("[0] - Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = sc.nextLine();
    		System.out.println();
            switch (opcao) {
                case "1":
                	cadastrarCliente();
                    break;
                case "2":
                	atualizarCliente();
                    break;
                case "3":
                	apagarCliente();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
	}
	
	public static void exibirSubmenuAgendamento() {
		while (true) {
            System.out.println("\n================== SUBMENU AGENDAMENTO ==================\n");
            exibirQuantidadeAgendamentosDia();
            System.out.println("[1] - Cadastrar");
            System.out.println("[2] - Editar");
            System.out.println("[3] - Cancelar");
            System.out.println("[4] - Exibir todos os agendamentos");
            System.out.println("[0] - Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = sc.nextLine();
    		System.out.println();
            switch (opcao) {
                case "1":
                	cadastrarAgendamento();
                    break;
                case "2":
                	editarAgendamento();
                    break;
                case "3":
                	cancelarAgendamento();
                    break;
                case "4":
                	exibirTodosAgendamentos();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
	}
	
	// MÉTODOS AGENDAMENTO 
	
	public static void listarAgendamentosDia() {
		AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
		ArrayList<AgendamentoDia> agendamentos = agendamentoDAO.selectByDia();
		for(AgendamentoDia agenda : agendamentos) {
			System.out.println(agenda.toString()+"\n");
		}
	}
	
	public static void exibirQuantidadeAgendamentosDia() {
		AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
		int qtdSerivosDia = agendamentoDAO.getQuantidadeAgendamentoDia();
		if(qtdSerivosDia > 0) {
			listarAgendamentosDia();
		}
		System.out.println(qtdSerivosDia+" Agendamento(s) encontrado(s) para hoje\n");
	}
	
	public static void cadastrarAgendamento() {
		listarServicosDisponiveis();
		System.out.print("Informe o código do serviço: ");
		int codServico = Integer.parseInt(sc.nextLine());
		ServicoDAO servicoDAO = new ServicoDAO();
		Servico servico = servicoDAO.selectById(codServico);
		while(servico.getCodigo() == 0 || !servico.isAtivo()) {
			System.out.print("Informe um código válido: ");
			codServico = Integer.parseInt(sc.nextLine());
			servico = servicoDAO.selectById(codServico);
		}
		if(listarClientes()) {			
			System.out.print("Informe o código do cliente: ");
			int codCliente = Integer.parseInt(sc.nextLine());
			ClienteDAO clienteDAO = new ClienteDAO();
			Cliente cliente = clienteDAO.selectById(codCliente);
			while(cliente.getCodigo() == 0) {
				System.out.print("Informe um código válido: ");
				codCliente = Integer.parseInt(sc.nextLine());
				cliente = clienteDAO.selectById(codCliente);
			}
			System.out.print("Informe o nome do profissional: ");
			String nomeProfissional = sc.nextLine();
			System.out.print("Informe a data do serviço (Exemplo: 01/01/2025): ");
			String dataServicoString = sc.nextLine();
			while(!Validacao.dataValida(dataServicoString)) {
				System.out.print("Digite uma data válida: ");
				dataServicoString = sc.nextLine();
			}
			LocalDate dataServico = LocalDate.parse(dataServicoString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			System.out.print("Informe a hora do serviço (Exemplo: 08:00): ");
			String horaServicoString = sc.nextLine();
			while(!Validacao.horaValida(horaServicoString)) {
				System.out.print("Digite um horário válido: ");
				horaServicoString = sc.nextLine();
			}
			LocalTime horaServico = LocalTime.parse(horaServicoString, DateTimeFormatter.ofPattern("HH:mm"));
			AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
			if(agendamentoDAO.countDataHora(dataServico, horaServico) > 0) {
				System.out.println("Já existe um agendamento cadastrado na mesma data e horário.");
			} else {
				Agendamento agendamento = new Agendamento(servico, cliente, nomeProfissional, dataServico, horaServico);
				agendamentoDAO.insert(agendamento);
			}
		}
	}
	
	public static void editarAgendamento() {
		if(exibirTodosAgendamentos()) {
			AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
			Agendamento agendamento = selecionarAgendamentoOperacao();
			System.out.println("[1] - Nome do Profissional");
			System.out.println("[2] - Data do Serviço");
			System.out.println("[3] - Hora do Serviço");
			System.out.println("[0] - Cancelar");
			System.out.print("Escolha a informação a ser atualizada: ");
			String atributos[] = {"cancelar", "nome_profissional", "data_servico", "hora_servico"};
			String exemploFormato[] = {"", "", " (Exemplo: 01/01/2025)", " (Exemplo: 08:00)"}; 
			int opcao = 111;
			try {
				opcao = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException ignore) {}
			while(opcao > 3 || opcao < 0) {
				System.out.print("Informe uma opção válida: ");
				try {
					opcao = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException ignore) {}
			}
			if(opcao != 0) {
				String atributo = atributos[opcao].replace("_", " ");
				String atributoComplemento = exemploFormato[opcao];
				if(opcao != 1) {
					atributo = atributos[opcao].replace("_", " do ").replace("c", "ç");
				}
				System.out.print("Informe o(a) novo(a) "+atributo+" do agendamento"+atributoComplemento+": ");
				String valor = sc.nextLine();
				if(opcao == 2) {
					while(!Validacao.dataValida(valor)) {
						System.out.print("Digite uma "+atributo+" válida: ");
						valor = sc.nextLine();
					}
					LocalDate dataServico = LocalDate.parse(valor, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					agendamentoDAO.update(atributos[opcao], dataServico, agendamento.getCodigo());
				} else if (opcao == 3) {
					while(!Validacao.horaValida(valor)) {
						System.out.print("Digite uma "+atributo+" válida: ");
						valor = sc.nextLine();
					}
					LocalTime horaServico = LocalTime.parse(valor, DateTimeFormatter.ofPattern("HH:mm"));
					agendamentoDAO.update(atributos[opcao], horaServico, agendamento.getCodigo());
				} else {
					agendamentoDAO.update(atributos[opcao], valor, agendamento.getCodigo());
				}				
			}
		}
	}
	
	public static void cancelarAgendamento() {
		AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
		if(exibirTodosAgendamentos()) {
			System.out.print("Digite o código do agendamento que deseja cancelar: ");
			int codigo = Integer.parseInt(sc.nextLine());
			Agendamento agendamento = agendamentoDAO.selectById(codigo);
			while(agendamento.getCodigo() == 0) {
				System.out.print("Informe um código válido: ");
				codigo = Integer.parseInt(sc.nextLine());
				agendamento = agendamentoDAO.selectById(codigo);
			}
			System.out.print("Deseja realmente cancelar o agendamento? 1-Sim/0-Não ");
			String opcao= sc.nextLine();	
			if(opcao.equals("1")) {
				agendamentoDAO.updateCancelamento(LocalDateTime.now(), codigo);
			}
		}
	}
	
	public static boolean exibirTodosAgendamentos() {
		AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
		ArrayList<Agendamento> agendamentos = agendamentoDAO.select();
		if(agendamentos.isEmpty()) {
			System.out.println("Nenhum agendamento cadastrado\n");
		}else {
			for (Agendamento agendamento : agendamentos) {
				System.out.println(agendamento.toString()+"\n");
			}
			return true;
		}
		return false;
	}
	
	public static Agendamento selecionarAgendamentoOperacao() {
		System.out.print("Informe o código do agendamento: ");
		int codigo = Integer.parseInt(sc.nextLine());
		AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
		Agendamento agendamento = agendamentoDAO.selectById(codigo);
		while(agendamento.getCodigo() == 0) {
			System.out.print("Agendamento não identificado, informe um código válido: ");
			codigo = Integer.parseInt(sc.nextLine());
			agendamento = agendamentoDAO.selectById(codigo);
		};
		return agendamento;
	}
	
	// MÉTODOS SERVIÇO
	
	public static void listarServicosDisponiveis() {
		ServicoDAO servicoDAO = new ServicoDAO();
		ArrayList<Servico> servicos = servicoDAO.selectAtivosView();
		for (Servico serv : servicos) {
			System.out.println(serv.toString()+"\n");
		}
	}
	
	public static void exibirQuantidadeServicosAtivos() {
		ServicoDAO servicoDAO = new ServicoDAO();
		int qtdSetivosAtivos = servicoDAO.countAtivos();
		if(qtdSetivosAtivos > 0) {
			listarServicosDisponiveis();
		}
		System.out.println(qtdSetivosAtivos+" Serviço(s) ativo(s)\n");
	}
	
	public static void ativarServico() {
	    ServicoDAO servico = new ServicoDAO();
	    System.out.print("Digite o código do serviço que gostaria de ativar: ");
	    int codigo = Integer.parseInt(sc.nextLine());
	    servico.updateAtivo(true, codigo);
	}
	
	public static void desativarServico() {
	    ServicoDAO servico = new ServicoDAO();
	    System.out.print("Digite o código do serviço que gostaria de desativar: ");
	    int codigo = Integer.parseInt(sc.nextLine());
	    servico.updateAtivo(false, codigo);
	}
	    
	public static void visualizarServicoID() {
	    ServicoDAO servico = new ServicoDAO();
	    System.out.println("Digite o código do serviço que gostaria de visualizar: ");
	    int codigo = Integer.parseInt(sc.nextLine());
	    servico.selectById(codigo);
	}
	
	public static void visualizarTodosServico() {
	    ServicoDAO servico = new ServicoDAO();
		ArrayList<Servico> servicos = servico.select();
		for (Servico serv : servicos) {
			System.out.println(serv.toString()+" | Ativo: "+serv.isAtivo()+"\n");
		}
		System.out.println(servicos.size()+" Serviço(s) cadastrado(s)\n");
	}
	
	public static void exibirMenuAtivarServico() {
		while (true) {
            System.out.println("[1] - Ativar Serviço");
            System.out.println("[0] - Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = sc.nextLine();
    		System.out.println();
            switch (opcao) {
                case "1":
                	ativarServico();
                	visualizarTodosServico();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
	}
	
	// MÉTODOS CLIENTE
	
	public static boolean listarClientes() {
		ClienteDAO clienteDAO = new ClienteDAO();
		ArrayList<Cliente> clientes = clienteDAO.select();
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
	
	public static void exibirQuantidadeClientes() {
		ClienteDAO clienteDAO = new ClienteDAO();
		int qtdClientes = clienteDAO.count();
		if(qtdClientes > 0) {
			listarClientes();
		}
		System.out.println(qtdClientes+" Cliente(s) cadastrado(s)\n");
	}
	
	public static void cadastrarCliente() {
		System.out.print("Informe o nome do cliente: ");
		String nome = sc.nextLine();
		System.out.print("Informe o CPF do cliente: ");
		String cpf = sc.nextLine();
		while(!Validacao.cpfValido(cpf)) {
			System.out.print("Informe um CPF válido: ");
			cpf = sc.nextLine();
		}
		System.out.print("Informe o endereço do cliente: ");
		String endereco = sc.nextLine();
		System.out.print("Informe o telefone do cliente (Exemplo: 71988885555): ");
		String telefone = sc.nextLine();
		while(!Validacao.telefoneValido(telefone)) {
			System.out.print("Informe um telefone válido: ");
			telefone = sc.nextLine();
		}
		Cliente cliente = new Cliente(nome, cpf, endereco, telefone);
		ClienteDAO clienteDAO = new ClienteDAO();
		clienteDAO.insert(cliente);
	}
	
	public static Cliente selecionarClienteOperacao() {
		System.out.print("Informe o código do cliente: ");
		int codigo = Integer.parseInt(sc.nextLine());
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = clienteDAO.selectById(codigo);
		while(cliente.getCodigo() == 0) {
			System.out.print("Cliente não identificado, informe um código válido: ");
			codigo = Integer.parseInt(sc.nextLine());
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
			System.out.print("Escolha a informação a ser atualizada: ");
			String atributos[] = {"cancelar", "nome", "cpf", "endereço", "telefone"};
			int opcao = -1;
			try {
				opcao = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException ignore) {}
			while(opcao > 4 || opcao < 0) {
				System.out.print("Informe uma opção válida: ");
				try {
					opcao = Integer.parseInt(sc.nextLine());
				} catch (NumberFormatException ignore) {}
			}
			if(opcao != 0) {
				String atributo = atributos[opcao];
				System.out.print("Informe o novo "+atributo+" do cliente: ");
				String valor = sc.nextLine();
				if(opcao == 2) {
					while(!Validacao.cpfValido(valor)) {
						System.out.print("Informe um CPF válido: ");
						valor = sc.nextLine();
					}
				}
				if(opcao == 4) {
					while(!Validacao.telefoneValido(valor)) {
						System.out.print("Informe um telefone válido: ");
						valor = sc.nextLine();
					}
				}
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
			if(sc.nextLine().equals("1")) {
				AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
				ArrayList<Agendamento> agendamentosClienteDia = agendamentoDAO.selectByClienteDia(cliente.getCodigo());
				if(agendamentosClienteDia.isEmpty()) {
					clienteDAO.delete(cliente.getCodigo());
				} else { 
					for (Agendamento agendamento : agendamentosClienteDia) {
						System.out.println(agendamento.toString());
					}
					System.out.print("Existe(m) agendamento(s) para este cliente, apagar mesmo assim? 1-Sim/0-Não");
					if(sc.nextLine().equals("1")) {
						for (Agendamento agendamento : agendamentosClienteDia) {
							agendamentoDAO.updateCancelamento(LocalDateTime.now(), agendamento.getCodigo());
						}
						clienteDAO.delete(cliente.getCodigo());
					}
				}
			}
		}
	}
	
}
