package com.tracker.controller;

import com.mvcFramework.annotations.controller.Controller;
import com.mvcFramework.annotations.parameters.ModelAttribute;
import com.mvcFramework.annotations.parameters.PathVariable;
import com.mvcFramework.annotations.request.GetMapping;
import com.mvcFramework.annotations.request.PostMapping;
import com.mvcFramework.models.Model;
import com.tracker.model.bindingModels.IssueBindModel;
import com.tracker.model.bindingModels.IssueEditBindingModel;
import com.tracker.model.bindingModels.LoggedUserModel;
import com.tracker.model.viewModels.IssueEditViewModel;
import com.tracker.model.viewModels.IssueViewModel;
import com.tracker.service.IssueService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.List;

@Stateless
@Controller
public class IssueController {

    @Inject
    private IssueService issueService;

    @GetMapping("/issues")
    public String getIssuePage(Model model){
        List<IssueViewModel> issueViewModels = this.issueService.findAllIssues();
        model.addAttribute("issueViewModels", issueViewModels);

        return "/templates/issues";
    }


    @GetMapping("/issues/add")
    public String getAddIssuePage(Model model){
        return "/templates/add-issue";
    }

    @PostMapping("/issues/add")
    public String addIssue(@ModelAttribute IssueBindModel issueBindingModel, HttpSession session){
        LoggedUserModel loggedUserModel = (LoggedUserModel) session.getAttribute("user");
        String username = loggedUserModel.getUsername();
        this.issueService.create(issueBindingModel, username);
        return "redirect:/issues";
    }

    @GetMapping("/issues/edit/{id}")
    public String getEditIssuePage(@PathVariable("id") long id, Model model){
        IssueEditViewModel issueEditViewModel = this.issueService.getById(id);
        model.addAttribute("issue", issueEditViewModel);
        return "/templates/edit-issue";
    }

    @PostMapping("/issues/edit/{id}")
    public String editIssue(@PathVariable("id") long id, @ModelAttribute IssueEditBindingModel issueBindingModel){
        issueBindingModel.setId(id);
        issueService.update(issueBindingModel);
        return "redirect:/issues";
    }
}
