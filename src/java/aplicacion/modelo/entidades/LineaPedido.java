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
public class LineaPedido
{
    private int cantidad;
    private float subtotal;
    private boolean esAlquiler;
    private Torta torta;

    /**
     * @return the cantidad
     */
    public int getCantidad()
    {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad)
    {
        this.cantidad = cantidad;
    }

    /**
     * @param cantidadDias
     * @return the subtotal
     */
    public float getSubtotal(int cantidadDias)
    {
        if(esAlquiler)
            subtotal = cantidad*torta.getPrecioAlquiler()*cantidadDias;
        else
            subtotal = cantidad*torta.getPrecioVenta();
        
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(float subtotal)
    {
        this.subtotal = subtotal;
    }

    /**
     * @return the esAlquiler
     */
    public boolean isEsAlquiler()
    {
        return esAlquiler;
    }

    /**
     * @param esAlquiler the esAlquiler to set
     */
    public void setEsAlquiler(boolean esAlquiler)
    {
        this.esAlquiler = esAlquiler;
    }

    /**
     * @return the torta
     */
    public Torta getTorta() 
    {
        return torta;
    }

    /**
     * @param torta the torta to set
     */
    public void setTorta(Torta torta)
    {
        this.torta = torta;
    }
}
