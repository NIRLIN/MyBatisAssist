package cn.nirlin.mybatisassist.action;

import cn.nirlin.mybatisassist.constant.Constants;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * @author nielin
 * @date 2022/1/25
 */
public class GenerateSql extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取编辑器对象
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        formatSql(editor);
    }

    private void formatSql(Editor editor) {
        SelectionModel selectionModel = editor.getSelectionModel();
        // 获取选中文本
        String selectedText = Optional.ofNullable(selectionModel.getSelectedText()).orElse("");
        // 判断字符串是否合规
        if (!verifyTrueSql(selectedText)) {
            // 选择不合格则进行提示
            NotificationGroupManager.getInstance().getNotificationGroup("Custom Notification Group").createNotification(Constants.SQL_INVALID_SELECTION, NotificationType.WARNING).notify(editor.getProject());
        } else {
            // sql参数填充，并复制到剪切板，并提示
            copyToClipboard(parameterPadding(selectedText));
            NotificationGroupManager.getInstance().getNotificationGroup("Custom Notification Group").createNotification(Constants.SQL_COPIED, NotificationType.INFORMATION).notify(editor.getProject());
        }
    }

    private boolean verifyTrueSql(String text) {
        // 判断是否存在关键字
        boolean flag = false;
        for (String keyField : Constants.SQL_KEY_FIELD) {
            if (text.contains(keyField) || text.contains(keyField.toLowerCase())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private String interceptSql(String text) {
        // sql语句截取出来
        for (String keyField : Constants.SQL_KEY_FIELD) {
            if (text.contains(keyField)) {
                return text.substring(text.indexOf(keyField));
            }
            if (text.contains(keyField.toLowerCase())) {
                return text.substring(text.indexOf(keyField.toLowerCase()));
            }
        }
        return "";
    }

    /**
     * 参数填充
     */
    private String parameterPadding(String selectedText) {
        List<String> selectRowList = Arrays.stream(selectedText.split("\n")).collect(Collectors.toList());
        // 构造sql
        StringBuilder stringBuilder = new StringBuilder();
        // 筛选出sql
        for (int i = 0; i < selectRowList.size(); i++) {
            String sqlLine = selectRowList.get(i);
            // 判断当前行是否有sql关键字
            if (verifyTrueSql(sqlLine)) {
                sqlLine = interceptSql(sqlLine);
                String paramLine = selectRowList.get(i + 1);
                // 将参数解析存储，清除前后空格
                paramLine = paramLine.substring(paramLine.indexOf(Constants.SQL_PARAMETERS));
                paramLine = paramLine.replaceFirst(Constants.SQL_PARAMETERS, "");
                Queue<String> paramQueue = new ConcurrentLinkedQueue<>();
                String[] params = paramLine.split(",");
                for (String param : params) {
                    param = param.split("\\(")[0].trim();
                    paramQueue.add(param);
                }

                for (char c : sqlLine.toCharArray()) {
                    if (c == '?') {
                        String param = paramQueue.remove();
                        if (StringUtils.isNumeric(param)) {
                            int parseInt = Integer.parseInt(param);
                            stringBuilder.append(parseInt);
                        }else {
                            stringBuilder.append("'");
                            stringBuilder.append(param);
                            stringBuilder.append("'");
                        }
                        continue;
                    }
                    stringBuilder.append(c);
                }
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 将文本复制到剪切板
     */
    public void copyToClipboard(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(text), null);
    }

}
