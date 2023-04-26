package com.sarac.Repository;

import com.sarac.entity.Project;
import com.sarac.entity.User;
import com.sarac.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProjectRepository extends JpaRepository<Project,Long> {

    Project findByProjectCode(String code);
    List<Project>findAllByAssignedManager(User manager);
    List<Project> findAllByProjectStatusIsNotAndAssignedManager(Status complete, User convertToEntity);

}
