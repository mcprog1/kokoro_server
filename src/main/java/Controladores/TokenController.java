/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import javax.persistence.EntityManager;
import Entidades.*;
import java.util.Calendar;
import java.util.Date;
import service.TokenFacadeREST;
/**
 *
 * @author nicol
 */
public class TokenController {
    
    private EntityManager em;
    
    public TokenController(EntityManager em){
        this.em = em;
    }
    
    public Token generarToken(Usuario usuario) 
    {
        int i = 15;
        String theAlphaNumericS;
        StringBuilder builder;
        
        theAlphaNumericS = "abcdefghisklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!#$%&"; 

        //create the StringBuffer
        builder = new StringBuilder(i); 

        for (int m = 0; m < i; m++) {

            // generate numeric
            int myindex 
                = (int)(theAlphaNumericS.length() 
                        * Math.random()); 

            // add the characters
            builder.append(theAlphaNumericS 
                        .charAt(myindex)); 
        } 
        Token token = new Token();
        token.setToken(builder.toString());
        
        
        Date fechaExpiracion = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaExpiracion);
        calendar.add(Calendar.HOUR, 12);//Le sumo 12 horas
        token.setFecha_expiracion(calendar.getTime());
        token.setUsuario(usuario);
     //   new TokenFacadeREST().create(token);
     //   return builder.toString(); 
        return token;
    }
    
    
}
