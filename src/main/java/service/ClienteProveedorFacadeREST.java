/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Entidades.ClienteProveedor;
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
@Path("entidades.clienteproveedor")
public class ClienteProveedorFacadeREST extends AbstractFacade<ClienteProveedor> {

    @PersistenceContext(unitName = "com.lan_KokoroApi_war_1.0-SNAPSHOTPU")
    public EntityManager em;

    public ClienteProveedorFacadeREST() {
        super(ClienteProveedor.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(ClienteProveedor entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, ClienteProveedor entity) {
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
    public ClienteProveedor find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<ClienteProveedor> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ClienteProveedor> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("/nuevo-cliente")
    @Produces(MediaType.APPLICATION_JSON)
    public String nuevoCliente(ClienteProveedor cl){
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Cliente registrado con exito");
        //rws.setData("");
        String json = "";
        if(cl.getDocumento().isBlank() || cl.getDocumento().isEmpty())
        {
            rws.setCode(100);
            rws.setMsg("El documento no puede ser vacio");
            //rws.setData("");
            json = new Gson().toJson(rws); 
            return json;
        }
        
        if(cl.getNombre().isBlank() || cl.getNombre().isEmpty())
        {
            rws.setCode(100);
            rws.setMsg("El nombre no puede estar vacio");
            //rws.setData("");
            json = new Gson().toJson(rws); 
            return json;
        }
        
        if(cl.getApellido().isBlank() || cl.getNombre().isBlank())
        {
            rws.setCode(100);
            rws.setMsg("El apellido no puede estar vacio");
            //rws.setData("");
            json = new Gson().toJson(rws); 
            return json;
        }
        
        String existeCliente = new ClienteProveedor().existeNroDocumento(cl.getDocumento(), cl.getEsProveedor());
        if(existeCliente.equals("S")) //Si existe un cliente con el documento que ademas es proveedor entonces tranco
        {
            rws.setCode(101);
            rws.setMsg("El documento ya esta registrado");
            //rws.setData("");
            json = new Gson().toJson(rws); 
            return json;
        }else{
            super.create(cl);
        }
            
        json = new Gson().toJson(rws);
        return json;
    }
    
}
