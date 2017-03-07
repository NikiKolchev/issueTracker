package com.tracker.serviceImpl;

import com.tracker.entities.Issue;
import com.tracker.entities.enums.Priority;
import com.tracker.entities.enums.Status;
import com.tracker.model.bindingModels.IssueBindModel;
import com.tracker.model.bindingModels.IssueEditBindingModel;
import com.tracker.model.viewModels.IssueEditViewModel;
import com.tracker.model.viewModels.IssueViewModel;
import com.tracker.repository.IssueRepository;
import com.tracker.repository.UserRepository;
import com.tracker.service.IssueService;
import com.tracker.utils.parser.interfaces.ModelParser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class IssueServiceImpl implements IssueService {

    @Inject
    private IssueRepository issueRepository;

    @Inject
    private ModelParser modelParser;

    @Inject
    private UserRepository userRepository;

    @Override
    public List<IssueViewModel> findAllIssues() {
        List<Issue> issues = this.issueRepository.findAll();
        List<IssueViewModel> viewIssues = new ArrayList<>();

        for (Issue issue : issues) {
            IssueViewModel currentIssue = this.modelParser.convert(issue, IssueViewModel.class);
            currentIssue.setPriority(issue.getPriority().toString());
            currentIssue.setStatus(issue.getStatus().toString());
            viewIssues.add(currentIssue);
        }

        return viewIssues;
    }

    @Override
    public void create(IssueBindModel issueBindModel, String username) {
        Issue issue = this.modelParser.convert(issueBindModel, Issue.class);
        issue.setPriority(Priority.valueOf(issueBindModel.getPriority().toUpperCase()));
        issue.setStatus(Status.valueOf(issueBindModel.getStatus().toUpperCase()));
        issue.setCreationDate(new Date());
        issue.setAuthor(this.userRepository.findByUsername(username));
        this.issueRepository.create(issue);
    }

    @Override
    public void update(IssueEditBindingModel issueBindModel) {
        Issue issue = this.modelParser.convert(issueBindModel, Issue.class);
        issue.setPriority(Priority.valueOf(issueBindModel.getPriority().toUpperCase()));
        issue.setStatus(Status.valueOf(issueBindModel.getStatus().toUpperCase()));
        this.issueRepository.update(issue);
    }

    @Override
    public IssueEditViewModel getById(long id) {
        Issue issue = this.issueRepository.findById(id);
        IssueEditViewModel issueEditBindingModel = this.modelParser.convert(issue, IssueEditViewModel.class);
        issueEditBindingModel.setPriority(issue.getPriority().toString());
        issueEditBindingModel.setStatus(issue.getStatus().toString());

        return issueEditBindingModel;
    }
}
