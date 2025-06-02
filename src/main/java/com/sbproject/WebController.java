package com.sbproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {
    // Controller indicates it can be web controller for receiving web traffic

    @RequestMapping("/")
    public String home() {
        System.out.println("indexPage");
        System.out.println();
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute User user, HttpSession session) {
        // Should add setup conn to DB and check table. Should have DB data class, DB Service class to get.
        // Hardcode below as chatgpt says lmao.
        if ("admin".equals(user.getuserName()) && "1234".equals(user.getpassWord())) {
            session.setAttribute("username", user.getuserName());
            System.out.println("User: " + user.getuserName());
            System.out.println("Pass: " + user.getpassWord());
            System.out.println("Login Success!");
            return "redirect:/dashboard";
        } else {
            System.out.println("User: " + user.getuserName());
            System.out.println("Pass: " + user.getpassWord());
            System.out.println("Login Failed!");
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }

}