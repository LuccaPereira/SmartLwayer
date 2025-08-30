import java.io.PrintStream;
import java.util.Scanner;
import java.lang.System;


// Peça ao usuário dois números inteiros e mostre:
// - A soma
// - A subtração
// - A multiplicação
// - A divisão inteira

public class soma {

    public static void operation(Long a, Long b, int operacao){

        switch (operacao) {
            case 1:
                System.out.println("A soma é: " + (a + b));
                break;
            case 2:
                System.out.println("A subtração é: " + (a - b));
                break;
            case 3:
                System.out.println("A multiplicação é: " + (a * b));
                break;
            case 4:
                if (b != 0) {
                    System.out.println("A divisão é: " + (a / b));
                } else {
                    System.out.println("Erro: Divisão por zero não é permitida.");
                }
                break;
            default:
                System.out.println("Operação inválida.");
        }
    }

    public static void main(String[] args) throws Exception {
    
        Scanner scanner = new Scanner(System.in);
        int escolha = 0;
        Long a;
        Long b;
        int operacao;
        

        while (escolha != 2) {
           
            System.out.println("Deseja começar uma operação? ");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
            escolha = scanner.nextInt();

            if (escolha == 1){
                System.out.println("Ótimo! Vamos começar.");

                System.out.println("Digite o primeiro número: ");
                a = scanner.nextLong();

                System.out.println("Digite o segundo número: ");
                b = scanner.nextLong();

                System.out.println("Escolha a operação que deseja realizar: ");
                System.out.println("1 - Soma");
                System.out.println("2 - Subtração");
                System.out.println("3 - Multiplicação");
                System.out.println("4 - Divisão");
                operacao = scanner.nextInt();
                
                operation(a, b, operacao);
                
            }
            else if(escolha == 2){ 
                System.out.println("Ok, até a próxima!");
                scanner.close();
                break;
            }
        }
    }
}