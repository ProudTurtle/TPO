/**
 * @author Wierzbicka Aleksandra S22477
 */

package zad1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;

import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Service {

    String CountryCode;
    private String country;
    String OpenWeatherURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    String OpenWeatherKey = "&APPID=9714a37a1420944cf514aa7e74edfc4b&units=metric";
    String Exchange = "https://api.exchangerate.host/latest?base=";
    String NBP = "http://api.nbp.pl/api/exchangerates/rates/a/";
    Map<String, String> countries = new HashMap<>();
    String curr1;


    //api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=9714a37a1420944cf514aa7e74edfc4b


    public Service(String country) {
        this.country = country;
        Locale.setDefault(new Locale("", "en"));
        for (String CountryCode : Locale.getISOCountries()) {
            Locale l = new Locale("", CountryCode);
            countries.put(l.getDisplayCountry().toLowerCase(), CountryCode);

        }

    }


    public String getWeather(String city) {
        System.out.println(countries.get(country.toLowerCase()));
        curr1 = Currency.getInstance(new Locale("", countries.get(country.toLowerCase()))).getCurrencyCode();
        JSONObject parse = new JSONObject();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(OpenWeatherURL + city + "," + countries.get(country.toLowerCase()) + OpenWeatherKey).openConnection().getInputStream()))) {
            String weatherJson = br.lines().collect(Collectors.joining(System.lineSeparator()));

            JSONParser parser = new JSONParser();
            parse = (JSONObject) parser.parse(weatherJson);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return parse.get("main").toString();
    }


    public Double getRateFor(String currency) {
        String rates = null;
        curr1 = Currency.getInstance(new Locale("", countries.get(country.toLowerCase()))).getCurrencyCode();
//        curr1 = Currency.getInstance(new Locale("", countries.get(country.toLowerCase()))).getCurrencyCode();
        JSONObject parse;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new URL(Exchange + currency + "&symbols=" + curr1).openConnection().getInputStream()))){
            String exchange = br.lines().collect(Collectors.joining(System.lineSeparator()));
            JSONParser parser = new JSONParser();
            parse = (JSONObject) parser.parse(exchange);
            rates = parse.get("rates").toString();
            rates = rates.replaceAll("[^0-9.]","");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }


        assert rates != null;
        return  Double.parseDouble(rates);
    }

    public Double getNBPRate() {
        //http://api.nbp.pl/api/exchangerates/rates/a/chf/
        String rates = null;
        JSONObject parse;
        if (curr1 == null) {
            return null;
        }

        if (curr1.equals("PLN")) {
            return 1d;
        }

            try(BufferedReader br = new BufferedReader(new InputStreamReader(new URL(NBP + curr1).openConnection().getInputStream()))){
                String exchange = br.lines().collect(Collectors.joining(System.lineSeparator()));
                JSONParser parser = new JSONParser();
                parse = (JSONObject) parser.parse(exchange);
                rates = parse.getJSONArray("rates").getJSONObject(0).optDouble("mid");


            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }


        assert rates != null;
        return  Double.parseDouble(rates);

    }


}
