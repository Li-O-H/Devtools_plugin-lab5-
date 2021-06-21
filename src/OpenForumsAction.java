import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.messages.MessageDialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class OpenForumsAction extends AnAction {

    public List<Forums> forumsList = List.of
            (Forums.GOOGLE, Forums.STACK_OVERFLOW, Forums.HABR_QNA, Forums.CYBERFORUM, Forums.STACK_OVERFLOW_RU, Forums.CODEPROJECT);

    public List<String> latestSearches = new ArrayList<>();
    int nowOnIndex = -1;

    @Override
    public void actionPerformed(AnActionEvent e) {
        Messages.InputDialog textInput = new Messages.InputDialog("Enter the text you want to search",
                "OpenForums", Messages.getQuestionIcon(), null, null);
        textInput.setResizable(false);
        textInput.setSize(1000, 350);
        final String[] beforePressingUp = {""};
        textInput.getTextField().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (nowOnIndex == -1) {
                        beforePressingUp[0] = textInput.getTextField().getText();
                    }
                    if (!latestSearches.isEmpty() && nowOnIndex + 1 <= latestSearches.size() - 1) {
                        nowOnIndex++;
                        textInput.getTextField().setText(latestSearches.get(nowOnIndex));
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (nowOnIndex == 0) {
                        nowOnIndex--;
                        textInput.getTextField().setText(beforePressingUp[0]);
                    }
                    if (!latestSearches.isEmpty() && nowOnIndex - 1 >= 0) {
                        nowOnIndex--;
                        textInput.getTextField().setText(latestSearches.get(nowOnIndex));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        textInput.show();
        String textToSearch = textInput.getInputString();
        if (textToSearch == null) {
            return;
        }
        if (textToSearch.isEmpty()) {
            MessageDialog m = new MessageDialog("Error: Text to search can't be empty", "OpenForums", new String[]{"Ok"}, 0, Messages.getErrorIcon());
            m.setResizable(false);
            m.show();
            return;
        }
        latestSearches.add(0, textToSearch);
        for (Forums forum : forumsList) {
            BrowserUtil.browse(forum.getUrl(textToSearch));
        }
    }

    @Override
    public boolean isDumbAware() {
        return true;
    }
}