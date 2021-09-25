package com.rabbitmq.test.rabbitmq_publisher_demo.repository;

import com.rabbitmq.test.rabbitmq_publisher_demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
    Employee findFirstByEmployeeId(int empid);
}
