package co.edu.itli.labs.appjsf.controller;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import co.edu.itli.labs.appjsf.data.UserRepository;
import co.edu.itli.labs.appjsf.model.User;
import co.edu.itli.labs.appjsf.util.SessionUtils;


/**
 * Simple login bean.
 * 
 * @author itcuties
 */
@ManagedBean(name="loginController")
@ViewScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 7765876811740798583L;

	// Simple user database :)
	private static final String[] users = {"admin:1234","user:1234"};
	
	
    @Inject
    private UserRepository UserRepo;
    
	private String username;
	private String password;
	
	private boolean loggedIn;

	/*@ManagedProperty(value="#{navigationBean}")
	private NavigationBean navigationBean;*/
	
	/**
	 * Login operation.
	 * @return
	 */
	public String doLogin() {
		// Get every user from our sample database :)
		
	User user=	UserRepo.
			findByEmailPassword(username, password);
			// Successful login
			if (user!=null) {
				
				SessionUtils.add("userlogued", username);//("userlogued"); 
				
				loggedIn = true;
				return "view/home?faces-redirect=true";
			}
		
		// Set login ERROR
		FacesMessage msg = new FacesMessage("Login error. usuario o password no existe.", "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
		
		// To to login page
		return "login";
		
	}
	
	/**
	 * Logout operation.
	 * @return
	 */
	public String doLogout() {
		// Set the paremeter indicating that user is logged in to false
		loggedIn = false;
		
		// Set logout message
		/*FacesMessage msg = new FacesMessage("Logout success!", "INFO MSG");
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, msg);*/
		
		SessionUtils.remove("userlogued");
		return "users/administrarUsuarios?faces-redirect=true";
	}

	// ------------------------------
	// Getters & Setters 
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	
}