package com.hanwha.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//����: DB�� ���� �ʴ� ������ ����Ѵ�
//��Ʈ�ѷ���û ->���񽺰� ����->DAO�� �湮�ؼ� ����� �����ͼ� �ٷ� ��Ʈ�ѷ��� ����
//�̰� Ʋó�� �̸� �ۼ��ϰ� dao �ۼ��ϴ� ���, ��Ʈ�ѷ� �ۼ��ϴ� ������� �Ѱ��ش�(�Լ� �̸� �� ������)

@Service
public class EmpService {
	
	//@Autowired
	//EmpDAO dao;
	
	@Autowired
	EmpDAO_mybatis dao;
	
	public List<Integer> selectAllManager() {
		return dao.selectAllManager();
	}
		
	
	public List<String> selectAllJob() {
		return dao.selectAllJob();
	}
	
	/*
	public List<EmpVO> selectByJob(String job_id, int sal) {
		return dao.selectByJob(job_id, sal);
	}
	
	public List<EmpVO> selectByDept(int department_id) {
		return dao.selectByDept(department_id);
	}
	*/
	public List<EmpVO> selectAll() {
		return dao.selectAll();
	}

	public EmpVO selectByID(int employee_id) {
		return dao.selectByID(employee_id);
	}

	public int insertEmp(EmpVO emp) {
		return dao.insertEmp(emp);
	}
	
	/*
	public int updateEmpAll(EmpVO emp) {
		return dao.updateEmpAll(emp);
	}*/
	
	public int updateEmp(EmpVO emp) {
		return dao.updateEmp(emp);
	}
	
	
	
	public int deleteEmp(int employee_id) {
		return dao.deleteEmp(employee_id);
	}

}
