package de.nachtara534.gameoflife.service;

import org.springframework.stereotype.Service;

import de.nachtara534.gameoflife.domain.Board;
import de.nachtara534.gameoflife.domain.Cell;
import de.nachtara534.gameoflife.domain.CellState;
import de.nachtara534.gameoflife.domain.Position;

@Service
public class GameService {

    public Cell generateCells(Position position, CellState isAlive, Board board) {
        Cell cell = new Cell(position, isAlive);

        board.addCell(cell);

        return cell;
    }

    public void generateNeighbours(Cell cell, Board board) {

        for (int x = cell.getOwnPosition().getX() - 1; x <= cell.getOwnPosition().getX() + 1; x++) {
            for (int y = cell.getOwnPosition().getY() - 1; y <= cell.getOwnPosition().getY() + 1; y++) {
                Position currentPosition = new Position(x, y);
                if (!board.getBoard().containsKey(currentPosition)) {
                    Cell neighhbourCell = new Cell(currentPosition, CellState.DEAD);
                    board.addCell(neighhbourCell);
                }

            }
        }
    }


}
