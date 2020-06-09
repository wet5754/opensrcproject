package opensrcproject;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataParser {
	ArrayList r;
	DataParser(String data) {
		try {
			JSONParser jsonParse = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParse.parse(data);
			
			JSONArray resultsObj = (JSONArray) jsonObj.get("results");
			
			String tempString = resultsObj.get(0).toString();
			System.out.println(tempString);
			
			JSONObject dataArray = (JSONObject) jsonParse.parse(tempString);
			JSONArray array = (JSONArray) dataArray.get("data");
			r = new ArrayList<>();
			for(int i =0;i<array.size();i++) {
				JSONObject resultObject = (JSONObject) array.get(i);
				String temp;
				if(resultObject.get("ratio") instanceof Double)
					temp = Double.toString((double) resultObject.get("ratio"));
				else
					temp = Long.toString((long) resultObject.get("ratio"));
				r.add((double)Double.parseDouble(temp));
				System.out.println(resultObject.get("ratio"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	ArrayList getR() {
		return r;
	}
}
