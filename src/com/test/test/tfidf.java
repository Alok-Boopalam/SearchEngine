
package irmongodb;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

public class tfidf {
	public static void main(String[] args) throws IOException{
		DB db = (new MongoClient("localhost", 27017)).getDB("irmongodatabase");
		DBCollection dbCollection = db.getCollection("Channel");
		BasicDBObject basicDBObject = new BasicDBObject();
		DBObject dock= dbCollection.findOne();
	stopwords sw= new stopwords();
		
	for(DBObject dock1 : dbCollection.find() ) {
		 TreeMap<String, Integer> counttokens = new TreeMap<String, Integer>();
	
			String addToken; String result = "";
		    String name = (String) dock1.get( "dnum" );
		    String address = (String) dock1.get( "websiteURL" );
		    String text = (String) dock1.get( "text" );
		    text = text.toLowerCase().trim();
		    StringTokenizer text1 = new StringTokenizer(text," \t\n\f\r|/»”’!@#$%^&*()_+-.={}][';:\\\",?><-~`");
			while(text1.hasMoreTokens()){
				
				addToken = text1.nextToken();
				if(sw.check(addToken)!=true)
				result = result + addToken + " ";
			}
		    text = result.replaceAll("»", "");
		    text = text.replaceAll(" [^a-z0-9]", "");
		    
		    System.out.println(text);
		 
		  String[] words = text.split(" ");
		
		  int i;
		 
		  for (i = 0; i < words.length; i++) 
		  {
		  	// code line credit to stackoverflow.com
		     counttokens.put((words[i]),counttokens.get(words[i]) == null?1:(counttokens.get(words[i])+1));
		   }
		  for(Map.Entry<String,Integer> entry : counttokens.entrySet()) {
			  String key = entry.getKey();
			  Integer value = entry.getValue();

			  System.out.println(key + " => " + value);
			}
System.out.println(counttokens);

	}
	}
}
