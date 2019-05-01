<%@page import="aplicacion.modelo.negocio.CatalogoDeVariedades"%>
<%@page import="aplicacion.modelo.entidades.Variedad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aplicacion.modelo.entidades.Torta"%>
<html>
    <head>
        <jsp:include page="head.jsp"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <%Torta tortaActual=(Torta)session.getAttribute("tortaActual"); 
          ArrayList<Variedad> listaVariedades = (ArrayList)session.getAttribute("variedades");%>
        <section>
            <div class="container">
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar">
                            <h2>Variedades</h2>
                            <div class="category-products">
                                <form action="Controlador" method="post" >
                                    <input type="hidden" name="form" value="TortasComando" >
                                    <ul class="nav nav-pills nav-stacked">
                                        <li><label class="etiquetaVariedad"><input onclick="submit()" type="radio" value="estreno" name="tipo"></label></li>
                                        <% for(Variedad v : listaVariedades){%>
                                        <li><label class="etiquetaVariedad"><input onclick="submit()" type="radio" value="<%=v.getIdVariedad()%>" name="tipo"><%=v.getDescripcion()%></label></li>
                                        <%}%>                                                                                        
                                    </ul>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-9 padding-right">
                        <div class="product-details"><!--product-details-->
                            <div class="col-sm-5">                                   
                                <div class="productinfo">
                                    <img class="imagenTorta" src="ProcesadorImagenes?id=<%=tortaActual.getIdTorta()%>" alt="<%=tortaActual.getNombre()%>">
                                </div>
                            </div>
                            <div class="col-sm-7">
                                <div class="product-information"><!--/product-information-->
                                    <h2><%=tortaActual.getNombre()%></h2>

                                    <p><b>Descripción: </b><%=tortaActual.getDescripcion()%></p>
                                    <form action="Controlador" method="post">
                                        <input type="hidden"  name="form" value="AgregarLineaComando"/>
                                        <input type="hidden" name="idTorta" value="<%=tortaActual.getIdTorta()%>"/>
                                        <input type="hidden" name="provieneDeTorta" value="true">
                                                                          
                                        <span>
                                            <span class="precios">Comprar $<%=String.format("%.2f",tortaActual.getPrecioVenta())%></span>
                                            <button type="submit" class="btn btn-fefault cart botonTorta"  name="tipoLinea" value="Comprar">
                                                <i class="fa fa-shopping-cart"></i> Comprar
                                            </button>
                                        </span>                                           
                                    </form>
                                </div><!--/product-information-->
                            </div>
                            
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <jsp:include page="footer.jsp"/>
    </body>
</html>