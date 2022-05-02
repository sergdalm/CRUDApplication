package controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WriterService;
import until.JspHelper;

import java.io.IOException;

@WebServlet("/writers")
public class WritersServlet extends HttpServlet {
    private final WriterService writerService = WriterService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("writers", writerService.getAllWriters());

        req.getRequestDispatcher(JspHelper.getPath("writers"))
                .forward(req, resp);
        System.out.println("hi!!!");
    }
}
