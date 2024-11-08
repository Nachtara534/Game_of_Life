package de.nachtara534.gameoflife.service;

import org.springframework.stereotype.Service;

import de.nachtara534.gameoflife.domain.Board;
import de.nachtara534.gameoflife.domain.Cell;
import de.nachtara534.gameoflife.domain.CellState;
import de.nachtara534.gameoflife.domain.Position;

@Service
public class OutputService {
//Zeigt das ganze board, 2 for schleifen, über map iterienen, schauen ob auf pos xy eine zelle definiert ist (hashmap.getOrDefault(Key, Default [z.b. null] ));

    public void printBoard(Board board) {
        for (int y = board.getMaxY(); y >= board.getMinY(); y--) {
            for (int x = board.getMinX(); x <= board.getMaxX(); x++) {
                Position position = new Position(x, y);
                Cell cell = board.getBoard().get(position);

                // Zeigt * für ALIVE und . für DEAD an
                if (cell != null && cell.getStatusCurrentStep() == CellState.ALIVE) {
                    System.out.print("|* |");
                } else {
                    System.out.print("|  |");
                }
            }
            System.out.println(); // Zeilenumbruch nach jeder Reihe
        }
        for (int x = board.getMinX(); x <= board.getMaxX(); x++) {
            System.out.print("----");
        }
        System.out.println();
    }

}
