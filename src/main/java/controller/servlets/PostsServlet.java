package controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.postgres.PostgresPostRepository;
import repository.postgres.PostgresWriterRepository;
import service.PostService;
import service.WriterService;
import until.JspHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/posts")
public class PostsServlet extends HttpServlet {
    private final WriterService writerService = new WriterService(PostgresWriterRepository.getInstance());
    private final PostService postService = new PostService(PostgresPostRepository.getInstance());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Integer writerId = Integer.valueOf(req.getParameter("writerId"));
        req.setAttribute("writer", writerService.getWriterById(writerId));
        req.setAttribute("writerName", writerService.getWriterById(writerId).getFullName());
        req.setAttribute("posts", postService.getAllPostsByWriterId(writerId));

        req.getRequestDispatcher(JspHelper.getPath("posts"))
                .forward(req, resp);
    }
}
