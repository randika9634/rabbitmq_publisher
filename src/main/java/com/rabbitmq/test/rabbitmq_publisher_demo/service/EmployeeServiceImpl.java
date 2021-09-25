package com.rabbitmq.test.rabbitmq_publisher_demo.service;

import com.rabbitmq.test.rabbitmq_publisher_demo.model.Employee;
import com.rabbitmq.test.rabbitmq_publisher_demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public Employee getEmpolyeeByEmployeeId(int id) {
        return employeeRepository.findFirstByEmployeeId(id);
    }
}
