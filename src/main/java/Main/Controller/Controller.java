package Main.Controller;

import java.sql.SQLException;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {


	
	@GetMapping(value = "/")
	@Retryable(
		      value = {Exception.class}, 
		      maxAttempts = 1,
		      backoff = @Backoff(1000))//defining retry configuration
	public String home() {
		
		RestTemplate restTemplate = new RestTemplate(); //creating RestTemplate instance
		
		ResponseEntity<String> request_body = restTemplate.exchange("https://jsonplaceholder.typicode.com/posts/1", 
				HttpMethod.GET, null, String.class );//making a request to jsonplacholder api
		
		String answer = request_body.getBody();
		
		return answer;
	}

	@Recover//defining recover method to SQLExceptions errors
	public String recover(SQLException e) {
		
		return "from recover method";
	}
	@Recover//defining recover method to Exceptions errors
	public String recover1(Exception e) {
		
		return "from recover method2";
	}

	
}
