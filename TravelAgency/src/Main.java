import java.io.*;
import java.util.*;

public class Main {
	
	public static void main(String args[]) {
		readCities();
		readPlaces();
		Date checkIn, checkOut;
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 5, 10);
		checkIn = cal.getTime();
		
		cal.set(2018, 6, 10);
		checkOut = cal.getTime();
		
		for(Place p : Agency.getInstance().getCities().get("City6").getTop5(checkIn, checkOut)) {
			System.out.println(p.getInfo());
		}
	}
	
	/**
	 * read cities from file containing on each line
	 * a city, their county and country
	 */
	public static void readCities() {
		Agency ag = Agency.getInstance();
		FileReader fr = null;
		BufferedReader br = null;
		HashMap<String, City> cities = ag.getCities();
		HashMap<String, County> counties = ag.getCounties();
		HashMap<String, Country> countries = ag.getCountries();
		
		
		try {
			fr = new FileReader("src/cities.txt");
			br = new BufferedReader(fr);
			
			String line, cityName, countyName, countryName;
			City city;
			County  county;
			Country country;
			
			line = br.readLine();
			
			/**
			 * read until the end of file
			 */
			while (line != null) {
				StringTokenizer strtok = new StringTokenizer(line, ";");
				
				cityName = strtok.nextToken();
				countyName = strtok.nextToken();
				countryName = strtok.nextToken();
				
				/**
				 * add the city/county/country in the agency lists
				 */
				if (!cities.containsKey(cityName)) {
					cities.put(cityName, new City(cityName));
				}
				
				if (!counties.containsKey(countyName)) {
					counties.put(countyName, new County(countyName));
				}
				
				if (!countries.containsKey(countryName)) {
					countries.put(countryName, new Country(countryName));
				}
				
				city = cities.get(cityName);
				county = counties.get(countyName);
				country = countries.get(countryName);
				
				/**
				 * make the links between the entities
				 */
				city.setCounty(county);
				county.setCountry(country);
				
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try{
				if(fr!=null){
					fr.close();
				}
				if(br!=null){
					br.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public static void readPlaces() {
		Agency ag = Agency.getInstance();
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader("src/input.txt");
			br = new BufferedReader(fr);
			
			String line, placeName, cityName;
			double price;
			String activities;
			List<String> act;
			Date start, end;
			City city;
			
			line = br.readLine();
			
			/**
			 * read until the end of file
			 */
			while (line != null) {
				StringTokenizer strtok = new StringTokenizer(line, ";");
				
				/**
				 * parse one line of input
				 * line format: placeName;cityName;price;act1,act2,...;startDate;endDate
				 */
				placeName = strtok.nextToken();
				cityName = strtok.nextToken();
				price = Double.parseDouble(strtok.nextToken());
				activities = strtok.nextToken();
				start = parseDate(strtok.nextToken());
				end = parseDate(strtok.nextToken());
				act = new ArrayList<String>();

				/**
				 * parse the activities string
				 */
				strtok = new StringTokenizer(activities, ",");
				while (strtok.hasMoreTokens()) {
					act.add(strtok.nextToken());
				}
				
				/**
				 * add the city to the database if it isn't already in
				 */
				if (!ag.getCities().containsKey(cityName)) {
					city = new City(cityName);
					ag.getCities().put(cityName, city);
				} else {
					city = ag.getCities().get(cityName);
				}
								
				Place p = new Place(placeName, price, city, act, start, end);
				/**
				 * add the place to the database and link it to a city
				 */
				ag.getPlaces().put(placeName, p);
				city.addPlace(p);
				
				line = br.readLine();
				
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try{
				if(fr!=null){
					fr.close();
				}
				if(br!=null){
					br.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param date - a string representing a date in the format dd/mm/yyyy
	 * @return a Date object with the value of the date in the string
	 */
	public static Date parseDate(String date) {
		StringTokenizer st = new StringTokenizer(date, "/");
		Calendar cal = Calendar.getInstance();
		
		int day, month, year;
		day = Integer.parseInt(st.nextToken());
		month = Integer.parseInt(st.nextToken());
		year = Integer.parseInt(st.nextToken());
		
		/**
		 * set the calendar with the date provided
		 */
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		
		/**
		 * return a the current time in a Date object
		 */
		return cal.getTime();
	}
}
