package com.example.cars.Controllers;

import com.example.cars.Models.MyUser;
import com.example.cars.Service.MyUserDetaleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
// @RestController   // Содержит ResponseBody - для автоматической сереализации в JSON любого объекта
@RequestMapping("/")
public class HomeController {

    private final MyUserDetaleService service;

    public HomeController(MyUserDetaleService service) {
        this.service = service;
    }

    // Страница для любого вида пользователя TODO
    @GetMapping("/welcome")
    public String welcome() {
        return "Для возможности пользоваться сервисом пройдите регистрацию";
    }


    @GetMapping("/")
    public String home() {
        return "home";
    }


    @PostMapping("/new-user")
    public String newUser(@RequestBody MyUser user) {
        service.newUser(user);
        return "Пользователь успешно добавлен";
    }
}