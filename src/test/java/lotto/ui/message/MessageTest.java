package lotto.ui.message;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {
    private static Message message;

    @BeforeAll
    public static void load() {
        String resource = "message/message_ko.properties";
        message = new Message(resource);
    }

    @Test
    @DisplayName("기본 텍스트 확인")
    public void baseTest() {
        String text = message.get("test.base");
        assertThat(text).isEqualTo("한글 테스트");
    }

    @Test
    @DisplayName("포맷 텍스트 확인")
    public void formatTest() {
        String text = message.get("test.format", "남자", 1);
        assertThat(text).isEqualTo("저는 남자입니다. 저의 생일은 1월 입니다.");
    }
}