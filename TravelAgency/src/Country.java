import java.util.ArrayList;
import java.util.List;

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

	@Override
	public String toString() {
		return name;
	}
}
