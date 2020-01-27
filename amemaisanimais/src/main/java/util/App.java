package util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static String APP_NAME = "Ame+Ani+";
    public static String BASE_URL = "/amemaisanimais";
    public static Date DATA_ATUAL;
    
    public static Map<String, String> getUF() {
        Map<String, String> UF = new HashMap<String, String>();
        UF.put("AC", "Acre");
        UF.put("AL", "Alagoas");
        UF.put("AP", "Amapá");
        UF.put("AM", "Amazonas");
        UF.put("BA", "Bahia");
        UF.put("CE", "Ceará");
        UF.put("DF", "Distrito Federal");
        UF.put("ES", "Espírito Santo");
        UF.put("GO", "Goiás");
        UF.put("MA", "Maranhão");
        UF.put("MT", "Mato Grosso");
        UF.put("MS", "Mato Grosso do Sul");
        UF.put("MG", "Minas Gerais");
        UF.put("PA", "Pará");
        UF.put("PB", "Paraíba");
        UF.put("PR", "Paraná");
        UF.put("PE", "Pernambuco");
        UF.put("PI", "Piauí");
        UF.put("RJ", "Rio de Janeiro");
        UF.put("RN", "Rio Grande do Norte");
        UF.put("RS", "Rio Grande do Sul");
        UF.put("RO", "Rondônia");
        UF.put("RR", "Roraima");
        UF.put("SC", "Santa Catarina");
        UF.put("SP", "São Paulo");
        UF.put("SE", "Sergipe");
        UF.put("TO", "Tocantins");
        return UF;
    }
    
}
