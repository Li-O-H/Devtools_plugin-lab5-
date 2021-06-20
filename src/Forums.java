import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public enum Forums {

    STACK_OVERFLOW {
        @Override
        public String getUrl(String textToSearch) {
            String encodedText = URLEncoder.encode(textToSearch, StandardCharsets.UTF_8);
            return String.format("https://stackoverflow.com/search?q=%s", encodedText);
        }
    },

    HABR_QNA {
        @Override
        public String getUrl(String textToSearch) {
            String encodedText = URLEncoder.encode(textToSearch, StandardCharsets.UTF_8);
            return String.format("https://qna.habr.com/search?q=%s", encodedText);
        }
    },

    GOOGLE {
        @Override
        public String getUrl(String textToSearch) {
            String encodedText = URLEncoder.encode(textToSearch, StandardCharsets.UTF_8);
            return String.format("https://www.google.com/search?q=%s", encodedText);
        }
    },

    STACK_OVERFLOW_RU {
        @Override
        public String getUrl(String textToSearch) {
            String encodedText = URLEncoder.encode(textToSearch, StandardCharsets.UTF_8);
            return String.format("https://ru.stackoverflow.com/search?q=%s", encodedText);
        }
    },

    CYBERFORUM {
        @Override
        public String getUrl(String textToSearch) {
            String encodedText = URLEncoder.encode(textToSearch, StandardCharsets.UTF_8);
            return String.format("https://www.cyberforum.ru/google.php?q=%s", encodedText);
        }
    },

    CODEPROJECT {
        @Override
        public String getUrl(String textToSearch) {
            String encodedText = URLEncoder.encode(textToSearch, StandardCharsets.UTF_8);
            return String.format("https://www.codeproject.com/search.aspx?q=%s", encodedText);
        }
    };

    public abstract String getUrl(String textToSearch);
}