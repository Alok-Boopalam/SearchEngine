package com.test.test;

import java.net.UnknownHostException;

import com.google.common.collect.Multimap;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

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
public class secondWord {

	public static Multimap<Integer, Integer> docpos1  = ArrayListMultimap.create();
	public static Multimap<Integer, Integer> docpos2  = ArrayListMultimap.create();
	public static ArrayList<String> toreturn = new ArrayList<String>();
	public ArrayList<String> fnSearch(String strIn) throws UnknownHostException{
	MongoClient m = new MongoClient("localhost", 27017);
	DB db = m.getDB( "CheckDatanew" );
	DBCollection coll = db.getCollection("basic");

	// create an empty query
	BasicDBObject query = new BasicDBObject(); 
	
	// configure fields to be returned (true/1 or false/0 will work)
	// YOU MUST EXPLICITLY CONFIGURE _id TO NOT SHOW
	
///Scanner in = new Scanner(System.in);
//System.out.println("Enter Search word: ");
	//String strIn = in.nextLine();
	strIn = strIn.toLowerCase();
	String[] bifurcate = strIn.split(" ");
	
   
		
		query.put("word",bifurcate[0]);
	    DBCursor cur = coll.find(query);

	     while(cur.hasNext()){
		
		 BasicDBObject obj = (BasicDBObject)cur.next();
		 
		
		//String[] process = (String[]) obj.get("positions");
		String str=cur.curr().get("positions").toString();
	    String str1=cur.curr().get("docNum").toString();
	    int docID = Integer.valueOf(str1);
		String process1 = str.replace("[", "").replace("]","").replace(" ","");
		String[] process2 = process1.split(",");
		for(int j = 0; j< process2.length; j++){
			String temp = process2[j];
		int temp1 = Integer.valueOf(temp);
	    
		docpos1.put(docID,temp1);
		
			
		}
		}
	//	System.out.println(docpos1);
		int flag = 0;
		if(bifurcate.length>1){

			
			
			query.put("word",bifurcate[1]);
		    DBCursor cur1 = coll.find(query);

		     while(cur1.hasNext()){
			
			 BasicDBObject obj = (BasicDBObject)cur1.next();
			 
						
			//String[] process = (String[]) obj.get("positions");
			String str=cur1.curr().get("positions").toString();
		    String str1=cur1.curr().get("docNum").toString();
		    String str2=cur1.curr().get("webURL").toString();
		    int docID = Integer.valueOf(str1);
			String process1 = str.replace("[", "").replace("]","").replace(" ","");
			String[] process2 = process1.split(",");
			for(int j = 0; j< process2.length; j++){
				String temp = process2[j];
			int temp1 = Integer.valueOf(temp);
			int temp2 = temp1-1;
			if(docpos1.containsKey(docID)&&docpos1.get(docID).contains(temp2)){
				if(flag == 0){
				//System.out.println(str2); 
					flag++;
				}
			docpos2.put(docID,temp1);
			toreturn.add(str1);
			}
				
			}
			flag=0;
			}
		     		}
		return toreturn;
		
		
	}
	   
	}


