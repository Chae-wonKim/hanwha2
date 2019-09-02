package com.hanwha.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hanwha.model.DeptDAO_mybatis;
import com.hanwha.model.DeptDTO;
import com.hanwha.model.EmpDAO;
import com.hanwha.model.EmpService;
import com.hanwha.model.EmpVO;

//<bean id="myController", class="" > �̰Ͱ� ���� annotation
@Controller
public class MyController {
	//autowired���� �ڵ����� new() ���ο� ��ü�� �����Ǽ� ���´�. >�̰Ͱ� ���� annotation
	//��, DAO�� repository�����Ѵ�.
	@Autowired
	DeptDAO_mybatis dao;
	

	//DeptDAO dao;
	/*
	 * @RequestMapping("/500") public String error500(Model model) {
	 * model.addAttribute("company", "��ȭICT"); model.addAttribute("manager",
	 * "����ī��"); model.addAttribute("phone", "010-2323-1515");
	 * 
	 * return "error500"; }
	 */
	
	@RequestMapping("/404")
	public String error404(Model model) {
		model.addAttribute("company", "��ȭICT");
		model.addAttribute("manager", "����ī��");
		return "error404";
	}
	@ExceptionHandler(Exception.class)
	public String error500(Exception ex, Model model) {
		model.addAttribute("company", "��ȭICT");
		model.addAttribute("manager", "����ī��");
		model.addAttribute("phone", "010-2323-1515");
		model.addAttribute("errormessage", ex.getMessage());
		return "error500";
	}
	
	@Autowired
	EmpDAO dao2;
	
	//�μ� ����Ʈ ������ȸ
	@RequestMapping("/dept/deptlist2") //��û�� �̸� = �ּ�â�̸�
	public String deptlist(Model model) {
		model.addAttribute("deptlist",dao.selectAll());
		return "dept/deptlist"; // view�� �̸� = ������ �̸�(��û�� �̸��� �޶� �ȴ�)
	}
	
	//�μ� ���̵� �ش��ϴ� �󼼺���
	@RequestMapping(value="/dept/deptdetail", method=RequestMethod.GET)
	public String deptDetailGet(int deptid, Model model) {
		model.addAttribute("dept", dao.selectByID(deptid));
		System.out.println(dao.selectByID(deptid));
		return "dept/deptdetail";
	}
	

	
	
	//�󼼳��� ����
	@RequestMapping(value="/dept/deptdetail", method=RequestMethod.POST)
	public String deptDetailPost(DeptDTO dept) {
		dao.updateDept(dept);
		return "redirect:deptlist2";
	}
	
	//�μ��Է�: �� �����ֱ�
	@RequestMapping(value="/dept/deptinsert", method=RequestMethod.GET)
	public String deptInsertGet() {
		return "dept/deptinsert";
	}
	
	// �μ��Է�:
	@RequestMapping(value = "/dept/deptinsert", method = RequestMethod.POST)
	public String deptInsertPost(DeptDTO dept, HttpServletRequest request) {
		MultipartFile uploadfile = dept.getUploadfile();
		if (uploadfile == null)
			return "redirect:deptinsert";

		String path = request.getSession().getServletContext().getRealPath("/resources");
		System.out.println("�� ������ �������: " + path);
		String fileName = uploadfile.getOriginalFilename();
		String fpath = path + "\\" + fileName;
		dept.setFileName(fileName);
		try {// ���1) FileOutputStream ���
				// byte[] fileData = file.getBytes();
				// FileOutputStream output = new FileOutputStream(fpath);
				// output.write(fileData);
				// 2. File ���
			File file = new File(fpath);
			uploadfile.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dao.insertDept(dept);
		return "redirect:deptlist2";
	}
		
		
	
	@RequestMapping("/dept/deptdelete")
	public String deptDeleteGet(int deptid) {
		dao.deleteDept(deptid);
		return "redirect:deptlist2";
	}
	
	@Autowired
	EmpService service;
	
	@RequestMapping("/emp/emplist")
	public ModelAndView selectAll() {
		ModelAndView mv= new ModelAndView();
		mv.addObject("empAll", service.selectAll());
		mv.setViewName("emp/empAll");
		return mv;
	}
	
	@RequestMapping("/emp/empdetail")
	public ModelAndView selectDetail(int empid) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("emp", service.selectByID(empid));
		mv.addObject("deptlist", dao.selectAll());
		mv.addObject("joblist", service.selectAllJob());
		mv.addObject("managerlist", service.selectAllManager());
		mv.setViewName("emp/empdetail");
		return mv;
	}
	
		//�󼼳��� ����
		@RequestMapping(value="/emp/empdetail", method=RequestMethod.POST)
		public String updateEmp(EmpVO empvo) {
			ModelAndView mv= new ModelAndView();
			mv.addObject("emp", service.updateEmp(empvo));
			
			return "redirect:emplist";
		}
		
		//insert
		
		//�����Է�: �� �����ֱ�
		@RequestMapping(value="/emp/empinsert", method=RequestMethod.GET)
		public ModelAndView empInsertGet() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("deptlist", dao.selectAll());
			mv.addObject("joblist", service.selectAllJob());
			mv.addObject("managerlist", service.selectAllManager());
			return mv;
		}
		
		@RequestMapping(value="/emp/empinsert", method=RequestMethod.POST)
		public String insertEmp(EmpVO emp) {
			service.insertEmp(emp);
			return "redirect:emplist";
		}
		
		@RequestMapping(value="/emp/empdelete")
		public String deleteEmp(int empid) {
			service.deleteEmp(empid);
			return "redirect:emplist";
		}
		
		@RequestMapping("/dept/deptdownload")
		public void download(String folder, String file, HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setHeader("Content-Disposition", "attachment;filename=" + file);
		String fullPath = request.getSession().getServletContext().getRealPath(folder + "/" + file);
		FileInputStream fi = new FileInputStream(fullPath);
		ServletOutputStream sout = response.getOutputStream();
		byte[] buf = new byte[1024];
		int size = 0;
		while ((size = fi.read(buf, 0, 1024)) != -1) {
			sout.write(buf, 0, size);
		}
		fi.close();
		sout.close();
		}
		
		
}






	//DeptDAO dao = new DAO();
	//<bean id="dao" class="com.hanhwa.model.DeptDAO"/>
	
	//<bean id="myController", class="" >
	//	<property name="dao" ref="dao">
	//</bean>