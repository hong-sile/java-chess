package chess.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String START_COMMAND = "start";
    private static final String END_COMMAND = "end";
    private static final String START_COMMAND_IS_NOT_VALIDATE_MESSAGE = "start 나 end 둘 중에 하나 입력해주세요.";
    private static final String MOVE_COMMAND_IS_NOT_VALIDATE_MESSAGE = "명령어 move 아님";
    private static final String GAME_START_INFO_MESSAGE = "> 체스 게임을 시작합니다." + System.lineSeparator()
            + "> 게임 시작 : start" + System.lineSeparator()
            + "> 게임 종료 : end" + System.lineSeparator()
            + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3";


    public static String readStartCommand() {
        System.out.println(GAME_START_INFO_MESSAGE);
        final String command = scanner.nextLine();

        if (!command.equals(START_COMMAND) && !command.equals(END_COMMAND)) {
            throw new IllegalArgumentException(START_COMMAND_IS_NOT_VALIDATE_MESSAGE);
        }

        return command;
    }

    public static List<String> readMoveCommand() {
        final String command = scanner.nextLine();
        final List<String> commands = Arrays.stream(command.split(" "))
                .collect(Collectors.toList());

        if (!commands.get(0).equals("move")) {
            throw new IllegalArgumentException(MOVE_COMMAND_IS_NOT_VALIDATE_MESSAGE);
        }

        return List.of(commands.get(1), commands.get(2));
    }
}
