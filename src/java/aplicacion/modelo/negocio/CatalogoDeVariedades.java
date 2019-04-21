/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.negocio;

import aplicacion.modelo.entidades.Variedad;
import aplicacion.modelo.datos.VariedadBD;
import aplicacion.utilidades.AefilepException;
import java.util.ArrayList;

/**
 *
 * @author Alumno
 */

public class CatalogoDeVariedades 
{
    VariedadBD VariedadBD = new VariedadBD();  
    
    public ArrayList<Variedad> obtenerVariedad() throws AefilepException
    {
        return VariedadBD.obtenerVariedades();
    }    
}
