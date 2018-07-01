package it.polito.tdp.music.db;

import it.polito.tdp.music.model.Country;

public class CountryPair {

	private double cnt;
	private Country country1;
	private Country country2;
	
	
	

	public CountryPair(double cnt, Country country1, Country country2) {
		super();
		this.cnt = cnt;
		this.country1 = country1;
		this.country2 = country2;
	}

	public double getCnt() {
		return cnt;
	}
	public void setCnt(double cnt) {
		this.cnt = cnt;
	}
	public Country getCountry1() {
		return country1;
	}
	public void setCountry1(Country country1) {
		this.country1 = country1;
	}
	public Country getCountry2() {
		return country2;
	}
	public void setCountry2(Country country2) {
		this.country2 = country2;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country1 == null) ? 0 : country1.hashCode());
		result = prime * result + ((country2 == null) ? 0 : country2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryPair other = (CountryPair) obj;
		if (country1 == null) {
			if (other.country1 != null)
				return false;
		} else if (!country1.equals(other.country1))
			return false;
		if (country2 == null) {
			if (other.country2 != null)
				return false;
		} else if (!country2.equals(other.country2))
			return false;
		return true;
	}
	
	
	
}
