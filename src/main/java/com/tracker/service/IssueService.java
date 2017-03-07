package com.tracker.service;


import com.tracker.model.bindingModels.IssueBindModel;
import com.tracker.model.bindingModels.IssueEditBindingModel;
import com.tracker.model.viewModels.IssueEditViewModel;
import com.tracker.model.viewModels.IssueViewModel;

import java.util.List;

public interface IssueService {

    List<IssueViewModel> findAllIssues();

    void create(IssueBindModel issueBindModel, String username);

    void update(IssueEditBindingModel issueBindModel);

    IssueEditViewModel getById(long id);
}
