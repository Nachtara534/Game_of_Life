package de.nachtara534.gameoflife;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.nachtara534.gameoflife.domain.Board;
import de.nachtara534.gameoflife.domain.Cell;
import de.nachtara534.gameoflife.domain.CellState;
import de.nachtara534.gameoflife.domain.Position;
import de.nachtara534.gameoflife.service.GameService;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    void test_generateCell() {
        Position position = new Position(0, 0);
        Board board = new Board();
        Cell result = gameService.generateCells(position, CellState.ALIVE, board);

        assertThat(result).isNotNull();
        assertThat(board.getBoard().containsValue(result)).isTrue();
    }

    @Test
    void test_generateNeighbours() {
        Position position = new Position(0, 0);
        Board board = new Board();
        Cell initCell = new Cell(position, CellState.ALIVE);
        gameService.generateNeighbours(new Cell(position, CellState.ALIVE), board);

        board.addCell(initCell);

        assertThat(board.getBoard().size()).isEqualTo(9);
        assertThat(board.getBoard().containsKey(position)).isTrue();

        assertThat(board.getBoard().get(position).getStatusCurrentStep()).isEqualTo(CellState.ALIVE);

        int maxNumbersOfALiveCellInHashMap = 1;

        assertThat(board.getBoard().values().stream()
                .filter(cell -> cell.getStatusCurrentStep().equals(CellState.ALIVE))
                .count()).isEqualTo(maxNumbersOfALiveCellInHashMap);
    }

    @Test
    void test_checkNextGeneration() {
        Position position = new Position(0, 0);
        Board board = new Board();
        Cell initCell = new Cell(position, CellState.ALIVE);

        board.addCell(initCell);
        gameService.generateNeighbours(new Cell(position, CellState.ALIVE), board);

        int livingNeighbours = gameService.countSurroundingLivingNeighbours(new Cell(new Position(0, 1), CellState.DEAD), board);
        assertThat(livingNeighbours).isEqualTo(1);
    }

    @Test
    void test_CheckMinMax() {
        Position position = new Position(0, 0);
        Board board = new Board();
        Cell initCell = new Cell(position, CellState.ALIVE);
        board.addCell(initCell);
        gameService.generateNeighbours(new Cell(position, CellState.ALIVE), board);

        assertThat(board.getMaxY()).isEqualTo(1);
        assertThat(board.getMinY()).isEqualTo(-1);

        assertThat(board.getMaxX()).isEqualTo(1);
        assertThat(board.getMinX()).isEqualTo(-1);
    }

    @Test
    void test_nextGeneration() {

        //Given
        Position position1 = new Position(0, 0);
        Position position2 = new Position(2, 0);
        Board board = new Board();

        Cell initCell1 = new Cell(position1, CellState.ALIVE);
        Cell initCell2 = new Cell(position2, CellState.ALIVE);

        //When
        board.addCell(initCell1);
        board.addCell(initCell2);

        gameService.generateNeighbours(initCell1, board);
        gameService.generateNeighbours(initCell2, board);

        gameService.checkNextGeneration(board);

        //Then
        assertThat(initCell1.getStatusNextStep()).isEqualTo(CellState.DEAD);
        assertThat(initCell2.getStatusNextStep()).isEqualTo(CellState.DEAD);
    }

    @Test
    void test_LivingNeighbours() {
        Position position1 = new Position(0, 0);
        Position position2 = new Position(1, 0);
        Position position3 = new Position(2, 0);

        Board board = new Board();

        Cell cell1 = gameService.generateCells(position1, CellState.ALIVE, board);
        Cell cell2 = gameService.generateCells(position2, CellState.ALIVE, board);
        Cell cell3 = gameService.generateCells(position3, CellState.ALIVE, board);

        gameService.generateNeighbours(cell1, board);
        gameService.generateNeighbours(cell2, board);
        gameService.generateNeighbours(cell3, board);

        gameService.checkNextGeneration(board);

        assertThat(gameService.countSurroundingLivingNeighbours(cell1, board)).isEqualTo(1);
        assertThat(gameService.countSurroundingLivingNeighbours(cell2, board)).isEqualTo(2);

        assertThat(cell1.getStatusNextStep()).isEqualTo(CellState.DEAD);
        assertThat(cell3.getStatusNextStep()).isEqualTo(CellState.DEAD);
    }


    @Test
    void test_play() throws InterruptedException {
        List<Position> startingPositions = List.of(new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(2, 1), new Position(1, 2));

        gameService.play(startingPositions);
    }
}
