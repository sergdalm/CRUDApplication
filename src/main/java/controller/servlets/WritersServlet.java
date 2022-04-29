package controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WriterService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/writers")
public class WritersServlet extends HttpServlet {
    private final WriterService writerService = WriterService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (var printWriter = resp.getWriter()) {
            printWriter.write("<h1>List of writers</h1>");
            printWriter.write("<ul>");
            writerService.getAllWriters().forEach(writerDto -> printWriter.write(
                    "<li>" +
                            writerDto.getFirstName() + " " + writerDto.getLastName()
                    + "</li>"
            ));
            printWriter.write("</ul>");
        }
    }
}
