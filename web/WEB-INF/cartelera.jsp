<%@page import="aplicacion.modelo.entidades.Variedad"%>
<%@page import="aplicacion.modelo.entidades.Parametro"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="head.jsp"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <%@page import="aplicacion.modelo.entidades.Torta"%>
        <%!ArrayList<Torta> listaCartelera = null;%>
        <%!ArrayList<Variedad> listaVariedades = null;%>
        <%listaVariedades = (ArrayList)session.getAttribute("variedades");%>
        <%int cantPaginas = 0;%>
        <%if(session.getAttribute("variedadObtenido")!=null)
        {
            if(session.getAttribute("tortasEncontradas")!=null)
                listaCartelera = (ArrayList)session.getAttribute("tortasEncontradas");
        }
        else
        {
            if(session.getAttribute("listaCartelera")!=null)
                listaCartelera = (ArrayList)session.getAttribute("listaCartelera");
        }
        if(session.getAttribute("cantidadTortas")!=null)
        {
            if((Integer)session.getAttribute("cantidadTortas") % 9==0)
                cantPaginas= ((Integer)session.getAttribute("cantidadTortas")/9);
            else
                cantPaginas= ((Integer)session.getAttribute("cantidadTortas")/9)+1;
        }
        %>      
        <section>
            <div class="container">
            <% if(request.getAttribute("ex")!=null){%>
                <div class="row">
                    <div class="col-sm-4">
                        <div class="alert alert-danger fade in">
                            <%= request.getAttribute("ex")%>
                        </div>
                    </div>
                </div>                                        
            <%} else {%>
                <div class="row">
                    <div class="col-sm-3">
                        <div class="left-sidebar">
                            <h2>Variedades</h2>
                            <div class="brands-name">
                                <form action="Controlador" method="post" >
                                    <input type="hidden" name="form" value="TortasComando" >
                                    <ul class="nav nav-pills nav-stacked">
                                        <li><label class="etiquetaVariedad"><input onclick="submit()" type="radio" value="estreno" name="tipo">Estrenos</label></li>
                                        <% for(Variedad v : listaVariedades){%>
                                        <li><label class="etiquetaVariedad"><input onclick="submit()" type="radio" value="<%=v.getIdVariedad()%>" name="tipo"><%=v.getDescripcion()%></label></li>
                                        <%}%>                                                                                        
                                    </ul>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-9 padding-right">
                        <h2 class="title text-center">Tortas</h2>
                        <div class="features_items">
                            <% if(session.getAttribute("errorNoEncontradas")!=null){%>
                            <div class="alert alert-danger">
                                <h2 class='text-center'>No hay tortas que coincidan con su búsqueda!!</h2>
                            </div>
                            <% session.setAttribute("errorNoEncontradas", null);}         
                             for(Torta p: listaCartelera){ %>
                            <div class="col-sm-4">
                                <div class="product-image-wrapper">
                                    <div class="single-products">
                                        <div class="productinfo text-center">
                                            <img class="imagen" src="ProcesadorImagenes?id=<%=p.getIdTorta()%>" alt="">
                                            <form action="Controlador" method="post">
                                                <h2>
                                                    <button class="tituloAbajo"><%=p.getNombre()%></button>
                                                </h2>
                                                <input type="hidden"  name="form" value="ObtenerTortaComando"/>
                                                <input type="hidden"  name="idTorta" value="<%=p.getIdTorta()%>"/>
                                            </form>
                                          <p>$ <%=String.format("%.2f",p.getPrecioVenta())%></p>
                                            <form action="Controlador" method="post">
                                                <input type="hidden"  name="form" value="AgregarLineaComando"/>
                                                <input type="hidden" name="idTorta" value="<%=p.getIdTorta()%>"/>
                                                
                                                <input class="btn btn-default add-to-cart linea" type="submit" name="tipoLinea" value="Comprar">
                                            </form>
                                        </div>
                                        <div class="product-overlay text-center">
                                            <div class="overlay-content">
                                                <form action="Controlador" method="post">
                                                    <h2>
                                                        <button><%=p.getNombre()%></button>
                                                    </h2>
                                                    <input type="hidden"  name="form" value="ObtenerTortaComando"/>
                                                    <input type="hidden"  name="idTorta" value="<%=p.getIdTorta()%>"/>
                                                </form>
                                                <p> <%=String.format("%.2f",p.getPrecioVenta())%></p>
                                                <form action="Controlador" method="post">
                                                    <input type="hidden"  name="form" value="AgregarLineaComando"/>
                                                    <input type="hidden" name="idTorta" value="<%=p.getIdTorta()%>"/>
                                                   
                                                    <input class="btn btn-default add-to-cart linea" type="submit" name="tipoLinea" value="Comprar">
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                               </div>
                            </div>
                            <%}%>        
                        </div>
                        <form action="Controlador" method="post">  
                            <ul class="pagination">
                                <%for(int j=1;j<=cantPaginas;j++){%>                                                                                                 
                                <li>
                                    <input type="submit" <%if((Integer)session.getAttribute("pActual")==j){%>class="active" disabled<%}%> name="paginacionActual" value="<%=j%>">
                                </li>
                                <%}%>  
                                <input type="hidden" name="form" value="TortasComando">                                                
                            </ul>
                        </form> 
                    </div>
                </div>
                <%}%>
            </div>
        </section>
        <jsp:include page="footer.jsp"/>
    </body>
</html>