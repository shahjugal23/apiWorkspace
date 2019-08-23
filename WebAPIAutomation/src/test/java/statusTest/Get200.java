package statusTest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Get200 {

	public static final String BASE_URL = "https://api.github.com";
	CloseableHttpClient client ;
	CloseableHttpResponse response;
	
	@BeforeMethod
	public void setup(){
		client =  HttpClientBuilder.create().build();
	}
	
	@Test
	public void testGet200() throws ClientProtocolException, IOException {
		
		HttpGet get = new HttpGet(BASE_URL);
		response = client.execute(get);
		int actualStatusCode = response.getStatusLine().getStatusCode();
		assertEquals(actualStatusCode,200);
	}
	@Test
	public void testrateLimits() throws ClientProtocolException, IOException {
		
		HttpGet get = new HttpGet(BASE_URL + "/rate_limit");
		HttpResponse response = client.execute(get);
		int actualStatusCode = response.getStatusLine().getStatusCode();
		assertEquals(actualStatusCode,200);
	}
	@Test
	public void testSearch() throws ClientProtocolException, IOException {
		
		HttpGet get = new HttpGet(BASE_URL +"/search/repositories?q=java");
		HttpResponse response = client.execute(get);
		int actualStatusCode = response.getStatusLine().getStatusCode();
		assertEquals(actualStatusCode,200);
	}
	@AfterMethod
	public void tearDown() throws IOException{
		client.close();
		response.close();
	}
}
