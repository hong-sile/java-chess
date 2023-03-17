package chess.view;

import chess.domain.piece.Bishop;
import chess.domain.piece.King;
import chess.domain.piece.Knight;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.piece.Rook;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final int FILE_SIZE = 8;
    private static final int RANK_SIZE = 8;
    private static final String GAME_START_INFO_MESSAGE = "> 체스 게임을 시작합니다." + System.lineSeparator()
            + "> 게임 시작 : start" + System.lineSeparator()
            + "> 게임 종료 : end" + System.lineSeparator()
            + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3";
    private static final Map<Class<? extends Piece>, String> PIECE_VALUE_MAP = new HashMap<>();
    public static final String EMPTY_POSITION = ".";

    static {
        PIECE_VALUE_MAP.put(King.class, "k");
        PIECE_VALUE_MAP.put(Queen.class, "q");
        PIECE_VALUE_MAP.put(Bishop.class, "b");
        PIECE_VALUE_MAP.put(Rook.class, "r");
        PIECE_VALUE_MAP.put(Knight.class, "n");
        PIECE_VALUE_MAP.put(Pawn.class, "p");
    }

    public static void printGameStartInfo() {
        System.out.println(GAME_START_INFO_MESSAGE);
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
            final int column = position.file().value();
            final int row = position.rank().value();
            final String pieceDisplay = formatPieceDisplay(piece);
            chessBoard.get(RANK_SIZE - row).set(column - 1, pieceDisplay);
        }
    }

    private static List<List<String>> initializeBoard() {
        final List<List<String>> emptyBoard = new ArrayList<>();
        for (int i = 0; i < RANK_SIZE; i++) {
            emptyBoard.add(new ArrayList<>(Collections.nCopies(FILE_SIZE, EMPTY_POSITION)));
        }
        return emptyBoard;
    }

    private static String formatPieceDisplay(final Piece piece) {
        String pieceDisplay = PIECE_VALUE_MAP.get(piece.getClass());
        if (piece.isBlack()) {
            return pieceDisplay.toUpperCase();
        }
        return pieceDisplay;
    }

    public static void printExceptionMessage(final Exception e) {
        System.out.println(e.getMessage());
    }
}
