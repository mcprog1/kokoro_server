/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Entidades.Documento;
import Entidades.DocumentoLinea;
import Entidades.ProductoTalle;
import Entidades.TipoDocumento;
import Entidades.RespuestaWebService;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author nicol
 */
public class DocumentoController {
    
    EntityManager em;
    
    public DocumentoController(EntityManager em)
    {
        this.em = em;
    }
    
    public RespuestaWebService finalizarDocumento(Documento documento)
    {
        RespuestaWebService rws = new RespuestaWebService();
        rws.setCode(0);
        rws.setMsg("Generado correctamente");

        Documento cabezal = em.find(Documento.class, documento.getId());
       // cabezal.setEstado("F");
        
        //Actualizo el estado del cabezal
      //  em.merge(cabezal);
        
        //Obtengo las lineas
        /*
        List<DocumentoLinea> lineas =  em.createNativeQuery("SELECT * FROM documentolinea WHERE documento_id = "+documento.getId(), DocumentoLinea.class).getResultList();
        
        for(int i = 0; i < lineas.size(); i ++)
        {
            DocumentoLinea linea = lineas.get(i);
            
            this.modificarStock(Long.MIN_VALUE, pT, i);
        }
        */
        
        rws.setData(cabezal);
        return rws;
    }
    
    
    public void modificarStock(Long tipoDocumento, ProductoTalle pT, int cantidadModificar)
    {
        ProductoTalle producto = em.find(ProductoTalle.class, pT.getId());
        TipoDocumento tipoDoc = em.find(TipoDocumento.class,tipoDocumento);
        int stockActual = producto.getStock();
        
        int stockNuevo = stockActual - ( cantidadModificar * tipoDoc.getSigno() );
        producto.setStock(stockNuevo);
        
        em.merge(producto);
        
    }
    
}
