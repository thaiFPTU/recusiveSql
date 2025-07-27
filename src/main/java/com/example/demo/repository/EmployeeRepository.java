package com.example.demo.repository;

import com.example.demo.dto.response.EmployeeResponse;
import com.example.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query(value = """
WITH employee_table(id,name,manager_id,designation) as
(
select e.id,e.name,e.manager_id,e.designation from employee e where e.id = :employeeId
UNION ALL
 select s.id,s.name,s.manager_id,s.designation from employee s join employee_table et on s.manager_id = et.id
)
SELECT * FROM employee_table
""" ,nativeQuery = true)
    List<EmployeeResponse> findAllEmployeeInPath(@Param("employeeId") Long employeeId);

    Employee findEmployeeById(Long id);
}
