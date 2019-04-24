/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.datos;

import aplicacion.modelo.entidades.Torta;
import aplicacion.utilidades.AefilepException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class TortaDB
{
    Conexion conec = new Conexion();
    Connection con = null;
    
    /**
     * agrega la torta a la base de datos
     * @param p torta a agregar
     * @throws AefilepException 
     */
    
    public void agregarTorta(Torta p) throws AefilepException
    {
        String transac = "insert into aefilep.tortas values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac,Statement.RETURN_GENERATED_KEYS);    
            pr.setNull(1,0);
            pr.setString(2, p.getNombre());
            pr.setInt(3, p.getDuracion());
            pr.setString(4, p.getFormato());
            pr.setInt(5, p.getStockAlquiler());
            pr.setInt(6, p.getStockVenta());
            pr.setBlob(7,p.getImagen());
            pr.setString(8, p.getReparto());
            pr.setDate(9, new java.sql.Date(p.getFechaCarga().getTime()));
            pr.setBoolean(10,p.isActivo());
            pr.setString(11, p.getUrlTrailer());
            pr.setFloat(12, p.getPrecioVenta());
            pr.setString(13, p.getSinopsis());
            pr.setInt(14, p.getAnio());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            
            if(rs.next())
            {
                int id = rs.getInt(1);
                p.setIdTorta(id);
            }
            
            new TortasVariedadesBD().agregarTortaVariedad(p);
            con.close();
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al registrar torta.",ex);
        }
    }
    
    public void actualizarStock(Torta p) throws AefilepException
    {         
        String sql = "update tortas set stock_alquiler=?, stock_compra=? where id_torta=?;";
            
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(sql);
            pr.setInt(1, p.getStockAlquiler());
            pr.setInt(2, p.getStockVenta());            
            pr.setInt(3, p.getIdTorta());
            pr.executeUpdate();
            con.close();
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al actualizar stock de la torta",ex);
        }    
    }
    
    /**
     * actualiza una torta
     * @param p torta a editar
     * @throws AefilepException 
     */
    public void actualizarTorta(Torta p) throws AefilepException
    {      
        if(p.getImagen()!=null)
        { 
            String sql = "update tortas set nombre=? , duracion=? , formato=? ,"
                + " stock_alquiler=? ,stock_compra=?, reparto=?, activo=?,url_trailer=? ,"
                + " precio_venta=?, descripcion=?, anio=?, imagen=? where id_torta=?";            
            try
            {
                con = conec.getConexion();
                PreparedStatement pr = con.prepareStatement(sql);
                pr.setString(1, p.getNombre());
                pr.setInt(2, p.getDuracion());
                pr.setString(3, p.getFormato());
                pr.setInt(4, p.getStockAlquiler());
                pr.setInt(5, p.getStockVenta());           
                pr.setString(6, p.getReparto());
                pr.setBoolean(7, p.isActivo());            
                pr.setString(8, p.getUrlTrailer());
                pr.setFloat(9, p.getPrecioVenta());
                pr.setString(10, p.getSinopsis());
                pr.setInt(11, p.getAnio());           
                pr.setBlob(12, p.getImagen());            
                pr.setInt(13, p.getIdTorta());
                
                new TortasVariedadesBD().actualizarTortasVariedades(p);
                pr.executeUpdate();

                con.close();
            }
            catch(SQLException ex)
            {
                throw new AefilepException("Error al actualizar datos de la torta",ex);
            }        
        }
        else            
        {
            String sql = "update tortas set nombre=? , duracion=? , formato=? ,"
                + " stock_alquiler=? ,stock_compra=?, reparto=?, activo=?,url_trailer=? ,"
                + " precio_venta=?, descripcion=?, anio=? where id_torta=?";
            try
            {
                con = conec.getConexion();
                PreparedStatement pr = con.prepareStatement(sql);
                pr.setString(1, p.getNombre());
                pr.setInt(2, p.getDuracion());
                pr.setString(3, p.getFormato());
                pr.setInt(4, p.getStockAlquiler());
                pr.setInt(5, p.getStockVenta());           
                pr.setString(6, p.getReparto());
                pr.setBoolean(7, p.isActivo());            
                pr.setString(8, p.getUrlTrailer());
                pr.setFloat(9, p.getPrecioVenta());
                pr.setString(10, p.getSinopsis());
                pr.setInt(11, p.getAnio());           
                pr.setInt(12, p.getIdTorta());
                
                new TortasVariedadesBD().actualizarTortasVariedades(p);
                pr.executeUpdate();
           
                con.close();
            }
            catch(SQLException ex)
            {
                throw new AefilepException("Error al actualizar datos de la torta",ex);
            }  
        }    
    }
     
    public byte[] buscarImagen(int id) throws AefilepException
    {
        String transac = "select imagen from tortas where id_torta=?;";
        byte[] imgData=null;
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setInt(1, id);
            ResultSet res = pr.executeQuery();      
            if(res.next())
            {
                imgData = res.getBytes("imagen");
            }
            con.close();
        } 
        catch (SQLException ex)
        {
            throw new AefilepException("Error al recuperar imagen.", ex);
        }
        return imgData;
    }
     
    public ArrayList<Torta> obtenerTortas() throws AefilepException
    {
        ArrayList<Torta> listaTortas = new ArrayList<>();
        String transac = "select * from tortas";
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            ResultSet res = pr.executeQuery();
                   
            while(res.next())
            {
                Torta p = new Torta();

                p.setIdTorta(res.getInt(1));
                p.setNombre(res.getString(2));
                p.setDuracion(res.getInt(3));
                p.setFormato(res.getString(4));
                p.setStockAlquiler(res.getInt(5));
                p.setStockVenta(res.getInt(6));
                p.setReparto(res.getString(8));
                p.setFechaCarga(new java.sql.Date(res.getDate(9).getTime()));
                p.setActivo(res.getBoolean(10));
                p.setUrlTrailer(res.getString(11));
                p.setPrecioVenta(res.getFloat(12));
                p.setSinopsis(res.getString(13));
                p.setAnio(res.getInt(14));

                if(p.isEstreno())
                {
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquilerEstreno());
                }
                else
                {
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquiler());
                }

                p.setVariedades(new TortasVariedadesBD().obtenerVariedadesTorta(p.getIdTorta()));
                listaTortas.add(p);
            }
            con.close();
        }
        catch(SQLException Ex)
        {
            throw new AefilepException("Error el recuperar datos de las tortas",Ex);
        }
         
        return listaTortas;
    }
    
    public ArrayList<Torta> obtenerTortas(String nombre, int inferior, int cantidad) throws AefilepException
    {
        ArrayList<Torta> listaTortas = new ArrayList<>();
        String transac = "select * from tortas where nombre like '%"+nombre+"%'and activo=1 limit ?,?;";
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setInt(1, inferior);
            pr.setInt(2, cantidad);

            ResultSet res = pr.executeQuery();
            while(res.next())
            {
                Torta p = new Torta();

                p.setIdTorta(res.getInt(1));
                p.setNombre(res.getString(2));
                p.setDuracion(res.getInt(3));
                p.setFormato(res.getString(4));
                p.setStockAlquiler(res.getInt(5));
                p.setStockVenta(res.getInt(6));
                p.setReparto(res.getString(8));
                p.setFechaCarga(new java.sql.Date(res.getDate(9).getTime()));
                p.setActivo(res.getBoolean(10));
                p.setUrlTrailer(res.getString(11));
                p.setPrecioVenta(res.getFloat(12));
                p.setSinopsis(res.getString(13));
                p.setAnio(res.getInt(14));

                if(p.isEstreno())
                {
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquilerEstreno());
                }
                else
                {
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquiler());
                }
                p.setVariedades(new TortasVariedadesBD().obtenerVariedadesTorta(p.getIdTorta()));

                listaTortas.add(p);
            }
            con.close();
        }
        catch(SQLException Ex)
        {
            throw new AefilepException("Error el recuperar datos de las tortas",Ex);
        }
        
        return listaTortas;
    }
     
    public ArrayList<Torta> buscarTortas(int inferior,int cantidad) throws AefilepException
    {
        ArrayList<Torta> listaTortas = new ArrayList<>();
        String transac = "select * from tortas where activo=1 limit ?,?;";
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            
            pr.setInt(1, inferior);
            pr.setInt(2, cantidad);
            
            ResultSet res = pr.executeQuery();
                   
            while(res.next())
            {
                Torta p = new Torta();
                
                p.setIdTorta(res.getInt(1));
                p.setNombre(res.getString(2));
                p.setDuracion(res.getInt(3));
                p.setFormato(res.getString(4));
                p.setStockAlquiler(res.getInt(5));
                p.setStockVenta(res.getInt(6));
                p.setReparto(res.getString(8));
                p.setFechaCarga(new java.sql.Date(res.getDate(9).getTime()));
                p.setActivo(res.getBoolean(10));
                p.setUrlTrailer(res.getString(11));
                p.setPrecioVenta(res.getFloat(12));
                p.setSinopsis(res.getString(13));
                p.setAnio(res.getInt(14));
                
                if(p.isEstreno())
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquilerEstreno());
                else
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquiler());
          
                listaTortas.add(p);
            }
            con.close();
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al recuperar tortas",ex);
        }
        return listaTortas;
    }
     
    public Torta obtenerTorta(int idPeli) throws AefilepException
    {
        Torta p = null;
        String transac = "select * from tortas where activo=1 and id_torta=?";
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setInt(1, idPeli);
            ResultSet res = pr.executeQuery();
                   
            if(res.next())
            {   
                p=new Torta();
                p.setIdTorta(res.getInt(1));
                p.setNombre(res.getString(2));
                p.setDuracion(res.getInt(3));
                p.setFormato(res.getString(4));
                p.setStockAlquiler(res.getInt(5));
                p.setStockVenta(res.getInt(6));
                p.setReparto(res.getString(8));
                p.setFechaCarga(new java.sql.Date(res.getDate(9).getTime()));
                p.setActivo(res.getBoolean(10));
                p.setUrlTrailer(res.getString(11));
                p.setPrecioVenta(res.getFloat(12));
                p.setSinopsis(res.getString(13));
                p.setAnio(res.getInt(14));  
            
                if(p.isEstreno())
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquilerEstreno());
                else
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquiler());
            }
            con.close();
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al recuperar los datos de la torta",ex);
        }
        
        return p;
    }
    /**
     * valida que la torta no exista en la base de datos comparando por nombre
     * @param nombreTorta nombre de la torta
     * @return true si existe torta
     * @throws AefilepException 
     */
    
    public boolean existeTorta(String nombreTorta) throws AefilepException
    {      
        String transac = "select count(*) from tortas where nombre=?";        
        
        int cantidad=0;
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setString(1, nombreTorta);
            ResultSet res = pr.executeQuery();
                   
            if(res.next())              
                cantidad = res.getInt(1);
                       
            con.close();
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al recuperar los datos de la torta",ex);
        }
        
        return cantidad > 0;
    }
    
    public int cantidadTortas() throws AefilepException
    {
        int i=0;
        String transac = "select count(*) from tortas;";
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            ResultSet res = pr.executeQuery();
            
             if(res.next())
            {
                i = res.getInt(1);
                con.close();   
            }
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al contar tortas",ex);
        }
        
        return i;
    }
     
    public int cantidadTortasActivas() throws AefilepException
    {
        int i=0;
        String transac = "select count(*) from tortas where activo=1;";
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            ResultSet res = pr.executeQuery();
            
             if(res.next())
            {
                i = res.getInt(1);
                con.close();   
            }
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al contar tortas",ex);
        }
        
        return i;
    }
          
     public int cantidadEstrenosActivos() throws AefilepException
     {
        int i=0;
        String transac = "select count(*) from tortas where activo=1 and (`fecha_carga` +7)>CURRENT_DATE();";
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            ResultSet res = pr.executeQuery();
            
            if(res.next())
            {
                i = res.getInt(1);
                con.close();
            }
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al contar estrenos",ex);
        }
        
        return i;
    }
     
    public int cantidadVariedadesActivas(int id) throws AefilepException
    {
        int i=0;
        String transac = "select count(*) from tortas p inner join tortas_variedades pg on p.id_torta=pg.id_torta where id_variedad=? and activo=1;";
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setInt(1,id);
            ResultSet res = pr.executeQuery();
            
            if(res.next())
            {
                i = res.getInt(1);
                con.close();
            }
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al contar géneros.", ex);
        }
        
        return i;
    }
    
    public int cantidadBuscadorActivos(String nombre) throws AefilepException
    {
        int i=0;
        String transac = "select count(*) from tortas where  nombre like '%"+nombre+"%'and activo=1 ;";
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            ResultSet res = pr.executeQuery();
            if(res.next())
            {
                i = res.getInt(1);
                con.close();
            }
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error con los datos de las tortas",ex);
        }
             
        return i;
    }
    
    public ArrayList<Torta> obtenerEstrenos(int inferior,int cantidad) throws AefilepException
    {
        ArrayList<Torta> listaEstrenos = new ArrayList<>();
        String transac = "select * from tortas where(`fecha_carga` +7)>CURRENT_DATE() and activo=1 limit ?,?;";
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setInt(1, inferior);
            pr.setInt(2, cantidad);
            ResultSet res = pr.executeQuery();
                   
            while(res.next())
            {
                Torta p = new Torta();
                
                p.setIdTorta(res.getInt(1));
                p.setNombre(res.getString(2));
                p.setDuracion(res.getInt(3));
                p.setFormato(res.getString(4));
                p.setStockAlquiler(res.getInt(5));
                p.setStockVenta(res.getInt(6));
                p.setReparto(res.getString(8));
                p.setFechaCarga(new java.sql.Date(res.getDate(9).getTime()));
                p.setActivo(res.getBoolean(10));
                p.setUrlTrailer(res.getString(11));
                p.setPrecioVenta(res.getFloat(12));
                p.setSinopsis(res.getString(13));
                p.setAnio(res.getInt(14));
                
                if(p.isEstreno())
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquilerEstreno());
                else
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquiler());

                
                p.setVariedades(new TortasVariedadesBD().obtenerVariedadesTorta(p.getIdTorta()));
                
                listaEstrenos.add(p);
            }
            con.close();
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al recuperar datos de las tortas",ex);
        }
        
        return listaEstrenos;
    }
    
    public ArrayList<Torta> obtenerEstrenos(int cant) throws AefilepException
    {
        ArrayList<Torta> listaEstrenos = new ArrayList<>();
        String transac = "select * from tortas where(`fecha_carga` +7)>CURRENT_DATE() and activo=1 order by `fecha_carga` desc limit 0,?;";
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setInt(1, cant);
            ResultSet res = pr.executeQuery();
                   
            while(res.next())
            {
                Torta p = new Torta();
                
                p.setIdTorta(res.getInt(1));
                p.setNombre(res.getString(2));
                p.setDuracion(res.getInt(3));
                p.setFormato(res.getString(4));
                p.setStockAlquiler(res.getInt(5));
                p.setStockVenta(res.getInt(6));
                p.setReparto(res.getString(8));
                p.setFechaCarga(new java.sql.Date(res.getDate(9).getTime()));
                p.setActivo(res.getBoolean(10));
                p.setUrlTrailer(res.getString(11));
                p.setPrecioVenta(res.getFloat(12));
                p.setSinopsis(res.getString(13));
                p.setAnio(res.getInt(14));
                
                if(p.isEstreno())
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquilerEstreno());
                else
                    p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquiler());
                
                p.setVariedades(new TortasVariedadesBD().obtenerVariedadesTorta(p.getIdTorta()));
                
                listaEstrenos.add(p);
            }
            con.close();
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al obtener estrenos",ex);
        }  
        
        if (listaEstrenos.size()<3)
        {    
            int limit= cant-listaEstrenos.size();
            String transac2 = "select * from tortas where(`fecha_carga` +7)<CURRENT_DATE() and activo=1 limit 0,?;";
            try
            {
                con = conec.getConexion();
                PreparedStatement pr2 = con.prepareStatement(transac2);
                pr2.setInt(1,limit);
                ResultSet res2 = pr2.executeQuery();

                while(res2.next())
                {
                    Torta p = new Torta();

                    p.setIdTorta(res2.getInt(1));
                    p.setNombre(res2.getString(2));
                    p.setDuracion(res2.getInt(3));
                    p.setFormato(res2.getString(4));
                    p.setStockAlquiler(res2.getInt(5));
                    p.setStockVenta(res2.getInt(6));
                    p.setReparto(res2.getString(8));
                    p.setFechaCarga(new java.sql.Date(res2.getDate(9).getTime()));
                    p.setActivo(res2.getBoolean(10));
                    p.setUrlTrailer(res2.getString(11));
                    p.setPrecioVenta(res2.getFloat(12));
                    p.setSinopsis(res2.getString(13));
                    p.setAnio(res2.getInt(14));
                     
                    if(p.isEstreno())
                        p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquilerEstreno());
                    else
                        p.setPrecioAlquiler(new ParametroBD().obtenerParametros().getPrecioAlquiler());

                    p.setVariedades(new TortasVariedadesBD().obtenerVariedadesTorta(p.getIdTorta()));
                    listaEstrenos.add(p);                  
                }
                con.close();
            }
            catch(SQLException ex)
            {
                throw new AefilepException("Error al recuperar estrenos",ex);
            }
        }
                    
        return listaEstrenos;
    }
    
    public ArrayList<Torta> obtenerVariedad(int idVariedad, int inferior, int cantidad) throws AefilepException
    {
        ArrayList<Torta> listaVariedad = new ArrayList<>();
        String transac = "select p.id_torta from tortas_variedades pg inner join tortas p on pg.id_torta=p.id_torta where pg.id_variedad=? and p.activo=1 limit ?,?";
        
        try
        {
            con = conec.getConexion();
            PreparedStatement pr = con.prepareStatement(transac);
            pr.setInt(1, idVariedad);
            pr.setInt(2, inferior);
            pr.setInt(3,cantidad);
            ResultSet res = pr.executeQuery();
                   
            while(res.next())
            {
                Torta p = obtenerTorta(res.getInt(1));
                if(p!=null)
                {               
                    listaVariedad.add(p);
                }
            }
            con.close();
        }
        catch(SQLException ex)
        {
            throw new AefilepException("Error al recuperar tortas",ex);
        }         
        return listaVariedad;
    }
}
