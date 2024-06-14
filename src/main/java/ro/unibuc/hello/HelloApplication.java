package ro.unibuc.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ro.unibuc.hello.data.GameEntity;
import ro.unibuc.hello.data.GameRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = GameRepository.class)
public class HelloApplication {

	@Autowired
	private GameRepository gameRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		System.out.println("Run");
		gameRepository.deleteAll();

	GameEntity firstData = new GameEntity();
	firstData.GameName = "Assassin's Creed Origins";
	firstData.Publisher = "UBISOFT";
	firstData.Review = "9/10";

	gameRepository.save(firstData);

	}

}
