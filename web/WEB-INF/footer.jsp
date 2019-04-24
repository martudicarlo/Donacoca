<%@page import="aplicacion.modelo.entidades.Usuario"%>
<footer id="footer"><!--Footer-->
    <div class="footer-widget">
        <div class="container">
            <div class="row"> 
                
                           
                
                
                <div class="col-sm-2">
                    <div class="single-widget">                         
                        <h2>Usuario</h2>
                        <ul class="nav nav-pills nav-stacked">
                        <%if(session.getAttribute("usuario")==null){%>
                            <li>
                                <form action="Controlador" method="post" class="formFooter">
                                    <input type="hidden"  name="form" value="RedireccionarComando"/>
                                    <input type="hidden"  name="destino" value="/login.jsp"/>
                                    <input type="submit" name="pagina" value="Iniciar sesión" >
                                </form>
                            </li>
                        <%}else{%>
                            <li>
                                <form action="Controlador" method="post"  class="formFooter">
                                    <input type="hidden"  name="form" value="RedireccionarComando"/>
                                    <input type="hidden"  name="destino" value="/cuenta.jsp"/>
                                    <input type="submit" name="pagina" value="Cuenta" >
                                </form>                               
                            </li>
                        <%}%>
                            <li>
                                <form action="Controlador" method="post"  class="formFooter">
                                    <input type="hidden"  name="form" value="RedireccionarComando"/>
                                    <input type="hidden"  name="destino" value="/carro.jsp"/>
                                    <input type="submit" name="pagina" value="Carrito" >
                                </form>
                            <%if(session.getAttribute("usuario")!=null && !((Usuario)session.getAttribute("usuario")).isEsAdmin()){%>
                            <li>                              
                               <form action="Controlador" method="post"  class="formFooter">
                                    <input type="hidden"  name="form" value="MisPedidosComando"/>
                                    <input type="submit" name="pagina" value="Mis Pedidos" >
                                </form>                                
                            </li>
                            <%}%>
                        </ul>
                    </div>
                </div>
                        
                          <div class="col-sm-2 colFooter">
                    <div class="single-widget listaVariedadesFooter">
                        <form action="Controlador" method="post">                               
                            <input type="hidden" name="form" value="TortasComando" >
                            <ul class="nav nav-pills nav-stacked">
                                                          
                            </ul>
                        </form>
                    </div>
                </div> 
                        
                        
                <div class="col-sm-2">
                    <div class="single-widget">
                        <h2>Tortas</h2>                            
                        <form action="Controlador" method="post">                                                             
                            <input type="hidden" name="form" value="TortasComando" >
                            <ul class="nav nav-pills nav-stacked">                                                                  
                                <li><label><input onclick="submit()" type="radio" name="tipo" value="todas">Todas </label></li>
                                <li><label><input onclick="submit()" type="radio" value="2" name="tipo">Clubes</label></li>
                                <li><label><input onclick="submit()" type="radio" value="1" name="tipo">Infantiles</label></li>
                                <li><label><input onclick="submit()" type="radio" value="4" name="tipo">Quince</label></li>
                                <li><label><input onclick="submit()" type="radio" value="7" name="tipo">Bautismos</label></li>                                                                    
                            </ul>
                        </form>                                
                    </div>
                </div>
                <div class="col-sm-2 colFooter">
                    <div class="single-widget listaVariedadesFooter">
                        <form action="Controlador" method="post">                               
                            <input type="hidden" name="form" value="TortasComando" >
                            <ul class="nav nav-pills nav-stacked">
                                <li><label><input onclick="submit()" type="radio" name="tipo" value="8">Tradicionales</label></li>
                                <li><label><input onclick="submit()" type="radio" value="2" name="9">Frutales</label></li>
                                <li><label><input onclick="submit()" type="radio" value="1" name="10">Combos</label></li>
                                <li><label><input onclick="submit()" type="radio" value="4" name="6">Budines</label></li>
                                <li><label><input onclick="submit()" type="radio" value="7" name="5">Casamientos</label></li>                                
                            </ul>
                        </form>
                    </div>
                </div>
                
                 <div class="col-sm-2 colFooter">
                    <div class="single-widget listaVariedadesFooter">
                        <form action="Controlador" method="post">                               
                            <input type="hidden" name="form" value="TortasComando" >
                            <ul class="nav nav-pills nav-stacked">
                                                          
                            </ul>
                        </form>
                    </div>
                </div>       
                        
                <div class="col-sm-2">
                    <div class="single-widget">
                        <h2 id="acercaDe">Acerca de</h2>
                        <ul class="nav nav-pills nav-stacked">
                            <li>
                                <form action="Controlador" method="post" class="formFooter">
                                    <input type="hidden"  name="form" value="RedireccionarComando"/>
                                    <input type="hidden"  name="destino" value="/nosotros.jsp"/>
                                    <input type="submit" name="pagina" value="Nosotros" >
                                </form>                               
                            </li>
                            <li><form action="Controlador" method="post"  class="formFooter">
                                        <input type="hidden"  name="form" value="RedireccionarComando"/>
                                         <input type="hidden"  name="destino" value="/contacto.jsp"/>
                                        <input type="submit" name="pagina" value="Contacto" >
                                </form>
                            </li>  
                        </ul>
                    </div>
                </div>
                 <%if(session.getAttribute("usuario")!=null && ((Usuario)session.getAttribute("usuario")).isEsAdmin()){%>
                <div class="col-sm-2">
                    <div class="single-widget">
                        <h2 id="acercaDe">Administrador</h2>
                        <ul class="nav nav-pills nav-stacked">                           
                            <li>
                                <form action="Controlador" method="post" class="formFooter">
                                    <input type="hidden"  name="desdeIndex" value="desdeIndex"/>
                                    <input type="hidden"  name="form" value="AdminTortasComando"/>
                                    <input type="submit" name="pagina" value="Tortas">
                                </form>
                            </li>
                            <li>
                                <form action="Controlador" method="post" class="formFooter">
                                    <input type="hidden"  name="form" value="AdminUsuariosComando"/>
                                    <input type="submit" name="pagina" value="Usuarios" >
                                </form>
                            </li>
                            <li>
                                <form action="Controlador" method="post" class="formFooter">
                                    <input type="hidden"  name="form" value="RedireccionarComando"/>
                                    <input type="hidden"  name="destino" value="/Devoluciones.jsp"/>
                                    <input type="submit" name="pagina" value="Devoluciones" >
                                </form>
                            </li>
                            <li>
                                <form action="Controlador" method="post" class="formFooter">
                                    <input type="hidden"  name="form" value="EnviosComando"/>
                                    <input type="submit" name="pagina" value="Envíos" />
                                </form>                 
                            </li>                              
                        </ul>
                    </div>
                </div>
                <%}%>
            </div>
        </div>
    </div>
    <div class="footer-bottom">
        <div class="container">
            <div class="row">
                <p class="pull-left">Copyright © 2019 Doña Coca Inc. Todos los derechos reservados.</p>
                <p class="pull-right">Diseñado por <span>DoñaCoca Team</span></p>
            </div>
        </div>
    </div>
</footer><!--/Footer-->

<script type="text/javascript" src="scripts/sha.js"></script>
<script src="scripts/jquery.js"></script>
<script src="scripts/bootstrap.min.js"></script>
<script src="scripts/jquery.scrollUp.min.js"></script>
<script src="scripts/price-range.js"></script>
<script src="scripts/jquery.prettyPhoto.js"></script>
<script src="scripts/main.js"></script>

<a id="scrollUp" href="index.jsp/#top" style="position: fixed; z-index: 2147483647; display: none;"><i class="fa fa-angle-up"></i></a>
