package sg.edu.nus.iss.day22_workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Day22WorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(Day22WorkshopApplication.class, args);

		// AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		// context.scan("sg.edu.nus.iss.day22_workshop");
		// context.refresh();

		// RsvpRepoImpl rsvpRepo = context.getBean(RsvpRepoImpl.class);
		// Integer countResult = rsvpRepo.countAll();

		// System.out.println("RSVP Count: " + countResult);
	}

}
