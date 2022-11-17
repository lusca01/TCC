package Controller;

import Model.CVE;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    static String BaseURLCVE = "https://services.nvd.nist.gov/rest/json/cves/2.0?cveId=";
    List<String> listCVE = new ArrayList<>();
    List<String> listVuln = new ArrayList<>();

    public void vuln (String endereco) throws Exception {
        try {
            Process x = Runtime.getRuntime().exec("sudo nmap -sV --script vuln " + endereco);
            InputStream fluxo = x.getInputStream();
            InputStreamReader leitor = new InputStreamReader(fluxo);
            BufferedReader buffer = new BufferedReader(leitor);
            String linha = buffer.readLine();
            while (linha != null) {
                String[] CVE = linha.split(" |\\t|:");
                for (String palavras : CVE){
                    if (palavras.contains("CVE-") && (!palavras.contains("/") && !palavras.contains("?"))){
                        //System.out.println(palavras);
                        listCVE.add(palavras);
                    }
                }
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

        verificaDescricao();
        for (String cves : listVuln){
            System.out.println(cves);
        }

    }

    public void verificaDescricao() throws Exception{
        int sucess = 200;
        for (String vuln : listCVE){
            String newUrl = BaseURLCVE + vuln;
            try {
                URL url = new URL(newUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (connection.getResponseCode() != sucess){
                    sucess = connection.getResponseCode();
                    break;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String jsonToString = Auxiliar.convertJsonToString(reader);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                System.out.println(gson.toJson(jsonToString));

                listVuln.add(jsonToString);
            }catch (Exception e){
                throw new Exception("Erro: " + e);
            }
        }
    }
}
