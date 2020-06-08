package opensrcproject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataParser {
	DataParser(String data) {
		try {
			JSONParser jsonParse = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParse.parse(data);
			JSONArray resultsObj = (JSONArray) jsonObj.get("results");
			String dataArray = (String) resultsObj.get(0);

			JSONObject temp = (JSONObject) jsonParse.parse(dataArray);
			JSONArray array = (JSONArray) temp.get("data");
			for(int i =0;i<array.size();i++) {
				JSONObject resultObject = (JSONObject) array.get(i);
				System.out.println(resultObject.get("period"));
				System.out.println(resultObject.get("ratio"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
