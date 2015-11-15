package com.test.test;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class getTFIDF {
	public static Map<String, Double> calculate =new HashMap<String, Double>();
	
	
	public Map<String,Double> computeTFIDF(String SearchWord) throws UnknownHostException{
	
			
	MongoClient m = new MongoClient("localhost", 27017);
	DB db = m.getDB( "CheckData4" );
	DBCollection coll = db.getCollection("TF_IDF");
	
	// create an empty query
	BasicDBObject query = new BasicDBObject(); 
	
	query.put("word",SearchWord);
    DBCursor cur = coll.find(query);

     while(cur.hasNext()){
	
	 BasicDBObject obj = (BasicDBObject)cur.next();
	 
	
	//String[] process = (String[]) obj.get("positions");
	String str=cur.curr().get("TF_IDF").toString();
    String str1=cur.curr().get("docNum").toString();
   
    double TF_IDF = Double.parseDouble(str);
    
	calculate.put(str1,TF_IDF);


	
	}
 	return calculate;
	}	
	
	
}
