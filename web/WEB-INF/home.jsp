<%@page import="aplicacion.modelo.negocio.CatalogoDeTortas"%>
<%@page import="aplicacion.modelo.entidades.Pedido"%>
<%@page import="aplicacion.modelo.entidades.Usuario"%>
<%@page import="aplicacion.modelo.entidades.Parametro"%>
<%@page import="aplicacion.modelo.datos.ParametroBD"%>
<%@page import="aplicacion.modelo.entidades.Torta"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="head.jsp"/>
    <body>
        <jsp:include page="header.jsp"/>
        <% String [] listaNombres = {"comedia","drama","terror","accion","thriller"};
        if(session.getAttribute("ex")!=null){   %>
        <div class="container">
            <div class="row">
                <div class="col-sm-4">            
                    <div class="alert alert-danger fade in">
                        <%= session.getAttribute("ex")%>
                    </div>                    
                </div>
            </div>
        </div>
        <%}else{
         session.setAttribute("ex", null);
         ArrayList<Torta> tortasCarrusel = (ArrayList)session.getAttribute("tortasCarrusel");
         ArrayList<ArrayList<Torta>> listaTortas = (ArrayList)session.getAttribute("listaTortas"); %>
        <section id="slider"><!--slider-->
            <div class="container">                            
                <div class="row">
                    <div class="col-sm-12">
                        <div id="slider-carousel" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#slider-carousel" data-slide-to="0" class=""></li>
                                <li data-target="#slider-carousel" data-slide-to="1" class="active"></li>
                                <li data-target="#slider-carousel" data-slide-to="2" class=""></li>
                            </ol>	                      
                            <div class="carousel-inner">
                            <% int indice=0;
                            for(Torta pc: tortasCarrusel){ %>
                                <div class="item <%if(indice==1){%>active<%}%>">
                                    <div class="col-sm-6">
                                        <h1><span>D</span>oña Coca - Pastelería</h1>
                                        <h2><%=pc.getNombre()%></h2>
                                        <p><%=pc.getDescripcion(150)%>...</p>
                                        <form action="Controlador" method="post">
                                            <button type="submit" class="btn btn-default get">Obtener ahora</button>
                                            <input type="hidden"  name="form" value="ObtenerTortaComando"/>
                                            <input type="hidden"  name="idTorta" value="<%=pc.getIdTorta()%>"/>
                                        </form>
                                    </div>
                                    <div class="col-sm-6">
                                        <img src="ProcesadorImagenes?id=<%=pc.getIdTorta()%>" class="imagenCarrusel img-responsive" alt="">
                                        <img src="./imagenes/new.png" class="pricing" alt="">
                                    </div>
                                </div>
                            <%indice++;}%>                                    
                            </div>
                            <a href="/#slider-carousel" class="left control-carousel hidden-xs" data-slide="prev">
                                <i class="fa fa-angle-left"></i>
                            </a>
                            <a href="/#slider-carousel" class="right control-carousel hidden-xs" data-slide="next">
                                <i class="fa fa-angle-right"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </section><!--/slider-->
       
        <section>
            <div class="container">
                <div class="row">
                    
                    
                    
                    <div class="col-sm-9 padding-right">
                        <div class="category-tab"><!--category-tab-->
                            <div class="col-sm-12">
                                <ul class="nav nav-tabs">
                                    <li class="active"><a href="#infantiles" data-toggle="tab">Infantiles</a></li>
                                    <li><a href="#quince" data-toggle="tab">Quince</a></li>
                                    <li><a href="#casamientos" data-toggle="tab">Casamientos</a></li>
                                    <li><a href="#bautismos" data-toggle="tab">Bautismos</a></li>
                                    <li><a href="#frutales" data-toggle="tab">Frutales</a></li>
                                </ul>
                            </div>
                            <div class="tab-content">
                            <% for(int i=0; i<listaTortas.size();i++){%>
                                <div class="tab-pane fade <%if(i==0){%>active in<%}%>" id="<%=listaNombres[i]%>">                      
                                <%for(Torta p: (listaTortas.get(i))){%>
                                    <div class="col-sm-3">
                                        <div class="product-image-wrapper">
                                            <div class="single-products">
                                                <div class="productinfo text-center">
                                                    <img class="imgChica" src="ProcesadorImagenes?id=<%=p.getIdTorta()%>" alt="">
                                                    <h2><%=p.getNombre()%></h2>
                                                    <form action="Controlador" method="post">
                                                        <button type="submit" class="btn btn-default get">Obtener ahora</button>
                                                        <input type="hidden"  name="form" value="ObtenerTortaComando"/>
                                                        <input type="hidden"  name="idTorta" value="<%=p.getIdTorta()%>"/>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <%}%>
                                </div>
                            <%}%>                             
                            </div>
                        </div><!--/category-tab-->	
                    </div>
                </div>
              <%}%>
            </div>
        </section>    
        <jsp:include page="footer.jsp"/>
    </body>
</html>
