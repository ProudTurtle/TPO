/**
 * @author Wierzbicka Aleksandra S22477
 */

package zad1;


import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Time {



    public static String passed(String from, String to) {
        Locale.setDefault(Locale.ENGLISH);

        SimpleDateFormat sdf = new SimpleDateFormat("'Od' d MMMM yyyy '('EEEE')'");
        SimpleDateFormat koniec = new SimpleDateFormat("'do' d MMMM yyyy '('EEEE')'");

        DateFormatSymbols dfs = sdf.getDateFormatSymbols();


        dfs.setMonths(
                new String[] {
                        "stycznia", "lutego", "marca", "kwietnia", "maja", "czerwca",
                        "lipca", "sierpnia", "września", "października", "listopada",
                        "grudnia"
                });
        dfs.setWeekdays(
                new  String[]{
                        "", "niedziela","ponidziałek", "wtorek", "środa", "czwartek", "piątek", "sobota"
                });

        sdf.setDateFormatSymbols(dfs);
        koniec.setDateFormatSymbols(dfs);


        try {
            if (from.contains("T") && to.contains("T")) {

                ZonedDateTime start = ZonedDateTime.of(LocalDateTime.parse(from), ZoneId.of("Europe/Warsaw"));
                ZonedDateTime end = ZonedDateTime.of(LocalDateTime.parse(to), ZoneId.of("Europe/Warsaw"));

                long godziny = Math.round(ChronoUnit.HOURS.between(start,end));
                long minuty = ChronoUnit.MINUTES.between(start,end);
                long dni = Math.round(godziny/24.00);
                double tygodnie = Math.round((dni/7.0)*100.0)/100.0;
                int month = start.getMonthValue()-1;
                int monthh = end.getMonthValue()-1;
                String poczatkowy = sdf.format(new GregorianCalendar(start.getYear(),  month , start.getDayOfMonth()).getTime());
                String koncowy = koniec.format(new GregorianCalendar(end.getYear(), monthh, end.getDayOfMonth()).getTime());

                return poczatkowy + " godz. "
                        + start.format(DateTimeFormatter.ofPattern("hh:mm")) + " " + koncowy +  " godz. " + end.format(DateTimeFormatter.ofPattern("hh:mm")) + "\n"  +
                        "- mija: "+ dni + (dni == 1 ? " dzień, " : " dni, ") + "tygodni "  + tygodnie + "\n" +
                        "- godzin: " + godziny + ", minut: " + minuty + "\n" +
                        (dni >= 1 ? "- kalendarzowo: " + Kalendarz(start.toLocalDate(), end.toLocalDate()) : "")  ;

            } else {
                LocalDate start = LocalDate.parse(from);
                LocalDate end = LocalDate.parse(to);

                long dni = ChronoUnit.DAYS.between(start,end);
                double tygodnie = Math.round((dni/7.0)*100.0)/100.0;
                int month = start.getMonthValue()-1;
                int monthh = end.getMonthValue()-1;

                String poczatkowy = sdf.format(new GregorianCalendar(start.getYear(),  month , start.getDayOfMonth()).getTime());
                String koncowy = koniec.format(new GregorianCalendar(end.getYear(), monthh, end.getDayOfMonth()).getTime());

                return poczatkowy + " " + koncowy + "\n" +
                        "- mija: "+ dni + " dni, tygodni "  + tygodnie + "\n" +
                        (dni >= 1 ? "- kalendarzowo: " + Kalendarz(start, end): "")  ;

            }

        } catch (DateTimeParseException ex) {
            return "*** java.time.format.DateTimeParseException: " + ex.getMessage();
        }

    }


    private static String Kalendarz(LocalDate from, LocalDate to) {

        int days = Period.between(from,to).getDays();
        int years = Period.between(from,to).getYears();
        int month = Period.between(from,to).getMonths();
        String kalendarzowo = "";

        if(years >= 1){
            if(years == 1){
                kalendarzowo += years + " rok, ";
            }else if(years <5){
                kalendarzowo += years + " lata, ";
            }else kalendarzowo += years + " lat, ";
            }
        if(month >= 1){
            if(month == 1){
                kalendarzowo += month + " miesiąc, ";
            }else if(month < 5 ){
                kalendarzowo += month + " miesiące, ";
            }else kalendarzowo += month + " miesięcy, ";
        }

        if(days >= 1){
            if(days == 1){
                kalendarzowo += days + " dzień, ";
            }else kalendarzowo += days + " dni, ";
        }
        return kalendarzowo.substring(0, kalendarzowo.length()-2);
    }

}
