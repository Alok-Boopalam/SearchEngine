package irmongodb;



import java.io.IOException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;


public class querydriver {
//	public static SetMultimap<String, String> invertedonly  = HashMultimap.create();
//	public static Multimap<String, Integer> inverted  = ArrayListMultimap.create();
	 
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException{
		StringBuilder sb = new StringBuilder();
		
		int count = 0;
		DB db = (new MongoClient("localhost", 27017)).getDB("irdatabaseTest");
		DBCollection dbCollection = db.getCollection("Channel4");
	    stopwords removeStopWords= new stopwords();	
	    String addToken; String result = "";
	    String docNum = null;String text;String webURL;
	    Mongo m1 = null;
		DB dbDup1 = null;
		Map<String,Integer> wordFreq = new TreeMap<String,Integer>();
		
	    //Process tuple by tuple
	    for(DBObject dock1 : dbCollection.find() ) {
			
	    	 //Obtaining attributes of tuple in dock1
		     docNum = (String) dock1.get( "dnum" );
		     webURL = (String) dock1.get( "websiteURL" );
		     text = (String) dock1.get( "text" );

		     //Tokenize the text by removing stop words    
		    text = text.toLowerCase().trim();
		    StringTokenizer text1 = new StringTokenizer(text," \t\n\f\r|/»”’!@#=?$%^&*()_+-.={}][';:\\\",?><-~`");
		    count = 0;
			while(text1.hasMoreTokens()){
				count++;
				addToken = text1.nextToken();
				if(removeStopWords.check(addToken)!=true)
				result = result + addToken + " ";
			}
		    text = result.replaceAll("»", "");
		    result = "";
		    text = text.replaceAll(" [^a-z0-9]", "");
		  
		   try{
				m1 = new Mongo("localhost");
				dbDup1 = m1.getDB("rawdata");
				
				DBCollection dbCollection1 = dbDup1.getCollection("channel");
				BasicDBObject basicDBObject = new BasicDBObject();
					
					
					basicDBObject.put("docNum",docNum );
					System.out.println(docNum);
					basicDBObject.put("webURL",webURL );
					System.out.println(webURL);
					basicDBObject.put("doclength", count);
				
					basicDBObject.put("text", text);
					
					dbCollection1.insert(basicDBObject);
				
				}catch (Exception e){
					e.printStackTrace();
				}
   		 m1.close();
			}  
		   
	}
}
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   //Store text in a String Array
		    /*String[] words = text.split(" ");
		    int position=0;
		    for (int i = 0; i < words.length; i++) {
		    	Integer Freq = wordFreq.get(words[i]);  
				  if(Freq==null)
					  wordFreq.put(words[i], 1);
				  else
					  wordFreq.put(words[i], Freq+1);
		    	
		      position = i+1;
		      //To store word and corresponding position
		      inverted.put(words[i], position);
		      System.out.println(words[i]);
		      //To store a word and document number it occurs in
		    	System.out.println(words[i]);
		      invertedonly.put(words[i], docNum);
		      
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		     }
		 
		   for(String key: inverted.keySet()){
				if(inverted.get(key)==null||wordFreq.get(key)==null) continue;
				else{
	    		 try{
	 				m1 = new Mongo("localhost");
	 				dbDup1 = m1.getDB("forf2f");
	 				
	 				DBCollection dbCollection1 = dbDup1.getCollection("inverted");
	 				BasicDBObject basicDBObject = new BasicDBObject();
	 					
	 					basicDBObject.put("word", key);
	 					System.out.println(key);
	 					basicDBObject.put("docNum",docNum );
	 					System.out.println(docNum);
	 					basicDBObject.put("webURL",webURL );
	 					System.out.println(webURL);
	 					basicDBObject.put("wordFreq", wordFreq.get(key));
	 					System.out.println(wordFreq.get(key));
	 					basicDBObject.put("positions", inverted.get(key));
	 					
	 					dbCollection1.insert(basicDBObject);
	 				
	 				}catch (Exception e){
	 					e.printStackTrace();
	 				}
	    		 m1.close();
				}
	    		 
		    }
		    //wordFreq.clear();
		    //inverted.clear();
		    
	    }
	    
	    /* 
		    Mongo m = null;
			DB dbDup = null;
		    for(String key: invertedonly.keySet()){
				if(invertedonly.get(key)==null) continue;
				else{
		    		 try{
		 				m = new Mongo("localhost");
		 				dbDup = m.getDB("forf2f");
		 				
		 				DBCollection dbCollection1 = dbDup.getCollection("invertedlistonly");
		 				BasicDBObject basicDBObject = new BasicDBObject();
		 					
		 					basicDBObject.put("word", key);
		 					System.out.println(key);
		 					basicDBObject.put("docList",invertedonly.get(key) );
		 					System.out.println(invertedonly.get(key));
		 					//basicDBObject.put("Snippet", text);
		 					dbCollection1.insert(basicDBObject);
		 				
		 				}catch (Exception e){
		 					e.printStackTrace();
		 				}
				}
		    	 m.close();
		     
		}
	    Mongo m3 = null;
		DB dbDup3 = null;
		
		    DB db2 = (new MongoClient("localhost", 27017)).getDB("forf2f");
			DBCollection dbCollection2 = db2.getCollection("inverted");
			
			String docNum1; String searchWord = null; String webURL1; int wordFrequency = 0;
			for(DBObject dock1 : dbCollection2.find() ){
				try{
				m3 = new Mongo("localhost");
 				dbDup3 = m3.getDB("forf2f");
 				DBCollection dbCollection1 = dbDup3.getCollection("tfidf");
 				BasicDBObject basicDBObject = new BasicDBObject();
				//System.out.println("Hello");
				webURL1 = (String) dock1.get("webURL");
				docNum1 = (String) dock1.get("docNum");
				searchWord = (String) dock1.get("word");
				wordFrequency = (int) dock1.get("wordFreq");
			//Retrieve a word, docNum, Its positions, all 5 fields, calculate TF-IDF
				if( invertedonly.get(searchWord).size()!=0)
				{
			double TF_IDF= (1+Math.log(wordFrequency))*( Math.log((9) / invertedonly.get(searchWord).size()));
			System.out.println(TF_IDF);
				
			basicDBObject.put("word", searchWord);
			basicDBObject.put("docNum", docNum1);
			basicDBObject.put("webURL",webURL1 );
			basicDBObject.put("TF_IDF",TF_IDF );
			
			dbCollection1.insert(basicDBObject);
				}}
				
				
			catch (Exception e){
					e.printStackTrace();
				}
			m.close();
			}
		
	}
}
*/
	

