/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.modelo.entidades;

/**
 *
 * @author JP
 */
public class Variedad
{
    private int idVariedad;
    private String descripcion;

    /**
     * @return the idVariedad
     */
    public int getIdVariedad()
    {
        return idVariedad;
    }

    /**
     * @param idVariedad the idVariedad to set
     */
    public void setIdVariedad(int idVariedad)
    {
        this.idVariedad = idVariedad;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion()
    {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) 
    {
        this.descripcion = descripcion;
    }
}
