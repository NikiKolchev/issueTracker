package com.tracker.repository;


import com.tracker.entities.Issue;

import java.util.List;

public interface IssueRepository {

    void create(Issue issue);

    void delete(long id);

    List<Issue> findAll();

    Issue findById(long id);

    void update(Issue issue);

}
