import app.controller.SupplierController;
import app.service.SupplierService;
import org.junit.Test;

/**
 * Created by ka on 22/02/16.
 */
public class TestRest {

   @Test
    public void testSuppler() {

       System.out.println(new SupplierService().getSuppliers());
   }


}
