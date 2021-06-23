package lotto.ui.input.request;

import lotto.core.omr.OmrCard;
import lotto.dto.LottoPurchaseDTO;
import lotto.ui.input.Input;
import lotto.ui.output.Output;
import java.util.stream.Collectors;
import static lotto.ui.output.GameOutput.NEW_LINE;

public class RequestOmrCard extends Request<OmrCard> {
    private final LottoPurchaseDTO lottoDTO;

    public RequestOmrCard(final Output output, final Input<String> input) {
        super(output, input);

        lottoDTO = new LottoPurchaseDTO();
    }

    @Override
    public OmrCard input() {
        requestLottoCount();
        requestManual();

        OmrCard omrCard = lottoDTO.toOmrCard();

        print(omrCard);

        return omrCard;
    }

    private void requestLottoCount() {
        request(() -> lottoDTO.setMoney(input.request("구입금액을 입력해 주세요.")));
        output.println("");
    }

    private void requestManual() {
        request(() -> lottoDTO.setManualCount(input.request("수동으로 구매할 로또 수를 입력해 주세요.")));
        output.println("");

        request(() -> lottoDTO.appendManualSixBalls(input.request("수동으로 구매할 번호를 입력해 주세요.")));

        for (int i = 0; i < lottoDTO.getManualCount() - 1; i++) {
            request(() -> lottoDTO.appendManualSixBalls(input.request("")));
        }
        output.println("");
    }

    private void print(final OmrCard omrCard) {
        output.println(String.format("수동으로 %d장, 자동으로 %d개를 구매했습니다.", omrCard.getManualCount(), omrCard.getAutoCount()));
        output.println(
                omrCard.stream()
                        .map(omr -> omr.toString())
                        .collect(Collectors.joining(NEW_LINE))
        );
        output.println("");
    }

}
