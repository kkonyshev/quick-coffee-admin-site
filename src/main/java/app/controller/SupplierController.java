package app.controller;

import app.model.Supplier;
import app.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@SuppressWarnings("unused")
@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    /*
    Index
     */

    @RequestMapping(path = {"/"})
    public ModelAndView list(Model model) {
        ModelAndView modelAndView = new ModelAndView("supplier/index");
        modelAndView.addObject("suppliers", supplierService.getSuppliers());
        return modelAndView;
    }


    /*
    Create
     */

    @RequestMapping("/create")
    public ModelAndView createModel(final Supplier supplier) {
        return new ModelAndView("supplier/create");
    }

    @RequestMapping(value="/supplier", method = RequestMethod.POST, params={"create"})
    public ModelAndView createAction(final Supplier supplier, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        try {
            supplierService.create(supplier);
        } catch (Exception e) {
            return createModel(supplier);
        }
        return new ModelAndView("redirect:/");
    }

    /*
    Update
     */

    @RequestMapping("/update/{id}")
    public ModelAndView updateModel(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("supplier/update");
        Supplier supplier = supplierService.read(id).getSupplier();
        mav.addObject("supplier", supplier);
        return mav;
    }

    @RequestMapping(value="/supplier", method = RequestMethod.POST, params={"update"})
    public ModelAndView updateAction(final Supplier supplier, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        try {
            supplierService.update(supplier);
        } catch (Exception e) {
            return updateModel(supplier.getId());
        }
        return new ModelAndView("redirect:/");
    }

    /*
    Delete
     */

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteModel(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("supplier/delete");
        Supplier supplier = supplierService.read(id).getSupplier();
        mav.addObject("supplier", supplier);
        return mav;
    }

    @RequestMapping(value="/supplier", method = RequestMethod.POST, params={"delete"})
    public ModelAndView deleteAction(final Supplier supplier, final BindingResult bindingResult) {
        supplierService.delete(supplier.getId());
        return new ModelAndView("redirect:/");
    }

    /*
    @ModelAttribute("suppliers")
    public List<Supplier> supplierList() {
        return supplierService.getSuppliers();
    }
    */
}
