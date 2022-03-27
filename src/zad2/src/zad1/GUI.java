package zad1;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.*;
import java.awt.*;


public class GUI {

    Service service;
    private final JFXPanel jfxPanel;
    private WebEngine engine;

    public GUI() {
        JFrame jf = new JFrame();
        jf.setVisible(true);
        jf.setSize(700, 500);
        jf.setTitle("TPO zadanie 2");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        JPanel panel = new JPanel(new BorderLayout());
        jfxPanel = new JFXPanel();
        createScene();


        JPanel pan1 = new JPanel(new GridLayout(0, 1));
        pan1.add(new JLabel("Country:"));
        JTextField countryField = new JTextField("Poland");
        pan1.add(countryField);
        pan1.add(new JLabel("City:"));
        JTextField cityField = new JTextField("Warsaw");
        pan1.add(cityField);
        pan1.add(new JLabel("Currency:"));
        JTextField currencyField = new JTextField("EUR");
        pan1.add(currencyField);
        JButton button = new JButton("Pobierz dane");
        pan1.add(button);
        panel.add(pan1, BorderLayout.NORTH);

        JPanel panLabel = new JPanel(new GridLayout(0, 1));
        JPanel pan2 = new JPanel(new GridLayout(0, 1));
        pan2.setSize(50, 50);
        JLabel weatherLabel = new JLabel("Weather:");
        panLabel.add(weatherLabel);
        JLabel RateLabel = new JLabel("Rate:");
        panLabel.add(RateLabel);
        JLabel NBPRate = new JLabel("NBP:");
        panLabel.add(NBPRate);

        panel.add(pan2, BorderLayout.CENTER);
        pan2.add(panLabel, BorderLayout.NORTH);
        pan2.add(jfxPanel, BorderLayout.CENTER);


        jf.getContentPane().add(panel);


        button.addActionListener(e -> {


            service = new Service(countryField.getText());
            String weather = service.getWeather(cityField.getText());
            Double rate = service.getRateFor(currencyField.getText());
            Double NBP = service.getNBPRate();

            weatherLabel.setText("Pogoda: " + weather);
            RateLabel.setText("Kurs wymiany waluty: " + rate);
            NBPRate.setText("Kurs NBP złotego wobec waluty: " + NBP);

            String url = "https://en.wikipedia.org/wiki/" + cityField.getText();


            Platform.runLater(() -> {
                engine.load(url);
            });


        });


    }

    private void createScene() {
        Platform.runLater(() -> {
            WebView view = new WebView();
            engine = view.getEngine();
            jfxPanel.setSize(700,300);

            jfxPanel.setScene(new Scene(view));
        });
    }


}

