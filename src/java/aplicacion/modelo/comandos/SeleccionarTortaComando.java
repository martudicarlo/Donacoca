/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.comandos;

import aplicacion.modelo.entidades.Torta;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JP
 */
public class SeleccionarTortaComando extends Comando
{

    @Override
    public String ejecutar(HttpServletRequest request, HttpServletResponse response)
    {
        int idPeliEdit = Integer.parseInt( request.getParameter("idTortaEdit"));
        
        if(idPeliEdit!=0)
        {
            ArrayList<Torta> tortas = (ArrayList<Torta>)request.getSession().getAttribute("ListaTortas");
            Torta tortaEdit=null;
            for(Torta torta:tortas)
            {
                if(idPeliEdit==torta.getIdTorta())
                    tortaEdit=torta;         
            }
            request.getSession().setAttribute("TortaEdit", tortaEdit);
        }
        else
        {
            request.getSession().setAttribute("TortaEdit", null);
        }
        
        request.getSession().setAttribute("Scroll",true);
        
        return "/ABMTortas.jsp";
    }    
}
