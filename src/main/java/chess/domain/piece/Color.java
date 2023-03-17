package chess.domain.piece;

public enum Color {

    BLACK,
    WHITE;

    public boolean isWhite() {
        return this == WHITE;
    }

    public boolean isSameColor(Color color) {
        return this == color;
    }

    public Color opposite() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
