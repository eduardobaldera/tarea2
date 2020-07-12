<#include "principal.ftl">

<head>
    <title>${titulo}</title>
</head>
<#macro page_body>
    <br>
    <div class="container">
        <div class="jumbotron">
            <h1 class="display-4">${titulo}.</h1>
            <br>
            <table class="table table-striped">

                <br>
                <thead>
                <tr>
                    <th scope="col" style="width:20%">ID</th>
                    <th scope="col " style="width:20%">Nombre</th>
                    <th scope="col" style="width:20%">Precio</th>
                    <th scope="col" style="width:20%">Cantidad </th>
                    <th scope="col" style="width:20%">Total </th>
                    <th scope="col" style="width:20%">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <#assign total_carrito = 0>
                <#if carrito.listaProductos?size gt 0>
                    <#list carrito.listaProductos as producto>
                        <tr>
                            <th scope="row">
                                <a href="visualizar/+${producto.id}"/>${producto.id}</a>
                            </th>
                            <td>${producto.nombre}</td>
                            <td>${producto.precio}</td>
                            <td>${producto.cantidad}</td>
                            <#assign total = producto.cantidad * producto.precio>
                            <#assign total_carrito += total>
                            <td>${total}</td>
                                <td>
                                    <a href="/tarea2/carrito/eliminar/+${producto.id}" th class="btn btn-danger btn-sm">Eliminar</a>
                                </td>
                        </tr>
                    </#list>
                </#if>
                </tbody>
                <tfoot>
                <tr>
                    <td><a href="/tarea2/comprar" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continuar Comprando</a></td>
                    <td colspan="2" class="hidden-xs"></td>
                    <td class="hidden-xs text-center"><strong>Total ${total_carrito}</strong></td>
                    <td><a href="" class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></a></td>
                </tr>
                </tfoot>
            </table>
            <br>

        </div>
    </div>
</#macro>

<@display_page/>