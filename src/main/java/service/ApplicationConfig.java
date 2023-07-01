/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author nicol
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.AdministradorFacadeREST.class);
        resources.add(service.CategoriaFacadeREST.class);
        resources.add(service.ClienteProveedorFacadeREST.class);
        resources.add(service.CobrosFacadeREST.class);
        resources.add(service.DocumentoFacadeREST.class);
        resources.add(service.DocumentoLineaFacadeREST.class);
        resources.add(service.ListaPrecioFacadeREST.class);
        resources.add(service.MedioDePagoFacadeREST.class);
        resources.add(service.ProductoFacadeREST.class);
        resources.add(service.ProductoTalleFacadeREST.class);
        resources.add(service.RolFacadeREST.class);
        resources.add(service.SeniaFacadeREST.class);
        resources.add(service.TalleFacadeREST.class);
        resources.add(service.TemporadaFacadeREST.class);
        resources.add(service.TipoDocumentoFacadeREST.class);
        resources.add(service.TokenFacadeREST.class);
        resources.add(service.UsuarioFacadeREST.class);
        resources.add(service.VendedorFacadeREST.class);
    }
    
}
