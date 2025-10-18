package ru.itis.study_manager.web;

import ru.itis.study_manager.entity.HomeworkEntity;

import java.util.Arrays;
import java.util.List;

public class HomeworkHtml extends HtmlManager {
    public HomeworkHtml() {
        super("Домашние задания", "homework");
    }

    public String getPage(List<HomeworkEntity> homeworkList) {
        return super.generate(getAddForm() + '\n' + getHomeworkList(homeworkList));
    }

    public String getAddForm() {
        return """
            <details open>
                <summary>Добавить</summary>
                <form method="post" action="/homework">
                    <div>
                        <label>
                            Дисциплина:
                            <input type="text" name="discipline_name" required />
                        </label>
                    </div>
                    <div>
                        <label>
                            Дедлайн:
                            <input type="date" name="deadline" />
                        </label>
                    </div>
                    <div>
                        <label>
                            Содержимое:
                            <textarea name="contents" rows="5" cols="33" required></textarea>
                        </label>
                    </div>
                    <div>
                        <input type="submit" value="Отправить">
                    </div>
                </form>
            </details>
            """;
    }

    public String getUpdateForm(int id) {
        return """
            <details>
                <summary>Обновить</summary>
                <form method="post" action="/homework">
                    <input type="hidden" name="id" value="%d" />
                    <div>
                        <label>
                            Дисциплина:
                            <input type="text" name="discipline_name" />
                        </label>
                    </div>
                    <div>
                        <label>
                            Дедлайн:
                            <input type="date" name="deadline" />
                        </label>
                    </div>
                    <div>
                        <label>
                            Содержимое:
                            <textarea name="contents" rows="5" cols="33"></textarea>
                        </label>
                    </div>
                    <div>
                        <input type="submit" value="Обновить">
                    </div>
                </form>
            </details>
            """.formatted(id);
    }

    private String getHomeworkList(List<HomeworkEntity> homeworkList) {
        StringBuilder sb = new StringBuilder();
        for (HomeworkEntity homework : homeworkList) {
            sb.append("<div class=\"homework-item %s\">\n".formatted(homework.getStatus()));
            sb.append("  <h2>%s</h2>\n".formatted(homework.getDisciplineName()));
            sb.append("  <p>%s</p>\n".formatted(homework.getContents()));
            if (homework.getAttachments().length > 0) {
                sb.append("  <details>\n");
                sb.append("    <summary>Вложения</summary>\n");
                sb.append("    <p>%s</p>\n".formatted(Arrays.toString(homework.getAttachments())));
                sb.append("  </details>\n");
            }
            sb.append("  <button onclick=\"deleteHomework(%s)\">Удалить</button>".formatted(homework.getId()));
            sb.append(getUpdateForm(homework.getId()));
            sb.append("</div>");
        }
        return sb.toString();
    }
}
