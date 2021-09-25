package com.rabbitmq.test.rabbitmq_publisher_demo.controller;

import com.rabbitmq.test.rabbitmq_publisher_demo.configuration.MassagingConfiguration;
import com.rabbitmq.test.rabbitmq_publisher_demo.model.Employee;
import com.rabbitmq.test.rabbitmq_publisher_demo.service.EmployeeService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    Environment environment;
@GetMapping("/getemployeebyemployeeId/{empid}")
    public @ResponseBody
ResponseEntity getEmployeeByEmpId(@PathVariable int empid){
    HttpStatus httpStatus;
    Employee employee =new Employee();
    try {
        employee = employeeService.getEmpolyeeByEmployeeId(empid);
        rabbitTemplate.convertAndSend(environment.getProperty("rabbitmq.exchange"), environment.getProperty("rabbitmq.routingkey"), employee);
        httpStatus = HttpStatus.OK;
    }catch (Exception e){
        httpStatus = HttpStatus.BAD_REQUEST;
        e.printStackTrace();
    }
return ResponseEntity.status(httpStatus).body(employee);
}

}
