/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.comandos;
import aplicacion.modelo.entidades.Torta;
import aplicacion.modelo.entidades.Variedad;
import aplicacion.modelo.negocio.CatalogoDeTortas;
import aplicacion.modelo.negocio.CatalogoDeVariedades;
import aplicacion.utilidades.AefilepException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
/**
 *
 * @author User
 */
public class AgregarTortaComando extends Comando
{
    Torta torta;
    CatalogoDeTortas cDp= new CatalogoDeTortas();
    CatalogoDeVariedades cdV = new CatalogoDeVariedades();
    
    @Override
    public String ejecutar(HttpServletRequest request, HttpServletResponse response)
    {
        boolean existeTorta = true;
        
        //valida que la torta sea única
        try
        {
            existeTorta = cDp.existeTorta((String)request.getParameter("nomPel"));
        }
        catch(AefilepException ex)
        {
            request.setAttribute("ex", ex.getMessage());
            return"/ABMTortas.jsp";
        }
        
        torta = new Torta();
        torta.setActivo(true);
        torta.setFormato(request.getParameter("formPel"));
        torta.setNombre(request.getParameter("nomPel"));
        torta.setDuracion(Integer.parseInt(request.getParameter("durPel")));
        torta.setPrecioVenta(Float.parseFloat(request.getParameter("pvtaPel")));
        torta.setReparto(request.getParameter("repPel"));
        torta.setSinopsis(request.getParameter("sinPel"));
        torta.setStockAlquiler(Integer.parseInt(request.getParameter("stockAlqPel")));
        torta.setStockVenta(Integer.parseInt(request.getParameter("stockVtaPel")));
        torta.setUrlTrailer(request.getParameter("urlPel"));
        torta.setAnio(Integer.parseInt(request.getParameter("anioPel")));
        DateFormat hoyFormato = new SimpleDateFormat("yyyy/MM/dd");      
        Date hoy=new Date();
        hoyFormato.format(hoy);
        torta.setFechaCarga(hoy);
        //Comparo todos las variedades con los seleccionados y los agrego a la película
        ArrayList<Variedad> variedades = (ArrayList)request.getSession().getAttribute("ListaVariedades");
        String selecc[] = request.getParameterValues("variedades");
        for(Variedad v: variedades)
        {
            for(int i=0; i<selecc.length;i++)  
            {
                if(v.getIdVariedad()==Integer.parseInt(selecc[i]))
                {
                    torta.agregarVariedad(v);
                }
            }
        }    
        //agregamos imagen a la película 
        Part imagen = null;
        try
        {
            if(request.getPart("imgPel")!=null)
            {
                imagen = request.getPart("imgPel");
                InputStream inputStream = imagen.getInputStream();
                if(inputStream!=null)
                    torta.setImagen(inputStream);
            }
        }
        catch (Exception ex)
        {
            request.setAttribute("ex","Error al cargar imagen");
            return ("/ABMTortas.jsp");
        }
        
        if(!existeTorta)
        {
            //Agrego la película y actualizo la lista
            ArrayList<Torta> tortas = new ArrayList<>();
            try
            {
                cDp.agregarTorta(torta);
                tortas = cDp.obtenerTortas();            
            }
            catch(AefilepException ex)
            {
                request.setAttribute("ex", ex.getMessage());
                request.getSession().setAttribute("Scroll",true);
                return "/ABMTortas.jsp";
            }         
            request.getSession().setAttribute("ListaTortas", tortas);
            request.setAttribute("ExitoPeli", true);
            request.setAttribute("tortaPorAgregar", null);
        }
        else
        {
            request.setAttribute("tortaPorAgregar", torta);
            request.setAttribute("ExitoPeli", false); 
        }
        
        request.getSession().setAttribute("Scroll",true);
        return "/ABMTortas.jsp";
    }    
}
