package com.test.test;


	import java.net.UnknownHostException;
import java.util.*;
import java.util.Map.Entry;









	import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;


	public class tf_idfSearch {
		
		public static String strIn= "";
		//public static Multimap<Integer, Integer> docpos1  = ArrayListMultimap.create();
		//public static Multimap<Integer, Integer> docpos2  = ArrayListMultimap.create();
		
		public static List<String> docList = new ArrayList<String>();
		
		@SuppressWarnings("unchecked")
		public ArrayList<String> fnSearch(String searchQuery) throws UnknownHostException{
			
			costransform x = new costransform();
		Map<String, Double> firstWord =new HashMap<String, Double>();
		Map<String, Double> secondWord =new HashMap<String, Double>();
		Map<String, Double> finallist =new HashMap<String, Double>();
		Map<String, Double> cosList =new HashMap<String, Double>();
		Map<String, Double> TFCosList =new HashMap<String, Double>();
		ArrayList<String> URL = new ArrayList<String>();
		// configure fields to be returned (true/1 or false/0 will work)
		// YOU MUST EXPLICITLY CONFIGURE _id TO NOT SHOW
		
		strIn = searchQuery;
		String[] bifurcate = strIn.split(" ");
		
	   
			
			getTFIDF obj1 = new getTFIDF();
			
		 firstWord = obj1.computeTFIDF(bifurcate[0]);
		
		 
		 if(bifurcate.length>1)
		 secondWord = obj1.computeTFIDF(bifurcate[1]);	
			
			
		     if(bifurcate.length==1){
		    	  
		    	 Map<String,Double> sorteddoc1 = new HashMap<String,Double>();
		     	List<String> docNumList = new ArrayList<String>(firstWord.keySet());
		       	MongoClient m5 = new MongoClient("localhost", 27017);
			 	DB db5= m5.getDB( "rawdata" );
			 	DBCollection coll5 = db5.getCollection("channel");
			 	
			 	for(int i=0; i<docNumList.size(); i++){
			 		
			 		BasicDBObject query1 = new BasicDBObject(); 
			 		query1.put("docNum",docNumList.get(i));
			 		DBCursor cur5 = coll5.find(query1);
				 	
				 	 while(cur5.hasNext()){
				 		
						 BasicDBObject obj = (BasicDBObject)cur5.next();
						 String str=cur5.curr().get("text").toString();
						 String docVector[] = str.split(" ");
						double ans = x.findCos(docVector,bifurcate); 	
						cosList.put(docNumList.get(i), ans);
						
						
				 	 }
				 	 
			 	}
				for(String docNum1 : firstWord.keySet())
		    	{
		    		for(String docNum2 :cosList.keySet())
		    		{
		    			if(docNum1==docNum2)
		    			{
		    				double sum = firstWord.get(docNum1)*cosList.get(docNum2);
		    				TFCosList.put(docNum1,sum);
		    			}
		    			else continue;
		    		}
		    		
		    	}
			 	
			 	
		    	 sorteddoc1 = sortMyMap(TFCosList);
		    	 List<String> toPrint = new ArrayList<String>(sorteddoc1.keySet());
		    int count = toPrint.size();
		    if (count < 5)
		    {
		    	 	    
		    	MongoClient m1 = new MongoClient("localhost", 27017);
			 	DB db1= m1.getDB( "irdatabaseTest" );
			 	DBCollection coll1 = db1.getCollection("Channel4");
			 	BasicDBObject query = new BasicDBObject();
			 	
			 	for(int i=0; i<toPrint.size(); i++){
			 	query.put("dnum",toPrint.get(i));
			 	DBCursor cur2 = coll1.find(query);
			 	String str = "";
			 	 while(cur2.hasNext()){
			 		
					 BasicDBObject obj = (BasicDBObject)cur2.next();
					 str=cur2.curr().get("websiteURL").toString();
					
			 	 }
			 	System.out.println(str);
			 	URL.add(str);
		    }
		    }
			 	else{
		    toPrint =  toPrint.subList(0,10);
		    System.out.println(toPrint);
		    MongoClient m1 = new MongoClient("localhost", 27017);
		 	DB db1= m1.getDB( "irdatabaseTest" );
		 	DBCollection coll1 = db1.getCollection("Channel4");
		 	BasicDBObject query1 = new BasicDBObject();
		 	for(int i=0; i<toPrint.size(); i++){
		 	query1.put("dnum",toPrint.get(i));
		 	DBCursor cur2 = coll1.find(query1);
		 	String str = "";
		
		 	 while(cur2.hasNext()){
		 		
				 BasicDBObject obj = (BasicDBObject)cur2.next();
				 str=cur2.curr().get("websiteURL").toString();
				
		 	 }
		 	System.out.println(str);
		 	URL.add(str);
		 	}
		 
		     }
		    }
		     
		     else
		     {
		    	 ArrayList<String> toPrint1 = new ArrayList<String>();
			 	 secondWord obj3 = new secondWord();
			 	 toPrint1= obj3.fnSearch(strIn);
		    	
		    	for(String docNum1 : firstWord.keySet())
		    	{
		    		for(String docNum2 :secondWord.keySet())
		    		{
		    			if(docNum1==docNum2 && toPrint1.contains(docNum1))
		    			{
		    				double sum = firstWord.get(docNum1)+secondWord.get(docNum2);
		    				finallist.put(docNum1,sum);
		    			}
		    			else continue;
		    		}
		    		
		    	}
		    	
		    	List<String> docNumList = new ArrayList<String>(finallist.keySet());
		       	MongoClient m5 = new MongoClient("localhost", 27017);
			 	DB db5= m5.getDB( "rawdata" );
			 	DBCollection coll5 = db5.getCollection("channel");
			 	
			 	for(int i=0; i<docNumList.size(); i++){
			 		
			 		BasicDBObject query1 = new BasicDBObject(); 
			 		query1.put("docNum",docNumList.get(i));
			 		DBCursor cur5 = coll5.find(query1);
				 	
				 	 while(cur5.hasNext()){
				 		
						 BasicDBObject obj = (BasicDBObject)cur5.next();
						 String str=cur5.curr().get("text").toString();
						 String docVector[] = str.split(" ");
						double ans = x.findCos(docVector,bifurcate); 	
						cosList.put(docNumList.get(i), ans);
						
						
				 	 }
				 	 
			 	} 	 
		 	   
		 	 
		 	 	for(String docNum1 : finallist.keySet())
		    	{
		    		for(String docNum2 :cosList.keySet())
		    		{
		    			if(docNum1==docNum2)
		    			{
		    				double sum = finallist.get(docNum1)*cosList.get(docNum2);
		    				TFCosList.put(docNum1,sum);
		    			}
		    			else continue;
		    		}
		    		
		    	}
		 	   
		 	   
		 	 	TFCosList = sortMyMap(TFCosList);
		 	  List<String> toPrint = new ArrayList<String>(TFCosList.keySet());
		 	
		 	  System.out.println(finallist);
		 	  System.out.println(cosList);
		 	  System.out.println(TFCosList);
		 	 
		    	int count = toPrint.size();
			    if (count < 5)
			    {
		 	
		 	 MongoClient m1 = new MongoClient("localhost", 27017);
			 	DB db1= m1.getDB( "irdatabaseTest" );
			 	DBCollection coll1 = db1.getCollection("Channel4");
			 	BasicDBObject query3 = new BasicDBObject();
			 	
			 	for(int i=0; i<toPrint.size(); i++){
			 	query3.put("dnum",toPrint.get(i));
			 	DBCursor cur2 = coll1.find(query3);
			 	String str = "";
			 	 while(cur2.hasNext()){
			 		
					 BasicDBObject obj = (BasicDBObject)cur2.next();
					 str=cur2.curr().get("websiteURL").toString();
					
			 	 }
			 	System.out.println(str);
			 	URL.add(str);
		    } 
		    	 
		    	 
		     
		     }
			 	else{
		    toPrint =  toPrint.subList(0,10);
		    System.out.println(toPrint);
		    MongoClient m1 = new MongoClient("localhost", 27017);
		 	DB db1= m1.getDB( "irdatabaseTest" );
		 	DBCollection coll1 = db1.getCollection("Channel4");
		 	BasicDBObject query6 = new BasicDBObject();
		 	for(int i=0; i<toPrint.size(); i++){
		 	query6.put("dnum",toPrint.get(i));
		 	DBCursor cur2 = coll1.find(query6);
		 	String str = "";
		 	 while(cur2.hasNext()){
		 		
				 BasicDBObject obj = (BasicDBObject)cur2.next();
				 str=cur2.curr().get("websiteURL").toString();
				
		 	 }
		 	System.out.println(str);
		 	URL.add(str);
		 	
		 	}
		 
			 	}
		     }
			return URL;
		    
		     		
		    		
		}
		//The idea of sorting within a hashmap was obtained from the internet (http://stackoverflow.com/questions/780541/how-to-sort-a-hashmap-in-java)
		public Map<String,Double> sortMyMap(Map<String, Double> M){
			int i = 0;
			   Set<Entry<String, Double>> sortMySet = M.entrySet();
		        List<Entry<String, Double>> listSortMySet = new ArrayList<Entry<String, Double>>(sortMySet);
		        Collections.sort( listSortMySet, new Comparator<Map.Entry<String, Double>>()
		        {
		            public int compare( Map.Entry<String, Double> obj1, Map.Entry<String, Double> obj2 )
		            {
		                return (obj2.getValue()).compareTo( obj1.getValue() );
		            }
		        } );
		        Map<String, Double> getSorted = new LinkedHashMap<String, Double>();
		        
		        for (Map.Entry<String, Double> Freq : listSortMySet)
		            getSorted.put(Freq.getKey(), Freq.getValue());
		        
		       
		        
		        
		        return getSorted;
		}

		   
		}




