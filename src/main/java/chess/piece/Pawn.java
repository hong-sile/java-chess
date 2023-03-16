package chess.piece;

import chess.Movement;
import chess.Position;
import chess.Rank;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class Pawn extends Piece {

    private static final Map<Color, List<Movement>> CATCHABLE_MOVEMENT = Map.of(
            Color.BLACK, List.of(Movement.UR, Movement.UL),
            Color.WHITE, List.of(Movement.DR, Movement.DL)
    );
    private static final Map<Color, Movement> BLANK_MOVEMENT = Map.of(
            Color.BLACK, Movement.D,
            Color.WHITE, Movement.U
    );
    private static final Map<Color, Predicate<Rank>> isMoved = Map.of(
            Color.BLACK, Rank.SEVEN::equals,
            Color.WHITE, Rank.TWO::equals
    );

    public Pawn(final Color color, final Position position) {
        super(color, position);
    }

    @Override
    public Movement isMovable(final Position from, final Position dest, final Optional<Piece> destPiece) {
        if (destPiece.isPresent()) {
            validateIsSameTeam(destPiece.get());
            return searchPossibleCatchFrom(dest);
        }
        return searchPossibleMovementFrom(dest);
    }

    @Override
    public Movement searchPossibleMovementFrom(final Position destination) {
        final Movement movement = destination.calculateUnitMovement(position);
        final int posDiff = position.subRankAndFileAbs(destination);
        if (!BLANK_MOVEMENT.get(color).equals(movement)) {
            //기본 최소 단위 움직임이 다를 때 Exception 반환
            throw new IllegalArgumentException();
        }
        if (posDiff == 1 || (isMoved.get(color).test(position.rank()) && posDiff == 2)) {
            return BLANK_MOVEMENT.get(color);
        }
        throw new IllegalArgumentException();
    }

    public Movement searchPossibleCatchFrom(final Position destination) {
        final Movement movement = destination.calculateUnitMovement(position);
        if (position.subRankAndFileAbs(destination) != 2) {
            throw new IllegalStateException();
        }
        return CATCHABLE_MOVEMENT.get(color).stream()
                .filter(movement::equals)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
