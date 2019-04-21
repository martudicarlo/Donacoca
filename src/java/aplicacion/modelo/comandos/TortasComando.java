/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.comandos;

import aplicacion.modelo.entidades.Torta;
import aplicacion.modelo.negocio.CatalogoDeTortas;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marti_000
 */
public class TortasComando extends Comando
{
    private CatalogoDeTortas cDp;
    @Override
    public String ejecutar(HttpServletRequest request, HttpServletResponse response)
    {
        int paginaActual = 1;
        
        if(request.getParameter("tipo")!=null)
        {  
            switch(request.getParameter("tipo"))
            {
                case "estreno": request.getSession().setAttribute("tipo","estreno");
                    break;
                case "buscador":request.getSession().setAttribute("tipo","buscador");
                    break;
                case "todas":request.getSession().setAttribute("tipo","todas");
                    break;
                default:request.getSession().setAttribute("tipo",Integer.parseInt(request.getParameter("tipo")));
                    break;
            }
        }
        
        if(request.getParameter("paginacionActual")==null)
        {
            paginaActual = 1; 
            if(request.getParameter("tipo").equals("buscador"))
                request.getSession().setAttribute("nombrePelicula", request.getParameter("nombrePelicula"));
        }
        else
        {
            paginaActual = Integer.parseInt(request.getParameter("paginacionActual"));
        }
        request.getSession().setAttribute("pActual", paginaActual);
        
        cDp = new CatalogoDeTortas();
        int cantidadDeTortas = 0;
        ArrayList<Torta> listaTortas = null;
        
        try
        {
            if(request.getSession().getAttribute("tipo")!=null)
            {
                if(request.getSession().getAttribute("tipo").equals("estreno"))             
                {
                    listaTortas = cDp.obtenerEstrenos((paginaActual-1)*9,9);
                    cantidadDeTortas=cDp.cantidadEstrenosActivos();
                }
                else if(request.getSession().getAttribute("tipo").equals("buscador"))
                {                     
                    listaTortas = cDp.obtenerTortas(request.getSession().getAttribute("nombrePelicula").toString(),(paginaActual-1)*9,9);
                    cantidadDeTortas=cDp.cantidadBuscadorActivos(request.getSession().getAttribute("nombrePelicula").toString());
                    
                    if(listaTortas.isEmpty())
                        request.getSession().setAttribute("errorNoEncontradas",true);
                    request.getSession().setAttribute("variedadObtenida",true);
                }
                else if(request.getSession().getAttribute("tipo").equals("todas"))
                { 
                    listaTortas = cDp.buscarTortas((paginaActual-1)*9,9);
                    cantidadDeTortas = cDp.cantidadTortasActivas();
                }
                else
                {
                    listaTortas = cDp.obtenerVariedad((Integer)request.getSession().getAttribute("tipo"),(paginaActual-1)*9,9);
                    request.getSession().setAttribute("variedadObtenida",true);
                    cantidadDeTortas=cDp.cantidadVariedadesActivas((Integer)request.getSession().getAttribute("tipo"));
                }
            }
        }
        catch(Exception ex)
        {
            request.setAttribute("ex",ex.getMessage());
            return "/cartelera.jsp";
        }
     
        request.getSession().setAttribute("listaCartelera", listaTortas);
        request.getSession().setAttribute("cantidadTortas",cantidadDeTortas);
        request.getSession().setAttribute("VariedadObtenida", null);
        
        return "/cartelera.jsp";       
    }
}
