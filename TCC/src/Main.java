import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller();
        String endereco;
        int op;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Digite o endereço que quer verificar vulnerabilidades: ");
            endereco = scan.next();

            controller.vuln(endereco);

            System.out.println("Verificar outro endereco? (1 - Nao | Outro numero para sim)");
            op = scan.nextInt();
        } while(op != 1);
        System.out.println("Bye!");
    }
}