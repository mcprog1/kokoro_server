/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;
import Entidades.Administrador;
import Entidades.RespuestaWebService;
import Entidades.Usuario;
import Entidades.Vendedor;
import com.google.gson.Gson;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import service.TokenFacadeREST;
/**
 *
 * @author nicol
 */
public class UsuariosController {
    //@PersistenceContext(unitName = "com.lan_KokoroApi_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public UsuariosController(EntityManager em)
    {
        this.em = em;
    }
    
    public String existeNickname(String usuario)
    {
        String existe = "S";
        List<Usuario> lista = em.createNativeQuery("SELECT * FROM usuario WHERE nickname = '"+usuario+"'", Usuario.class).getResultList();
        if (lista.isEmpty()) {
            existe = "N";
        }
        return existe;
    }
    
    public RespuestaWebService login(Usuario datosLogin)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Login existoso.");

        if(datosLogin.getNickname().isEmpty() || datosLogin.getNickname().isBlank())
        {
            rws.setCode(100);
            rws.setMsg("El nickname es obligatorio.");
               
            return rws;
        }
        
        if(datosLogin.getClave().isEmpty() || datosLogin.getClave().isBlank())
        {
            rws.setCode(100);
            rws.setMsg("La clave es obligatoria.");
            
            return rws;
        }
        
        datosLogin.encriptarClave();
        
        List<Usuario> usuarioEncontrado = em.createNativeQuery("SELECT * FROM usuario WHERE nickname = '"+datosLogin.getNickname()+"'", Usuario.class).getResultList();
        if(!usuarioEncontrado.isEmpty())
        {
            Usuario usuario = usuarioEncontrado.get(0);
            if(datosLogin.getClave().equals(usuario.getClave()))
            {
                
                List<Administrador> administrador = em.createNativeQuery("SELECT * FROM usuario WHERE nickname = '"+datosLogin.getNickname()+"'", Administrador.class).getResultList();
                if(!administrador.isEmpty())
                {
                    rws.setData(administrador);
                }else{
                    List<Vendedor> vendedor = em.createNativeQuery("SELECT * FROM usuario WHERE nickname = '"+datosLogin.getNickname()+"'", Vendedor.class).getResultList();
                    rws.setData(vendedor);
                }
            }else{
                rws.setCode(101);
                rws.setMsg("La contraseña es incorrecta.");
            }
        }else{
            rws.setCode(100);
            rws.setMsg("No existe el usuario registrado");
        }
        return rws;
    }
    
}
