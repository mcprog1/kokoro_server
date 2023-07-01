/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

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

/**
 *
 * @author nicol
 */
@Stateless
@Path("entidades.productotalle")
public class ProductoTalleFacadeREST extends AbstractFacade<ProductoTalle> {

    @PersistenceContext(unitName = "com.lan_KokoroApi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public ProductoTalleFacadeREST() {
        super(ProductoTalle.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(ProductoTalle entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, ProductoTalle entity) {
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
    public ProductoTalle find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<ProductoTalle> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ProductoTalle> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("/nuevo-talle")
    @Produces(MediaType.APPLICATION_JSON)
    public String nuevoTalleProducto(List<ProductoTalle> pt)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Los talles se registraron con exito");
        rws.setData(null);

        if(!pt.isEmpty())
        {
            for(ProductoTalle talle: pt)
            {
                if(talle.getMargenGanancia() < 0)
                {
                    rws.setCode(100);
                    rws.setMsg("El margen de ganancia es obligatorio");
                    return new Gson().toJson(rws);
                }
                
                if(talle.getPrecioCosto()< 0)
                {
                    rws.setCode(100);
                    rws.setMsg("El precio costo es obligatorio");
                    return new Gson().toJson(rws);
                }
                
                
                if(talle.getPrecioVenta()< 0)
                {
                    rws.setCode(100);
                    rws.setMsg("El precio de venta es obligatorio");
                    return new Gson().toJson(rws);
                }
                
                if(talle.getStock()< 0)
                {
                    rws.setCode(100);
                    rws.setMsg("El precio de venta es obligatorio");
                    return new Gson().toJson(rws);
                }
                
                if(talle.getProducto() == null || talle.getProducto().getId() <= 0)
                {
                    rws.setCode(100);
                    rws.setMsg("El producto es obligatorio");
                    return new Gson().toJson(rws);
                }
                
                if(talle.getTalle()== null || talle.getTalle().getId() <= 0)
                {
                    rws.setCode(100);
                    rws.setMsg("El talle es obligatorio");
                    return new Gson().toJson(rws);
                }
            }
            
            //Una ves chequeado que este todo ok entonces creo el talle
            for(ProductoTalle pTalle: pt)
            {
                super.create(pTalle);
            }
        }else{
            rws.setCode(100);
            rws.setMsg("No se envio ningun talle");
        }
        
        return new Gson().toJson(rws);
    }
    
    
    @POST
    @Path("/agregar-stock")
    @Produces(MediaType.APPLICATION_JSON)
    public String agregarStock(ProductoTalle pt)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("El stock se modifico con exito");
        rws.setData(null);
        
        if(pt.getId() < 0)
        {
            rws.setCode(100);
            rws.setMsg("EL talle a agregar stock tiene que ser obligatorio");
            return new Gson().toJson(rws);
        }
        
        if(pt.getStock() > 0)
        {
            rws.setCode(100);
            rws.setMsg("El stock a agregar tiene que ser mayor a 0");
            return new Gson().toJson(rws);
        }
        
        ProductoTalle ptEdit = super.find(pt.getId());
        int stockActual = ptEdit.getStock();
        int stockNuevo = stockActual + pt.getStock();
        ptEdit.setStock(stockNuevo);
        super.edit(ptEdit);
        
        return new Gson().toJson(rws);
    }
    
    @POST
    @Path("/eliminar-stock")
    @Produces(MediaType.APPLICATION_JSON)
    public String eliminarStock(ProductoTalle pt)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("El stock se modifico con exito");
        rws.setData(null);
        
        if(pt.getId() < 0)
        {
            rws.setCode(100);
            rws.setMsg("EL talle a agregar stock tiene que ser obligatorio");
            return new Gson().toJson(rws);
        }
        
        if(pt.getStock() > 0)
        {
            rws.setCode(100);
            rws.setMsg("El stock a eliminar tiene que ser mayor a 0");
            return new Gson().toJson(rws);
        }
        
        ProductoTalle ptEdit = super.find(pt.getId());
        int stockActual = ptEdit.getStock();
        int stockNuevo = stockActual - pt.getStock();
        ptEdit.setStock(stockNuevo);
        super.edit(ptEdit);
        
        return new Gson().toJson(rws);
    }
    
}
