package headerTest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GetAPIHeaders {
	
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
	public void checkHeadersContentType1() throws ClientProtocolException, IOException{
		HttpGet get = new HttpGet(BASE_URL);
		response = client.execute(get);
		
		Header headerValue = response.getEntity().getContentType();
		assertEquals(headerValue.getValue(), ".application/json; charset=utf-8");
	}
	
	@Test
	public void checkHeadersContentType2() throws ClientProtocolException, IOException{
		HttpGet get = new HttpGet(BASE_URL);
		response = client.execute(get);
		
		ContentType ct = ContentType.getOrDefault(response.getEntity());
		assertEquals(ct.getMimeType(), "application/json");
	}
	
	@Test
	public void checkAllHeaders()throws ClientProtocolException, IOException{
		HttpGet get = new HttpGet(BASE_URL);
		response = client.execute(get);
		
		String headerValue = getHeader(response ,"Server");
		
		System.out.println("***************The header value is  : "+ headerValue + "  **********************");
		
		String newValue = getHeader(response, "X-RateLimit-Limit");
		assertEquals(newValue, "60");
	}
	
	@Test
	public void checkPresenceofHeader() throws ClientProtocolException, IOException{
		HttpGet get = new HttpGet(BASE_URL);
		response = client.execute(get);
		boolean present = checkHeaderpresent(response,"E1Tag");
		System.out.println("Is the ETag present ? :"+present);
	}


	private String getHeader(CloseableHttpResponse response2, String headerName) {
		// Get all the Headers.
		Header headersArray[] = response2.getAllHeaders();
		List<Header> httpHeaders = Arrays.asList(headersArray);
		
		System.out.println("The list of the httpHeaders is : \n"+httpHeaders+ "\n");
		String rtheader = "";
		
		// Loop over the headers list
		for(Header h : httpHeaders){
			if(headerName.equalsIgnoreCase(h.getName())){
				rtheader = h.getValue();
			}
		}
		
		// If no header found , throw an exception.
		if(rtheader.isEmpty())
			throw new RuntimeException("Could not find the header : "+ headerName);
		
		// Return the header.
		return rtheader;
	}
	
	private boolean checkHeaderpresent(CloseableHttpResponse response2, String headerName) {
		// Get all the Headers.
				Header headersArray[] = response2.getAllHeaders();
				List<Header> httpHeaders = Arrays.asList(headersArray);
				
			//	System.out.println("The list of the httpHeaders is : \n"+httpHeaders+ "\n");
			//	String rtheader = "";
				
				// Loop over the headers list
				for(Header h : httpHeaders){
					if(headerName.equalsIgnoreCase(h.getName())){
						return true;
					}
				}
				
				// If no header found return false.
				return false;
	}


}
