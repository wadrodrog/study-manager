package ru.itis.study_manager.web;

public class RegisterHtml extends HtmlManager {
    public RegisterHtml() {
        super("Регистрация", "");
    }

    public String getPage() {
        return super.generate(getRegisterForm());
    }

    private String getRegisterForm() {
        return """
            <form method="post" action="/register">
                <div>
                    <label>
                        Имя пользователя:
                        <input type="username" name="username" required />
                    </label>
                </div>
                <div>
                    <label>
                        Пароль:
                        <input type="password" name="password" required />
                    </label>
                </div>
                <div>
                    <label>
                        Повторите пароль:
                        <input type="password" name="repeat_password" required />
                    </label>
                </div>
                <div>
                    <input type="submit" value="Зарегистрироваться">
                </div>
            </form>
            """;
    }
}
