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

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @RequestMapping("/list")
    public String list(Model model) {
        return "suppliers";
    }

    @RequestMapping("/add")
    public String add(final Supplier supplier) {
        return "add";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("update");
        Supplier supplier = supplierService.get(id).getSupplier();
        mav.addObject("supplier", supplier);
        return mav;
    }

    @RequestMapping(value="/supplier", method = RequestMethod.POST, params={"add"})
    public String addRow(final Supplier supplier, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        System.out.println(supplier);
        supplierService.add(supplier);
        return "redirect:/list";
    }

    @RequestMapping(value="/supplier", method = RequestMethod.POST, params={"modify"})
    public String modify(final Supplier supplier, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        System.out.println(supplier);
        supplierService.update(supplier);
        return "redirect:/list";
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @ModelAttribute("suppliers")
    public List<Supplier> supplierList() {
        return supplierService.getSuppliers();
    }
}
