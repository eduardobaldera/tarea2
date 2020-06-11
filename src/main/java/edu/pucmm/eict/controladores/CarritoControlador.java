package edu.pucmm.eict.controladores;

import edu.pucmm.eict.encapsulaciones.Producto;
import edu.pucmm.eict.servicios.FakeServices;
import edu.pucmm.eict.util.BaseControlador;
import io.javalin.Javalin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.javalin.apibuilder.ApiBuilder.*;

public class CarritoControlador extends BaseControlador {
    FakeServices fakeServices = FakeServices.getInstancia();

    public CarritoControlador(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(() -> {
            path("/carrito/", () -> {


                get("/", ctx -> {
                    ctx.redirect("/carrito/listar");
                });

                get("/listar", ctx -> {
                    //tomando el parametro utl y validando el tipo.
                    List<Producto> lista = fakeServices.listarProducto();
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Listado de Productos");
                    modelo.put("lista", lista);
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/carrito.html", modelo);
                });

                get("/crear", ctx -> {
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Registrar Producto");
                    modelo.put("accion", "/crud-simple/crear");
                    //enviando al sistema de plantilla.
                    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
                });

                /**
                 * manejador para la creación del estudiante, una vez creado
                 * pasa nuevamente al listado.
                 */
                post("/crear", ctx -> {
                    //obteniendo la información enviada.
                    int matricula = ctx.formParam("matricula", Integer.class).get();
                    String nombre = ctx.formParam("nombre");
                    BigDecimal precio = new BigDecimal(ctx.formParam("carrera"));
                    //
                    Producto tmp = new Producto(matricula, nombre, precio);
                    //realizar algún tipo de validación...
                    fakeServices.crearProducto(tmp); //puedo validar, existe un error enviar a otro vista.
                    ctx.redirect("/crud-simple/");
                });

                get("/visualizar/:id", ctx -> {
                    Producto producto = fakeServices.getProductoPorId(ctx.pathParam("id", Integer.class).get());
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Visualizar Producto "+ producto.getId());
                    modelo.put("producto", producto);
                    modelo.put("visualizar", true); //para controlar en el formulario si es visualizar
                    modelo.put("accion", "/crud-simple/");

                    //enviando al sistema de ,plantilla.
                    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
                });

                get("/editar/:id", ctx -> {
                    Producto producto = fakeServices.getProductoPorId(ctx.pathParam("id", Integer.class).get());
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Formulario Editar Producto "+producto.getId());
                    modelo.put("producto", producto);
                    modelo.put("accion", "/crud-simple/editar");

                    //enviando al sistema de ,plantilla.
                    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
                });

                get("/carrito/:id", ctx -> {
                    Producto producto = fakeServices.getProductoPorId(ctx.pathParam("id", Integer.class).get());
                    //
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Carrito de Compras");
                    modelo.put("producto", producto);
                    //modelo.put("accion", "/crud-simple/editar");

                    //enviando al sistema de ,plantilla.
                    ctx.render("/templates/crud-tradicional/crearEditarVisualizar.html", modelo);
                });

                /**
                 * Proceso para editar un estudiante.
                 */
                post("/editar", ctx -> {
                    //obteniendo la información enviada.
                    int id = ctx.formParam("Id", Integer.class).get();
                    String nombre = ctx.formParam("nombre");
                    BigDecimal precio = new BigDecimal(ctx.formParam("Precio"));
                    //
                    Producto tmp = new Producto(id, nombre, precio);
                    //realizar algún tipo de validación...
                    fakeServices.actualizarProducto(tmp); //puedo validar, existe un error enviar a otro vista.
                    ctx.redirect("/crud-simple/");
                });

                /**
                 * Puede ser implementando por el metodo post, por simplicidad utilizo el get. ;-D
                 */
                get("/eliminar/:matricula", ctx -> {
                    fakeServices.eliminandoProducto(ctx.pathParam("matricula", Integer.class).get());
                    ctx.redirect("/crud-simple/");
                });

            });
        });
    }

}
