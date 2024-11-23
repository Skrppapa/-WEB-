package com.example.cars.Controllers;

import com.example.cars.Models.Car;
import com.example.cars.Models.Showroom;
import com.example.cars.Service.CarService;
import com.example.cars.Service.ShowroomService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.RequiredArgsConstructor;

import java.util.List;

// Контроллер - 1 уровень. Выполняет управляет потоками управления данных
// Этот 1 уровень использует методы реализованные в репозитории (3 ур.), которые, в свою очередь
// Вызываются в сервисах (2 ур.), а контроллер использует их через интерфейсы сервиса


//@ RestController Содержит ResponseBody - для автоматической сереализации в JSON любого объекта
@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;   // Car внедряется автоматически Spring - ом
    private final ShowroomService showroomService;

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("showrooms", showroomService.findAll());
        return "cars/create";
    }

    @PostMapping
    public String createCar(@Valid @ModelAttribute Car car, BindingResult result, @RequestParam Long showroomId, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cars/create";
        }
        Showroom showroom = showroomService.findById(showroomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid showroom Id:" + showroomId));
        car.setShowroom(showroom);
        carService.save(car);
        redirectAttributes.addFlashAttribute("successMessage", "Автомобиль успешно добавлен!");
        return "redirect:/cars";
    }

    @GetMapping
    public String listCars(@RequestParam(required = false) Long showroomId, Model model) {
        List<Car> cars = getCars(showroomId);
        model.addAttribute("cars", cars);
        model.addAttribute("showrooms", showroomService.findAll());
        return "cars/list";
    }

    private List<Car> getCars(Long showroomId) {
        if (showroomId != null && showroomId > 0) {
            return carService.findByShowroomIdAndSoldStatus(showroomId, false);
        }
        return carService.findBySoldStatus(false);
    }


    // Для форматирования отображения цены
    private String formatPrice(Double price) {
        if (price == null) {
            return "0 руб.";
        }
        return String.format("%,.0f руб.", price).replace(",", " ");
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Car car = carService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
        model.addAttribute("car", car);
        model.addAttribute("showrooms", showroomService.findAll());
        return "cars/edit";
    }

    @PostMapping("/{id}")
    public String updateCar(@PathVariable Long id, @Valid @ModelAttribute Car car, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cars/edit";
        }

        Car existingCar = carService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));

        BeanUtils.copyProperties(car, existingCar, "id", "showroom");  // Копирует свойства из одного объекта в другой

        if (car.getShowroom() != null) {
            Showroom showroom = showroomService.findById(car.getShowroom().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid showroom Id:" + car.getShowroom().getId()));
            existingCar.setShowroom(showroom);
        }

        existingCar.setFormattedPrice(formatPrice(existingCar.getPrice()));
        carService.save(existingCar);
        redirectAttributes.addFlashAttribute("successMessage", "Данные успешно обновлены!");
        return "redirect:/cars";
    }


    @GetMapping("/{id}/delete")
    public String deleteCar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        carService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Автомобиль успешно удален!");
        return "redirect:/cars";
    }
}
