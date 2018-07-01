package it.polito.tdp.music.model;

public class Artist {
	
	private int id ;
	private String artist ;
	private int numAscolti;
	
	public Artist(int id, String artist) {
		super();
		this.id = id;
		this.artist = artist;
	}
	
	
	public Artist(String artist, int numAscolti) {
		super();
		this.artist = artist;
		this.numAscolti = numAscolti;
	}


	public int getNumAscolti() {
		return numAscolti;
	}
	public void setNumAscolti(int numAscolti) {
		this.numAscolti = numAscolti;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
}
