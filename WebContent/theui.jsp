<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*"%>
<%@ page import="com.mongodb.*"%>
<%@ page import="com.test.test.linkjava"%>
<%@ page import="com.test.test.tf_idfSearch"%>
<%@ page import="com.test.test.costransform"%>
<%@ page import="com.test.test.getTFIDF"%>
<%@ page import="com.test.test.secondWord"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Information Retrieval</title>
</head>
<body>


<img src="http://www.chem.uci.edu/~lawm/UCI%20seal%203.jpg" style="width:500px;height:300px">



<form method="post">
           
          Enter Search: <br>
          <input name= "query" type="text">
             <input type="submit" value="List"/>
</form>
<%
String query = request.getParameter("query");

      if(query!=null){  
    	               
  	       linkjava test = new linkjava();
  	       
  	       ArrayList<String> URL = new ArrayList<String>();
  	        URL= test.test(query);
  	        
  	     out.println(query);
  	            for(int i =0; i<URL.size();i++){
  	            	
  	            	//out.println("<p><a href=\"" +URL[i] +"\">" +URL[i]+ "</a></p>");
  	            	out.println("<p><a href=\"" +URL.get(i) +"\">" +URL.get(i)+ "</a></p>");
  	            } 	          
    	  	}
      %>	
      
</body>
</html>