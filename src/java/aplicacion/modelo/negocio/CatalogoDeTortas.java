/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.negocio;

import aplicacion.modelo.datos.TortaDB;
import aplicacion.modelo.entidades.Torta;
import aplicacion.utilidades.AefilepException;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class CatalogoDeTortas 
{  
    TortaDB tortas = new TortaDB();
    
    public void agregarTorta(Torta torta) throws AefilepException
    {
        tortas.agregarTorta(torta);
    }
    
    public byte[] buscarImagen(int id) throws AefilepException
    {
        return tortas.buscarImagen(id);
    }
    
    public void actualizarTorta(Torta p) throws AefilepException
    {
        tortas.actualizarTorta(p);
    }
    
    public void actualizarStock(Torta p) throws AefilepException
    {
        tortas.actualizarStock(p);
    }
    
    public Torta obtenerTorta(int idPel) throws AefilepException
    {
        return tortas.obtenerTorta(idPel);
    }
    
    public boolean existeTorta(String nombreTorta) throws AefilepException
    {
        return tortas.existeTorta(nombreTorta);
    }
    
    public ArrayList<Torta> obtenerTortas() throws AefilepException
    {
        return tortas.obtenerTortas();
    }
    
    public ArrayList<Torta> obtenerTortas(String nombre,int inferior,int cantidad) throws AefilepException
    {
        return tortas.obtenerTortas(nombre,inferior,cantidad);
    }
    
    public ArrayList<Torta> buscarTortas(int inferior, int cantidad) throws AefilepException
    {
        return tortas.buscarTortas(inferior,cantidad);
    }
    
    public int cantidadTortasActivas() throws AefilepException
    {
        return tortas.cantidadTortasActivas();
    }
    
    public int cantidadEstrenosActivos() throws AefilepException
    {
        return tortas.cantidadEstrenosActivos();
    }
    
    public int cantidadBuscadorActivos(String nombre) throws AefilepException
    {
        return tortas.cantidadBuscadorActivos(nombre);
    }
    
    public int cantidadVariedadesActivas(int id) throws AefilepException
    {
        return tortas.cantidadVariedadesActivas(id);
    }
    
    public ArrayList<Torta> obtenerEstrenos(int inferior,int cantidad) throws AefilepException
    {
        return tortas.obtenerEstrenos(inferior,cantidad);
    }
    
    public ArrayList<Torta> obtenerEstrenos(int cant) throws AefilepException
    {
        return tortas.obtenerEstrenos(cant);
    }
    
    public ArrayList<Torta> obtenerVariedad(int idVariedad, int inferior, int cantidad) throws AefilepException
    {
        return tortas.obtenerVariedad(idVariedad,inferior,cantidad);
    }
}
