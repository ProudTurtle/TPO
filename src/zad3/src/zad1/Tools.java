/**
 *
 *  @author Wierzbicka Aleksandra S22477
 *
 */

package zad1;


import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tools {




    public static Options createOptionsFromYaml(String fileName) throws Exception {

        final String collect =  new BufferedReader(new InputStreamReader(new FileInputStream(fileName))).lines().collect(Collectors.joining(System.lineSeparator()));
        Yaml yaml = new Yaml();

       Map<String, Object> map = yaml.load(collect);
       Map<String, List<String>> clientsMap;

       clientsMap = (Map<String, List<String>>) map.get("clientsMap");

       return new Options(
               map.get("host").toString(),
               Integer.parseInt(map.get("port").toString()),
               Boolean.parseBoolean(map.get("concurMode").toString()),
               Boolean.parseBoolean(map.get("showSendRes").toString()),
               clientsMap
       );
    }
}
