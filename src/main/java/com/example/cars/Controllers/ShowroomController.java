package com.example.cars.Controllers;

import com.example.cars.Models.Showroom;
import com.example.cars.Service.ShowroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/showrooms")
@RequiredArgsConstructor
public class ShowroomController {

    private final ShowroomService showroomService;

    @GetMapping
    public String listShowrooms(Model model) {
        model.addAttribute("showrooms", showroomService.findAll());
        return "showrooms/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("showroom", new Showroom());
        return "showrooms/create";
    }

    @PostMapping
    public String createShowroom(@Valid @ModelAttribute Showroom showroom, BindingResult result, RedirectAttributes redirectAttributes) {
        return handleShowroom(result, showroom, redirectAttributes, "create");
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Showroom showroom = showroomService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid showroom Id:" + id));
        model.addAttribute("showroom", showroom);
        return "showrooms/edit";
    }

    @PostMapping("/{id}")
    public String updateShowroom(@PathVariable Long id, @Valid @ModelAttribute Showroom showroom, BindingResult result, RedirectAttributes redirectAttributes) {
        showroom.setId(id);
        return handleShowroom(result, showroom, redirectAttributes, "edit");
    }

    private String handleShowroom(BindingResult result, Showroom showroom, RedirectAttributes redirectAttributes, String view) {
        if (result.hasErrors()) {
            return "showrooms/" + view;
        }
        showroomService.save(showroom);
        redirectAttributes.addFlashAttribute("successMessage", "Данные успешно " + (view.equals("create") ? "добавлены!" : "обновлены!"));
        return "redirect:/showrooms";
    }

    @GetMapping("/{id}/delete")
    public String deleteShowroom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        showroomService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Автосалон успешно удален!");
        return "redirect:/showrooms";
    }
}
