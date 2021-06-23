package lotto.dto;

import lotto.core.omr.OmrCard;
import lotto.core.omr.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("로또 구매입력 정보 테스트")
class LottoPurchaseDTOTest {
    LottoPurchaseDTO lottoPurchaseDTO;

    @BeforeEach
    public void setup() {
        lottoPurchaseDTO = new LottoPurchaseDTO();
    }

    @DisplayName("로또 구매입력정보가 정상적인 경우")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void toOmrCard(int manual) {
        int expectedTotal = 10;

        //When
        lottoPurchaseDTO.setMoney(String.valueOf(Purchase.DEFAULT_PRICE.calcAmountWithPrice(10)));
        lottoPurchaseDTO.setManualCount(String.valueOf(manual));
        for (int i = 0; i < manual; i++) {
            lottoPurchaseDTO.appendManualSixBalls("8, 21, 23, 41, 42, 43");
        }

        //Then
        OmrCard omrCard = lottoPurchaseDTO.toOmrCard();
        assertAll(
            () -> assertThat(omrCard.getAutoCount() + omrCard.getManualCount()).isEqualTo(expectedTotal),
            () -> assertThat(omrCard.getManualCount()).isEqualTo(manual),
            () -> assertThat(omrCard.getAutoCount()).isEqualTo(expectedTotal - manual)
        );
    }

    @DisplayName("구입금액이 숫자가 아닌경우")
    @Test
    public void toOmrCardException2() {
        assertThatThrownBy(() -> lottoPurchaseDTO.setMoney("TEXT"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("구입금액은 숫자만");
    }

    @DisplayName("수동으로 구매할 로또 수가 숫자가 아닌경우")
    @Test
    public void toOmrCardException4() {
        assertThatThrownBy(() -> lottoPurchaseDTO.setManualCount("TEXT"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자만 가능");
    }

    @DisplayName("수동으로 구매할 번호 리스트가 csv형식의 숫자가 아닌경우")
    @Test
    public void toOmrCardException5() {
        lottoPurchaseDTO.setManualCount("1");

        assertThatThrownBy(() -> lottoPurchaseDTO.appendManualSixBalls("1, A, B"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("숫자만 가능");
    }

    @DisplayName("구입금액이 최소 구입금액보다 작을경우")
    @Test
    public void toOmrCardException1() {
        // when
        lottoPurchaseDTO.setMoney("900");

        // then
        assertThatThrownBy(() -> lottoPurchaseDTO.toOmrCard())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("구입 최소금액");
    }

    @DisplayName("수동으로 구매할 로또 수가 구입금액보다 큰경우")
    @Test
    public void toOmrCardException3() {
        // when
        int money = 1000;
        int count = Purchase.DEFAULT_PRICE.howMany(money);

        lottoPurchaseDTO.setMoney(String.valueOf(money));
        lottoPurchaseDTO.setManualCount(String.valueOf(count + 10));

        // then
        assertThatThrownBy(() -> lottoPurchaseDTO.toOmrCard())
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("수동으로 최대");
    }

}