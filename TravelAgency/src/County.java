import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

public class County {
	private String name;
	private List<City> cities;
	private Country country;
	
	
	/**
	 * @param name - name of the county
	 */
	public County(String name) {
		super();
		this.name = name;
		this.cities = new ArrayList<City>();
	}


	/**
	 * getters and setters for the County class fields
	 */
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<City> getCities() {
		return cities;
	}


	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	
	public void addCity(City city) {
		this.cities.add(city);
		
		/**
		 * make sure that the city has a refrence to this county
		 */
		if (city.getCounty() != this) {
			city.setCounty(this);
		}
	}

	public Country getCountry() {
		return country;
	}


	public void setCountry(Country country) {
		this.country = country;
		
		/**
		 * make sure that the country has this county in their list
		 */
		if (!country.getCounties().contains(this)) {
			country.getCounties().add(this);
		}
	}
	
	/**
	 * 
	 * @param A - check-in date
	 * @param B - check-out date
	 * @return a List of the top 5 destinations in this county
	 */
	public List<Place> getTop5(Date checkIn, Date checkOut) {
		List<Place> res = new ArrayList<Place>();
		PriorityQueue<Place> pq = new PriorityQueue<Place>(new PlaceComparator());
		
		/**
		 * get top locations from all the cities in the county
		 */
		for(City city : this.getCities()) {
			pq.addAll(city.getTop5(checkIn, checkOut));
		}
		
		/**
		 * return only the top 5 locations in this county
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
