/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.datos;

import aplicacion.modelo.entidades.Variedad;
import aplicacion.modelo.entidades.Torta;
import aplicacion.utilidades.AefilepException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marti_000
 */
public class TortasVariedadesBD
{
    Conexion conec = new Conexion();

    /**
     * agrega los generos a una torta
     * @param t
     * @throws AefilepException 
     */
    public void agregarTortaVariedad(Torta t) throws AefilepException
    {
        Connection con = conec.getConexion();
        String transac = "insert into aefilep.tortas_variedades values ";
        
        for(int i=0; i<t.getVariedades().size(); i++)
        {
            transac = transac +"("+t.getIdTorta()+","+t.getVariedades().get(i).getIdVariedad()+")";
            
            if(i==t.getVariedades().size()-1)            
                transac = transac+";";
            else            
                transac = transac+",";            
        }
        
        try
        {   
            PreparedStatement pr = con.prepareStatement(transac);
            pr.executeUpdate();
            con.close(); 
        }
        catch(SQLException ex)
        {
            throw(new AefilepException ("Error al insertar género en la torta",ex));
        }  
    }
    
    /**
     * elimina y agrega las variedades de una torta
     * @param t torta a editar
     * @throws AefilepException 
     */ 
    public void actualizarTortasVariedades(Torta t) throws AefilepException
    {
        Connection con = conec.getConexion();
        String transac = "delete from aefilep.tortas_variedades where id_torta=?;";
        
        try
        {   
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setInt(1, t.getIdTorta());
            pr.executeUpdate();
            
            con.close();
            
            this.agregarTortaVariedad(t);
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al actualizar datos de la torta",ex);
        }
    }
     
    public ArrayList<Variedad> obtenerVariedadesTorta(int id) throws AefilepException
    {
        ArrayList<Variedad> variedades = new ArrayList<Variedad>();
        Connection con = conec.getConexion();
        String transac = "SELECT aefilep.variedades.id_variedad, descripcion FROM aefilep.tortas_variedades inner join aefilep.variedades " +
                         "on aefilep.tortas_variedades.id_variedad=aefilep.variedades.id_variedad " +
                         "where id_torta=?;";
        try
        {   
           
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setInt(1, id);
            ResultSet res = pr.executeQuery();
            while(res.next())
            {
                Variedad var = new Variedad();
                var.setIdVariedad(res.getInt(1));
                var.setDescripcion(res.getString(2));
                variedades.add(var);
            }
            con.close(); 
        }
        catch(SQLException ex)
        {
            throw(new AefilepException ("Error al obtener variedades de la torta",ex));
        }
        
        return variedades;
    }
}
