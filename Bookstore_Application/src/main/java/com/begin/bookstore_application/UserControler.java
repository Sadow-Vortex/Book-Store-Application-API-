package com.begin.bookstore_application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserControler {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String login() {return "login";}

    @PostMapping("/login")
    public String login(Model model, @RequestParam String email,@RequestParam String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            model.addAttribute("error", "Invalid email");
            return "login";
        }
        User user = optionalUser.get();
        if(!user.getPassword().equals(password)) {
            model.addAttribute("error", "Wrong password");
            return "login";
        }
        return "/books/all";
    }

    @GetMapping("/bookstore")
    public String bookstore() {return "bookstore";}

    @GetMapping("/register")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/submit")
    public String submitForm(@ModelAttribute User user,
                             @RequestParam String confirmPassword,
                             Model model) {
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            return "register";
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            model.addAttribute("error", "Username is already exist");
            return "register";
        }
        userRepository.save(user);
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}
