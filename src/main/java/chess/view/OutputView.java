package chess.view;

import chess.File;
import chess.Position;
import chess.Rank;
import chess.piece.Bishop;
import chess.piece.King;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.Piece;
import chess.piece.Queen;
import chess.piece.Rook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OutputView {

    private static final Map<Class<? extends Piece>, String> PIECE_VALUE_MAP = new HashMap<>();

    static {
        PIECE_VALUE_MAP.put(King.class, "k");
        PIECE_VALUE_MAP.put(Queen.class, "q");
        PIECE_VALUE_MAP.put(Bishop.class, "b");
        PIECE_VALUE_MAP.put(Rook.class, "r");
        PIECE_VALUE_MAP.put(Knight.class, "n");
        PIECE_VALUE_MAP.put(Pawn.class, "p");
    }

    public static void printBoard(final Map<Position, Piece> boardMap) {
        List<List<String>> chessBoard = initializeBoard();

        mappingChessBoard(boardMap, chessBoard);

        chessBoard.stream()
                .map(board -> String.join("", board))
                .forEach(System.out::println);
    }

    private static void mappingChessBoard(final Map<Position, Piece> boardMap, final List<List<String>> chessBoard) {
        for (final Map.Entry<Position, Piece> positionPieceEntry : boardMap.entrySet()) {
            final Position position = positionPieceEntry.getKey();
            final Piece piece = positionPieceEntry.getValue();
            final File file = position.file();
            final Rank rank = position.rank();
            final int column = file.value();
            final int row = rank.value();

            String pieceDisplay = formatPieceDisplay(piece);
            chessBoard.get(row - 1).set(column - 1, pieceDisplay);
        }
    }

    private static List<List<String>> initializeBoard() {
        return IntStream.range(0, 8)
                .mapToObj(it -> new ArrayList<>(Collections.nCopies(8, ".")))
                .collect(Collectors.toList());
    }

    private static String formatPieceDisplay(final Piece piece) {
        String pieceDisplay = PIECE_VALUE_MAP.get(piece.getClass());

        if (piece.isBlack()) {
            return pieceDisplay.toUpperCase();
        }
        return pieceDisplay;
    }
}
