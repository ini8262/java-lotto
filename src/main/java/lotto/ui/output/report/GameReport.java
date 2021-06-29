package lotto.ui.output.report;

import lotto.core.result.GameResult;

public interface GameReport<R extends GameResult> {
    String format(R r);
}
