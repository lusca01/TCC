import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Controller {

    public void vuln (String endereco) {
        try {
            Process x = Runtime.getRuntime().exec("sudo nmap -sV --script vuln " + endereco);
            InputStream fluxo = x.getInputStream();
            InputStreamReader leitor = new InputStreamReader(fluxo);
            BufferedReader buffer = new BufferedReader(leitor);
            String linha = buffer.readLine();
            while (linha != null) {
                if (linha.contains("Starting Nmap")){
                    linha = buffer.readLine();
                    continue;
                }
                System.out.println(linha);
                linha = buffer.readLine();
            }
            buffer.close();
            leitor.close();
            fluxo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
