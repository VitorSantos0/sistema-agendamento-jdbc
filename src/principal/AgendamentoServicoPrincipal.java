package principal;

import conexao.ConexaoDB;

public class AgendamentoServicoPrincipal {

	public static void main(String[] args) {
		
		ConexaoDB conexao = new ConexaoDB("sistema_agendamento");
		
		conexao.getConnection();
		
		conexao.closeConnection();

	}

}
