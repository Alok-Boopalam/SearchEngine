
package com.test.test;
import java.net.UnknownHostException;
import java.util.*;

public class linkjava {
	

	public  ArrayList<String> test(String query) throws UnknownHostException
		{
			tf_idfSearch test = new tf_idfSearch();
			ArrayList<String> URL1 = new ArrayList<String>();
			URL1 = test.fnSearch(query);
		    /** A Java program that demonstrates console based input and output. */
		//	String testing[] = {"http://google.com","http://yahoo.com","http://msn.com","http://bing.com","http://intel.com"};
		    

		       // Read a line of text from the user.
	           
		       
		       return URL1;
		     
		        } // end main method

		    } // end MyConsoleIO class
