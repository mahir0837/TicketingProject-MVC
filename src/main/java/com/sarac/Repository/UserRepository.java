package com.sarac.Repository;

import com.sarac.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User>findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);
    User findByUserNameAndIsDeleted(String userName,Boolean deleted);
    List<User>findByRoleDescriptionIgnoreCaseAndIsDeleted(String description,Boolean deleted);
}
