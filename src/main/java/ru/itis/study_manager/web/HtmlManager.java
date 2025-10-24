package ru.itis.study_manager.web;

public class HtmlManager {
    private final String title;
    private final String css;

    public HtmlManager(String title, String css) {
        this.title = title;
        this.css = css;
    }

    public String generate(String content) {
        return """
            <!DOCTYPE html>
            <html lang="ru">
            %s
            %s
            </html>
            """.formatted(getHead(), getBody(content));
    }

    private String getHead() {
        return """
               <head>
                 <meta charset="UTF-8">
                 <title>%s</title>
                 <link href="/favicon.ico" rel="icon">
                 %s
               </head>
               """.formatted(title, getCss());
    }

    private String getCss() {
        StringBuilder sb = new StringBuilder();
        sb.append("<link href=\"/static/css/style.css\" rel=\"stylesheet\">");
        if (!css.isEmpty()) {
            sb.append("\n<link href=\"/static/css/%s.css\" rel=\"stylesheet\">".formatted(css));
            sb.append("\n<script src=\"/static/js/%s.js\"></script>".formatted(css));
        }
        return sb.toString();
    }

    private String getBody(String content) {
        return """
               <body>
               %s
               %s
               </body>
               """.formatted(getHeader(), getMain(title, content));
    }

    private String getHeader() {
        return """
               <header>
                 <nav>
                   <ul>
                     <li><a href="/welcome">Главная</a></li>
                     <li><a href="/homework">Домашние задания</a></li>
                     <li><a href="/login">Войти</a></li>
                     <li><a href="/register">Зарегистрироваться</a></li>
                     <li><a href="/logout">Выйти</a></li>
                   </ul>
                 </nav>
               </header>
               """;
    }

    private String getMain(String title, String content) {
        return """
               <main>
                 <h1>%s</h1>
                 %s
               </main>
               """.formatted(title, content);
    }
}
