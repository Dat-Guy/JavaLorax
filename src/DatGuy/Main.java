/*

ISSUE LIST:

* Nothing is set up in accordance to how the printer stores data, as there is no info yet on how it is formatted.
  - A data sample is preferable, but if unavailable, these info are needed:
    > Print task formatting - How is the data sorted? By user? By task?
    > Username formatting - Is it the full email, or just the username?
    > Date formatting - Is it in Unix epoch time? HH:MM (AM/PM) MM/DD/YYYY? HH/MM (24-hour) DD/MM/YYYY?
    > What indexes are the data in the CVS file stored at? User at first, print id at 0, etc?
    
* The preferred way for the email to be formatted is requested, so this script can format the email, or ALTERNATELY
give a plugin so data for email recipients and key data can be passed along.

* Will the script auto-run once a month, will it be manually run, or should there be a daemon set up so the script
re-runs itself once a month after the initial run?

*/


package DatGuy;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
	
	private static final String FILE_NAME = ""; //Where the CVS file is located
	private static final Long SECONDS_AGO = (long) 60 * 60 * 24 * 30; //Tells the system to get all emails from the past month (in seconds).
	
	private static final char SEPARATOR = ','; //CVS element separator
	private static final char QUOTE = '\"'; //CVS quotation
	
	//TODO: Insert proper values for 'User Element', the index of element in each print task with the username, and 'Print Qty Element', similar use.
	
	private static final int USER_ELEMENT = 0; //User id index
	private static final int PRINT_QTY_ELEMENT = 1; //Print quantity index
	private static final int PRINT_DATE_ELEMENT = 2; //Print execution time TODO: Get date formatting.
	
	public static void main(String[] args) throws FileNotFoundException {
		
		
		//Grab the CVS file, processed into individual lines and elements.
		ArrayList<ArrayList<String>> data = CVSFileReader.FileReader.ReadFile(FILE_NAME, SEPARATOR, QUOTE);
		
		//TODO: Verify the CVS file stores data by print task, and not by user, print quantity, or any other means.
		
		//Setup temporary storage of users and print counts
		ArrayList<String> Users = new ArrayList<>();
		
		ArrayList<Long> PrintCount = new ArrayList<>();
		
		for (int i = 0; i < data.size(); i++) {
			String User = data.get(i).get(USER_ELEMENT); //Grab print task user
			Long PrintQty = Long.parseUnsignedLong(data.get(i).get(PRINT_QTY_ELEMENT)); //Grab print task quantity
			
			String RawDate = data.get(i).get(PRINT_DATE_ELEMENT); //Grab the raw date element
			
			//TODO: Using Data formatting, process RawDate into number of seconds before start of script
			
			Long SecondsAgo = (long) 0; //Set the number of seconds beforehand information was printed
			
			//If the number of seconds ago the print happened falls within the filer...
			if (SecondsAgo < SECONDS_AGO) {
				
				//If the user is already defined (a previous print by the same user already been processed)
				if (Users.indexOf(User) != -1) {
					PrintCount.set(Users.indexOf(User), PrintQty + PrintCount.get(Users.indexOf(User))); //Add to the existing print count.
				} else {
					Users.add(User); //Add new user entry
					PrintCount.add(PrintQty); //Add new print quantity
				}
			}
		}
		
		//TODO: Add code to pass data to automated email script
		//After that, the total print counts for each user are passed to an automated e-mail generator;
		//This will likely be used with an already existing automated email system
		
		//This is just testing a basic formatting for emails, which could be passed to the script:
		
		//Setup email recipient and email content arrays
		ArrayList<String> Emails = new ArrayList<>();
		
		ArrayList<String> EmailContent = new ArrayList<>();
		
		for (int i = 0; i < Users.size(); i++) {
			
			//Transform username into emails
			Emails.add(Users.get(i) + "@granvilleschools.org"); //TODO: Get formatting for users (is whole email already provided)?
			
			//Create email
			EmailContent.add("We see that you have printed a total of " + PrintCount.get(i) + " pages!"); //TODO: Actually format a good email
			
		}
		
		//Email emails or pass along to helper script.
		
	}
}
