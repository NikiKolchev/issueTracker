package com.tracker.model.bindingModels;


import com.tracker.entities.enums.Role;

import java.util.List;

public class LoggedUserModel {

    private String username;

    private Role role;

    private List<IssueEditBindingModel> issues;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<IssueEditBindingModel> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueEditBindingModel> issues) {
        this.issues = issues;
    }
}
