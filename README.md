# SpringBootRetryPattern
Example of Retry Pattern based on Spring Boot 

Main dependencies:

    <dependencies>  
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
      </dependency>
       <dependency>
          <groupId>org.springframework.retry</groupId>
          <artifactId>spring-retry</artifactId>
      </dependency>
      <dependency>
           <groupId>org.springframework</groupId>
           <artifactId>spring-aspects</artifactId>
      </dependency>
     </dependencies>
   
   
Enabling Retry:

    @SpringBootApplication
    @EnableRetry
    public class Main {

      public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
      }
    }

Defining controller class:

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

Configuring retryable in method using @Retryable
parameters:
<ul>
   <li>     value = {Exception.class}//if any exception occurs the recover methos is triggered, you can define any exception class for example SQLException</li>
    <li>    maxAttempts = 1, //attempts alloweds if any exception occurs before trigger the recover method</li>
     <li>   backoff = @Backoff(1000)// setting the time for attempts</li>
<ul>

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
   
  Defining recover method for Exception class errors in home method:
  
        @Recover//defining recover method to Exceptions errors
        public String recover1(Exception e) {

          return "from recover method1";
        }

Behavior:

If any error occurs in the home method call, the recover method recover 1 is triggered, you can make a test setting wrong the call to jsonplaceholder api, for example writing the api endpoint wrong, and the answer will be

"from recover method1"
