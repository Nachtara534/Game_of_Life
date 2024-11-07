package de.nachtara534.gameoflife.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Cell {

    Position ownPosition;

    CellState statusCurrentStep;

    boolean livingNeighbour;

    CellState statusNextStep;

    public Cell(Position ownPosition, CellState statusCurrentStep) {
        this.ownPosition = ownPosition;
        this.statusCurrentStep = statusCurrentStep;
    }


}