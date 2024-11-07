package de.nachtara534.gameoflife.domain;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Board {

    int minX;

    int maxX;

    int minY;

    int maxY;

    HashMap<Position, Cell> board = new HashMap<>();

    //Start zellen erzeugen + nachbar felder generieren(tod), nicht random,

    public void addCell(final Cell cell) {
        this.board.put(cell.ownPosition, cell);

        Position temp = cell.ownPosition;
        minY = Math.min(temp.getY(), minY);
        maxY = Math.max(temp.getY(), maxY);

        minX = Math.min(temp.getX(), minX);
        maxX = Math.max(temp.getX(), maxX);
    }
}
