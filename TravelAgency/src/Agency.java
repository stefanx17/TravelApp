import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
	
	/**
	 * @return the cheapest destination from this agency
	 */
	public Place getCheapest() {
		Place cheapest = null;
		
		/**
		 * iterate through all the places to find the cheapest one
		 */
		for(Place place : places.values()) {
			long diff = place.getEndDate().getTime() - place.getStartDate().getTime();
		    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			
		    /**
		     * only check the places that can be booked for at least 10 days
		     */
		    if (days < 10) {
				continue;
			}
			if (cheapest == null) {
				cheapest = place;
			} else {
				if (cheapest.getPrice() > place.getPrice()) {
					cheapest = place;
				}
			}
		}
		return cheapest;
	}
}

class PlaceComparator implements Comparator<Place>{

	@Override
	public int compare(Place placeA, Place placeB) {
		if (placeA.getPrice() > placeB.getPrice()) {
			return 1;
		} else {
			if (placeA.getPrice() == placeB.getPrice()) {
				return placeA.getName().compareTo(placeB.getName());
			}
		}
		
		return -1;
	}
	
}
