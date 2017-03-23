
package prueba;

import negocios.admInventario.FacAdmInventario;
import objetosNegocio.Modelo;
import objetosNegocio.Talla;
import pvcco.interfaces.IntAdmInventario;

/**
 *
 * @author Roberto Pedraza Coello
 */
public class PruebaInventario {
    public static void main(String[] args) throws Exception{
        IntAdmInventario adm = new FacAdmInventario();
        
        Talla talla = new Talla("2");
        talla.setIdModelo(new Modelo("1", "Zapatilla / Roja", 33.3f));
        talla.setInventarioApartado(0);
        talla.setInventarioRegular(12);
        talla.setNoCodigoDeBarras("0000000");
        talla.setTalla("3");
        
        adm.agregarProductoAInventario(talla);
    }
}
