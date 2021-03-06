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
package co.edu.itli.labs.appjsf.service;

import co.edu.itli.labs.appjsf.model.User;
import co.edu.itli.labs.appjsf.util.HashTextUtils;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;


import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class UserRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

   

    public void register(User User) throws Exception {
        log.info("Registering " + User.getName());
        
        String passEntradaWeb=User.getPassword();   
        
        User.setPassword(HashTextUtils.sha256(passEntradaWeb)); 
        
        em.persist(User);
       // UserEventSrc.fire(User);
    }
    
    /**
     * Permite actualizar un objeto User en la base de datos
     * @param User
     * @throws Exception
     */
    public void actualizar(User user) throws Exception {
        log.info("Actualizando " + user.getName());
        
        String passEntradaWeb=user.getPassword();       
        user.setPassword(HashTextUtils.sha256(passEntradaWeb));
        
         em.merge(user);
        // UserEventSrc.fire(user);
    }
    
    /**
     * Elimina objeto memeber de la base de datos
     * @param id
     * @throws Exception
     */
    public void borrar(Long id) throws Exception {
        log.info("Eliminando User con id: " + id);
        
        em.remove(em.find(User.class, id));
       
        
    }
}
