/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.edu.itli.labs.appjsf.controller;

import javax.annotation.PostConstruct;
//import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import co.edu.itli.labs.appjsf.data.UserRepository;
import co.edu.itli.labs.appjsf.model.User;
import co.edu.itli.labs.appjsf.service.UserRegistration;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
//@Model
@ManagedBean(name="userController")
@ViewScoped
public class UserController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private UserRegistration userRegistration;

    @Produces
    @Named
    private User newUser;
    
    @Inject
    private UserRepository UserRepo;
    
    private java.util.List<User> UserLista;
    

    @PostConstruct
    public void initNewUser() {
        newUser = new User();
        consultarTodos();
    }

    public void consultarTodos() {
		UserLista=UserRepo.findAllOrderedByName();
	}
    public void register() throws Exception {
        try {
        	
        	if(newUser!=null 
        			&& newUser.getId()!=null) {
        		
        		actualizar();
        		return;
        		
        	}
        	
        	
        	if(newUser==null) {
        		FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "error!", "error inesperado");
                facesContext.addMessage(null, m);
                return;
        	}
            userRegistration.register(newUser);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
            initNewUser();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
    }
    
    public void actualizar() throws Exception {
        try {
        	if(newUser==null && newUser.getId()!=null 
        			&& newUser.getEmail()!=null) {
        		
        		FacesMessage m = new FacesMessage(
        				FacesMessage.SEVERITY_ERROR, 
        				"error!", "error validando ");
                facesContext.addMessage(null, m);
                return;
        	}
        	
            userRegistration.actualizar(newUser);
            FacesMessage m = new FacesMessage(
            		FacesMessage.SEVERITY_INFO, 
            		"Actualizando!", "Actualizacion successful");
            facesContext.addMessage(null, m);
            initNewUser();
                        
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
    }
    
    /**
     * remover objeto memeber por id
     * @param idAEliminar
     * @throws Exception
     */
    public void remover(Long idAEliminar) throws Exception {
        try {
            userRegistration.borrar(idAEliminar);
            consultarTodos();
            FacesMessage m = new FacesMessage
            		(FacesMessage.SEVERITY_INFO, "Eliminado!", 
            				"REliminado id: "+idAEliminar);
            
            facesContext.addMessage(null, m);
          
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
            		errorMessage, "Error eliminando un Memeber");
            facesContext.addMessage(null, m);
        }
    }


    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public java.util.List<User> getUserLista() {
		return UserLista;
	}

	public void setUserLista(java.util.List<User> UserLista) {
		this.UserLista = UserLista;
	}

}
