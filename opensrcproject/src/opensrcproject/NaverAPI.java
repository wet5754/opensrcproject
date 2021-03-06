package opensrcproject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NaverAPI {
	private String responseBody;
	private DataParser data;
	private ArrayList temp;

	public NaverAPI(String keyword) {
		temp = new ArrayList<>();
        for(int i=0;i<244;i++) {
        	temp.add((double)1.0);
        }
        String clientId = "HJkHxgJ4FbnBrihGey_o"; // 애플리케이션 클라이언트 아이디
        String clientSecret = "txaBaD4bW5"; // 애플리케이션 클라이언트 시크릿

        String apiUrl = "https://openapi.naver.com/v1/datalab/search";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        requestHeaders.put("Content-Type", "application/json");
        // 임시
        String[] keywordarr = new String[1];
        keywordarr[0] = "start";
        
        if(keyword!=null) {
        	keywordarr =keyword.split(" ");  
        }
        
        String requestBody = "{\"startDate\":\"2019-10-01\"," +
                "\"endDate\":\"2020-05-31\"," +
                "\"timeUnit\":\"date\","+"\"keywordGroups\":[{\"groupName\":"+"\""+keywordarr[0]+"\"," + "\"keywords\":[\"";
        for(int i =0;i<keywordarr.length;i++) 
            requestBody+= keywordarr[i]+"\",\"";
        requestBody+= keywordarr[0]+"\"]}," +
                "{\"groupName\":\"영어\"," + "\"keywords\":[\"영어\",\"english\"]}]," +
                "\"device\":\"pc\"," +
                "\"ages\":[\"1\",\"2\"]," +
                "\"gender\":\"f\"}";

        responseBody = post(apiUrl, requestHeaders, requestBody);
        System.out.println(keyword+responseBody);
        
        data = new DataParser(responseBody);

        for(int i=0;i<data.getR().size();i++) {
        	if(i==244) break;
//        	if((double)data.getR().get(i)==1.0) tempcount++;
        	temp.set(i, (double)data.getR().get(i));
        }
        for(int i=0;i<244;i++) {
        	if((double)temp.get(i)==1.0) {
        		temp.remove(i);
            	long seed = System.currentTimeMillis();
            	Random ran = new Random(seed-i);
            	int c = ran.nextInt(i);
//            	System.out.println(temp.get(c)+"-----------------------"+temp.size());
            	temp.add(c, temp.get(c));
//            	System.out.println(temp.get(c)+"======================="+temp.size());
        	}
        }
//        System.out.println(data.getR().size());
//        System.out.println(temp.size());
	}

	public ArrayList getR() {
		return this.temp;
	}
	public String getResponse() {
		return this.responseBody;
	}

    private static String post(String apiUrl, Map<String, String> requestHeaders, String requestBody) {
        HttpURLConnection con = connect(apiUrl);

        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(requestBody.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect(); // Connection을 재활용할 필요가 없는 프로세스일 경우
        }
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}