package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.Repository.EmployeesRepo;
import com.thoughtworks.springbootemployee.model.Employees;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeesTest {

    EmployeesRepo repository = Mockito.mock(EmployeesRepo.class);

    @Test
    void shoul_return_all_employess_when_getallemployees_given_all_employess() {
        //given
        List<Employees> employeesList = new ArrayList<>();

        employeesList.add(new Employees(1, "Angelo", 23, "male", 1000, 1));
        employeesList.add(new Employees(2, "Angela", 26, "female", 900, 1));

        when(repository.getAllEmployees()).thenReturn(employeesList);
        EmployeeService service = new EmployeeService(repository);

        //when
        List<Employees> actualEmployess = service.getEmployeesList();

        assertEquals(actualEmployess.size(), employeesList.size());
        assertEquals(actualEmployess, employeesList);
    }


    @Test
    void shoul_return_1_employess_when_add_employee_given_1_employee() {
        //given
        Employees employee = new Employees(1, "Leo", 18, "male", 1000 , 1);
        EmployeeService service = new EmployeeService(repository);
        when(repository.add(employee)).thenReturn(employee);
        //when
        Employees actual = service.create(employee);
        //then
        assertEquals(1, actual.getId());
    }

    @Test
    void should_get_employee_when_get_by_id_given_employee_id() {
        //given
        Employees employee = new Employees(1, "Leo", 18, "male", 1000 , 1);
        when(repository.getEmployeeById(employee.getId())).thenReturn(employee);
        EmployeeService service = new EmployeeService(repository);
        //when
        Employees actual = service.findByID(employee.getId());
        //then
        assertEquals(employee.getId(), actual.getId());
    }

}