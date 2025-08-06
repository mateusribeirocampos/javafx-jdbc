package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public List<Department> findAll() {
		
		return dao.findAll();
		
		/*List<Department> list = new ArrayList<Department>();
		list.add(new Department(1, "Books", 60, "Various titles of major bestsellers"));
		list.add(new Department(2, "Computers", 15, "Top computer brands"));
		list.add(new Department(3, "Electronics", 40, "Lots of electronics for your devices"));
		return list;*/
	}
	
}
