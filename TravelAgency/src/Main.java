import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
	
	public static void main(String args[]) {
		readCities();
		readPlaces();
		getCommands(); // use for testing
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
	
	/**
	 * reads and executes commands from Stdin
	 */
	public static void getCommands() {
		Agency ag = Agency.getInstance();
		Scanner sc = new Scanner(System.in);
		String line, command, name, type;
		Date checkIn, checkOut;
		boolean over = false;
		long diff, days;
		List<Place> places;
		
		/**
		 * print menu and usage info
		 */
		System.out.println("Introduceti o comanda: ");
		System.out.println("Comenzi: ");
		System.out.println("1) info placeName");
		System.out.println("2) top[City/County/Country] cityName/countyName/countryName checkIn(dd/mm/yyyy) checkOut(dd/mm/yyyy)");
		System.out.println("3) cheapest");
		System.out.println("4) exit\n");
		
		while(!over) {
			
			line = sc.nextLine();
			StringTokenizer st = new StringTokenizer(line, " ");
			command = st.nextToken();
			
			/**
			 * do specific actions based on the command
			 */
			switch(command) {
			case "info":
				name = st.nextToken();
				if (ag.getPlaces().containsKey(name)) {
					System.out.println(ag.getPlaces().get(name).getInfo());
				} else {
					System.out.println("Locatia introdusa nu exista");
				}
				break;	
			case "topCity":
				name = st.nextToken();
				checkIn = parseDate(st.nextToken());
				checkOut = parseDate(st.nextToken());
				diff = checkOut.getTime() - checkIn.getTime();
			    days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				
				places = ag.getCities().get(name).getTop5(checkIn, checkOut);
				System.out.println("Top 5 locatii in " + name);
				for (Place p : places) {
					System.out.println(p + " Pret pe sejur: " + (days * p.getPrice()) + " $");
				}
				break;
			case "topCounty":
				name = st.nextToken();
				checkIn = parseDate(st.nextToken());
				checkOut = parseDate(st.nextToken());
				diff = checkOut.getTime() - checkIn.getTime();
			    days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				
				places = ag.getCounties().get(name).getTop5(checkIn, checkOut);
				System.out.println("Top 5 locatii in " + name);
				for (Place p : places) {
					System.out.println(p + " Pret pe sejur: " + (days * p.getPrice()) + " $");
				}
				break;
			case "topCountry":
				name = st.nextToken();
				checkIn = parseDate(st.nextToken());
				checkOut = parseDate(st.nextToken());
				diff = checkOut.getTime() - checkIn.getTime();
			    days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				
				places = ag.getCountries().get(name).getTop5(checkIn, checkOut);
				System.out.println("Top 5 locatii in " + name);
				for (Place p : places) {
					System.out.println(p + " Pret pe sejur: " + (days * p.getPrice()) + " $");
				}
				break;
			case "cheapest":
				Place p = ag.getCheapest();
				System.out.println("Cea mai ieftina locatie ");
				System.out.println(p + " Pret pe un sejur de 10 zile: " + (p.getPrice() * 10) + " $");
				break;
			case "exit":
				over = true;
				break;
			}
		}
			
		
		sc.close();
	}
}
