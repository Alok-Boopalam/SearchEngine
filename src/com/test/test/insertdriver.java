package irmongodb;

import java.net.UnknownHostException;

import com.mongodb.*;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class insertdriver  {
	public static void main(String[] args) throws UnknownHostException{
	DB db = (new MongoClient("localhost", 27017)).getDB("irmongodatabase1");
	DBCollection dbCollection = db.getCollection("Channel");
	BasicDBObject basicDBObject = new BasicDBObject();
	basicDBObject.put("dnum", "1");
	basicDBObject.put("websiteURL", "yahoo.com");
	basicDBObject.put("text", "abcd");
	dbCollection.insert(basicDBObject);

}
}
