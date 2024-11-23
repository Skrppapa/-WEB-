package com.example.cars.Controllers;

import com.example.cars.Models.Customer;
import com.example.cars.Service.CustomerService;
import com.example.cars.Service.ShowroomService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final ShowroomService showroomService;

    public CustomerController(CustomerService customerService, ShowroomService showroomService) {
        this.customerService = customerService;
        this.showroomService = showroomService;
    }

    private void addShowroomsToModel(Model model) {
        model.addAttribute("showrooms", showroomService.findAll());  // Для добавление списка в модель
    }

    // Объедененный меиод валидации
    private String handleValidationErrors(BindingResult result, Model model, String viewName) {
        if (result.hasErrors()) {
            addShowroomsToModel(model);
            return viewName;
        }
        return null;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        addShowroomsToModel(model);
        return "customers/create";
    }

    @PostMapping
    public String createCustomer(@Valid @ModelAttribute Customer customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        String errorView = handleValidationErrors(result, model, "customers/create");
        if (errorView != null) {
            return errorView;
        }
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Покупатель успешно добавлен!");
        return "redirect:/customers";
    }

    @GetMapping
    public String listCustomers(@RequestParam(required = false) Long showroomId, Model model) {
        List<Customer> customers;

        if (showroomId != null && showroomId > 0) {
            customers = customerService.findByShowroomId(showroomId); // Клиенты из указанного автосалона
        } else {
            customers = customerService.findAll(); // Все Клиенты
        }

        addShowroomsToModel(model);
        model.addAttribute("customers", customers);
        return "customers/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        addShowroomsToModel(model);
        return "customers/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable Long id, @Valid @ModelAttribute Customer customer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        String errorView = handleValidationErrors(result, model, "customers/edit");
        if (errorView != null) {
            return errorView;
        }
        customer.setId(id);
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Данные успешно обновлены!");
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        customerService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Покупатель успешно удален!");
        return "redirect:/customers";
    }
}
