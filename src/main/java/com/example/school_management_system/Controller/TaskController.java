package com.example.school_management_system.Controller;

import com.example.school_management_system.Entity.Task;
import com.example.school_management_system.Entity.User;
import com.example.school_management_system.Service.TaskService;
import com.example.school_management_system.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("tasks", taskService.getTasksByUser_Username(username));
        return "task-list";
    }

    @GetMapping("/new")
    public String newTask(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task task, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        task.setUser(user);

        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id, Principal principal) {
        Task task = taskService.getTaskById(id);

        if (task != null && task.getUser().getUsername().equals(principal.getName())) {
            taskService.deleteTask(id);
        }

        return "redirect:/tasks";
    }

    @GetMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable("id") Long id, Principal principal) {
        Task task = taskService.getTaskById(id);

        if (task != null && task.getUser().getUsername().equals(principal.getName())) {
            task.setCompleted(!task.isCompleted());
            taskService.saveTask(task);
        }

        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable("id") Long id, Model model, Principal principal) {
        Task task = taskService.getTaskById(id);

        if (task != null && task.getUser().getUsername().equals(principal.getName())) {
            model.addAttribute("task", task);
            return "task-form";
        }

        return "redirect:/tasks";
    }
}