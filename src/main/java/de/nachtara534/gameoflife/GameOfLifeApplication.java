package de.nachtara534.gameoflife;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.nachtara534.gameoflife.domain.Position;
import de.nachtara534.gameoflife.service.GameService;

@SpringBootApplication
public class GameOfLifeApplication {


    public static void main(String[] args) {
        SpringApplication.run(GameOfLifeApplication.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(GameService gameService) {
        return (args) -> {
            List<Position> startingPositions = List.of(
                    new Position(1, 0),
                    new Position(0, 0),
                    new Position(-1, 0)
            );

            // Aufruf der play-Methode im GameService mit den Startzellen
            gameService.play(startingPositions);
        };
    }
}