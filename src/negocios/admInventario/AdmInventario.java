package negocios.admInventario;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import objetosNegocio.Apartado;
import objetosNegocio.BajaDeInventario;
import objetosNegocio.Modelo;
import objetosNegocio.Talla;
import objetosNegocio.TallaApartado;
import persistencia.Persistencia;
import pvcco.interfaces.IntPersistencia;

/**
 *
 * @author Raul Karim Sabag Ballesteros
 */
public class AdmInventario {

    /**
     * Sirve para que no se pueda instanciar fuera de este paquete.
     */
    protected AdmInventario() {

    }

    public void agregarProductoAlInventario(Talla talla) throws Exception {
        IntPersistencia persistencia = new Persistencia();

        //Vamos a checar si la talla ya existe dentro de la base de datos. En dado caso
        //de que si exista, no se debe agregar como un nuevo modelo, sino el inventario
        //regular de el existente se actualizara a la cantidad de la talla dada por el
        //parametro.
        Talla existente = persistencia.obten(talla);

        //Si existe la talla dentro de la base de datos, no nos dara null..
        if (existente != null) {
            //Ahora vamos a checar si el modelo de esta talla es igual a la que se quiere agregar.
            //En dado caso, tenemos que actualizar la cantidad del producto de esa talla.
            if (existente.getIdModelo().getIdModelo().equalsIgnoreCase(talla.getIdModelo().getIdModelo())) {
                int nuevoInventario = existente.getInventarioRegular() + talla.getInventarioRegular();
                existente.setInventarioRegular(nuevoInventario);
                persistencia.actualizar(existente);

                return;
            }
        }

        //Si no existe, simplemente vamos a agregar un nuevo registro de la talla.
        persistencia.agregar(talla);
    }

    public void bajaEnInventario(List<Talla> productos, List<Integer> cantidades, String descripcion) throws Exception {
        IntPersistencia persistencia = new Persistencia();

        //Vamos a asignar el ID de la baja obteniendo la lista de todas
        //las bajas registradas para asignar un ID. De aqui vamos
        //a iterar por todos los productos y hacer nuevos objetos
        //BajaDeInventario por cada talla.
        int IDBaja = persistencia.obtenBajasDeInventario().size();

        //Vamos a obtener la fecha de hoy.
        Calendar c = Calendar.getInstance();
        Date fecha = c.getTime();

        for (int i = 0; i < productos.size(); i++) {
            Talla talla = productos.get(i);
            BajaDeInventario baja = new BajaDeInventario(String.valueOf(IDBaja));

            //Vamos a restarle la cantidad que se quiere bajar al inventario regular
            //del producto.
            int cantidadBaja = cantidades.get(i);

            //Esto es para no ir abajo de 0, que nunca deberia de pasar.
            if (cantidadBaja > 0) {
                talla.setInventarioRegular(talla.getInventarioRegular() - cantidadBaja);
                //Actualizamos la talla en la base de datos.
                persistencia.actualizar(talla);
            }

            //Ponemos los datos de la baja
            baja.setIdTalla(talla);
            baja.setCantidad(cantidadBaja);
            baja.setDescripcion(descripcion);
            baja.setFecha(fecha);

            //La agregamos a la base de datos.
            persistencia.agregar(baja);

            //Para una nueva baja, tenemos que crear un nuevo ID.
            IDBaja++;
        }
    }

    /**
     * Para cada talla que se va a apartar, se le restara uno al inventario
     * regular y se le sumara uno al inventario apartado. El unico requisito
     * sera que todas las tallas dentro de la collecion tienen que tener un ID
     * asignado.
     *
     * @param apartado El apartado a modificar.
     * @throws Exception
     */
    public void modificarPorApartadoAgregado(Apartado apartado) throws Exception {
        IntPersistencia persistencia = new Persistencia();

        //Para cada talla apartada del apartado, tenemos que modificar el inventario.
        for (TallaApartado tallaApartada : apartado.getTallaApartadoCollection()) {
            //Esto es por si la talla en si esta vacia, y solamente se le asigno un ID.
            Talla talla = persistencia.obten(tallaApartada.getIdTalla());

            //Cambiamos el inventario..
            talla.setInventarioRegular(talla.getInventarioRegular() - 1);
            talla.setInventarioApartado(talla.getInventarioApartado() + 1);

            //Finalmente actualizamos la talla en la base de datos.
            persistencia.actualizar(talla);
        }
    }

    /**
     * Para cada talla del apartado dado por el parametro, se le restara uno al
     * inventario de apartado ya que este se esta liquidando.
     *
     * @param apartado El apartado a modificar.
     * @throws Exception
     */
    public void modificarPorApartadoLiquidado(Apartado apartado) throws Exception {
        IntPersistencia persistencia = new Persistencia();

        for (TallaApartado tallaApartada : apartado.getTallaApartadoCollection()) {
            Talla talla = tallaApartada.getIdTalla();

            talla.setInventarioApartado(talla.getInventarioApartado() - 1);
            persistencia.actualizar(talla);
        }
    }

    /**
     * Para cada talla apartada se le restara uno al inventario de apartado y se
     * le sumara uno al inventario regular.
     *
     * @param apartado El apartado a modificar.
     * @throws Exception
     */
    public void modificarPorApartadoCancelado(Apartado apartado) throws Exception {
        IntPersistencia persistencia = new Persistencia();

        for (TallaApartado tallaApartada : apartado.getTallaApartadoCollection()) {
            Talla talla = tallaApartada.getIdTalla();

            talla.setInventarioApartado(talla.getInventarioApartado() - 1);
            talla.setInventarioRegular(talla.getInventarioRegular() + 1);

            persistencia.actualizar(talla);
        }
    }

    /**
     * Resta productos por venta del inventario, restandole al inventario
     * regular de cada uno de los zapatos que se vendieron.
     *
     * @param productos Los productos a modificar su inventario.
     * @param cantidades Las cantidades vendidas.
     */
    public void movimientoEnInventarioPorVenta(List<Talla> productos, List<Integer> cantidades) throws Exception {
        IntPersistencia persistencia = new Persistencia();

        for (int i = 0; i < productos.size(); i++) {
            Talla producto = productos.get(i);
            int cantidad = cantidades.get(i);

            producto.setInventarioRegular(producto.getInventarioRegular() - cantidad);
            persistencia.actualizar(producto);
        }
    }

    /**
     * Busca una talla existente con el mismo ID dado por el parametro. En caso
     * de que no se encuentre se regresara nulo.
     *
     * @param talla La talla con el ID a buscar.
     * @return La talla en la base de datos si es que existe.
     *
     * @throws Exception
     */
    public Talla obten(Talla talla) throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obten(talla);
    }

    /**
     * Busca un modelo existente con el mismo ID dado por el parametro. En caso
     * de que no se encuentre se regresara nulo.
     *
     * @param talla El modelo con el ID a buscar.
     * @return El modelo en la base de datos si es que existe.
     *
     * @throws Exception
     */
    public Modelo obten(Modelo modelo) throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obten(modelo);
    }

    /**
     * Regresa una lista de todos los registros en Tallas.
     *
     * @return La lista de todas las tallas existentes.
     * @throws Exception
     */
    public List<Talla> obtenListaTallas() throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obtenTallas();
    }

    /**
     * Regresa una lista de todos los registros en Modelos.
     *
     * @return La lista de todos los modelos existentes.
     * @throws Exception
     */
    public List<Modelo> obtenListaModelos() throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obtenModelos();
    }
}
