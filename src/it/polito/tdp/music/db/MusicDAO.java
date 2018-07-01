package it.polito.tdp.music.db;

import it.polito.tdp.music.model.Artist;
import it.polito.tdp.music.model.City;
import it.polito.tdp.music.model.Country;
import it.polito.tdp.music.model.Listening;
import it.polito.tdp.music.model.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MusicDAO {
	
	public List<Country> getAllCountries() {
		final String sql = "SELECT id, country FROM country" ;
		
		List<Country> countries = new LinkedList<Country>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				countries.add( new Country(res.getInt("id"), res.getString("country"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return countries ;
		
	}
	
	public List<City> getAllCities() {
		final String sql = "SELECT id, city FROM city" ;
		
		List<City> cities = new LinkedList<City>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				cities.add( new City(res.getInt("id"), res.getString("city"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return cities ;
		
	}

	
	public List<Artist> getAllArtists() {
		final String sql = "SELECT id, artist FROM artist" ;
		
		List<Artist> artists = new LinkedList<Artist>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				artists.add( new Artist(res.getInt("id"), res.getString("artist"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return artists ;
		
	}

	public List<Track> getAllTracks() {
		final String sql = "SELECT id, track FROM track" ;
		
		List<Track> tracks = new LinkedList<Track>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				tracks.add( new Track(res.getInt("id"), res.getString("track"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return tracks ;
		
	}
	
	public List<Listening> getAllListenings() {
		final String sql = "SELECT id, userid, month, weekday, longitude, latitude, countryid, cityid, artistid, trackid FROM listening" ;
		
		List<Listening> listenings = new LinkedList<Listening>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				listenings.add( new Listening(res.getLong("id"), res.getLong("userid"), res.getInt("month"), res.getInt("weekday"),
						res.getDouble("longitude"), res.getDouble("latitude"), res.getInt("countryid"), res.getInt("cityid"),
						res.getInt("artistid"), res.getInt("trackid"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return listenings ;
		
	}


	//MIEI
	public List<Artist> getClassifica(int num) {
		final String sql = "select a.artist, count(l.id) as cnt " + 
				"from artist a, listening l " + 
				"where a.id=l.artistid " + 
				"and l.month=? " + 
				"group by a.artist " + 
				"order by cnt DESC " + 
				"limit 20 " ;
		
		List<Artist> artists = new LinkedList<Artist>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, num);
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				artists.add( new Artist(res.getString("artist"), res.getInt("cnt"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return artists ;
		
	}
	
	
	public List< Country> getcountryOfListening(Artist a, int numMese) {
        String sql = "select distinct c.id, c.country " + 
        		"from listening l, country as c " + 
        		"where c.id=l.countryid " + 
        		"and l.month=? " + 
        		"and l.artistid=? " ;
		
		List<Country> countries = new LinkedList<Country>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, numMese);
			st.setString(2, a.getArtist());
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				countries.add( new Country(res.getInt("id"), res.getString("country"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return countries ;
	}
	
	
	public List<CountryPair> getCountryPairs(int numMese, Map<Integer, Country> mappaPaesi) {

		String sql = "select count(distinct l1.artistid) as cnt, c1.id as country1, c2.id as country2 " + 
				"from country c1, listening l1, listening l2, country c2 " + 
				"where l1.countryid=c1.id " + 
				"and l2.countryid=c2.id " + 
				"and l1.artistid=l2.artistid " + 
				"and c1.id<>c2.id " + 
				"and l1.month=? " + 
				"and l2.month=? " + 
				"group by c1.id, c2.id ";

		List<CountryPair> list = new ArrayList<>();

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, numMese);
			st.setInt(2, numMese);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				list.add(new CountryPair(
						(double)rs.getInt("cnt"),
						mappaPaesi.get(rs.getInt("country1")), 
						mappaPaesi.get(rs.getInt("country2"))));
			}

			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	/*eventuale qury per prendere tutte le distanze tra i country
	    select distinct l1.countryid, l1.longitude, l1.latitude, l2.countryid, l2.longitude, l2.latitude
		from country c1, listening l1, listening l2, country c2 
		where l1.countryid=c1.id 
		and l2.countryid=c2.id
		and l1.artistid=l2.artistid
		and c1.id<>c2.id
		and l1.month=11
		and l2.month=11
		group by c1.id, c2.id */
	
	
	
	
	
	
	
	
	
	//PER DEBUG
	
	public static void main(String[] args) {
		MusicDAO dao = new MusicDAO() ;
		
		List<Country> countries = dao.getAllCountries() ;
		//System.out.println(countries) ;
		
		List<City> cities = dao.getAllCities() ;
		//System.out.println(cities) ;
		
		List<Artist> artists = dao.getAllArtists() ;
		
		List<Track> tracks = dao.getAllTracks() ;
		
		List<Listening> listenings = dao.getAllListenings() ;



		System.out.format("Loaded %d countries, %d cities, %d artists, %d tracks, %d listenings\n", 
				countries.size(), cities.size(), artists.size(), tracks.size(), listenings.size()) ;
	}

	

}
