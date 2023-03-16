package chess;

import java.util.Objects;

public class Position {

    private final File file;
    private final Rank rank;

    public Position(final int file, final int rank) {
        this.file = File.from(file);
        this.rank = Rank.from(rank);
    }

    //수정해야
    public Movement calculateUnitMovement(final Position subtracted) {
        final int fileDiff = file.value() - subtracted.file.value();
        final int rankDiff = rank.value() - subtracted.rank.value();

        final int gcd = gcd(Math.min(fileDiff, rankDiff), Math.max(fileDiff, rankDiff));

        return Movement.of(fileDiff / gcd, rankDiff / gcd);
    }

    private int gcd(final int value1, final int value2) {
        if (value2 == 0) {
            return value1;
        }
        return gcd(value2, value1 % value2);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return file == position.file && rank == position.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }

    public File file() {
        return file;
    }

    public Rank rank() {
        return rank;
    }

    @Override
    public String toString() {
        return "Position{" +
                "file=" + file +
                ", rank=" + rank +
                '}';
    }

    public int subRankAndFileAbs(final Position destination) {
        return Math.abs(rank.value() - destination.rank.value())
                + Math.abs(file.value() - destination.file.value());
    }
}
