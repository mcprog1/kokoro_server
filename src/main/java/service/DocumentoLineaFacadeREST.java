/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Controladores.ListaPrecioController;
import Entidades.DocumentoLinea;
import Entidades.ListaPrecio;
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
@Path("entidades.documentolinea")
public class DocumentoLineaFacadeREST extends AbstractFacade<DocumentoLinea> {

    @PersistenceContext(unitName = "com.lan_KokoroApi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public DocumentoLineaFacadeREST() {
        super(DocumentoLinea.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(DocumentoLinea entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, DocumentoLinea entity) {
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
    public DocumentoLinea find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<DocumentoLinea> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<DocumentoLinea> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @POST
    @Path("/nueva-linea")
    @Produces(MediaType.TEXT_PLAIN)
    public String agregarLinea(DocumentoLinea linea){
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Actualizado correctamente");
        
        if(linea.getProductoTalle() == null)
        {
            rws.setCode(100);
            rws.setMsg("El producto es obligatorio.");
            return new Gson().toJson(rws);
        }

        if(linea.getCantidad() <= 0)
        {
            rws.setCode(100);
            rws.setMsg("La cantidad tiene que ser mayor o igual a 1.");
            return new Gson().toJson(rws);
        }
        rws.setData(linea);
        //Creo la linea 
        super.create(linea);
        
        //Checkeo la LP si hay y cual aplicar
        
        ListaPrecio listaPrecioAplicar = new ListaPrecioController(this.em).obtenerListaPrecioAplicar(linea.getProductoTalle());
        if(!listaPrecioAplicar.equals(null))//Si tiene lista de precio entonces tengo que aplicar el valor correspondiente a la linea
        {    
            rws.setData(this.aplicarDescuento(linea, listaPrecioAplicar));
        }
        

        return new Gson().toJson(rws);
    }
    
    public DocumentoLinea aplicarDescuento(DocumentoLinea linea, ListaPrecio listaPrecio)
    {
        float valorAplicar = listaPrecio.getValor();
        float precio = linea.getPrecioUnitario();
        
        
        if(valorAplicar < 0)
        {
            valorAplicar = valorAplicar * (-1);
            linea.setDescuento(valorAplicar);
        }
        
        valorAplicar = valorAplicar / 100;//Si es 10 lo convierto a 0,1, Si
        
        if(listaPrecio.getValor() < 0)
        {
            valorAplicar = 1 - valorAplicar;
        }else{
            valorAplicar = 1 + valorAplicar;
        }
        
        precio = precio * valorAplicar;
        
        linea.setPrecioUnitario(precio);
        
        super.edit(linea);
        
        return linea;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
