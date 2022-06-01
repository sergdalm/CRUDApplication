package controller.servlets;

import dto.WriterDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.postgres.PostgresLabelRepository;
import service.LabelService;
import service.PostService;
import service.WriterService;
import until.JspHelper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/post")
public class PostServlet extends HttpServlet {
    private final PostService postService = PostService.getInstance();
    private final WriterService writerService = WriterService.getInstance();
    private final LabelService labelService = new LabelService(PostgresLabelRepository.getInstance());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Integer postId = Integer.valueOf(req.getParameter("postId"));
        Integer writerId = Integer.valueOf(req.getParameter("writerId"));
        req.setAttribute("post", postService.getById(postId));
        req.setAttribute("writer", writerService.getWriterById(writerId));
        req.setAttribute("labels", labelService.getLabelsByPostId(postId) );

        req.getRequestDispatcher(JspHelper.getPath("post"))
                .forward(req, resp);
    }
}
