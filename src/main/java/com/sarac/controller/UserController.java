package com.sarac.controller;
import com.sarac.dto.UserDTO;
import com.sarac.service.UserService;
import com.sarac.service.impl.RoleServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final RoleServiceImpl roleService;
    private final UserService userService;

    public UserController(RoleServiceImpl roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createUser( Model model){

        model.addAttribute("user",new UserDTO());

        model.addAttribute("roles",roleService.findAll());

        model.addAttribute("users",userService.findAll());

        return "user/create";
    }

    @PostMapping("/create")
    public String insertUser(@ModelAttribute("user") UserDTO user ){

        userService.save(user);

        return "redirect:/user/create";
    }
}
