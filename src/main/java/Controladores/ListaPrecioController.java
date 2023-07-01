/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.ListaPrecio;
import Entidades.Producto;
import Entidades.ProductoTalle;
import javax.persistence.EntityManager;

/**
 *
 * @author nicol
 */
public class ListaPrecioController {
    private EntityManager em;
    
    public ListaPrecioController(EntityManager em)
    {
        this.em = em;
    }
    
    /**
     * Recibo el producto talle para saber que producto es y si aplica alguna lista de precio
     *
     */
    public ListaPrecio obtenerListaPrecioAplicar(ProductoTalle idTalle)
    {
        ProductoTalle pTalle = (ProductoTalle) em.createNativeQuery("SELECT * FROM productotalle WHERE id = "+idTalle.getId(), ProductoTalle.class).getSingleResult();
        Producto producto = (Producto) em.createNativeQuery("SELECT * FROM producto WHERE id = " + pTalle.getProducto().getId(), Producto.class).getSingleResult();
        ListaPrecio listaPrecio = null;
        char tieneTemp = 'N';
        char tieneCat = 'N';
        
        if(producto.getTemporada() == null)//Tiene temporada
        {
            tieneTemp = 'S';
        }
        
        if(producto.getCategoria() == null)//Tiene categoria
        {
            tieneCat = 'S';
        }

        String where = "";
        
            if(tieneTemp == 'S' && tieneCat == 'S')// Si tiene las dos cosas entonces 
        {
           where = "  categoria_id = "+producto.getCategoria().getId()+" AND temporada_id = "+producto.getTemporada().getId();
           
           if(em.createNativeQuery("SELECT * FROM listaprecio WHERE ((fechaDesde <= NOW() OR fechaDesde IS NULL) AND (fechaHasta IS NULL OR fechaHasta >= NOW())) AND "+where, ListaPrecio.class).getResultList().size() != 0)
           {
                return (ListaPrecio) em.createNativeQuery("SELECT * FROM listaprecio WHERE ((fechaDesde <= NOW() OR fechaDesde IS NULL) AND (fechaHasta IS NULL OR fechaHasta >= NOW())) AND "+where, ListaPrecio.class).getSingleResult();    
           }
           
           where = "  temporada_id = "+producto.getTemporada().getId();
           if(em.createNativeQuery("SELECT * FROM listaprecio WHERE ((fechaDesde <= NOW() OR fechaDesde IS NULL) AND (fechaHasta IS NULL OR fechaHasta >= NOW())) AND "+where, ListaPrecio.class).getResultList().size() != 0)
           {
                return (ListaPrecio) em.createNativeQuery("SELECT * FROM listaprecio WHERE ((fechaDesde <= NOW() OR fechaDesde IS NULL) AND (fechaHasta IS NULL OR fechaHasta >= NOW())) AND "+where, ListaPrecio.class).getSingleResult();
           }    
           
           where = "  categoria_id = "+producto.getCategoria().getId();
           if(em.createNativeQuery("SELECT * FROM listaprecio WHERE ((fechaDesde <= NOW() OR fechaDesde IS NULL) AND (fechaHasta IS NULL OR fechaHasta >= NOW())) AND "+where, ListaPrecio.class).getResultList().size() != 0)
           {
                return (ListaPrecio) em.createNativeQuery("SELECT * FROM listaprecio WHERE ((fechaDesde <= NOW() OR fechaDesde IS NULL) AND (fechaHasta IS NULL OR fechaHasta >= NOW())) AND "+where, ListaPrecio.class).getSingleResult();    
           }
        }else{
            if(tieneTemp == 'S')
            {
                where = "  temporada_id = "+producto.getTemporada().getId();
                if(em.createNativeQuery("SELECT * FROM listaprecio WHERE ((fechaDesde <= NOW() OR fechaDesde IS NULL) AND (fechaHasta IS NULL OR fechaHasta >= NOW())) AND "+where, ListaPrecio.class).getResultList().size() != 0)
                {
                    return (ListaPrecio) em.createNativeQuery("SELECT * FROM listaprecio WHERE ((fechaDesde <= NOW() OR fechaDesde IS NULL) AND (fechaHasta IS NULL OR fechaHasta >= NOW())) AND "+where, ListaPrecio.class).getSingleResult();
                }
            }else if(tieneCat == 'S'){
                where = "  categoria_id = "+producto.getCategoria().getId();
                if(em.createNativeQuery("SELECT * FROM listaprecio WHERE ((fechaDesde <= NOW() OR fechaDesde IS NULL) AND (fechaHasta IS NULL OR fechaHasta >= NOW())) AND "+where, ListaPrecio.class).getResultList().size() != 0)
                {
                    return (ListaPrecio) em.createNativeQuery("SELECT * FROM listaprecio WHERE ((fechaDesde <= NOW() OR fechaDesde IS NULL) AND (fechaHasta IS NULL OR fechaHasta >= NOW())) AND "+where, ListaPrecio.class).getSingleResult();
                }
            }
        }
        
        return listaPrecio;
    }
    
    
}
