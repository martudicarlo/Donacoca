/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.datos;

import aplicacion.modelo.entidades.Variedad;
import aplicacion.modelo.entidades.Pelicula;
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
public class PeliculasVariedadesBD
{
    Conexion conec = new Conexion();

    /**
     * agrega los generos a una pelicula
     * @param p
     * @throws AefilepException 
     */
    public void agregarPeliculaVariedad(Pelicula p) throws AefilepException
    {
        Connection con = conec.getConexion();
        String transac = "insert into aefilep.peliculas_variedades values ";
        
        for(int i=0; i<p.getVariedades().size(); i++)
        {
            transac = transac +"("+p.getIdPelicula()+","+p.getVariedades().get(i).getIdVariedad()+")";
            
            if(i==p.getVariedades().size()-1)            
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
            throw(new AefilepException ("Error al insertar género en la película",ex));
        }  
    }
    
    /**
     * elimina y agrega las variedades de una pelicula
     * @param p pelicula a editar
     * @throws AefilepException 
     */ 
    public void actualizarPeliculasVariedades(Pelicula p) throws AefilepException
    {
        Connection con = conec.getConexion();
        String transac = "delete from aefilep.peliculas_variedades where id_pelicula=?;";
        
        try
        {   
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setInt(1, p.getIdPelicula());
            pr.executeUpdate();
            
            con.close();
            
            this.agregarPeliculaVariedad(p);
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al actualizar datos de la película",ex);
        }
    }
     
    public ArrayList<Variedad> obtenerVariedadesPelicula(int id) throws AefilepException
    {
        ArrayList<Variedad> variedades = new ArrayList<Variedad>();
        Connection con = conec.getConexion();
        String transac = "SELECT aefilep.variedades.id_variedad, descripcion FROM aefilep.peliculas_variedades inner join aefilep.variedades " +
                         "on aefilep.peliculas_variedades.id_variedad=aefilep.variedades.id_variedad " +
                         "where id_pelicula=?;";
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
            throw(new AefilepException ("Error al obtener géneros de la película",ex));
        }
        
        return variedades;
    }
}
