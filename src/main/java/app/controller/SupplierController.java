package app.controller;

import app.model.Place;
import app.model.Supplier;
import app.model.api.SupplierPlaceGetRes;
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
@RequestMapping(path = {"/supplier"})
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    /*
    Index
     */

    @RequestMapping(path = {""})
    public ModelAndView list(Model model) {
        ModelAndView modelAndView = new ModelAndView("supplier/index");
        modelAndView.addObject("suppliers", supplierService.getSuppliers());
        return modelAndView;
    }


    /*
    Create
     */

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createModel(final Supplier supplier) {
        return new ModelAndView("supplier/create");
    }

    @RequestMapping(value="/create", method = RequestMethod.POST, params={"create"})
    public ModelAndView createAction(final Supplier supplier, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        try {
            supplierService.create(supplier);
        } catch (Exception e) {
            e.printStackTrace();
            return createModel(supplier);
        }
        return new ModelAndView("redirect:/supplier");
    }

    /*
    Update
     */

    @RequestMapping("/update/{supplierId}")
    public ModelAndView updateModel(@PathVariable Long supplierId) {
        ModelAndView mav = new ModelAndView("supplier/update");
        Supplier supplier = supplierService.read(supplierId).getSupplier();
        mav.addObject("supplier", supplier);
        return mav;
    }

    @RequestMapping(value="/update", method = RequestMethod.POST, params={"update"})
    public ModelAndView updateAction(final Supplier supplier, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        try {
            supplierService.update(supplier);
        } catch (Exception e) {
            return updateModel(supplier.getId());
        }
        return new ModelAndView("redirect:/supplier");
    }

    /*
    Delete
     */

    @RequestMapping("/delete/{supplierId}")
    public ModelAndView deleteModel(@PathVariable Long supplierId) {
        ModelAndView mav = new ModelAndView("supplier/delete");
        Supplier supplier = supplierService.read(supplierId).getSupplier();
        mav.addObject("supplier", supplier);
        return mav;
    }

    @RequestMapping(value="/delete", method = RequestMethod.POST, params={"delete"})
    public ModelAndView deleteAction(final Supplier supplier, final BindingResult bindingResult) {
        supplierService.delete(supplier.getId());
        return new ModelAndView("redirect:/supplier");
    }

    /*
    @ModelAttribute("suppliers")
    public List<Supplier> supplierList() {
        return supplierService.getSuppliers();
    }
    */

    /*
     */


    /*
    supplier place list
    model
     */

    @RequestMapping("/{supplierId}/place")
    public ModelAndView supplierPlaceModel(@PathVariable Long supplierId) {
        ModelAndView mav = new ModelAndView("place/index");
        Supplier supplier = supplierService.read(supplierId).getSupplier();
        mav.addObject("supplier", supplier);
        return mav;
    }

    /*
    update supplier place
    model
     */

    @RequestMapping("/{supplierId}/place/{placeId}/update")
    public ModelAndView updatePlaceModel(@PathVariable Long supplierId, @PathVariable Long placeId) {
        ModelAndView mav = new ModelAndView("place/update");
        SupplierPlaceGetRes placeRes = supplierService.getSupplierPlaces(supplierId, placeId);
        mav.addObject("supplierId", supplierId);
        mav.addObject("place", placeRes.getPlace());
        return mav;
    }

    @RequestMapping(value="/{supplierId}/place/update", method = RequestMethod.POST, params={"update"})
    public ModelAndView updatePlaceAction(@PathVariable Long supplierId, final Place place, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        try {
            supplierService.updatePlace(supplierId, place);
        } catch (Exception e) {
            return updatePlaceModel(supplierId, place.getId());
        }
        return new ModelAndView("redirect:/supplier/" + supplierId + "/place");
    }


    /*
    create new place
     */

    @RequestMapping(value = "/{supplierId}/place/create", method = RequestMethod.GET)
    public ModelAndView createPlaceModel(@PathVariable Long supplierId, final Place place) {
        ModelAndView modelAndView = new ModelAndView("place/create");
        modelAndView.addObject("supplierId", supplierId);
        return modelAndView;
    }

    @RequestMapping(value="/{supplierId}/place/create", method = RequestMethod.POST, params={"create"})
    public ModelAndView createPlaceAction(@PathVariable Long supplierId, final Place place, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        try {
            supplierService.createPlace(supplierId, place);
        } catch (Exception e) {
            e.printStackTrace();
            return createPlaceModel(supplierId, place);
        }
        return new ModelAndView("redirect:/supplier/" + supplierId + "/place");
    }

    @RequestMapping(value = "/{supplierId}/place/{placeId}/delete", method = RequestMethod.GET)
    public ModelAndView deletePlaceModel(@PathVariable Long supplierId, @PathVariable Long placeId) {
        ModelAndView modelAndView = new ModelAndView("place/delete");
        modelAndView.addObject("supplierId", supplierId);
        modelAndView.addObject("place", supplierService.getSupplierPlaces(supplierId, placeId).getPlace());
        return modelAndView;
    }

    @RequestMapping(value="/{supplierId}/place/delete", method = RequestMethod.POST, params={"delete"})
    public ModelAndView deletePlaceAction(@PathVariable Long supplierId, final Place place, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        try {
            supplierService.deletePlace(supplierId, place.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return createPlaceModel(supplierId, place);
        }
        return new ModelAndView("redirect:/supplier/" + supplierId + "/place");
    }
}
