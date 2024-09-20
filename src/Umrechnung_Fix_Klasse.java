import java.util.HashMap;

public class Umrechnung_Fix_Klasse implements Waehrungsrechner_Umrechnung_Klasse{


    final private HashMap<String,Double> fixedWerte = new HashMap<>();




    public Umrechnung_Fix_Klasse(){

        fixedWerte.put("USD",1.0);
        fixedWerte.put("EUR",0.85);
        fixedWerte.put("GBP",0.73);

    }



    @Override
    public double convertCurrency(double amount, String sourceCurrency, String targetCurrency) {
        final double sourceExchangeRate = fixedWerte.get(sourceCurrency);
        final double sourceTargetRate = fixedWerte.get(targetCurrency);

        return amount*sourceExchangeRate/sourceTargetRate;
    }
}
