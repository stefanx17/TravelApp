import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

public class Country {
	private String name;
	private List<County> counties;
	
	/**
	 * @param name - name of the country
	 */
	public Country(String name) {
		super();
		this.name = name;
		this.counties = new ArrayList<County>();
	}

	/**
	 * getters and setters for the Country class fields
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<County> getCounties() {
		return counties;
	}

	public void setCounties(List<County> counties) {
		this.counties = counties;
	}
	
	public void addCounty(County county) {
		this.counties.add(county);
		
		/**
		 * make sure that the county has a refrence to this country
		 */
		if (county.getCountry() != this) {
			county.setCountry(this);
		}
	}

	/**
	 * 
	 * @param A - check-in date
	 * @param B - check-out date
	 * @return a List of the top 5 destinations in this country
	 */
	public List<Place> getTop5(Date checkIn, Date checkOut) {
		List<Place> res = new ArrayList<Place>();
		PriorityQueue<Place> pq = new PriorityQueue<Place>(new PlaceComparator());
		
		/**
		 * get top locations from all the counties in this country
		 */
		for (County county : this.getCounties()) {
			pq.addAll(county.getTop5(checkIn, checkOut));
		}
		
		/**
		 * get only the top 5 destinations in this country
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
