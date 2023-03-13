package com.sarac.controller;
import com.sarac.dto.ProjectDTO;
import com.sarac.enums.Status;
import com.sarac.service.ProjectService;
import com.sarac.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;

    public ProjectController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public String createProject(Model model){

        model.addAttribute("project", new ProjectDTO());

        model.addAttribute("managers", userService.findManager());

        model.addAttribute("projectList" , projectService.findAll());

        return "/project/create";
    }

    @PostMapping("/create")
    public String insertProject(@ModelAttribute("project") ProjectDTO project){


        projectService.save(project);

        return "redirect:/project/create";
    }

    @GetMapping("/delete/{projectCode}")
   public String deleteProject(@PathVariable("projectCode") String projectCode){

        projectService.deleteById(projectCode);
        return "redirect:/project/create";
   }

   @GetMapping("/complete/{projectCode}")
   public String completeProject(@PathVariable("projectCode") String projectCode){

        projectService.complete(projectService.findById(projectCode));

        return "redirect:/project/create";
   }

    @GetMapping("/update/{projectCode}")
    public String editProject(@PathVariable("projectCode") String projectCode, Model model){

        model.addAttribute("project",projectService.findById(projectCode));

        model.addAttribute("managers", userService.findManager());

        model.addAttribute("projects",projectService.findAll());

        return "/project/update";
    }

    @PostMapping("/update")
    public String updateProject(@ModelAttribute("project") ProjectDTO projectDTO){

       projectService.save(projectDTO);

        return "redirect:/project/create";
    }
}
