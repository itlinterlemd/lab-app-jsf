package co.edu.itli.labs.appjsf.util;

import java.io.Serializable;

import javax.faces.context.FacesContext;

public class SessionUtils implements Serializable {

    public static void add(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext()
        .getSessionMap().put(key, value);
    }
    
    public static void remove(String key) {
        FacesContext.getCurrentInstance().getExternalContext()
        .getSessionMap().remove(key);
    }

    public static Object get(String key) {
      return FacesContext.getCurrentInstance().getExternalContext()
        .getSessionMap().get(key);
    }
}