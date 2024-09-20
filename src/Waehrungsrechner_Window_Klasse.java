import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.Date;

public class Waehrungsrechner_Window_Klasse {

    //Test-Werte für JCombobox
    //private final String [] optionenTest = new String[] {"USD","EUR","YPJ"};
    //private final String [] optionenTest1 = new String[] {"Historisch","Fix","Echtzeit"};
    private final Waehrungsrechner_InterfaceMain_Klasse interfaceKlasse;

    private  JTextField amount;
    private  JComboBox<String> currentWaehrung;
    private  JLabel ergebnis;

    public Waehrungsrechner_Window_Klasse(Waehrungsrechner_InterfaceMain_Klasse interfaceKlasse){

        this.interfaceKlasse = interfaceKlasse;


        JFrame jFrame = new JFrame("Währungsrechner 1.0");

        jFrame.setSize(400,300);


        jFrame.add(ausgangsWertPanel(),BorderLayout.NORTH);
        jFrame.add(zielWaehrungPanel(),BorderLayout.CENTER);
        jFrame.add(ergebnisPanel(),BorderLayout.SOUTH);

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jFrame.setVisible(true);
        //anpassung des Fensters
        jFrame.pack();

    }



   private JPanel ausgangsWertPanel(){

        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),"Ausgangswert"));

        amount = new JTextField(10);

        amount.setPreferredSize(new Dimension(100,20));
        jPanel.add(amount);

        currentWaehrung = new JComboBox<>();
        currentWaehrung.setModel(new DefaultComboBoxModel<>(interfaceKlasse.getCurrencies()));

        jPanel.add(currentWaehrung);


        return jPanel;
    }


    private JPanel zielWaehrungPanel(){

        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),"Zielwährung"));


        final  JComboBox<String> jComboBoxWaehrung = new JComboBox<>();
        final  JComboBox<String> jComboBoxHistorisch = new JComboBox<>();
        jComboBoxWaehrung.setModel(new DefaultComboBoxModel<>(interfaceKlasse.getCurrencies()));
        jComboBoxHistorisch.setModel(new DefaultComboBoxModel<>(interfaceKlasse.getConverters()));

        jPanel.add(jComboBoxWaehrung);
        jPanel.add(jComboBoxHistorisch);

        JSpinner jSpinner = new JSpinner(new SpinnerDateModel());
        jSpinner.setEditor(new JSpinner.DateEditor(jSpinner,"yyyy-MM-dd"));



        final JButton umrechnenButton = new JButton("Umrechnen");
        umrechnenButton.addActionListener(e -> {
           interfaceKlasse.setDate((Date)jSpinner.getValue());
           final double result =  interfaceKlasse.performConversion(Integer.parseInt(amount.getText()),(String) currentWaehrung.getSelectedItem(),(String) jComboBoxWaehrung.getSelectedItem(),(String)jComboBoxHistorisch.getSelectedItem());
           ergebnis.setText("Ergebnis: " + result);
        });

        jPanel.add(jSpinner);
        jPanel.add(umrechnenButton);

        return jPanel;
    }


    private JPanel ergebnisPanel(){

        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        jPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),"Ergebnis"));

        ergebnis = new JLabel("Ergebnis:");
        ergebnis.setPreferredSize(new Dimension(200,20));

        jPanel.add(ergebnis,BorderLayout.CENTER);

        return jPanel;
    }



}
