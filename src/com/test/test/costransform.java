//This code has been written with the help from  http://computergodzilla.blogspot.com/2013/07/how-to-calculate-tf-idf-of-document.html
package com.test.test;





import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

  public class costransform {

public static double cosineTransform(String[] query, String[] document) {
    Map<String, Integer> queryCountMap = new HashMap<String, Integer>();
    Map<String, Integer> documentCountMap = new HashMap<String, Integer>();
    Set<String> uniqueSet = new HashSet<String>();
    Integer temp = null;
    for (String queryWord : query) {
        temp = queryCountMap.get(queryWord);
        if (temp == null) {
            queryCountMap.put(queryWord, 1);
            uniqueSet.add(queryWord);
        } else {
            queryCountMap.put(queryWord, temp + 1);
        }
    }
    for (String docWord : document) {
        temp = documentCountMap.get(docWord);
        if (temp == null) {
            documentCountMap.put(docWord, 1);
            uniqueSet.add(docWord);
        } else {
            documentCountMap.put(docWord, temp + 1);
        }
    }
    int[] queryVector = new int[uniqueSet.size()];
    int[] documentVector = new int[uniqueSet.size()];
    int index = 0;
    Integer tempCount = 0;
    for (String uniqueWord : uniqueSet) {
        tempCount = queryCountMap.get(uniqueWord);
        queryVector[index] = tempCount == null ? 0 : tempCount;
        tempCount = documentCountMap.get(uniqueWord);
        documentVector[index] = tempCount == null ? 0 : tempCount;
        index++;
    }
    return cosineCalculate(queryVector, documentVector);
}


private static double cosineCalculate(int[] queryVector,
        int[] documentVector) {
    if (queryVector.length != documentVector.length)
        return 1;
    double dotProduct = 0;
    double queryNorm = 0;
    double docNorm = 0;
    for (int i = 0; i < queryVector.length; i++) {
        dotProduct += queryVector[i] * documentVector[i];
        queryNorm += queryVector[i] * queryVector[i];
        docNorm += documentVector[i] * documentVector[i];
    }

    double result = dotProduct
            / (Math.sqrt(queryNorm) * Math.sqrt(docNorm));
    return result;
}

public double findCos(String[] docvector,String[] query) {
    //Take query input
	
	//find all document numbers in which first two words exist
	
	//go to these document numbers and retrieve one by one and calculate cosine transform
	// Store docNum and cosine value in hashmap
	
	//Sort hashmap and pick highest 5 cosine values
	double ans = cosineTransform(query,docvector);
	//System.out.println(ans);
	return ans;
	
	
}
}
