package com.example.cars.Controllers;

import com.example.cars.Models.Seller;
import com.example.cars.Service.SellerService;
import com.example.cars.Service.ShowroomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;
    private final ShowroomService showroomService;

    @Autowired
    public SellerController(SellerService sellerService, ShowroomService showroomService) {
        this.sellerService = sellerService;
        this.showroomService = showroomService;
    }

    private void addCommonModelAttributes(Model model) {
        model.addAttribute("showrooms", showroomService.findAll());
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("seller", new Seller());
        addCommonModelAttributes(model);
        return "sellers/create";
    }

    @PostMapping
    public String createSeller(@Valid @ModelAttribute Seller seller, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addCommonModelAttributes(model);
            return "sellers/create";
        }
        sellerService.save(seller);
        redirectAttributes.addFlashAttribute("successMessage", "Менеджер успешно добавлен!");
        return "redirect:/sellers";
    }

    @GetMapping
    public String listSellers(@RequestParam(required = false) Long showroomId, Model model) {
        List<Seller> sellers;

        if (showroomId != null && showroomId > 0) {
            sellers = sellerService.findByShowroomId(showroomId); // Манагеры из указанного автосалона
        } else {
            sellers = sellerService.findAll(); // Все манагеры
        }

        for (Seller seller : sellers) {
            seller.setFormattedSalary(formatSalary(seller.getSalary()));
        }

        addCommonModelAttributes(model);
        model.addAttribute("sellers", sellers);
        return "sellers/list";
    }

    private String formatSalary(Double salary) {
        if (salary == null) {
            return "0 руб.";
        }
        return String.format("%,.0f руб.", salary).replace(",", " ");
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Seller seller = sellerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid seller Id:" + id));
        model.addAttribute("seller", seller);
        addCommonModelAttributes(model);
        return "sellers/edit";
    }

    @PostMapping("/{id}")
    public String updateSeller(@PathVariable Long id, @Valid @ModelAttribute Seller seller, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addCommonModelAttributes(model);
            return "sellers/edit";
        }
        seller.setId(id);
        sellerService.save(seller);
        redirectAttributes.addFlashAttribute("successMessage", "Данные успешно обновлены!");
        return "redirect:/sellers";
    }

    @GetMapping("/{id}/delete")
    public String deleteSeller(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        sellerService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Менеджер успешно удален!");
        return "redirect:/sellers";
    }
}
