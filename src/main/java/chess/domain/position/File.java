package chess.domain.position;

import java.util.Arrays;

public enum File {

    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8);

    private static final String FILE_NOT_FOUND_EXCEPTION = "일치하는 File을 찾을 수 없습니다.";

    private final int value;

    File(final int value) {
        this.value = value;
    }

    static File from(int value) {
        return Arrays.stream(values())
                .filter(it -> it.value == value)
                .findAny()
                .orElseThrow(() -> new IllegalStateException(FILE_NOT_FOUND_EXCEPTION));
    }

    public int value() {
        return value;
    }
}
