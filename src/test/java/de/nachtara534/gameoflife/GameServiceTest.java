package de.nachtara534.gameoflife;

import static org.assertj.core.api.Assertions.assertThat;

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
}
