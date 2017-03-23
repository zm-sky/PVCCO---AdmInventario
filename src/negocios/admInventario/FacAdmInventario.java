
package negocios.admInventario;

import java.util.List;
import objetosNegocio.Apartado;
import objetosNegocio.Talla;
import pvcco.interfaces.IntAdmInventario;

/**
 *
 * @author Raul Karim Sabag Ballesteros
 */
public class FacAdmInventario implements IntAdmInventario{

    private AdmInventario inv;
    
    public FacAdmInventario(){
        inv = new AdmInventario();
    }
    
    /**
     * Agrega zapatos nuevos al inventario. Si el zapato ya existe, se agregara la cantidad
     * del inventarioRegular de la talla dada a la existente.
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
     * Para cada talla apartada se le restara uno al inventario de apartado y se le sumara uno
     * al inventario regular.
     * 
     * @param apartado El apartado a modificar.
     * @throws Exception 
     */
    @Override
    public void modificarPorApartadoCancelado(Apartado apartado) throws Exception {
        inv.modificarPorApartadoCancelado(apartado);
    }

    /**
     * Para cada talla que se va a apartar, se le restara uno al inventario regular y se le sumara
     * uno al inventario apartado. El unico requisito sera que todas las tallas dentro de la collecion
     * tienen que tener un ID asignado.
     * 
     * @param apartado El apartado a modificar.
     * @throws Exception 
     */
    @Override
    public void modificarPorApartadoAgregado(Apartado apartado) throws Exception {
        inv.modificarPorApartadoAgregado(apartado);
    }

    /**
     * Para cada talla del apartado dado por el parametro, se le restara uno al inventario de apartado
     * ya que este se esta liquidando.
     * 
     * @param apartado El apartado a modificar.
     * @throws Exception 
     */
    @Override
    public void modificarPorApartadoLiquidado(Apartado apartado) throws Exception {
        inv.modificarPorApartadoLiquidado(apartado);
    }

    /**
     * Resta productos por venta del inventario, restandole al inventario regular de cada uno
     * de los zapatos que se vendieron.
     * 
     * @param productos Los productos a modificar su inventario.
     * @param cantidades Las cantidades vendidas.
     */
    @Override
    public void movimientoEnInventarioPorVenta(List<Talla> productos, List<Integer> cantidades) {
        inv.movimientoEnInventarioPorVenta(productos, cantidades);
    }

}
