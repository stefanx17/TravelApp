import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Place {
	private String name;
	private double price;
	private City city;
	private List<String> activities = null;
	private Date startDate, endDate;
	
	/**
	 * Constructor using all the fields
	 * @param name       - name of the place
	 * @param price      - average price per day
	 * @param city       - the city it is located in
	 * @param activities - list of activities you can do there
	 * @param startDate  - the date this place becomes available for booking
	 * @param endDate    - the date this place becomes unavailable for booking
	 */
	public Place(String name, double price, City city, List<String> activities, Date startDate, Date endDate) {
		super();
		this.name = name;
		this.price = price;
		this.city = city;
		this.activities = activities;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Place(String name) {
		this(name, 0.0, null, new ArrayList<String>(), null, null);
	}
	
	public Place() {
		this("");
	}

	/**
	 * getters and setters for the class fields
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
		
		/**
		 * we have to make sure that the place is in the
		 * city's list of places
		 */
		if(!city.getPlaces().contains(this)) {
			city.getPlaces().add(this);
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public void addActivity(String activity) {
		activities.add(activity);
	}
	
	public void delActivity(String activity) {
		activities.remove(activity);
	}
	
	public List<String> getActivities() {
		return activities;
	}

	public void setActivities(List<String> activities) {
		this.activities = activities;
	}

	public String getInfo() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String info = "";
		info += "Nume loc: " + this.getName() + "\n";
		info += "Oras: " + this.getCity().toString() + "\n";
		info += "Pret mediu pe zi: " + this.getPrice() + "$\n";
		info += "Activitati: ";
		for (String act : this.getActivities()) {
			info += act + " ";
		}
		info += "\nPerioda: " + sdf.format(this.getStartDate()) + " - " + sdf.format(this.getEndDate()) + "\n";
		return info;
	}
	@Override
	public String toString() {
		return name;
	}
}
