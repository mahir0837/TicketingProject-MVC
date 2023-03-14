package com.sarac.controller;
import com.sarac.dto.UserDTO;
import com.sarac.service.UserService;
import com.sarac.service.impl.RoleServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public String insertUser(@Valid @ModelAttribute("user") UserDTO user,BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){

            model.addAttribute("users",userService.findAll());

            model.addAttribute("roles",roleService.findAll());
            return "/user/create";
        }
        userService.save(user);

        return "redirect:/user/create";
    }
    @GetMapping("/update/{username}")
    public String editUser(@PathVariable("username") String username, Model model){

        model.addAttribute("user",userService.findById(username));

        model.addAttribute("roles",roleService.findAll());

        model.addAttribute("users",userService.findAll());

        return "/user/update";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") UserDTO userDTO,BindingResult bindingResult,Model model){

        if (bindingResult.hasErrors()){

            model.addAttribute("users",userService.findAll());

            model.addAttribute("roles",roleService.findAll());
            return "/user/create";
        }
        userService.update(userDTO);

        return "redirect:/user/create";
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username){

        userService.deleteById(username);
        return "redirect:/user/create";
    }

}
