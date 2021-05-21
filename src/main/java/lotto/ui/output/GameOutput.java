package lotto.ui.output;

import lotto.ui.Program;

import java.io.OutputStream;
import java.io.PrintStream;

public class GameOutput implements Output {
    private PrintStream out;

    public GameOutput(OutputStream out) {
        this.out = (PrintStream) out;
    }

    @Override
    public void println(String text) {
        out.println(text);
    }

    @Override
    public void error(String text) {
        out.println(Program.MESSAGE.get("response.error", text));
    }
}
