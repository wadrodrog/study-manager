package ru.itis.web;

public class HtmlManager {
    private final String title;

    public HtmlManager(String title) {
        this.title = title;
    }

    public String generate(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("  <meta charset=\"UTF-8\">\n");
        sb.append("  <title>%s</title>\n".formatted(title));
        sb.append("  <link href=\"/favicon.ico\" rel=\"icon\">\n");
        sb.append("  <link href=\"/css/store/style.css\" rel=\"stylesheet\">\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("<h1>%s</h1>\n".formatted(title));
        sb.append(content);
        sb.append("</body>\n");
        sb.append("</html>\n");
        return sb.toString();
    }
}
