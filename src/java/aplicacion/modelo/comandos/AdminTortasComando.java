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
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * guarda en la sesion las tortas y los generos
 * y se guarda la primer torta para editarla
 * 
 */
public class AdminTortasComando extends Comando
{
    CatalogoDeTortas CdeP;
    CatalogoDeVariedades CdeV;
    
    @Override
    public String ejecutar(HttpServletRequest request, HttpServletResponse response) 
    {
        CdeP = new CatalogoDeTortas();
        CdeV = new CatalogoDeVariedades();
        ArrayList<Torta> tortas = new ArrayList<Torta>();
        ArrayList<Variedad> variedades = new ArrayList<Variedad>();
        Torta tortaEdit = null;
        try
        {
            tortas = CdeP.obtenerTortas();
           variedades = CdeV.obtenerVariedad();
        }
        catch(Exception ex)
        {
            request.setAttribute("ex",ex.getMessage());
            return "/ABMTortas.jsp";
        }
      
        tortaEdit = tortas.get(0);
        request.getSession().setAttribute("ListaTortas", tortas);
        request.getSession().setAttribute("ListaVariedades", variedades);
        request.getSession().setAttribute("PeliEdit", tortaEdit);
        
        return "/ABMTortas.jsp";
    } 
}
