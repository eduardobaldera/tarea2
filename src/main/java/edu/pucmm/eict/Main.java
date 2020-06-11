package edu.pucmm.eict;

import edu.pucmm.eict.controladores.*;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

public class Main {
    public static void main(String[] args) {
        String mensaje = "Hola Mundo en Javalin :-D";
        System.out.println(mensaje);

        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //desde la carpeta de resources
            config.registerPlugin(new RouteOverviewPlugin("/rutas")); //aplicando plugins de las rutas
            config.enableCorsForAllOrigins();
        }).start(getHerokuAssignedPort());

        //creando el manejador
        app.get("/", ctx -> ctx.redirect("/crud-simple/"));


        new CrudTradicionalControlador(app).aplicarRutas();
        new CompraControlador(app).aplicarRutas();
        new CarritoControlador(app).aplicarRutas();

    }
    /**
     * Metodo para indicar el puerto en Heroku
     * @return
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }
}
