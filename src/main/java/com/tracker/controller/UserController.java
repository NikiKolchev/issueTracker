package com.tracker.controller;

import com.mvcFramework.annotations.controller.Controller;
import com.mvcFramework.annotations.parameters.ModelAttribute;
import com.mvcFramework.annotations.request.GetMapping;
import com.mvcFramework.annotations.request.PostMapping;
import com.mvcFramework.models.Model;
import com.tracker.model.bindingModels.LoggedUserModel;
import com.tracker.model.bindingModels.LoginUserModel;
import com.tracker.model.bindingModels.RegisterUserModel;
import com.tracker.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Stateless
@Controller
public class UserController {

    @Inject
    private UserService userService;

    @GetMapping("/register")
    public String getRegisterPage() {
        return "/templates/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegisterUserModel registerUserModel, Model model){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<RegisterUserModel>> constraint = validator.validate(registerUserModel);
        List<String> errors= new ArrayList<>();

        for (ConstraintViolation<RegisterUserModel> violation : constraint) {
            errors.add(violation.getMessage());
        }

        if(!registerUserModel.getPassword().equals(registerUserModel.getConfirmPassword())) {
            errors.add("Password mismatch!");
        }

        RegisterUserModel foundUser = this.userService.findByUsername(registerUserModel.getUsername());
        if(foundUser != null) {
            errors.add("Username exist!");
        }

        if(errors.size() > 0) {
            model.addAttribute("error", errors);
        }

        this.userService.create(registerUserModel);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/templates/login";
    }

    @PostMapping("/login")
    public String loginUser(HttpSession session,@ModelAttribute LoginUserModel login, Model model) {
        LoggedUserModel foundUser = this.userService.findByUsernameAndPassword(login.getUsername(), login.getPassword());
        if(foundUser == null) {
            model.addAttribute("error", "Invalid user");
            return "/templates/login";
        }

        session.setAttribute("user", foundUser);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String getLogOut(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
