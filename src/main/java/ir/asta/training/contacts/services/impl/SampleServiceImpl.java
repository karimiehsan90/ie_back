package ir.asta.training.contacts.services.impl;

import ir.asta.training.contacts.entities.TimeEntity;
import ir.asta.training.contacts.entities.BookEntity;
import ir.asta.training.contacts.manager.SampleManager;
import ir.asta.training.contacts.manager.RequestManager;
import ir.asta.training.contacts.services.SampleService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import java.io.IOException;

@Service("sampleService")
public class SampleServiceImpl implements SampleService {

    @Inject
    private RequestManager requestManager;

    @Inject
    private SampleManager sampleManager;

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;


    @Override
    public BookEntity processBook(BookEntity entity) {
        requestManager.saveToSession(request);
        requestManager.saveToApplication(request);
        return sampleManager.book(entity);
    }

    @Override
    public TimeEntity convertDate(int year, int month, int day) {
        requestManager.saveToSession(request);
        requestManager.saveToApplication(request);
        return sampleManager.time(year, month, day);
    }

    @Override
    public void submit() throws ServletException, IOException {
        requestManager.saveToSession(request);
        requestManager.saveToApplication(request);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/submit.jsp");
        dispatcher.forward(request, response);
    }


}
