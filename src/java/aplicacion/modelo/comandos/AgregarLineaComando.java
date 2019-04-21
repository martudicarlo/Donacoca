/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.comandos;
import aplicacion.modelo.entidades.LineaPedido;
import aplicacion.modelo.entidades.Pedido;
import aplicacion.modelo.entidades.Torta;
import aplicacion.modelo.negocio.CatalogoDeTortas;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JP
 */
public class AgregarLineaComando extends Comando
{

    @Override
    public String ejecutar(HttpServletRequest request, HttpServletResponse response)
    {
        int idTorta = Integer.parseInt(request.getParameter("idTorta"));
        boolean alquiler = request.getParameter("tipoLinea").equals("Alquilar");
        Pedido pedido = (Pedido)request.getSession().getAttribute("pedido");
        int lineaExiste=0;
        
        for(LineaPedido lp: pedido.getLineas())
        {
            if(lp.getTorta().getIdTorta()==idTorta && (lp.isEsAlquiler()==alquiler))
            {
                if(alquiler==false)//Porque solo la compra admite un aumento de cantidad
                {
                   lp.setCantidad(lp.getCantidad()+1);
                   request.getSession().setAttribute("exitoTortaAgregada", true);
                }
                
                lineaExiste=1;
            }        
        }
        
        if(lineaExiste==0)
        {
            CatalogoDeTortas cdp = new CatalogoDeTortas();
            LineaPedido linea = new LineaPedido();
            linea.setEsAlquiler(alquiler);
            try
            {
                Torta torta = cdp.obtenerTorta(idTorta);
                linea.setCantidad(1);
                linea.setTorta(torta);
                pedido.setLinea(linea);
                request.getSession().setAttribute("exitoTortaAgregada", true);
            }
            catch(Exception ex)
            {
                request.getSession().setAttribute("exitoTortaAgregada", false);
            }   
        }
        
        request.getSession().setAttribute("pedido",pedido);
        
        if(request.getParameter("provieneDeTorta")!=null)
           return "/torta.jsp";
        
        return "/cartelera.jsp";
    }
    
}
