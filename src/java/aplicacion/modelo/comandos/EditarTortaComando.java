/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.comandos;

import aplicacion.modelo.entidades.Variedad;
import aplicacion.modelo.entidades.Torta;
import aplicacion.modelo.negocio.CatalogoDeVariedades;
import aplicacion.modelo.negocio.CatalogoDeTortas;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author JP
 */
public class EditarTortaComando extends Comando
{
    CatalogoDeTortas CdeP;
    CatalogoDeVariedades CdeV;
    
    @Override
    public String ejecutar(HttpServletRequest request, HttpServletResponse response)
    {
        CdeP = new CatalogoDeTortas();
        CdeV = new CatalogoDeVariedades();
        Torta PeliEditada = new Torta();
        ArrayList<Variedad> variedades = null;
        try
        {
            variedades = CdeV.obtenerVariedad();
        } 
        catch (Exception ex) 
        {
            request.setAttribute("ex",ex.getMessage());
            return ("/ABMTortas.jsp");
        }
        
        PeliEditada.setIdTorta(Integer.parseInt(request.getParameter("ID")));
        PeliEditada.setFormato(request.getParameter("formPel"));
        PeliEditada.setNombre(request.getParameter("nomPel"));
        PeliEditada.setDuracion(Integer.parseInt(request.getParameter("durPel")));
        PeliEditada.setPrecioVenta(Float.parseFloat(request.getParameter("pvtaPel")));
        PeliEditada.setReparto(request.getParameter("repPel"));
        PeliEditada.setDescripcion(request.getParameter("sinPel"));
        PeliEditada.setStockAlquiler(Integer.parseInt(request.getParameter("stockAlqPel")));
        PeliEditada.setStockVenta(Integer.parseInt(request.getParameter("stockVtaPel")));
        PeliEditada.setUrlTrailer(request.getParameter("urlPel"));
        PeliEditada.setAnio(Integer.parseInt(request.getParameter("anioPel")));
        
        Part imagen = null;
        try 
        {           
            if(request.getPart("imgPel").getSubmittedFileName().equals(""))
            {
                PeliEditada.setImagen(null);
            } 
            else
            {
                imagen = request.getPart("imgPel");
                InputStream inputStream = imagen.getInputStream();
                
                if(inputStream!=null)
                    PeliEditada.setImagen(inputStream);
            }            
        } 
        catch (IOException | ServletException ex)
        {       
            request.setAttribute("ex",ex.getMessage());
            return ("/ABMTortas.jsp");
        }
        
        SimpleDateFormat formato =  new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try
        {
            fecha = formato.parse(request.getParameter("fCargaPel"));              
            PeliEditada.setFechaCarga(new java.sql.Date(fecha.getTime()));
        }
        catch(Exception ex)
        {
            request.setAttribute("ex","Ha ocurrido un error");
            return ("/ABMTortas.jsp");
        }
        
        String selecc[] = request.getParameterValues("variedades");
        for(Variedad v: variedades)
        {
            for(int i=0; i<selecc.length;i++)                  
            {
                if(v.getIdVariedad()==Integer.parseInt(selecc[i]))               
                    PeliEditada.agregarVariedad(v);
            }
        }
        
        Boolean esActivo = (request.getParameter("Activa")!=null);
        PeliEditada.setActivo(esActivo);
        
        ArrayList<Torta> tortas;
        try
        {
            CdeP.actualizarTorta(PeliEditada);
            tortas = CdeP.obtenerTortas();          
        }
        catch(Exception ex)
        {
            request.setAttribute("ex",ex.getMessage());
            return "/ABMTortas.jsp";
        }
        
        request.getSession().setAttribute("ListaTortas", tortas);
        request.getSession().setAttribute("PeliEdit", PeliEditada);
        request.getSession().setAttribute("Scroll",true);
        request.setAttribute("ExitoPeli", true);
         
        return "/ABMTortas.jsp";
    }    
}
