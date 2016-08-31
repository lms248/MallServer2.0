package common.utils;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * session管理器
 */
public class SessionContext {
    private static HashMap<String, HttpSession> map = new HashMap<String, HttpSession>();

    public static synchronized void addSession(HttpSession session) {
        if (session != null) {
        	map.put(session.getId(), session);
        }
    }

    public static synchronized void delSession(HttpSession session) {
        if (session != null) {
        	map.remove(session.getId());
        }
    }

    public static synchronized HttpSession getSession(String sessionId) {
        if (sessionId == null)
        return null;
        return (HttpSession) map.get(sessionId);
    }
}