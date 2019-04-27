package com.lydl.store.controller;

import com.lydl.store.dao.DepartmentDao;
import com.lydl.store.dao.EmployeeDao;
import com.lydl.store.entity.Department;
import com.lydl.store.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping()
public class IndexController {
    @RequestMapping("/")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/index")
    public String toindex(){
        return "index";
    }

    //@RequestMapping(value = "/login",method = RequestMethod.POST)这是之前的写法,下面的用法和这个等同
    @PostMapping("/login")
    public String toIndex(@RequestParam("username") String username, @RequestParam("password") String password,
                          Map<String,Object> map){

        if(!StringUtils.isEmpty(username)&& "123456".equals(password)){
            return "index";
        }else {
            map.put("msg","用户名密码错误");
            return "login";
        }

    }

    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;
    @RequestMapping("/list")
    public String toList(Model model){
        Collection<Employee> employees = employeeDao.getAll();
        model.addAttribute("lists",employees);
        return "emp/list";
    }

    @RequestMapping("/add")
    public String toAddPage(Model model){
        Collection<Department> departments= departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    @PostMapping("/addemp")
    public String addDepart(Employee employee){
//        System.out.println(employee);
        employeeDao.save(employee);
        return "redirect:/list";
    }

    @GetMapping("/update/{id}")
    public String updateDepart(@PathVariable("id") Integer id, Model model){
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);
        //显示所有的部门列表
        Collection<Department> departments= departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }

    /**
     * 员工修改:需要提交员工id
     * @param employee
     * @return
     */
    @PutMapping("/addemp")
    public String updateEmployee(Employee employee){
        System.out.println("修改的员工数据:"+employee);
        employeeDao.save(employee);
        return "redirect:/list";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/list";
    }
}

