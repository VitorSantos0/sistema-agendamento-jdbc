package entidade.dao;

public class LogSql {
	
	private static boolean mostrarSql;
	
	public static void exibicao(boolean ativa) {
		mostrarSql = ativa;
    }

	public static void exibirComandoSql(String sql){
		if(mostrarSql) {			
			System.out.println("------------------------------- SQL -------------------------------");
			System.out.println("\t"+sql);
			System.out.println("------------------------------- SQL -------------------------------\n");
		}
	}

}
