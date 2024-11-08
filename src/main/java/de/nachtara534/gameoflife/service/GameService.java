package de.nachtara534.gameoflife.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.nachtara534.gameoflife.domain.Board;
import de.nachtara534.gameoflife.domain.Cell;
import de.nachtara534.gameoflife.domain.CellState;
import de.nachtara534.gameoflife.domain.Position;

@Service
public class GameService {

    @Autowired
    private OutputService outputService;

    public void play(List<Position> startingCells) throws InterruptedException {
        Board board = new Board();

        for (Position currentPosition : startingCells) {
            Cell currentCell = generateCells(currentPosition, CellState.ALIVE, board);
            generateNeighbours(currentCell, board);
        }

        outputService.printBoard(board);

        while (livingCellsRemaining(board)) {
            checkNextGeneration(board);
            changeToNextGeneration(board);

            outputService.printBoard(board);

            Thread.sleep(1000);
        }
    }

    public boolean livingCellsRemaining(Board board) {
        Set<Position> allCells = board.getBoard().keySet();

        for (Position currentKey : allCells) {
            if (board.getBoard().get(currentKey).isHasLivingNeighbour()
                    || board.getBoard().get(currentKey).getStatusCurrentStep() == CellState.ALIVE) {
                return true;
            }
        }
        return false;
    }

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
                    neighhbourCell.setHasLivingNeighbour(true);
                    board.addCell(neighhbourCell);
                }

            }
        }
    }

    public int countSurroundingLivingNeighbours(Cell cell, Board board) {
        int livingNeighbours = 0;
//        System.out.println(board.getBoard().toString());
        for (int x = cell.getOwnPosition().getX() - 1; x <= cell.getOwnPosition().getX() + 1; x++) {
            for (int y = cell.getOwnPosition().getY() - 1; y <= cell.getOwnPosition().getY() + 1; y++) {
                Position currentPosition = new Position(x, y);

                boolean isNeighbor =
                        board.getBoard().containsKey(currentPosition) && !currentPosition.equals(cell.getOwnPosition());
                boolean isAlive =
                        isNeighbor && board.getBoard().get(currentPosition).getStatusCurrentStep() == CellState.ALIVE;

                if (isAlive) {
                    livingNeighbours++;
                }

            }

        }
        return livingNeighbours;
    }


    public void checkNextGeneration(final Board board) {
        Set<Position> allCells = board.getBoard().keySet();

        for (Position position : allCells) {
            Cell cell = board.getBoard().get(position);

            int livingNeighbours = countSurroundingLivingNeighbours(cell, board);


            if (cell.getStatusCurrentStep() == CellState.ALIVE) {
                cell.setStatusNextStep((livingNeighbours == 2
                        || livingNeighbours == 3) ? cell.getStatusCurrentStep() : CellState.DEAD);
            } else {
                cell.setStatusNextStep(livingNeighbours == 3 ? CellState.ALIVE : CellState.DEAD);
            }
        }
    }

    public void changeToNextGeneration(final Board board) {
        Set<Position> allCells = new HashSet<>(board.getBoard().keySet());

        for (Position position : allCells) {
            Cell currentCell = board.getBoard().get(position);

            currentCell.setStatusCurrentStep(currentCell.getStatusNextStep());


            generateNeighbours(currentCell, board);

        }
    }
}

