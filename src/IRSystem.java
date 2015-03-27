import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class IRSystem {
	
	static FreeBaseAPI fb = new FreeBaseAPI();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//String query = "Bill Gates";
		//fb.infobox(query);
        //fb.QandA("Who created microsoft");
		IRSystem fbSys = new IRSystem();
		fbSys.startSystem(args);
	}
	
	public void startSystem(String[] args) throws IOException {
		int type = checkArgs(args);
		if (type != -1)
			fb.setFBKey(args[1]);
		FileInputStream fstream;
		
		switch (type) {
			case -1:
				break;
			case 0:
				interactiveMode();
				break;
			case 1:
				fb.infobox(args[3]);
				break;
			case 2:
				fb.QandA(args[3]);
				break;
			case 3:
				fstream = new FileInputStream(args[3]);
				try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
				    String line;
				    while ((line = br.readLine()) != null) {
				    	line = line.trim();
				    	if (line.length() != 0) {
				    		System.out.println("Query: " + line);
				    		fb.infobox(line);
				    		System.out.println("\n");
				    	}
				    }
				}
				break;
			case 4:
				fstream = new FileInputStream(args[3]);
				try (BufferedReader br = new BufferedReader(new InputStreamReader(fstream))) {
				    String line;
				    while ((line = br.readLine()) != null) {
				    	line = line.trim();
				    	if (line.length() != 0) {
				    		System.out.println("Question: " + line);
				    		fb.QandA(line);
				    		System.out.println("\n");
				    	}
				    }
				}
				break;
		}		
	}
	
	private void interactiveMode() {
		System.out.println("Welcome to infoxbox creator using Freebase knowledge graph.");
		System.out.println("Feel curious? Start exploring...");
		System.out.println("(To exit the system, just type: _exit_)\n");
		Scanner scanner = new Scanner(System.in);
		
		while(true) {
			promt();
		    String query = scanner.nextLine().toLowerCase();
		    
		    if (query.equals("_exit_")) {
		    	System.out.println("Good Bye!");
		    	break;
		    }
		    
		    if (query.startsWith("who created ")) {
		    	System.out.println("Let me see...");
		    	fb.QandA(query);
		    	System.out.println("\n");
		    } else {
		    	System.out.println("Let me see...");
		    	fb.infobox(query);
		    	System.out.println("\n");
		    }
		}
		
		scanner.close();
	}
	
	private void promt() {
		Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");
    	System.out.print(sdf.format(cal.getTime()) + " " + System.getProperty("user.name") + "@fb_ibox> ");
	}
	
	private int checkArgs(String[] args) {
		// 0 for ./run.sh -key <Freebase API key>
		// 1 for ./run.sh -key <Freebase API key> -q <query> -t infobox
		// 2 for ./run.sh -key <Freebase API key> -q <query> -t question
		// 3 for ./run.sh -key <Freebase API key> -f <file of queries> -t infobox
		// 4 for ./run.sh -key <Freebase API key> -f <file of queries> -t question
		
		if ((args.length != 2 && args.length != 6)
							|| !args[0].equals("-key") 
							|| (args.length > 2 && !args[2].equals("-q") && !args[2].equals("-f"))
							|| (args.length > 2 && !args[4].equals("-t"))
							|| (args.length > 2 && !args[5].equals("infobox") && !args[5].equals("question"))) {
			usage();
			return -1;
		}
		
		int res = 0;
		if (args.length == 2)
			return res;
		if (args[2].equals("-q"))
			res = 1;
		else
			res = 3;
		if (args[5].equals("question"))
			res += 1;
			
		return res;
	}
	
	private void usage() {
		System.out.println("Usage:");
		System.out.println("./run.sh -key <Freebase API key> -q <query> -t <infobox|question>");
		System.out.println("./run.sh -key <Freebase API key> -f <file of queries> -t <infobox|question>");
		System.out.println("./run.sh -key <Freebase API key>");
	}

}
