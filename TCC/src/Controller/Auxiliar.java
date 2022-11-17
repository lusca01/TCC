package Controller;

import java.io.BufferedReader;
import java.io.IOException;

public class Auxiliar {

    public static String convertJsonToString(BufferedReader reader) throws IOException{
        String answer, jsonToString = "";
        while((answer = reader.readLine()) != null){
            jsonToString += answer;
        }
        return jsonToString;
    }
}