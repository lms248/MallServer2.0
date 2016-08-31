package common.utils;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

public class SessionContext {
    private static HashMap<String, HttpSession> map = new HashMap<String, HttpSession>();

    public static synchronized void AddSession(HttpSession session) {
        if (session != null) {
        	map.put(session.getId(), session);
        }
    }

    public static synchronized void DelSession(HttpSession session) {
        if (session != null) {
        	map.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String session_id) {
        if (session_id == null)
        return null;
        return (HttpSession) map.get(session_id);
    }
}