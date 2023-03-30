package chess.model.dao;

import chess.model.domain.board.Turn;
import chess.model.domain.piece.Color;
import chess.model.exception.QueryFailException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseChessGameDao implements ChessGameDao {

    private static final DataBaseChessGameDao INSTANCE = new DataBaseChessGameDao();

    private DataBaseChessGameDao() {
    }

    public static DataBaseChessGameDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Long> findAllId() {
        final String findAllGameId = "SELECT id FROM chess_game";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(findAllGameId)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<Long> chessGameIds = new ArrayList<>();
            while (resultSet.next()) {
                final long chessGameId = resultSet.getLong(1);
                chessGameIds.add(chessGameId);
            }
            return chessGameIds;
        } catch (final SQLException e) {
            throw new QueryFailException();
        }
    }

    @Override
    public long generateNewGame() {
        final String generateNewGameQuery = "INSERT INTO CHESS_GAME VALUES (null, ?)";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(generateNewGameQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, Color.WHITE.name());
            preparedStatement.executeUpdate();
            final ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            throw new SQLException();
        } catch (final SQLException e) {
            throw new QueryFailException();
        }
    }

    @Override
    public void updateTurn(final Turn turn, final long gameId) {
        final String generateNewGameQuery = "UPDATE CHESS_GAME SET TURN = ? WHERE id = ?";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(generateNewGameQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, turn.getTurn().name());
            preparedStatement.setLong(2, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new QueryFailException();
        }
    }

    @Override
    public Turn loadTurn(final long gameId) {
        final String generateNewGameQuery = "SELECT turn FROM chess_game WHERE id = ?";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(generateNewGameQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, gameId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Color turn = Color.valueOf(resultSet.getString(1));
                return new Turn(turn);
            }
            throw new SQLException();
        } catch (final SQLException e) {
            throw new QueryFailException();
        }
    }

    @Override
    public void deleteGame(final long gameId) {
        final String deleteBoardQuery =
                "DELETE FROM chess_game WHERE id = ?";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final PreparedStatement preparedStatement =
                     connection.prepareStatement(deleteBoardQuery)) {
            preparedStatement.setLong(1, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new QueryFailException();
        }
    }
}
