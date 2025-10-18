package ru.itis.study_manager.web;

public class HtmlManager {
    private final String title;

    public HtmlManager(String title) {
        this.title = title;
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
                 <link href="/static/css/style.css" rel="stylesheet">
               </head>
               """.formatted(title);
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
                     <li><a href="/index">Главная</a></li>
                     <li><a href="/schedule">Расписание</a></li>
                     <li><a href="/homework">Домашние задания</a></li>
                     <li><a href="/notes">Заметки</a></li>
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
