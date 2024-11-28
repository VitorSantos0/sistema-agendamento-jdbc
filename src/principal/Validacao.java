package principal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validacao {
	
	public static boolean dataValida(String dateString) {
        try {
            LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
	
	public static boolean horaValida(String horaString) {
        try {
            LocalTime.parse(horaString, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
	}
	
	public static boolean cpfValido(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.length() != 11) {
            return false;
        }
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstVerifier = 11 - (sum % 11);
            if (firstVerifier >= 10) {
                firstVerifier = 0;
            }
            if (firstVerifier != Character.getNumericValue(cpf.charAt(9))) {
                return false;
            }
            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondVerifier = 11 - (sum % 11);
            if (secondVerifier >= 10) {
                secondVerifier = 0;
            }
            return secondVerifier == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	public static boolean telefoneValido(String telefone) {
        String regex = "^\\d{2}\\d{1}\\d{8}$";
        return Pattern.matches(regex, telefone);
    }

}
