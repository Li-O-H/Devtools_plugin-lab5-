import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OpenForumsAction extends AnAction {

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

        STACK_OVERFLOW_RU{
            @Override
            public String getUrl(String textToSearch) {
                String encodedText = URLEncoder.encode(textToSearch, StandardCharsets.UTF_8);
                return String.format("https://ru.stackoverflow.com/search?q=%s", encodedText);
            }
        },

        CYBERFORUM{
            @Override
            public String getUrl(String textToSearch) {
                String encodedText = URLEncoder.encode(textToSearch, StandardCharsets.UTF_8);
                return String.format("https://www.cyberforum.ru/google.php?q=%s", encodedText);
            }
        },

        CODEPROJECT{
            @Override
            public String getUrl(String textToSearch) {
                String encodedText = URLEncoder.encode(textToSearch, StandardCharsets.UTF_8);
                return String.format("https://www.codeproject.com/search.aspx?q=%s", encodedText);
            }
        };

        public abstract String getUrl(String textToSearch);
    }

    public List<Forums> forumsList = List.of
            (Forums.GOOGLE, Forums.STACK_OVERFLOW, Forums.HABR_QNA, Forums.CYBERFORUM, Forums.STACK_OVERFLOW_RU, Forums.CODEPROJECT);

    @Override
    public void actionPerformed(AnActionEvent e) {
        String textToSearch = Messages.showInputDialog("Enter the text you want to search",
                "OpenForums", Messages.getQuestionIcon());
        if (textToSearch == null) {
            return;
        }
        if (textToSearch.isEmpty()) {
            Messages.showMessageDialog("Error: Text to search can't be empty",
                    "OpenForums", Messages.getErrorIcon());
            return;
        }
        for (Forums forum : forumsList) {
            BrowserUtil.browse(forum.getUrl(textToSearch));
        }
    }

    @Override
    public boolean isDumbAware(){
        return true;
    }
}