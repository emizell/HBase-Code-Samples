package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataGenerator {
	
	private volatile static DataGenerator instance = null;
	
	private static List<String> firstNameList = new ArrayList<String>();
	private static List<String> lastNameList = new ArrayList<String>();
	private static List<String> genderList = new ArrayList<String>();
	private static List<String> emailProviderList = new ArrayList<String>();
	private static List<String> streetList = new ArrayList<String>();
	private static List<String> cityList = new ArrayList<String>();
	private static List<String> stateList = new ArrayList<String>();
	private static List<String> zipList = new ArrayList<String>();
	private long beginTime = Timestamp.valueOf("1940-01-01 00:00:00").getTime();
	private long endTime = Timestamp.valueOf("2010-12-31 00:00:00").getTime();
	
	protected DataGenerator() {
		// Exists only to defeat instantiation.
	}
	
	public static DataGenerator getInstance() {
		if (instance == null) {
			synchronized (DataGenerator.class) {
				if (instance == null) {
					instance = new DataGenerator();
					try {
						setupData();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("Failed to setup data arrays " + e.getStackTrace());
					}
					
				}
			}
			
		}
		return instance;
	}
	
	//Instantiate data
	private static void setupData() throws Exception {
		setupFirstNames();
		setupLastNames();
		setupEmailProviders();
		setupGenders();
		setupZips();
		setupStates();
		setupCities();
		setupStreetAddress();
	}
	
	public Integer getRandomCustomerValue () throws Exception {
		return RandomUtil.generateRandomNumber();
	}
	
	public String getDOB() throws Exception {
		return generateRandomDate();
	}
	
	public String getFirstName() throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(firstNameList);
		return returnString;
	}
	
	public String getLastName() throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(lastNameList);
		return returnString;
	}
	
	public String getEmailProvider() throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(emailProviderList);
		return returnString;
	}
	
	public String getZipCode() throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(zipList);
		return returnString;
	}
	
	public String getState() throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(stateList);
		return returnString;
	}
	
	public String getStreetAddress() throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(streetList);
		return returnString;
	}
	
	public String getCity() throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(cityList);
		return returnString;
	}
	
	public String getGender() throws Exception {
		String returnString = (String) RandomUtil.getRandomObjectFromList(genderList);
		return returnString;
	}
	
	private static void setupZips() throws Exception {
		zipList.add("80915");
		zipList.add("27502");
		zipList.add("30101");
		zipList.add("32010");
		zipList.add("80909");
		zipList.add("21501");
		zipList.add("33201");
		zipList.add("70122");
		zipList.add("40221");
		zipList.add("56022");
		zipList.add("57822");
		zipList.add("30505");
		zipList.add("60774");
		zipList.add("89774");
		zipList.add("45332");
	}
	
	private static void setupStates() throws Exception {
		stateList.add("NC");
		stateList.add("AL");
		stateList.add("CA");
		stateList.add("CO");
		stateList.add("DE");
		stateList.add("GA");
		stateList.add("FL");
		stateList.add("HI");
		stateList.add("VA");
		stateList.add("TN");
		stateList.add("SD");
		stateList.add("WA");
		stateList.add("RI");
	}
	
	private static void setupCities() throws Exception {
		cityList.add("Raleigh");
		cityList.add("Atlanta");
		cityList.add("Apex");
		cityList.add("Marietta");
		cityList.add("Cary");
		cityList.add("New York");
		cityList.add("San Francisco");
		cityList.add("Palo Alto");
		cityList.add("Reno");
		cityList.add("Austin");
		cityList.add("Maui");
		cityList.add("Topeeka");
	}
	
	private static void setupStreetAddress() throws Exception {
		streetList.add("100 West North Street");
		streetList.add("200 East West Street");
		streetList.add("443 Marina Street");
		streetList.add("532 West Place");
		streetList.add("4000 Park Place");
		streetList.add("3442 Winward Parkway");
		streetList.add("3022 Lazio Lane");
		streetList.add("1088 Cree Drive");
		streetList.add("2533 Howell Farms Way");
		streetList.add("7788 Creedmont Park Drive");
		streetList.add("1100 York Drive");
		streetList.add("339 Peak Place");
		streetList.add("9333 Montgomery Drive");
		streetList.add("3837 Boring Drive");
		streetList.add("2847 Water Way Drive");
		streetList.add("1000 Fedex Circle");
		streetList.add("44747 Last Entry Way");
	}
	
	private static void setupGenders() throws Exception {
		genderList.add("male");
		genderList.add("female");
		genderList.add("unknown");
	}
	
	private static void setupEmailProviders() throws Exception {
		emailProviderList.add("@gmail.com");
		emailProviderList.add("@att.net");
		emailProviderList.add("@comcast.com");
		emailProviderList.add("@yahoo.com");
		emailProviderList.add("@hotmail.com");
	}
	
	private static void setupLastNames() throws Exception {
		lastNameList.add("Smith");
		lastNameList.add("Baker");
		lastNameList.add("Finley");
		lastNameList.add("Hunter");
		lastNameList.add("Furter");
		lastNameList.add("Carlson");
		lastNameList.add("Beaks");
		lastNameList.add("Jones");
		lastNameList.add("Hathaway");
		lastNameList.add("Lawson");
		lastNameList.add("Night");
		lastNameList.add("Yunts");
		lastNameList.add("Jenks");
		lastNameList.add("Williams");
		lastNameList.add("Potter");
		lastNameList.add("Henry");
		lastNameList.add("McKenny");
		lastNameList.add("Woods");
		lastNameList.add("Milborne");
		lastNameList.add("Winkler");
		lastNameList.add("Katz");
		lastNameList.add("Wilkinson");
		lastNameList.add("Barns");
		lastNameList.add("Cobra");
	}
	
	private static void setupFirstNames() throws Exception {
		firstNameList.add("Joe");
		firstNameList.add("Sally");
		firstNameList.add("Sara");
		firstNameList.add("Holly");
		firstNameList.add("Frank");
		firstNameList.add("George");
		firstNameList.add("Henry");
		firstNameList.add("Eric");
		firstNameList.add("Tracey");
		firstNameList.add("Kyle");
		firstNameList.add("Hailey");
		firstNameList.add("Sue");
		firstNameList.add("Dan");
		firstNameList.add("Jamie");
		firstNameList.add("Mike");
		firstNameList.add("Daryl");
		firstNameList.add("Hailey");
		firstNameList.add("Alan");
		firstNameList.add("Dave");
		firstNameList.add("Bill");
	}
	
	public String generateRandomDate() {
    	 SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    	 Date randomDate = new Date(getRandomTimeBetweenTwoDates());
    	 return dateFormat.format(randomDate);
    }
    
    private long getRandomTimeBetweenTwoDates () {
        long diff = endTime - beginTime + 1;
        return beginTime + (long) (Math.random() * diff);
    }
}
