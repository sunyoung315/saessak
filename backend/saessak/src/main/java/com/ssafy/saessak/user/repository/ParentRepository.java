package com.ssafy.saessak.user.repository;

import com.ssafy.saessak.user.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    Optional<Parent> findByParentEmailAndParentName(String parentEmail, String parentName);

//    Optional<Parent> findBySocialId(Long socialId);

}
