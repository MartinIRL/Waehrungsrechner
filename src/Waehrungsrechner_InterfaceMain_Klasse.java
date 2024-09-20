import java.util.Date;

public interface Waehrungsrechner_InterfaceMain_Klasse {



    String [] getCurrencies();

    String [] getConverters();

    double performConversion(double menge,String sourceWaehrung, String targetWaehrung,String typUmrechner);

    void setDate(Date date);

    Date getDate();
}
