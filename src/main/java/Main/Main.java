package Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class Main {

	public static void main(String[] args) {
		
		SpringApplication.run(Main.class, args);
		System.out.println("i am in");

	}

}
