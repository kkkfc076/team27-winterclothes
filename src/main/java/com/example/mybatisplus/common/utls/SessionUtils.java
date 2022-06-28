package com.example.mybatisplus.common.utls;

import com.example.mybatisplus.model.domain.Batch;
import com.example.mybatisplus.model.domain.Manager;
import com.example.mybatisplus.model.domain.Student;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class SessionUtils {
    private static final String USERKEY = "sessionUser";
    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    public static void saveCurUser(Manager manager) {
        session().setAttribute("curUser",manager);
    }
    public static void saveCurUser(Student student) {
        session().setAttribute("curUser",student);
    }


    public static Manager getCurUser() { return (Manager) session().getAttribute("curUser");}

    public static Student getCurstu() {
        return (Student) session().getAttribute("curUser");
    }

    public static Student getCurSUser() {return (Student) session().getAttribute("curUser");}

    public static void saveCurTime(LocalDateTime dateTime) {session().setAttribute("logintime",dateTime);}

    public static LocalDateTime getCurTime() {return (LocalDateTime) session().getAttribute("logintime");}

    public static void saveCurBatch(Batch batch) {session().setAttribute("curBatch",batch);}

    public static Batch getCurBatch(){return (Batch) session().getAttribute("curBatch");}


}
