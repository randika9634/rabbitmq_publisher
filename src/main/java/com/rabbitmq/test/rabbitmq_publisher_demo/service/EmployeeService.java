package com.rabbitmq.test.rabbitmq_publisher_demo.service;

import com.rabbitmq.test.rabbitmq_publisher_demo.model.Employee;

public interface EmployeeService {
    Employee getEmpolyeeByEmployeeId(int id);
}
