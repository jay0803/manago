package com.manago.repository;
import com.manago.vo.DepartmentVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentVO, Integer> { }
