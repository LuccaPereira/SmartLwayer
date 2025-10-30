public class TesteManual2 {
    public static void main(String[] args) {
        String cpf = "98765432100";
        
        // Primeiro dígito
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int digito1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        System.out.println("CPF: " + cpf);
        System.out.println("Soma digito1: " + soma);
        System.out.println("Digito1 calculado: " + digito1 + ", no CPF: " + cpf.charAt(9));
        
        // Segundo dígito
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int digito2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        System.out.println("Soma digito2: " + soma);
        System.out.println("Digito2 calculado: " + digito2 + ", no CPF: " + cpf.charAt(10));
        
        boolean valido = Character.getNumericValue(cpf.charAt(9)) == digito1 &&
                         Character.getNumericValue(cpf.charAt(10)) == digito2;
        System.out.println("CPF valido? " + valido);
    }
}

