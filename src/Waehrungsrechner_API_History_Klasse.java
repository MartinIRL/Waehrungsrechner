import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Waehrungsrechner_API_History_Klasse implements Waehrungsrechner_Umrechnung_Klasse {

    private final Waehrungsrechner_InterfaceMain_Klasse interfaceKlasse;

    public Waehrungsrechner_API_History_Klasse(Waehrungsrechner_InterfaceMain_Klasse interfaceKlasse){

        this.interfaceKlasse = interfaceKlasse;
    }

    // https://api.freecurrencyapi.com/v1/historical
    // https://api.freecurrencyapi.com/v1/latest?apikey=YOUR-APIKEY     query params
    // "apikey: YOUR-APIKEY"   => apiKey = "fca_live_gF78AGKP8uRSNzRUeRwSQuZfjI17IlRQiAl8U7Bt";

    // https://api.freecurrencyapi.com/v1/
    // historical?apikey=fca_live_gF78AGKP8uRSNzRUeRwSQuZfjI17IlRQiAl8U7Bt
    // &currencies=EUR%2CUSD%2CCAD&date_from=2024-08-12T10%3A45%3A33.917Z&date_to=2024-08-13T10%3A45%3A33.917Z
    //https://api.freecurrencyapi.com/v1/historical?apikey=fca_live_gF78AGKP8uRSNzRUeRwSQuZfjI17IlRQiAl8U7Bt&currencies=EUR%2CUSD%2CCAD&date_from=2023-10-16T12%3A25%3A34.628Z&date_to=2023-10-18T12%3A25%3A34.629Z


    @Override
    public double convertCurrency(double amount, String sourceCurrency, String targetCurrency) {

        final String apiURL = "https://api.freecurrencyapi.com/v1/historical", apiKey ="fca_live_gF78AGKP8uRSNzRUeRwSQuZfjI17IlRQiAl8U7Bt";


        LocalDate localDate = interfaceKlasse.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final String dateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        try {
            final String urlString = String.format("%s?apikey=%s&base_currency=%s&currencies=%s&date_from=%s&date_to=%s",
                    apiURL,apiKey,sourceCurrency,targetCurrency, URLEncoder.encode(dateString,"UTF-8"),URLEncoder.encode(dateString,"UTF-8"));


            final URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Fehler beim auslesen");
                return -1;
            }

            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String response = "";
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }

            bufferedReader.close();
            final double zielWaehrung = waehrungskursExtrahieren(response,targetCurrency);
            return amount*zielWaehrung;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


    //{"data":{"2023-08-05":{"EUR":0.9082600926}}}
    private double waehrungskursExtrahieren(String daten, String zielWaehrung) {
        final String currencyString = "\"" + zielWaehrung + "\":";
        final int index = daten.indexOf(currencyString) + currencyString.length();
        final String number = daten.substring(index, daten.length() - 3);

        return Double.parseDouble(number);
    }



    }




