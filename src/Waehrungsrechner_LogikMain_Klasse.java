import java.util.Date;
import java.util.HashMap;

public class Waehrungsrechner_LogikMain_Klasse implements Waehrungsrechner_InterfaceMain_Klasse {

    private final HashMap<String,Waehrungsrechner_Umrechnung_Klasse> converters = new HashMap<>();




    //private final String [] currencies = new String[]{"EUR","GBR", "USD"};
    //einlesen aus Datei.txt
    private  final Waehrungsrechner_Waehrungen_Klasse waehrungsrechnerWaehrungenKlasse = new Waehrungsrechner_Waehrungen_Klasse();
    private Date ausgewaehltesDatum;

    public Waehrungsrechner_LogikMain_Klasse(){
        this.waehrungsrechnerWaehrungenKlasse.addToWaehrungsListe();
        converters.put("Echtzeit", new Waehrungsrechner_API_Klasse());
        converters.put("Historisch", new Waehrungsrechner_API_History_Klasse(this));
        converters.put("Fix", new Umrechnung_Fix_Klasse());

    }



    @Override
    public String[] getConverters() {
        return converters.keySet().toArray(new String[converters.size()]);
    }

    @Override
    public String[] getCurrencies() {
        return this.waehrungsrechnerWaehrungenKlasse.getWaehrungsListe().keySet().toArray(new String[0]);
    }

    @Override
    public double performConversion(double menge, String sourceWaehrung, String targetWaehrung, String typUmrechner) {
        final Waehrungsrechner_Umrechnung_Klasse umrechnungsConverter = converters.get(typUmrechner);
        return umrechnungsConverter.convertCurrency(menge,sourceWaehrung,targetWaehrung);
    }

    @Override
    public void setDate(Date date) {
        this.ausgewaehltesDatum = date;
    }

    @Override
    public Date getDate() {
        return this.ausgewaehltesDatum;
    }


}
