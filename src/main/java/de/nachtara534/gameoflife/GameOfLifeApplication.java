package de.nachtara534.gameoflife;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.nachtara534.gameoflife.domain.Position;
import de.nachtara534.gameoflife.service.GameService;
import de.nachtara534.gameoflife.service.OutputService;

@SpringBootApplication
public class GameOfLifeApplication {


    public static void main(String[] args) {
        SpringApplication.run(GameOfLifeApplication.class, args);

        GameService gameService = new GameService();

        List<Position> startingPositions = List.of(new Position(0, 1), new Position(1, 1),new Position(2, 1));

        gameService.play(startingPositions);
    }

}
