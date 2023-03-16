package chess;

import chess.piece.Piece;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<Position, Piece> board;

    public Board(final BoardFactory boardFactory) {
        this.board = boardFactory.createInitialBoard();
    }

    public Map<Position, Piece> board() {
        return board;
    }

    public void move(final Position from, final Position destination) {
        //next에 다른 얘가 있는지
        if (!board.containsKey(from)) {
            throw new IllegalArgumentException();
        }
        final Piece movingPiece = board.get(from);
        final Movement movable = movingPiece
                .isMovable(from, destination, Optional.ofNullable(board.get(destination)));
        if (!board.get(from).isKnight()) {
            validatePathContainOtherPiece(movable, from, destination);
        }
        final Piece remove = board.remove(from);
        board.put(destination, remove);
        //내 팀인지 아닌지 == 잡을 수 있는지 없는지
        //(Piece) 이 피스가 움직일 수 있는 자리인지
    }

    private void validatePathContainOtherPiece(final Movement movement, final Position from, final Position dest) {
        Position iterator = from.add(movement);
        while (!iterator.equals(dest)) {
            if (board.containsKey(iterator)) {
                throw new IllegalArgumentException();
            }
            iterator.add(movement);
        }
    }
}
