import java.io.*;
import java.util.HashMap;

public class Waehrungsrechner_Waehrungen_Klasse {


    private final HashMap<String,String> waehrungsListe;


    public Waehrungsrechner_Waehrungen_Klasse(){
            this.waehrungsListe = new HashMap<>();
    }

    public  void addToWaehrungsListe() {
        try {
            String myString ="";
            try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("waehrung.txt"));

            int value;
            while ((value = bufferedReader.read()) != -1) {
                if ((char) value == '\n') {
                    final String[] myArr = myString.split(";");
                    this.waehrungsListe.put(myArr[0], myArr[1]);
                    myString = "";
                } else {
                   myString+=(char) value;
                }

            }
            }catch(FileNotFoundException exp){
                exp.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getWaehrungsListe() {
        return waehrungsListe;
    }
}
