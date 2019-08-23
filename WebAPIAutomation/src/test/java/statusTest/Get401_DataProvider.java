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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Get401_DataProvider {
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
	
	@DataProvider
	private Object[][] endpoints(){
		return new Object[][]{
			{"/user"},
			{"/user/followers"},
			{"/notifications"}
		};
	}
	
	@Test(dataProvider="endpoints")
	public void testGet401(String ep) throws ClientProtocolException, IOException {
		
		HttpGet get = new HttpGet(BASE_URL + ep);
		response = client.execute(get);
		int actualStatusCode = response.getStatusLine().getStatusCode();
		assertEquals( actualStatusCode, 401);
	}
	
}
