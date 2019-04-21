/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.comandos;

import aplicacion.modelo.entidades.Torta;
import aplicacion.modelo.negocio.CatalogoDeTortas;
import aplicacion.utilidades.AefilepException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
public class ObtenerTortaComando extends Comando
{
    @Override
    public String ejecutar(HttpServletRequest request, HttpServletResponse response)
    {
        CatalogoDeTortas CdP= new CatalogoDeTortas();
        try
        {
            Torta tortaActual= CdP.obtenerTorta(Integer.parseInt(request.getParameter("idTorta")));
            request.getSession().setAttribute("tortaActual", tortaActual);  
        }
        catch(AefilepException ex)
        {
            request.getSession().setAttribute("tortaActual", null);
            request.setAttribute("ex", ex.getMessage());
        }
        
        return "/torta.jsp";            
    }    
}
