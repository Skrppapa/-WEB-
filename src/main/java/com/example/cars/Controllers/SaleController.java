package com.example.cars.Controllers;

import com.example.cars.Models.Car;
import com.example.cars.Models.Sale;
import com.example.cars.Service.*;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/sales")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')") // Доступно только пользователю с ролью ADMIN
public class SaleController {

    private final SaleService saleService;
    private final CarService carService;
    private final CustomerService customerService;
    private final ShowroomService showroomService;
    private final SellerService sellerService;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("sale", new Sale());
        model.addAttribute("cars", carService.findBySoldStatus(false)); // Только непроданные автомобили
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("showrooms", showroomService.findAll());
        model.addAttribute("sellers", sellerService.findAll());
        return "sales/create";
    }

    @PostMapping
    public String createSale(@Valid @ModelAttribute Sale sale, BindingResult result, RedirectAttributes redirectAttributes) {
        if (sale.getSaleDate() == null) {
            sale.setSaleDate(new Date());
        }

        // Получаем автомобиль, который продается
        Car car = carService.findById(sale.getCar().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + sale.getCar().getId()));
        car.setSold(true);
        carService.save(car); // Сохраняем изменения в автомобиле

        // Заполняем остальные данные для продажи
        populateSaleDetails(sale);

        saleService.save(sale);
        redirectAttributes.addFlashAttribute("successMessage", "Продажа успешно добавлена!");
        return "redirect:/sales";
    }

    @GetMapping
    public String listSales(@RequestParam(required = false) Long showroomId, Model model) {
        List<Sale> sales = (showroomId != null && showroomId > 0)
                ? saleService.findByShowroomId(showroomId)
                : saleService.findAll(); // Все Продажи

        sales.forEach(sale -> sale.setFormattedPrice(formatPrice(sale.getSalePrice()))); // Форматируем цену
        model.addAttribute("showrooms", showroomService.findAll());
        model.addAttribute("sales", sales);
        return "sales/list";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Sale sale = saleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sale Id:" + id));
        model.addAttribute("sale", sale);
        model.addAttribute("cars", carService.findBySoldStatus(false)); // Только непроданные автомобили
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("showrooms", showroomService.findAll());
        model.addAttribute("sellers", sellerService.findAll());
        return "sales/edit";
    }

    @PostMapping("/{id}/update")
    public String updateSale(@PathVariable("id") Long id, @Valid @ModelAttribute Sale sale, BindingResult result, RedirectAttributes redirectAttributes) {
        if (sale.getSaleDate() == null) {
            sale.setSaleDate(new Date());
        }

        sale.setId(id);
        populateSaleDetails(sale);
        saleService.save(sale);
        redirectAttributes.addFlashAttribute("successMessage", "Данные успешно обновлены!");
        return "redirect:/sales";
    }

    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        saleService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Продажа успешно удалена!");
        return "redirect:/sales";
    }

    // Помогалка для заполнения данных о продаже
    private void populateSaleDetails(Sale sale) {
        sale.setCar(carService.findById(sale.getCar().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + sale.getCar().getId())));
        sale.setCustomer(customerService.findById(sale.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + sale.getCustomer().getId())));
        sale.setShowroom(showroomService.findById(sale.getShowroom().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid showroom Id:" + sale.getShowroom().getId())));
        sale.setSeller(sellerService.findById(sale.getSeller().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid seller Id:" + sale.getSeller().getId())));
    }

    // Для форматирования цены
    private String formatPrice(Double price) {
        return String.format("%,.0f руб.", price != null ? price : 0).replace(",", " ");
    }
}
