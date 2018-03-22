import java.util.HashMap;

public class Agency {
	private static Agency instance = null;
	
	/**
	 * keep mappings of all the cities/counties/countries/places
	 * managed by the agency
	 */
	private HashMap<String, City> cities = new HashMap<String, City>();
	private HashMap<String, County> counties = new HashMap<String, County>();
	private HashMap<String, Country> countries = new HashMap<String, Country>();
	private HashMap<String, Place> places = new HashMap<String, Place>();
	
	/**
	 * make constructor private so the object 
	 * can't be instantiated
	 */
	private Agency() {}
	
	/*
	 * get current instance of the Agency
	 */
	public static Agency getInstance(){
		if(instance == null){
			instance = new Agency();
		}
		return instance;
	}

	/**
	 * getters for the map lists of the agency
	 */
	public HashMap<String, City> getCities() {
		return cities;
	}

	public HashMap<String, County> getCounties() {
		return counties;
	}

	public HashMap<String, Country> getCountries() {
		return countries;
	}

	public HashMap<String, Place> getPlaces() {
		return places;
	}
	
	
}
