package statusTest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Get401 {
	public static final String BASE_URL = "https://api.github.com";
	CloseableHttpClient client ;
	CloseableHttpResponse response;
	
	@BeforeMethod
	public void setup(){
		client =  HttpClientBuilder.create().build();
	}
	
	
	@AfterMethod
	public void tearDown() throws IOException{
		client.close();
		response.close();
	}
	@Test
	public void testGet401() throws ClientProtocolException, IOException {
		
		HttpGet get = new HttpGet(BASE_URL + "/user");
		response = client.execute(get);
		int actualStatusCode = response.getStatusLine().getStatusCode();
		assertEquals( actualStatusCode, 401);
	}
	@Test
	public void testFollowers401() throws ClientProtocolException, IOException {
		
		HttpGet get = new HttpGet(BASE_URL + "/user/followers");
		response = client.execute(get);
		int actualStatusCode = response.getStatusLine().getStatusCode();
		assertEquals(actualStatusCode,401);
	}
	@Test
	public void testnotifications401() throws ClientProtocolException, IOException {
		
		HttpGet get = new HttpGet(BASE_URL + "/notifications");
		response = client.execute(get);
		int actualStatusCode = response.getStatusLine().getStatusCode();
		assertEquals(actualStatusCode,402);
	}
}
