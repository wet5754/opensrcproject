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

public class NaverAPI {
	private String responseBody;
	private DataParser data;
	private ArrayList temp;

	public NaverAPI(String keyword) {
		temp = new ArrayList<>();
        for(int i=0;i<31;i++) {
        	temp.add((double)1.0);
        }
        String clientId = "HJkHxgJ4FbnBrihGey_o"; // ���ø����̼� Ŭ���̾�Ʈ ���̵�
        String clientSecret = "txaBaD4bW5"; // ���ø����̼� Ŭ���̾�Ʈ ��ũ��

        String apiUrl = "https://openapi.naver.com/v1/datalab/search";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        requestHeaders.put("Content-Type", "application/json");
        // �ӽ�
        String[] keywordarr = new String[1];
        keywordarr[0] = "start";
        
        if(keyword!=null) {
        	keywordarr =keyword.split(" ");  
        }
        
        String requestBody = "{\"startDate\":\"2020-05-01\"," +
                "\"endDate\":\"2020-05-31\"," +
                "\"timeUnit\":\"date\","+"\"keywordGroups\":[{\"groupName\":"+"\""+keywordarr[0]+"\"," + "\"keywords\":[\"";
        for(int i =0;i<keywordarr.length;i++) 
            requestBody+= keywordarr[i]+"\",\"";
        requestBody+= keywordarr[0]+"\"]}," +
                "{\"groupName\":\"����\"," + "\"keywords\":[\"����\",\"english\"]}]," +
                "\"device\":\"pc\"," +
                "\"ages\":[\"1\",\"2\"]," +
                "\"gender\":\"f\"}";

        responseBody = post(apiUrl, requestHeaders, requestBody);
        System.out.println(keyword+responseBody);
        
        data = new DataParser(responseBody);

        for(int i=0;i<data.getR().size();i++) {
        	if(i==31) break;
        	temp.set(i, (double)data.getR().get(i));
        }

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
            if (responseCode == HttpURLConnection.HTTP_OK) { // ���� ����
                return readBody(con.getInputStream());
            } else {  // ���� ����
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API ��û�� ���� ����", e);
        } finally {
            con.disconnect(); // Connection�� ��Ȱ���� �ʿ䰡 ���� ���μ����� ���
        }
    }

    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL�� �߸��Ǿ����ϴ�. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("������ �����߽��ϴ�. : " + apiUrl, e);
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
            throw new RuntimeException("API ������ �дµ� �����߽��ϴ�.", e);
        }
    }
}