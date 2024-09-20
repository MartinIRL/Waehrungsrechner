import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Waehrungsrechner_API_Klasse implements Waehrungsrechner_Umrechnung_Klasse{

    //freecurrencyapi.com
    // default key // fca_live_gF78AGKP8uRSNzRUeRwSQuZfjI17IlRQiAl8U7Bt
    // https://api.freecurrencyapi.com/v1/latest?apikey=YOUR-APIKEY     query params
    // "apikey: YOUR-APIKEY"
    //






    @Override
    public double convertCurrency(double amount, String sourceCurrency, String targetCurrency) {
        final String apiURL = "https://api.freecurrencyapi.com/v1/latest",
                apiKey = "fca_live_gF78AGKP8uRSNzRUeRwSQuZfjI17IlRQiAl8U7Bt";
        final String urlString = String.format("%s?apikey=%s&base_currency=%s&currencies=%s",apiURL,apiKey,sourceCurrency,targetCurrency);

        try {
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
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
        return 0;
    }



    //exchangeRate rausholen die aktueller Währungskurs rausholen
    // ausgabe der api (json format) {"data":{"EUR":0.8930101319}}
    // zuerst den string repräsentieren (nachstellen) "EUR":

    private double waehrungskursExtrahieren(String daten, String zielWaehrung){
        // mit "\"" und  "\":" bereich von "Währung":  =>  "EUR":
        final String waehrungsString =  "\"" + zielWaehrung + "\":";
        //index gibt uns die position 0 von 0.893 zurück aus den daten.indexof zurück bzw die 0 nach dem :
        // die +3 passt hierbei für jede Währungsabkürzung da "zielwährung": 1","2,:3
        final int index = daten.indexOf(waehrungsString)+ zielWaehrung.length()+3;
        //erstellt den String 0.8930101319 ohne die klammern am schluss
        final String number = daten.substring(index,daten.length()-2);

        return Double.parseDouble(number);
    }








}
