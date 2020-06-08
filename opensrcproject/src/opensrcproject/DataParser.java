package opensrcproject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataParser {
	String[] p;
	double[] r;
	DataParser(String data) {
		try {
			JSONParser jsonParse = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParse.parse(data);
			
			JSONArray resultsObj = (JSONArray) jsonObj.get("results");
			
			String tempString = resultsObj.get(0).toString();
			System.out.println(tempString);
			
			JSONObject dataArray = (JSONObject) jsonParse.parse(tempString);
			JSONArray array = (JSONArray) dataArray.get("data");
			p = new String[30];
			r = new double[30];
			for(int i =0;i<30;i++) {
				JSONObject resultObject = (JSONObject) array.get(i);
				p[i] = (String) resultObject.get("period");
				System.out.println(resultObject.get("period"));
				r[i] = (double) resultObject.get("ratio");
				System.out.println(resultObject.get("ratio"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	String[] getP() {
		return p;
	}
	double[] getR() {
		return r;
	}
}
