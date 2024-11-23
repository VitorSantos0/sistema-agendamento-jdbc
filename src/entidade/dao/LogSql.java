package entidade.dao;

public class LogSql {
	
	private static boolean mostrarSql;
	
	public static void exibicao(boolean ativa) {
		mostrarSql = ativa;
    }

	public static void exibirComandoSql(String sql){
		if(mostrarSql) {			
			System.out.print("[JDBC-PGSQL]: ");
			System.out.println(sql);
			System.out.println();
		}
	}

}
