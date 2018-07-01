package it.polito.tdp.music;

import java.net.URL;
import java.time.Month;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.music.db.CountryPair;
import it.polito.tdp.music.model.Artist;
import it.polito.tdp.music.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MusicController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Month> boxMese;

    @FXML
    private Button btnArtisti;

    @FXML
    private Button btnNazioni;

    @FXML
    private TextArea txtResult;

    private Model model;
    
    public void setModel(Model model) {
		this.model=model;
	}
    
    @FXML
    void ElencoAscolti(ActionEvent event) {
        this.txtResult.clear();
		
		Month m=boxMese.getValue();
		if(m!=null) {
		
		List<Artist> classifica= model.calcolaClassifica(m);	
		txtResult.setText("Classifica del mese: "+m);
		for(Artist a: classifica) {
			txtResult.appendText(String.format("\n"+a.getArtist()+": "+a.getNumAscolti()));
		}
		
		} else {
			this.txtResult.setText("Selezionare un mese!");
		}
    }

    @FXML
    void doDistanza(ActionEvent event) {

    	try {
    	 	model.creaGrafo();
    		
    	 	int distanza= model.calcolaDistanza();
    	 
    	    txtResult.appendText(String.format("\nLa coppia più distante è formata lontana: \n"+distanza));
    	    
    	} catch(RuntimeException e) {
         	this.txtResult.setText("Errore creazione grafo");
    	}
   
    	
    }
    
    @FXML
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert btnArtisti != null : "fx:id=\"btnArtisti\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert btnNazioni != null : "fx:id=\"btnNazioni\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MusicA.fxml'.";

        for(int mese=1; mese<=12; mese++ ) {
			boxMese.getItems().add(Month.of(mese)); //.of riceve come parametro il numero intero del mese e da mese corrispondente
		}
    }

	
}
