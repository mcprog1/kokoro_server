/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Controladores.DocumentoController;
import Entidades.Documento;
import Entidades.RespuestaWebService;
import com.google.gson.Gson;
import java.util.Date;
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
@Path("entidades.documento")
public class DocumentoFacadeREST extends AbstractFacade<Documento> {

    @PersistenceContext(unitName = "com.lan_KokoroApi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public DocumentoFacadeREST() {
        super(Documento.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Documento entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Documento entity) {
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
    public Documento find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Documento> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Documento> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("/cambiar-tipo-cabezal")
    @Produces(MediaType.APPLICATION_JSON)
    public String cambiarTipoCabezal(Documento documento)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Actualizado correctamente");
    
        Documento cabezal = super.find(documento.getId());
        
        if(documento.getTipoDocumento() != null)
        {
            if(!documento.getTipoDocumento().equals(cabezal.getTipoDocumento()))
            {
                cabezal.setTipoDocumento(cabezal.getTipoDocumento());
                super.edit(cabezal);
                rws.setData(cabezal);
            }
        }
        
        return new Gson().toJson(rws);
        
    }
    
    
    @POST
    @Path("/cambiar-cliente")
    @Produces(MediaType.APPLICATION_JSON)
    public String colocarClienteProveedor(Documento documento)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Actualizado correctamente");
    
        Documento cabezal = super.find(documento.getId());
        //String continuo = "N";
        
        if(documento.getClienteProveedor() != null)
        {
            if(!documento.getClienteProveedor().equals(cabezal.getClienteProveedor()))
            {
                cabezal.setClienteProveedor(cabezal.getClienteProveedor());
                super.edit(cabezal);
            }
        }
        
        return new Gson().toJson(rws);
    }
    
    @POST
    @Path("/cambiar-forma-pago")
    @Produces(MediaType.APPLICATION_JSON)
    public String cambiarFormaPago(Documento documento)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Actualizado correctamente");
    
        Documento cabezal = super.find(documento.getId());
        String continuo = "N";
        
        if(documento.getFormaPago() == 2)//Si es forma de pago credito tengo que checkear la fecha de vencimiento
        {
            if(documento.getFechaVencimiento() == null)//Si esta vacio entonces no hago nada
            {
                rws.setCode(100);
                rws.setMsg("Si es a credito la fecha de vencimiento tiene que ser obligatoria");
            }else{
                Date fechaVencimiento = documento.getFechaVencimiento();
                if(fechaVencimiento.before(cabezal.getFechaEmision()))//Si la fecha de emision es menor a la fecha de emision entonces no lo dejo seguir
                {
                    rws.setCode(101);
                    rws.setMsg("La fecha de vencimiento tiene que ser mayor a la fecha de emision");
                }else{
                    //Seteo la fecha de vencimiento y la forma de pago
                    continuo = "S";
                    cabezal.setFechaVencimiento(fechaVencimiento);
                    cabezal.setFormaPago(documento.getFormaPago());
                }
            }
        }else{ //Si es contado entonces lo que tenog que hacer es setear la forma de pago y colocar en null la fecha de vencimiento
            continuo = "S";
            cabezal.setFormaPago(documento.getFormaPago());
            cabezal.setFechaVencimiento(null);
        }
        
        if(continuo.equals("S"))
        {
            super.edit(cabezal);
        }
        
        return new Gson().toJson(rws);
    }
    
    @POST
    @Path("/nuevo-cabezal")
    @Produces(MediaType.APPLICATION_JSON)
    public String generarCabezal(Documento documento)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Generado correctamente");
        documento.setEstado("V");
        super.create(documento);
        rws.setData(documento.getId().toString());
        return new Gson().toJson(rws);
    }
    
    @POST
    @Path("/finalizar-cabezal")
    @Produces(MediaType.APPLICATION_JSON)
    public String confirmarCabezal(Documento documento)
    {
        RespuestaWebService rws = new RespuestaWebService();    
        rws = new DocumentoController(this.em).finalizarDocumento(documento);
        return new Gson().toJson(rws);
    }
    
}
