/**
 * Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * https://github.com/javaee/firstcup-examples/LICENSE.txt
 */
package firstcup.ejb;

import firstcup.entity.FirstcupUser;
import java.util.Calendar;//Calendario
import java.util.Date;//Fechas
import java.util.GregorianCalendar;//Subclase calendario
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * DukesBirthdayBean is a stateless session bean that calculates the age
 * difference between a user and Duke, who was born on May 23, 1995.
 */
@Stateless
public class DukesBirthdayBean {

    private static final Logger logger = Logger.getLogger("firstcup.ejb.DukesBirthdayBean");
    @PersistenceContext
    private EntityManager em;
/**
 * El siguiente método de negocio obtiene o devuelve la diferencia de edad 
 * promedio de todos los usuarios
 */
    public Double getAverageAgeDifference() {
        // Insert code here
        Double avgAgeDiff = (Double) em.createNamedQuery("findAverageAgeDifferenceOfAllFirstcupUsers").getSingleResult();
        logger.log(Level.INFO, "Average age difference is: {0}", avgAgeDiff);
        return avgAgeDiff;
    }

    public int getAgeDifference(Date date) {
        
        int ageDifference;
        Calendar theirBirthday = new GregorianCalendar();
        Calendar dukesBirthday = new GregorianCalendar(1995,Calendar.MAY,23);;
        //Establecer el objeto de calendario a la fecha ingresada por argumentos
        theirBirthday.setTime(date);
        // Resta la edad del usuario de la edad de Duke
        ageDifference = dukesBirthday.get(Calendar.YEAR) - theirBirthday.get(Calendar.YEAR);
        // Se ingresa datos al log para evidenciarlos en la información que sale por consola
        logger.log(Level.INFO, "Raw ageDifference is: {0}", ageDifference);
        // Compruebe si el cumpleaños de Duke se produce antes que el del usuario. Si es así,
        // Restar uno de la diferencia de edad
        if (dukesBirthday.before(theirBirthday)&& (ageDifference>0)){
            ageDifference--;
        }
        // Crea y almacena el cumpleaños del usuario en la base de datos
        FirstcupUser user = new FirstcupUser(date,ageDifference);
        em.persist(user);
        return ageDifference;
    }
}
