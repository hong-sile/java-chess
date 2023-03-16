package chess.piece;

import chess.Movement;
import chess.Position;

import java.util.Objects;
import java.util.Optional;

public abstract class Piece {

    protected final Color color;
    protected final Position position;

    public Piece(final Color color, final Position position) {
        this.color = color;
        this.position = position;
    }

    public boolean isBlack() {
        return color == Color.BLACK;
    }

    public Movement isMovable(final Position from, final Position dest, final Optional<Piece> destPiece) {
        destPiece.ifPresent(this::validateIsSameTeam);
        return searchPossibleMovementFrom(dest);
    }

    final void validateIsSameTeam(final Piece other) {
        if (other.color == color) {
            throw new IllegalArgumentException();
        }
    }

    public abstract Movement searchPossibleMovementFrom(final Position destination);

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Piece piece = (Piece) o;
        return color == piece.color && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, position);
    }
}
