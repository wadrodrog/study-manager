package ru.itis.study_manager.web;

public class LoginHtml extends HtmlManager {
    public LoginHtml() {
        super("Вход в аккаунт", "");
    }

    public String getPage() {
        return super.generate(getLoginForm());
    }

    public String getLoginForm() {
        return """
            <form method="post" action="/login">
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
                    <input type="submit" value="Войти">
                </div>
            </form>
            """;
    }
}
