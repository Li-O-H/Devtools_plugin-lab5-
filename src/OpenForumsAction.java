import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.messages.MessageDialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class OpenForumsAction extends AnAction {

    public List<Forums> forumsList = List.of(Forums.GOOGLE, Forums.STACK_OVERFLOW, Forums.HABR_QNA, Forums.CYBERFORUM);

    public List<String> latestSearches = new ArrayList<>();
    int nowOnIndex = -1;

    @Override
    public void actionPerformed(AnActionEvent e) {
        String textToSearch;

        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        String selectedText = caretModel.getCurrentCaret().getSelectedText();
        if (selectedText == null || selectedText.isEmpty()) {
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
            textToSearch = textInput.getInputString();
        } else {
            textToSearch = selectedText;
        }

        if (textToSearch == null || textToSearch.isEmpty()) {
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