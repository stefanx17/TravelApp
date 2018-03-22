import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

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
	
	/**
	 * 
	 * @param A - check-in date
	 * @param B - check-out date
	 * @return a List of the top 5 cheapest destinations
	 */
	public List<Place> getTop5(Date checkIn, Date checkOut) {
		List<Place> places = this.getPlaces();
		List<Place> res = new ArrayList<Place>();
		PriorityQueue<Place> pq = new PriorityQueue<Place>(new PlaceComparator());
		
		/**
		 * only get the places that can be booked for the whole period
		 */
		for (Place p : places) {
			if (p.getStartDate().before(checkIn) && p.getEndDate().after(checkOut)) {
				pq.add(p);
			}
		}
		
		/**
		 * get only the first 5 cheapest locations to return 
		 */
		int count = 5;
		while(!pq.isEmpty() && count > 0) {
			res.add(pq.poll());
			count--;
		}
		
		return res;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
