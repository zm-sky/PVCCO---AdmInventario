package negocios.admInventario;

import java.util.List;
import objetosNegocio.Apartado;
import objetosNegocio.Modelo;
import objetosNegocio.Talla;
import pvcco.interfaces.IntAdmInventario;

/**
 *
 * @author Raul Karim Sabag Ballesteros
 */
public class FacAdmInventario implements IntAdmInventario {

    private AdmInventario inv;

    public FacAdmInventario() {
        inv = new AdmInventario();
    }

    /**
     * Agrega zapatos nuevos al inventario. Si el zapato ya existe, se agregara
     * la cantidad del inventarioRegular de la talla dada a la existente.
     *
     * @param talla La talla a agregarse.
     * @throws Exception
     */
    @Override
    public void agregarProductoAInventario(Talla talla) throws Exception {
        inv.agregarProductoAlInventario(talla);
    }

    /**
     * Elimina zapatos del inventario por causas dadas por la descripcion.
     *
     * @param productos Los zapatos a eliminarse
     * @param cantidades Las cantitades a eliminarse
     * @param descripcion La descripcion de la baja
     * @throws Exception
     */
    @Override
    public void bajaEnInventario(List<Talla> productos, List<Integer> cantidades, String descripcion) throws Exception {
        inv.bajaEnInventario(productos, cantidades, descripcion);
    }

    /**
     * Para cada talla apartada se le restara uno al inventario de apartado y se
     * le sumara uno al inventario regular.
     *
     * @param apartado El apartado a modificar.
     * @throws Exception
     */
    @Override
    public void modificarPorApartadoCancelado(Apartado apartado) throws Exception {
        inv.modificarPorApartadoCancelado(apartado);
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
    @Override
    public void modificarPorApartadoAgregado(Apartado apartado) throws Exception {
        inv.modificarPorApartadoAgregado(apartado);
    }

    /**
     * Para cada talla del apartado dado por el parametro, se le restara uno al
     * inventario de apartado ya que este se esta liquidando.
     *
     * @param apartado El apartado a modificar.
     * @throws Exception
     */
    @Override
    public void modificarPorApartadoLiquidado(Apartado apartado) throws Exception {
        inv.modificarPorApartadoLiquidado(apartado);
    }

    /**
     * Resta productos por venta del inventario, restandole al inventario
     * regular de cada uno de los zapatos que se vendieron.
     *
     * @param productos Los productos a modificar su inventario.
     * @param cantidades Las cantidades vendidas.
     */
    @Override
    public void movimientoEnInventarioPorVenta(List<Talla> productos, List<Integer> cantidades) {
        //inv.movimientoEnInventarioPorVenta(productos, cantidades);
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
    @Override
    public Talla obten(Talla talla) throws Exception {
        return inv.obten(talla);
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
    @Override
    public Modelo obten(Modelo modelo) throws Exception {
        return inv.obten(modelo);
    }

    /**
     * Regresa una lista de todos los registros en Tallas.
     *
     * @return La lista de todas las tallas existentes.
     * @throws Exception
     */
    @Override
    public List<Talla> obtenListaTallas() throws Exception {
        return inv.obtenListaTallas();
    }

    /**
     * Regresa una lista de todos los registros en Modelos.
     *
     * @return La lista de todos los modelos existentes.
     * @throws Exception
     */
    @Override
    public List<Modelo> obtenListaModelos() throws Exception {
        return inv.obtenListaModelos();
    }

    /**
     * Este metodo regresa una lista de las tallas de un modelo especifico.
     *
     * @param modelo
     * @return lista de tallas
     */
    @Override
    public List<Talla> obtenTallasDeModelo(Modelo modelo) throws Exception {
        return inv.obtenTallasDeModelo(modelo);
    }

}
