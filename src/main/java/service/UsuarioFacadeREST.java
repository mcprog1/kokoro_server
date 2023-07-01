/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Controladores.UsuariosController;
import Entidades.Usuario;
import Entidades.Vendedor;
import Entidades.Administrador;
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
import service.AbstractFacade;
import com.google.gson.Gson;  
import Entidades.RespuestaWebService;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author nicol
 */
@Stateless
@Path("entidades.usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "com.lan_KokoroApi_war_1.0-SNAPSHOTPU")
    public EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON})
    public void create(Usuario entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, Usuario entity) {
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
    public Usuario find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {        
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("/prueba")
    @Produces(MediaType.TEXT_PLAIN)
    public Date pruebaRest()
    {        
        Date fechaExpiracion = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaExpiracion);
        calendar.add(Calendar.HOUR, 12);//Le sumo 12 horas
        return calendar.getTime();
    }
    
    @POST
    @Path("/cambiar-clave")
    @Produces(MediaType.APPLICATION_JSON)
    public String pruebaRest(Usuario a)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Clave cambiada con exito");
                
        if(a.getClave().isEmpty() || a.getClave().isBlank())
        {
            rws.setCode(101);
            rws.setMsg("La clave es obligatoria.");
               
            String json = new Gson().toJson(rws);
            return json;
        }else{
            
            if(a.getClave().length() < 8)
            {
                rws.setCode(102);
                rws.setMsg("La clave tiene que ser un minimo de 8 caracteres de largo");
                   
                String json = new Gson().toJson(rws);
                return json;
            }
        }
        
        Usuario usuario = super.find(a.getId());
        
        usuario.setClave(a.getClave());
        usuario.encriptarClave();
        super.edit(usuario);
        
        String json = new Gson().toJson(rws);
        return json;
    }
    
    
    /*
    * Creador de vendedores
    */
    @POST
    @Path("/nuevo-usuario/vendedor")
    @Produces(MediaType.APPLICATION_JSON)
    public String nuevoUsuarioVendedorRest(Vendedor v)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Vendedor registrado con exito");
        //rws.setData("");
        //Si se manda vacio o un espacio
        if(v.getNickname().isEmpty() || v.getNickname().isBlank())
        {
            rws.setCode(100);
            rws.setMsg("El nickname es obligatorio para crear el usuario");
               
            String json = new Gson().toJson(rws);
            return json;
        }
        //Si se manda vacio o un espacio
        if(v.getClave().isEmpty() || v.getClave().isBlank())
        {
            rws.setCode(101);
            rws.setMsg("La clave es obligatoria.");
               
            String json = new Gson().toJson(rws);
            return json;
        }else{
            
            if(v.getClave().length() < 8)
            {
                rws.setCode(102);
                rws.setMsg("La clave tiene que ser un minimo de 8 caracteres de largo");
                   
                String json = new Gson().toJson(rws);
                return json;
            }
        }
        
        //Si esta vacio o envio un espacio en blanco entonces tranco
        if(v.getNombre().isEmpty() || v.getNombre().isBlank() )
        {
            rws.setCode(103);
            rws.setMsg("El nombre tiene es obligatorio");
            String json = new Gson().toJson(rws);
            return json;
        }
        String existeUsername = new UsuariosController(this.em).existeNickname(v.getNickname());
        if(existeUsername.equals("N"))//Si no existe el usuario entonces lo creo
        {
            v.encriptarClave();
            v.setTipo("V");
            super.create(v);
        }else{
            rws.setCode(104);
            rws.setMsg("El nickname ya existe.");
        }
        
        String json = new Gson().toJson(rws);
        return json;
    }
    
    /* 
     * Nuevo administador 
    */
    @POST
    @Path("/nuevo-usuario/administrador")
    @Produces(MediaType.APPLICATION_JSON)
    public String nuevoUsuarioAdmnistradorRest(Administrador a)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Administrador registrado con exito");
        //rws.setData("");
        //Si se manda vacio o un espacio
        if(a.getNickname().isEmpty() || a.getNickname().isBlank())
        {
            rws.setCode(100);
            rws.setMsg("El nickname es obligatorio para crear el usuario");
               
            String json = new Gson().toJson(rws);
            return json;
        }
        //Si se manda vacio o un espacio
        if(a.getClave().isEmpty() || a.getClave().isBlank())
        {
            rws.setCode(101);
            rws.setMsg("La clave es obligatoria.");
               
            String json = new Gson().toJson(rws);
            return json;
        }else{
            
            if(a.getClave().length() < 8)
            {
                rws.setCode(102);
                rws.setMsg("La clave tiene que ser un minimo de 8 caracteres de largo");
                   
                String json = new Gson().toJson(rws);
                return json;
            }
        }
        
        //Si esta vacio o envio un espacio en blanco entonces tranco
        if(a.getNombre().isEmpty() || a.getNombre().isBlank() )
        {
            rws.setCode(103);
            rws.setMsg("El nombre tiene es obligatorio");
            String json = new Gson().toJson(rws);
            return json;
        }
        String existeUsername = new UsuariosController(this.em).existeNickname(a.getNickname());
        if(existeUsername.equals("N"))//Si no existe el usuario entonces lo creo
        {
            a.encriptarClave();
            a.setTipo("A");
            super.create(a);
        }else{
            rws.setCode(104);
            rws.setMsg("El nickname ya existe.");
        }
        
        String json = new Gson().toJson(rws);
        return json;
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String loginRest(String datos)
    {
        Usuario usuario = new Gson().fromJson(datos, Usuario.class);
        RespuestaWebService rws = new UsuariosController(this.em).login(usuario);
        String json = new Gson().toJson(rws);
        return json;
    }
    
}
