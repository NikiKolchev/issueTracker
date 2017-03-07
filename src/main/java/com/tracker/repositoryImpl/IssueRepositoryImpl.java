package com.tracker.repositoryImpl;

import com.tracker.entities.Issue;
import com.tracker.repository.IssueRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class IssueRepositoryImpl implements IssueRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Issue issue) {
        this.entityManager.persist(issue);
    }

    @Override
    public void delete(long id) {
        Query query = this.entityManager.createQuery(
          "DELETE FROM Issue AS i WHERE i.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Issue> findAll() {
        Query query = this.entityManager.createQuery("SELECT i FROM Issue AS i");
        List<Issue> issues = query.getResultList();

        return issues;
    }

    @Override
    public Issue findById(long id) {
        return this.entityManager.find(Issue.class, id);
    }

    @Override
    public void update(Issue issue) {
        this.entityManager.merge(issue);
    }
}
