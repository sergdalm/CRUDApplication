package controller.servlets;

import dto.LoginWriterDto;
import exceptions.ValidException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.postgres.PostgresWriterRepository;
import service.WriterService;
import until.JspHelper;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final WriterService writerService = new WriterService(PostgresWriterRepository.getInstance());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginWriterDto loginWriterDto = LoginWriterDto.builder()
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
        try {
            writerService.createWriter(loginWriterDto);
        } catch (ValidException exc) {
            req.setAttribute("errors", exc.getErrors());
            doGet(req, resp);
        }
    }
}
