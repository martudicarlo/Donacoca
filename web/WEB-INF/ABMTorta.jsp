
<%@page import="aplicacion.modelo.entidades.Parametro"%>
<%@page import="aplicacion.modelo.entidades.Variedad"%>
<%@page import="aplicacion.modelo.entidades.Torta"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <jsp:include page="head.jsp"/>
    <body onload="scrollDiv()">
        <jsp:include page="header.jsp"/>
        <%!ArrayList<Torta> tortas;%>
        <%!ArrayList<Variedad> variedades;%>
        <%!Parametro param;%>
        <%!Torta tort;%>
        <% if(session.getAttribute("parametros")!=null){ param = (Parametro) session.getAttribute("parametros"); }%>
        <% if(session.getAttribute("ListaTortas")!=null) { tortas = (ArrayList)session.getAttribute("ListaTortas");}%>
        <% if(session.getAttribute("ListaVariedades")!=null) { variedades = (ArrayList)session.getAttribute("ListaVariedades");}%>
        <% tort = (Torta)session.getAttribute("PeliEdit"); 
            if(request.getAttribute("tortaPorAgregar")!=null)        
                tort = (Torta)request.getAttribute("tortaPorAgregar");  
        %>
        <div class="cuenta">
            <div class="container"> 
                <%if(request.getAttribute("ex")!=null && tortas==null ){ %>
                <div class="row">
                    <div class="alert alert-success fade in">
                        <%= request.getAttribute("ex")%>
                    </div>
                </div>
                <%}%>
                <% if(tortas!=null) { %>
                <div class="row">
                    <h2 class="title text-center">Lista de Tortas</h2> 
                    <div class="col-sm-12">
                        <div class="table-responsive" style="height:400px; overflow:auto">
                            <div class="table-striped">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Nombre</th>
                                            <th>Duración</th>
                                            <th>Stock Alqui.</th>
                                            <th>Stock Venta</th>
                                            <th>F. Alta</th>
                                            <th>Año</th>
                                            <th>Activa</th>
                                            <th>Estreno</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td> - </td>
                                            <td> - </td>
                                            <td> - </td>
                                            <td> - </td>
                                            <td> - </td>
                                            <td> - </td>
                                            <td> - </td>
                                            <td> - </td>
                                            <td> - </td>
                                            <td>
                                                <form action="Controlador" method="post">
                                                    <input type="hidden"  name="form" value="SeleccionarTortaComando"/>
                                                    <input type="hidden" name="idPeliEdit" value="0">
                                                    <input type="submit" value="+ Nuevo">
                                                </form>
                                            </td>
                                        </tr>
                                        <%for(Torta torta:tortas){%>
                                        <tr>
                                            <td><%= torta.getIdTorta()%></td>
                                            <td><%= torta.getNombre()%></td>
                                            <td><%= torta.getDuracion()%></td>
                                            <td><%= torta.getStockAlquiler()%></td>
                                            <td><%= torta.getStockVenta()%></td>
                                            <td><%= torta.getFechaCarga()%></td>
                                            <td><%= torta.getAnio()%></td>
                                            <td><% if(torta.isActivo()){%><img src="./imagenes/check.png"><%}%></td>
                                            <td><% if(torta.isEstreno()){%><img src="./imagenes/check.png"><%}%></td>
                                            <td>
                                                <form action="Controlador" method="post">
                                                    <input type="hidden"  name="form" value="SeleccionarTortaComando"/>
                                                    <input type="hidden" name="idPeliEdit" value="<%= torta.getIdTorta()%>">
                                                    <input type="submit" value="Editar">
                                                </form>
                                            </td>
                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div> 
                </div>
                <div <%if(session.getAttribute("Scroll")!=null){%> id="Edit" <%session.setAttribute("Scroll", null); }%> class="row">
                    <br/>         
                    <h2 class="title text-center"><%if(tort!=null && request.getAttribute("tortaPorAgregar")==null){%>EDITAR<%} else{%>AGREGAR<%}%> TORTA </h2>
                    <br/>
                    <form name="DatosTorta" class="tortas" action="Controlador" method="post" enctype="multipart/form-data" onsubmit="return validarChecks()">  
                        <div class="col-sm-6 ">
                            <div class="row">
                                <div class="col-sm-3">
                                    <h4 class="text-left">ID</h4>
                                </div>
                                <div class="col-sm-9">
                                    <input class="control form-control"  type="text" placeholder="ID (Automático)" maxlength="15" name="ID" readonly="" value="<%if(tort!=null && request.getAttribute("tortaPorAgregar")==null )%><%=tort.getIdTorta()%>">
                                </div>
                            </div>
                            <%if(tort!=null && request.getAttribute("tortaPorAgregar")==null){%>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h4 class="text-left">F. Alta</h4>
                                </div>
                                <div class="col-sm-9">
                                    <input type="date" class="control form-control" name="fCargaPel" readonly="" value="<%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%= tort.getFechaCarga()%>">
                                </div>                           
                            </div>
                            <%}%>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h4 class="text-left">Título</h4>
                                </div>
                                <div class="col-sm-9">
                                    <input type="text" class="control form-control" name="nomPel" placeholder="*"  required value="<%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%=tort.getNombre()%>">
                                </div>
                            </div>
                            <div class="row">
                            <div class="col-sm-3">
                                    <h4 class="text-left">Año</h4>
                                </div>
                                <div class="col-sm-9">
                                    <input type="text" class="control form-control" name="anioPel" placeholder="* (Año de lanzamiento)" pattern="^[0-9]*$" title="Numero" required value="<%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%= tort.getA("tortaPorAgregarnio() %>">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <h4 class="text-left">Duración</h4>
                                    <input class="control form-control"  type="text" placeholder="* (En min.)" name="durPel" pattern="^[0-9]*$" title="Numero" value="<%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%=tort.getDuracion()%>">
                                </div>
                                <div class="col-sm-6">
                                    <h4 class="text-left">Formato</h4>
                                    <input type="text" class="control form-control" name="formPel" placeholder="*"  required value="<%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%=tort.getFormato()%>">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-12">
                                    <h4 class="text-left">Sinopsis</h4>
                                    <textarea rows="5" class="form-control" maxlength="400" placeholder="*" name="sinPel"><%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%=tort.getSinopsis()%></textarea> 
                                </div>    
                            </div>                              
                            <div class="row">
                                <div class="col-sm-12">
                                    <h4 class="text-left">Reparto</h4>                       
                                    <textarea rows="4" class="form-control" maxlength="200" placeholder="*" name="repPel"><%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%= tort.getReparto()%></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 ">
                            <div class="row">
                                <div class="col-sm-6">
                                    <h4 class="text-left">Stock. Alq.</h4>
                                    <input type="text" class="control form-control" name="stockAlqPel" placeholder="*" pattern="^[0-9]*$" title="Numero" required value="<%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%=tort.getStockAlquiler()%>">
                                </div>
                                <div class="col-sm-6">
                                    <h4 class="text-left">Stock. Vta.</h4>
                                    <input type="text" class="control form-control" name="stockVtaPel" placeholder="*" pattern="^[0-9]*$" title="Numero" required value="<%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%=tort.getStockVenta()%>">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <h4 class="text-left">Precio. Alq.</h4>
                                    <input type="text" class="control form-control" name="palqPel" placeholder="Precio Alquiler" pattern="^[0-9]*$" title="Numero" readonly required value="<%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null){%><%= tort.getPrecioAlquiler()%><%}else if(tort==null){%><%= param.getPrecioAlquilerEstreno()%><%}%>">
                                </div>
                                <div class="col-sm-6">
                                    <h4 class="text-left">Precio. Vta.</h4>
                                    <input type="text" class="control form-control" name="pvtaPel" placeholder="* (En $)" pattern="^[0-9]+(\.[0-9]+)?$" title="Numero" required value="<%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%= tort.getPrecioVenta() %>">
                                </div>
                            </div>                        
                            <div class="row">
                                <div class="col-sm-3">
                                    <h4 class="text-left">Portada</h4>
                                </div>
                                <div class="col-sm-9">
                                    <input type="file" class="control form-control" name="imgPel">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h4 class="text-left">Link Trailer</h4>
                                </div>
                                <div class="col-sm-9">
                                    <input type="text" class="control form-control" name="urlPel" placeholder="*" required value="<%if(tort!=null || request.getAttribute("tortaPorAgregar")!=null)%><%= tort.getUrlTrailer() %>">                               
                                </div>
                            </div>
                            <div class="row">
                                 <div class="col-sm-12">
                                     <label class="puntero"><input class="enLinea" type="checkbox" name="Activa" value="true" <% if((tort!=null || request.getAttribute("tortaPorAgregar")!=null) && tort.isActivo())%>checked<%;%>><h4 class="enLinea">Activa</h4></label>
                                </div>                        
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                <h4 class="text-left">Variedades</h4>

                                    <div class="table-responsive" style="height:120px; overflow:auto;">
                                        <table class="table-striped col-lg-12">
                                            <tbody>
                                            <% for(int i=0;i<veriedades.size();i++){%>
                                                <tr>
                                                    <td>
                                                        <label class="puntero"><input class="check" type="checkbox" name="variedades" value="<%=variedades.get(i).getIdVariedad()%>" <%if((tort!=null || request.getAttribute("tortaPorAgregar")!=null) && tort.contieneVariedad(variedad.get(i)))%>checked<%;%>><%=variedades.get(i).getDescripcion()%></label>
                                                    </td>
                                                </tr>
                                                <%}%>
                                           </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <div class="row"> 
                                <div class="col-sm-12">
                                    <%if(request.getAttribute("ex")!=null){ %>                           
                                        <div class="alert alert-danger">
                                            <p class="text-center"><%= request.getAttribute("ex")%></p>
                                        </div>
                                    <%}%>
                                    <%if(request.getAttribute("ExitoPeli")!=null){
                                        if((Boolean)request.getAttribute("ExitoPeli")){%>
                                       <div class="alert alert-success">
                                           <p class="text-center">Torta <%if(tort==null && request.getAttribute("tortaPorAgregar")==null){ %>agregada<% }else{%>editada<%}%> con éxito.</p>        
                                        </div>
                                    <% }else if(!(Boolean)request.getAttribute("ExitoPeli")){ %>
                                        <div class="alert alert-danger ">
                                            <p class="text-center">Ya existe una torta con el mismo nombre</p>        
                                        </div>           
                                    <% }}%>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12">
                            <input type="hidden" name="form" value="<%if(tort!=null && request.getAttribute("tortaPorAgregar")==null) {%>EditarTortaComando<%}else{%>AgregarTortaComando<%}%>">
                            <button type="submit" class="btn btn-default"><%if(tort!=null && request.getAttribute("tortaPorAgregar")==null) {%>Guardar Cambios<%}else{%>Agregar Torta<%}%></button>
                            <h5>Los campos marcados con * son obligatorios</h5>
                        </div>
                    </form>
                </div>
                <% }%>
            </div>
        </div>
    </body>
    <jsp:include page="footer.jsp"/>
</html>
