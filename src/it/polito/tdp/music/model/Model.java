package it.polito.tdp.music.model;

import java.time.Month;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.music.db.CountryPair;
import it.polito.tdp.music.db.MusicDAO;

public class Model {

	private MusicDAO dao;
	
	List<Artist> classifica;
	int numMese;
	List<Country> vertici;
	
	SimpleWeightedGraph<Country, DefaultWeightedEdge> grafo;
	//creo mappa country perche richiede ggetto country
	private Map<Integer, Country> mappaPaesi;
	
	public Model() {
		dao= new MusicDAO();
		classifica= new LinkedList<>();
		vertici = new LinkedList<>();
		mappaPaesi= new HashMap<>();

	}


	public List<Artist> calcolaClassifica(Month m) {
		numMese=m.getValue();
		classifica=dao.getClassifica(numMese);
		return classifica;
	}


	public void creaGrafo() {
		
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
	  //Vertivi: elenco delle nazioni (country) in cui sono stati ascoltati in tal mese
		for(Artist a: classifica) {
			vertici.addAll(dao.getcountryOfListening(a,numMese));
		}
		
	  //aggiungo i vertici al grafo
		Graphs.addAllVertices(grafo, vertici);
		
		//archi
		List<CountryPair> archi= dao.getCountryPairs(numMese, mappaPaesi);
		for(CountryPair c: archi) {
			if(vertici.contains(c.getCountry1()) && vertici.contains(c.getCountry2())  )
			Graphs.addEdgeWithVertices(grafo,  c.getCountry1(), c.getCountry2(), c.getCnt());
		}
	}


	public int calcolaDistanza() {
	int max = 0 ;
		
		for( DefaultWeightedEdge e : grafo.edgeSet() ) {
			if ( grafo.getEdgeWeight(e) > max )
				max = (int)grafo.getEdgeWeight(e) ;
		}
		return max ;
	}

}
