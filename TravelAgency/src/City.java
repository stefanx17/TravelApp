import java.util.ArrayList;
import java.util.List;

public class City {
	private String name;
	private List<Place> places;
	private County county;
	
	/**
	 * @param name - the name of the city
	 */
	public City(String name) {
		this.name = name;
		this.places = new ArrayList<Place>();
	}

	/**
	 * getters and setters for the City class fields
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
		
		/**
		 * make sure that the county has this city in their list
		 */
		if(!county.getCities().contains(this)) {
			county.getCities().add(this);
		}
	}
	
	public void addPlace(Place place) {
		this.places.add(place);
		
		/**
		 * make sure that the place has a reference to this city
		 */
		if (place.getCity() != this) {
			place.setCity(this);
		}
	}

	@Override
	public String toString() {
		return name;
	}
	
}
