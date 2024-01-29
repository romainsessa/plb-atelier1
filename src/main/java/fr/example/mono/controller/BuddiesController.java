package fr.example.mono.controller;

import fr.example.mono.configuration.SecurityConfiguration;
import fr.example.mono.dto.FriendDto;
import fr.example.mono.model.User;
import fr.example.mono.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BuddiesController {
    @Autowired
    private UserService userService;

    @GetMapping("/contact")
    public String contact(Model model) {
        String buddyEmail = new String();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        List<FriendDto> friendDtoList = userService.getFriendDtoList(email);
        model.addAttribute("buddyEmail", buddyEmail);
        model.addAttribute("friendList", friendDtoList);
        return "contact";
    }

    @PostMapping("/addBuddy")
    public String addBuddy(@ModelAttribute("buddyEmail") String buddyEmail, BindingResult result, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = SecurityConfiguration.getEmailFromAuthentication(authentication);
        User existingUser = userService.findUserByEmail(buddyEmail);
        if(existingUser == null) {
            result.rejectValue("email", "error.email",
                    "The user doesn't exist");
            return "redirect:/contact";
        } else {
            if(!userService.addBuddy(email, buddyEmail)) {
                result.rejectValue("email", "error.email", "The user is already your friend");
                return "redirect:/contact";
            }
        }
        return "redirect:/contact?success";
    }
}
