/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.datos;

import aplicacion.modelo.entidades.Variedad;
import aplicacion.utilidades.AefilepException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Alumno
 */
public class VariedadBD 
{
    Conexion conec = new Conexion();
    public ArrayList<Variedad> obtenerVariedades() throws AefilepException 
    {
        ArrayList<Variedad> listaVariedades = new ArrayList<>();
        try
        {
            Connection con = conec.getConexion();
            String transac = "select * from variedades;";
           
                PreparedStatement pr = con.prepareStatement(transac);
                ResultSet res = pr.executeQuery();
                
                while(res.next())
                {
                    Variedad g = new Variedad();
                    
                    g.setIdVariedad(res.getInt(1));
                    g.setDescripcion(res.getString(2));
                    
                    listaVariedades.add(g);
                }
                con.close();
                
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al recuperar la variedad",ex);
        }
                   
        return listaVariedades;
    }        
}

