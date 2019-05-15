package ir.asta.training.contacts.manager;

import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named("requestManager")
public class RequestManager {

    public void saveToSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        Object o = session.getAttribute("cnt");
        int cnt = o != null ? (int)o : 0;
        session.setAttribute("cnt", cnt + 1);
    }

    public void saveToApplication(HttpServletRequest request){
        ServletContext context = request.getServletContext();
        Object o = context.getAttribute("cnt");
        int cnt = o != null ? (int)o:0;
        context.setAttribute("cnt", cnt + 1);
    }
}
