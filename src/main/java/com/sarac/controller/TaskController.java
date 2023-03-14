package com.sarac.controller;

import com.sarac.dto.TaskDTO;
import com.sarac.enums.Status;
import com.sarac.service.ProjectService;
import com.sarac.service.TaskService;
import com.sarac.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    public TaskController(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createTask(Model model){

        model.addAttribute("task",new TaskDTO());

        model.addAttribute("tasks",taskService.findAll());

        model.addAttribute("employees",userService.findEmployee());

        model.addAttribute("projects",projectService.findAll());

        return "/task/create";
    }

    @PostMapping("/create")
    public String insertTask(@ModelAttribute("task") TaskDTO taskDTO){

        taskService.save(taskDTO);

        return "redirect:/task/create";
    }

    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId")Long taskId){

        taskService.deleteById(taskId);
        return "redirect:/task/create";
    }

    @GetMapping("/update/{taskId}")
    public String editTask(@PathVariable("taskId")Long taskId, Model model){

        model.addAttribute("task",taskService.findById(taskId));

        model.addAttribute("tasks",taskService.findAll());

        model.addAttribute("projects",projectService.findAll());

        model.addAttribute("employees",userService.findEmployee());

        return "/task/update";
    }
    @PostMapping("/update/{id}")
    public String updateTask(TaskDTO taskDTO){

        taskService.update(taskDTO);

        return "redirect:/task/create";
    }

    @GetMapping("/employee/pending-tasks")
    public String employeePendingTask(Model model){

        model.addAttribute("tasks",taskService.findAllTaskByStatusIsNot(Status.COMPLETE));
        return "/task/pending-tasks";
    }
    @GetMapping("/employee/archive")
    public String employeeArchivedTasks(Model model){

      model.addAttribute("tasks",taskService.findAllTaskByStatus(Status.COMPLETE));

        return "/task/archive";
    }
    @GetMapping("/employee/edit/{id}")
    public String employeestatusUpdate(@PathVariable Long id,Model model){

        model.addAttribute("task",taskService.findById(id));
//        model.addAttribute("projects",projectService.findAll());
//        model.addAttribute("employees",userService.findEmployee());

        model.addAttribute("statuses",Status.values());
        model.addAttribute("tasks",taskService.findAllTaskByStatusIsNot(Status.COMPLETE));
        return "/task/status-update";
    }
    @PostMapping("/employee/update/{id}")
    public String employeeUpdateTask(TaskDTO task){
        taskService.updateStatus(task);
        return "redirect:/task/employee/pending-tasks";
    }
}
