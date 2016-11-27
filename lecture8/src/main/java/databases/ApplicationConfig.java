package databases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Created by tom on 22.11.2016.
 */
@SpringBootApplication
public class ApplicationConfig {

    /**
     * spring databases.springjdbc startup
     * @param args commandline arguments
     */
    public static void main(String args[]) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
