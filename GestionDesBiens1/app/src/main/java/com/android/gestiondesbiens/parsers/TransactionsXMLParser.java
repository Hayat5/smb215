package com.android.gestiondesbiens.parsers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.gestiondesbiens.model.Transactions;


public class TransactionsXMLParser {

	public static List<Transactions> parseFeed(String content) {
		
		try {
			
		    boolean inDataItemTag = false;
		    String currentTagName = "";
		    Transactions transactions = null;
		    List<Transactions> transactionsList = new ArrayList<>();

		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    XmlPullParser parser = factory.newPullParser();
		    parser.setInput(new StringReader(content));

		    int eventType = parser.getEventType();

		    while (eventType != XmlPullParser.END_DOCUMENT) {

		        switch (eventType) {
		            case XmlPullParser.START_TAG:
		                currentTagName = parser.getName();
		                if (currentTagName.equals("transaction")) {
		                    inDataItemTag = true;
		                    transactions = new Transactions();
		                    transactionsList.add(transactions);
		                }
		                break;

		            case XmlPullParser.END_TAG:
		                if (parser.getName().equals("transaction")) {
		                    inDataItemTag = false;
		                }
		                currentTagName = "";
		                break;

		            case XmlPullParser.TEXT:
		                if (inDataItemTag && transactions != null) {
		                    switch (currentTagName) {
			                    case "itemId":
		                        	transactions.setItemId(Integer.parseInt(parser.getText()));
		                            break;
			                    case "itemCode":
		                        	transactions.setItemCode(parser.getText());
		                            break;
			                    case "itemName":
		                        	transactions.setItemName(parser.getText());
		                            break;
			                    case "itemSpecification":
		                        	transactions.setItemSpecification(parser.getText());
		                            break;
			                    case "locationId/centerName":
		                        	transactions.setCenterSrcName(parser.getText());
		                            break;
			                    case "locationId/salleName":
		                        	transactions.setSalleSrcName(parser.getText());
		                            break;
			                    case "locationId/personnelName":
		                        	transactions.setPersonnelSrcName(parser.getText());
		                            break;
		                        
		                       case "locationIdDest.centerName":
		                        	transactions.setCenterDesName(parser.getText());
		                        	break;
		                        case "locationIdDest.salleName":
		                        	transactions.setSalleDesName(parser.getText());
		                        	break;
		                        case "locationIdDest.personnelName":
		                        	transactions.setPersonnelDesName(parser.getText());
		                        	break;
		                        
		                        case "transactionDate":
		                        	transactions.setTransactionDateCreated(parser.getText());
		                        	break;
		                        case "status":
		                        	transactions.setStatus(parser.getText());
		                        	break;
		                        case "name":
		                        	transactions.setName(parser.getText());
		                        	break;
		                        

		                    }
		                }
		                break;
		        }

		       eventType = parser.next();

		    }

		    return transactionsList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

		
	}
	
}

