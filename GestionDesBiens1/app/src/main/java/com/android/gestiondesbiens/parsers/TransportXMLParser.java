package com.android.gestiondesbiens.parsers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.gestiondesbiens.model.Transport;


public class TransportXMLParser {

	public static List<Transport> parseFeed(String content) {
		
		try {
			
		    boolean inDataItemTag = false;
		    String currentTagName = "";
		    Transport transport = null;
		    List<Transport> transportList = new ArrayList<>();

		    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    XmlPullParser parser = factory.newPullParser();
		    parser.setInput(new StringReader(content));

		    int eventType = parser.getEventType();

		    while (eventType != XmlPullParser.END_DOCUMENT) {

		        switch (eventType) {
		            case XmlPullParser.START_TAG:
		                currentTagName = parser.getName();
		                if (currentTagName.equals("transport")) {
		                    inDataItemTag = true;
		                    transport = new Transport();
		                    transportList.add(transport);
		                }
		                break;

		            case XmlPullParser.END_TAG:
		                if (parser.getName().equals("transport")) {
		                    inDataItemTag = false;
		                }
		                currentTagName = "";
		                break;

		            case XmlPullParser.TEXT:
		                if (inDataItemTag && transport != null) {
		                    switch (currentTagName) {
		                        case "transportId":
		                        	transport.setTransportId(Integer.parseInt(parser.getText()));
		                            break;
		                        case "personnelName":
		                        	transport.setPersonnelName(parser.getText());
		                        	break;
		                        case "transportDate":
		                        	transport.setTransportDate(parser.getText());
		                        	break;
		                        

		                    }
		                }
		                break;
		        }

		       eventType = parser.next();

		    }

		    return transportList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 

		
	}
	
}

