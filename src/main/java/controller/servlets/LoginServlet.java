package controller.servlets;

import dto.LoginWriterDto;
import exceptions.LoginErrorException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.postgres.PostgresWriterRepository;
import service.WriterService;
import until.JspHelper;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final WriterService writerService = new WriterService(PostgresWriterRepository.getInstance());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            LoginWriterDto writerDto = writerService.loginWriter(email, password);


        } catch (LoginErrorException e) {
            throw new RuntimeException(e);
        }
    }
}
