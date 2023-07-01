/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Entidades.Categoria;
import Entidades.Producto;
import Entidades.ProductoTalle;
import Entidades.RespuestaWebService;
import com.google.gson.Gson;
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
import service.ProductoTalleFacadeREST;

/**
 *
 * @author nicol
 */
@Stateless
@Path("entidades.producto")
public class ProductoFacadeREST extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "com.lan_KokoroApi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public ProductoFacadeREST() {
        super(Producto.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Producto entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Producto entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Producto find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Producto> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Producto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @POST
    @Path("/nuevo-producto")
    @Produces(MediaType.APPLICATION_JSON)
    public String nuevoProducto(Producto p)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Producto agregado con exito");
        rws.setData(null);
        
        if(p.getNombre().isBlank() || p.getNombre().isBlank())
        {
            rws.setCode(100);
            rws.setMsg("El nombre es obligatorio");
            return new Gson().toJson(rws);
        }
        
        if(p.getGenero().isBlank() || p.getGenero().isEmpty())
        {
            rws.setCode(100);
            rws.setMsg("El genero es obligatorio");
            return new Gson().toJson(rws);
        }
     
        if(p.getCategoria() == null || p.getCategoria().getId() <= 0)//Si envio alguna categoria
        {
            rws.setCode(100);
            rws.setMsg("La categoria es obligatoria");
            return new Gson().toJson(rws);
        }
            
        super.create(p);
        rws.setData(p);
        return new Gson().toJson(rws);
    }
    
    
    
}
