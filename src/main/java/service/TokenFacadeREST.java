/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Controladores.TokenController;
import Entidades.Token;
import Entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author nicol
 */
@Stateless

public class TokenFacadeREST extends AbstractFacade<Token> {

    @PersistenceContext(unitName = "com.lan_KokoroApi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public TokenFacadeREST() {
        super(Token.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Token entity) {
        super.create(entity);
    }

    
    public void edit(@PathParam("id") Long id, Token entity) {
        super.edit(entity);
    }

    
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    
    
    public Token find(@PathParam("id") Long id) {
        return super.find(id);
    }

    
    public List<Token> findAll() {
        return super.findAll();
    }

    
    public List<Token> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    
    public String countREST() {
        return String.valueOf(super.count());
    }

    
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public String generarToken(EntityManager em,Usuario usuario)
    {
        Token token = new TokenController(em).generarToken(usuario); 
        this.em = em;
        String a = "";
        super.create(token);
        return token.getToken();
    }
    
}
