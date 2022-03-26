/**
 *
 *  @author Wierzbicka Aleksandra S22477
 *
 */

package zad1;


public class Main {
  public static void main(String[] args) {
    Service s = new Service("Spain");
    String weatherJson = s.getWeather("Madrid");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI

    System.out.println("Pogoda w wybranym kraju: " + weatherJson);
    System.out.println("Kurs waluty: " + rate1);
    System.out.println("Kurs waluty w odniesieniu do PLN: " + rate2);
  }
}
