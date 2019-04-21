/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.comandos;

import aplicacion.modelo.entidades.Variedad;
import aplicacion.modelo.entidades.Parametro;
import aplicacion.modelo.entidades.Pedido;
import aplicacion.modelo.entidades.Torta;
import aplicacion.modelo.entidades.Usuario;
import aplicacion.modelo.negocio.CatalogoDeVariedades;
import aplicacion.modelo.negocio.CatalogoDeParametros;
import aplicacion.modelo.negocio.CatalogoDeTortas;
import aplicacion.modelo.negocio.CatalogoDeUsuarios;
import aplicacion.utilidades.AefilepException;
import java.sql.Array;
import java.util.ArrayList;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ligia
 */
public class InicioComando extends Comando{

    @Override
    public String ejecutar(HttpServletRequest request, HttpServletResponse response)
    {        
         //crea pedido inicial
        Pedido pedido= new Pedido(); 
        request.getSession().setAttribute("pedido", pedido);
        request.getSession().setAttribute("cantidadDias", 1);
                
        // carga de tortas del home, carrusel y tablita de abajo
        CatalogoDeTortas CdP= new CatalogoDeTortas();
        ArrayList<ArrayList<Torta>> listaTortas=new ArrayList<ArrayList<Torta>>();
        ArrayList<Torta>tortasCarrusel= new ArrayList<Torta>();

        try
        {       
            listaTortas.add(CdP.obtenerVariedad(6, 0, 4));
            listaTortas.add(CdP.obtenerVariedad(3, 0, 4));
            listaTortas.add(CdP.obtenerVariedad(1, 0, 4));
            listaTortas.add(CdP.obtenerVariedad(2, 0, 4));
            listaTortas.add(CdP.obtenerVariedad(5, 0, 4));
            tortasCarrusel= CdP.obtenerEstrenos(3);    
        }
        catch(AefilepException ex)
        {
            request.getSession().setAttribute("ex", ex.getMessage());
            return "/home.jsp";
        }
        request.getSession().setAttribute("listaTortas", listaTortas);
        request.getSession().setAttribute("tortasCarrusel", tortasCarrusel);
        
        //carga de géneros
        CatalogoDeVariedades cDeVar = new CatalogoDeVariedades();
        try
        {
            ArrayList<Variedad> variedades = cDeVar.obtenerVariedad();
            request.getSession().setAttribute("variedades", variedades);
        }
        catch(AefilepException ex)
        {
            request.setAttribute("ex", ex.getMessage());
            return "/home.jsp";
        }
               
        //mantenerme conectado
        String nomUsu = null;
        String contra = null;
        if( request.getCookies()!=null && request.getSession().getAttribute("usuario")==null )
        {
            Cookie[] cookies = request.getCookies();
            for(Cookie c:cookies)
            {
                if(c.getName().equals("nomUsuarioAefilep"))
                    nomUsu=c.getValue();
                if(c.getName().equals("contraAefilep"))
                    contra=c.getValue();
            }
            if(nomUsu!=null && contra!=null)
            {
                CatalogoDeUsuarios cDeUsus = new CatalogoDeUsuarios();
                Usuario usu;
                try
                {
                    usu = cDeUsus.buscarUsuario(nomUsu, contra);
                }
                catch(AefilepException ex)
                {
                    request.getSession().setAttribute("ex", ex.getMessage());
                    return "/home.jsp";
                }
                
                if(usu!=null)
                {
                    request.getSession().setAttribute("usuario", usu);
                    request.getSession().setAttribute("exitoLogin", true);
                }
            }
        }
        
        //carga de parámetros desde la BD
        CatalogoDeParametros cDePar = new CatalogoDeParametros();
        Parametro parametros = new Parametro();
        try
        {
            parametros = cDePar.obtenerParametros();
        } 
        catch (AefilepException ex) 
        {
            request.getSession().setAttribute("ex", ex.getMessage());
            return "/home.jsp";
        }
        request.getSession().setAttribute("parametros", parametros);
        
        return "/home.jsp";
    }
    
}
