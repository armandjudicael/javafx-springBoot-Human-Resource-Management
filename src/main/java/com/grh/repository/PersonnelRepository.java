package com.grh.repository;
import com.grh.entities.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PersonnelRepository extends JpaRepository<Personnel,Long>{
}
