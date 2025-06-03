package com.sbproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {
    // Controller indicates it can be web controller for receiving web traffic

    @RequestMapping("/")
    public String home(HttpSession session,Model model) {
        if (session.getAttribute("userName")!=null){
            System.out.println(session.getAttribute("userName")+" just entered index in.");
            model.addAttribute("userName", session.getAttribute("userName"));
        } // asd
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(name="error", required=false) String error,Model model) {
        if(error!=null){
            model.addAttribute("errorMsg", "Invalid Credentials");
        }
        model.addAttribute("user", new User());
        return "login";
    }
    @GetMapping("/logout")
    public String logoutPage(HttpSession session){
        String userName = (String) session.getAttribute("userName");
        session.invalidate(); 
        System.out.println(userName+" just logged out.");
        return "redirect:/";
    }

    @GetMapping("/contact")
    public String contactPage() {
        // aassas
        return "redirect:/login";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        model.addAttribute("currDateTime",currentDateTime.format(formatDateTime));
        return "about";
    }

    @Autowired
    private UserDAO userDAO;

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute User user, HttpSession session) {
        // Should add setup conn to DB and check table. Should have DB data class, DB Service class to get.
        // Hardcode below as chatgpt says lmao.
        User dbUsr = userDAO.getByUserName(user.getuserName());

        if (dbUsr!=null && dbUsr.getpassWord().equals(user.getpassWord())) {
            session.setAttribute("userName", user.getuserName());
            // System.out.println("User: " + user.getuserName());
            // System.out.println("Pass: " + user.getpassWord());
            // System.out.println("Login Success!");
            String urlString = "redirect:/dashboard/"+ user.getuserName();
            return urlString;
        } else {
            System.out.println("User: " + user.getuserName());
            System.out.println("Pass: " + user.getpassWord());
            System.out.println("Login Failed!");
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/dashboard/{userName}")
    public String dashboard(@PathVariable(required=false) String userName,HttpSession session, Model model) {
        String loggedInUser = (String) session.getAttribute("userName");
        System.out.println("loggedInUser: "+loggedInUser);

        if (loggedInUser==null || !loggedInUser.equals(userName)){
            session.invalidate();
            return "redirect:/login?unauthorized=true";
        }

        model.addAttribute("userName", userName);
        return "dashboard";
        }
}