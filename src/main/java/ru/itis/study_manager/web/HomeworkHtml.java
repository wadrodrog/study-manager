package ru.itis.study_manager.web;

public class HomeworkHtml extends HtmlManager {
    public HomeworkHtml() {
        super("Домашние задания");
    }

    public String getHomeworkAddForm() {
        return super.generate("""
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
            """);
    }
}
