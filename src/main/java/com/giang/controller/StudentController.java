package com.giang.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.giang.model.Class;
import com.giang.model.Student;
import com.giang.model.StudentCustom;
import com.giang.paging.Page;
import com.giang.service.StudentService;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes({ "keySearch", "pageCurrent", "keySearchDate" })
public class StudentController {

	@Autowired
	StudentService studentService;

	/**
	 * Page index
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/student/home", method = RequestMethod.GET)
	public String home(ModelMap modelMap) {
		//showStudentList(modelMap, 1);
		showStudentCustomList(modelMap, 1);
		return "home1";
	}

	/**
	 * Forward to searchAccount.jsp
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/student/accountsearch")
	public String searchAccount() {
		return "searchAccount";
	}

	/**
	 * Forward to 403.jsp
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/student/403")
	public String errorDeined() {
		return "403";
	}

	/**
	 * Delete all search sesions
	 * 
	 * @param ModelMap
	 * @param SessionStatus
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/student/removeSession", method = RequestMethod.GET)
	public String removeSession(ModelMap modelMap, SessionStatus status) {
		// delete session
		status.setComplete();
		return "redirect:/student/home";
	}

	/**
	 * Displays information when forward page
	 * 
	 * @param ModelMap
	 * @param int
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/student/page/{page_id}", method = RequestMethod.GET)
	public String showStudentListByPageID(ModelMap modelMap, @PathVariable("page_id") int id) {
//		showStudentList(modelMap, id);
//		return "home";
		showStudentCustomList(modelMap, id);
		return "home1";
	}

	/**
	 * Performs search function and handles sending search results for view
	 * 
	 * @param ModelMap
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 * @param String
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/student/search", method = RequestMethod.POST)
	public String searchStudent(ModelMap modelMap, @ModelAttribute(value = "studentName") String studentName,
			@ModelAttribute(value = "classId") String classId, @ModelAttribute(value = "sexStudent") String sexStudent,
			@ModelAttribute(value = "averageScore") String averageScore,
			@ModelAttribute(value = "startDate") String startDateStr,
			@ModelAttribute(value = "endDate") String endDateStr) {

		// set properties for student
		Student student = new Student();
		student.setStudentName(studentName);
		student.setSex(sexStudent);
		student.setAverageScore(Double.parseDouble(averageScore));
		student.setClassStudent(studentService.findClassByIdService(Integer.parseInt(classId)));

		// convert String -> Date
		Date startDate = null;
		Date endDate = null;

		// case user import from date and no import to date
		// find from date to current date
		if (!startDateStr.equals("") && endDateStr.equals("")) {
			endDate = new Date();
		}

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			startDate = dateFormat.parse(startDateStr);
			if (endDate == null) {
				endDate = dateFormat.parse(endDateStr);
			}
		} catch (ParseException e) {

		}
		// execute showStudentListByAll
		showStudentListByAll(modelMap, student, startDate, endDate, 1);

		return "home";
	}

	/**
	 * Displays information with search status, when forward page
	 * 
	 * @param ModelMap
	 * @param int
	 * @param HttpSession
	 * 
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/student/search/{page_id}", method = RequestMethod.GET)
	public String searchStudentWithPage(ModelMap modelMap, @PathVariable("page_id") int id, HttpSession session) {
		// get student from session
		Student student = (Student) session.getAttribute("keySearch");
		// get List<Date> from session
		List<Date> dateList = (List<Date>) session.getAttribute("keySearchDate");
		showStudentListByAll(modelMap, student, dateList.get(0), dateList.get(1), id);
		return "home";
	}

	/**
	 * Switch to "insert" and send request
	 * 
	 * @param ModelMap
	 * @return insert
	 */
	@RequestMapping(value = "/student/insert", method = RequestMethod.GET)
	public String insertStudent(ModelMap modelMap) {
		// Get all class from database for combox Class
		List<Class> classList = studentService.getClassListService();

		// send request
		modelMap.addAttribute("classList", classList);
		modelMap.put("student", new Student());

		return "insert";
	}

	/**
	 * Add student
	 * 
	 * @param ModelMap
	 * @param BindingResult
	 * @param HttpServletRequest
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/student/insert", method = RequestMethod.POST)
	public String insertStudent(ModelMap modelMap, @ModelAttribute(value = "student") @Valid Student student,
			BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			// Get all class from database
			List<Class> classList = studentService.getClassListService();

			// Send request
			modelMap.addAttribute("classList", classList);
			modelMap.addAttribute("student", student);
			
			
			return "insert";
		}
		// Checking access permissions before insert
		if (request.isUserInRole("ROLE_ADMIN")) {
			studentService.insertStudentService(student);
		}
		// message
		modelMap.addAttribute("message", studentService.getMessage());
		return "redirect:/student/home";
	}

	/**
	 * delete student
	 * 
	 * @param ModelMap
	 * @param int
	 * @param HttpSession
	 * @param HttpServletRequest
	 * 
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/student/delete/{student_id}", method = RequestMethod.GET)
	public String deleteStudent(@PathVariable("student_id") int student_id, ModelMap modelMap, HttpSession session,
			HttpServletRequest request) {
		// Checking access permissions
		if (request.isUserInRole("ROLE_ADMIN")) {
			// Perform a delete function
			studentService.deleteStudentService(student_id);
		}
		Student studentSession = (Student) session.getAttribute("keySearch");
		//String a= studentService.getMessage();
		modelMap.addAttribute("message", studentService.getMessage());
		// case Not in the search state
		if (((List<Date>) session.getAttribute("keySearchDate")).size() == 0 && studentSession.getStudentName() == ""
				&& studentSession.getSex().equals("No") && studentSession.getAverageScore() == 0
				&& studentSession.getClassStudent() == null) {
			return "redirect:/student/page/" + session.getAttribute("pageCurrent");
		} else {
			return "redirect:/student/search/" + session.getAttribute("pageCurrent");
		}
	}

	/**
	 * student_id receive view get student by student id, sent over view
	 * 
	 * @param int
	 * @param ModelMap
	 * @return String
	 */
	@RequestMapping(value = "/student/update/{student_id}", method = RequestMethod.GET)
	public String updateStudent(@PathVariable("student_id") int studentId, ModelMap modelMap) {
		// get student has studentId equal studentId param
		Student student = studentService.findStudentbyIdService(studentId);
		// Get all class from database
		List<Class> classList = studentService.getClassListService();
		// Send request
		modelMap.addAttribute("classList", classList);
		modelMap.put("student", student);

		return "update";
	}

	/**
	 * receive a request from "/student/update" with method = post, update
	 * student from request and check data is valid
	 * 
	 * @param ModelMap
	 * @param Student
	 * @param BindingResult
	 * @param HttpServletRequest
	 * @param HttpSession
	 * 
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/student/update/{student_id}", method = RequestMethod.POST)
	public String updateStudent(ModelMap modelMap, @ModelAttribute(value = "student_id") int id,
			@ModelAttribute(value = "student") @Valid Student student, BindingResult bindingResult,
			HttpServletRequest request, HttpSession session) {
		// set id for student
		student.setStudentId(id);
//		// avoid xss
//		student.setStudentName(studentService.convertEndcodingReverse(student.getStudentName()));
//		student.setAddress(studentService.convertEndcodingReverse(student.getAddress()));
//		student.setStudentCode(studentService.convertEndcodingReverse(student.getStudentCode()));
		// check validate
		if (bindingResult.hasErrors()) {
			// Get all class from database
			List<Class> classList = studentService.getClassListService();

			// send request
			modelMap.addAttribute("classList", classList);
			modelMap.put("student", student);

			return "update";
		}
		// Checking access permissions before update
		if (request.isUserInRole("ROLE_ADMIN")) {
			studentService.updateStudentService(student);
		}
		Student studentSession = (Student) session.getAttribute("keySearch");
		// message
		modelMap.addAttribute("message", studentService.getMessage());
		// case Not in the search state
		if (((List<Date>) session.getAttribute("keySearchDate")).size() == 0 && studentSession.getStudentName() == ""
				&& studentSession.getSex().equals("No") && studentSession.getAverageScore() == 0
				&& studentSession.getClassStudent() == null) {
			return "redirect:/student/page/" + session.getAttribute("pageCurrent");
		} else {
			return "redirect:/student/search/" + session.getAttribute("pageCurrent");
		}
	}

	/**
	 * Process display the list of student when the page with (int) : number
	 * page
	 * 
	 * @param Model
	 * @param int
	 * 
	 * 
	 */
	public void showStudentList(ModelMap modelMap, int pageId) {
		// number record of page

		int recordPage = 10;
		List<Student> studentList = new ArrayList<Student>();
		// number all student
		int countStudent = studentService.countAllStudentService();
		// init page
		Page page = new Page(countStudent, recordPage, pageId);
		// case user import larger than size of page in url
		// then current page equal size page.
		if (page.getSize() < pageId) {
			pageId = (int) page.getSize();
			page.setCurrentPosition(pageId);
		}

		// case user import number 0 or negative number in url
		// then current page equal size page.
		if (pageId < 1 && page.getSize() != 0) {
			pageId = 1;
			page.setCurrentPosition(pageId);
		}
		if (pageId == 1) {
			// a list students index
			studentList = studentService.getStudentlistSercive(0, recordPage);

		} else {
			// a list students when turning pages
			studentList = studentService.getStudentlistSercive((int) (recordPage * (pageId - 1)), recordPage);
		}

		// get all class from database
		List<Class> classList = studentService.getClassListService();

		// Send request
		modelMap.addAttribute("studentList", studentList);
		modelMap.addAttribute("classList", classList);
		modelMap.addAttribute("page", page);

		// Update session
		modelMap.addAttribute("keySearch", new Student());
		modelMap.addAttribute("keySearchDate", new ArrayList<Date>());

		// Check record last record there is of one page if true update session
		// pageCurrent
		if (countStudent % 10 == 1 && countStudent != 1) {
			modelMap.addAttribute("pageCurrent", pageId - 1);
		} else {
			modelMap.addAttribute("pageCurrent", pageId);
		}
	}

	
	public void showStudentCustomList(ModelMap modelMap, int pageId) {
		// number record of page

		int recordPage = 10;
		List<StudentCustom> studentList = new ArrayList<StudentCustom>();
		// number all student
		int countStudent = studentService.countAllStudentService();
		// init page
		Page page = new Page(countStudent, recordPage, pageId);
		// case user import larger than size of page in url
		// then current page equal size page.
		if (page.getSize() < pageId) {
			pageId = (int) page.getSize();
			page.setCurrentPosition(pageId);
		}

		// case user import number 0 or negative number in url
		// then current page equal size page.
		if (pageId < 1 && page.getSize() != 0) {
			pageId = 1;
			page.setCurrentPosition(pageId);
		}
		if (pageId == 1) {
			// a list students index
			studentList = studentService.showStudentCustomService(0, recordPage);

		} else {
			// a list students when turning pages
			studentList = studentService.showStudentCustomService((int) (recordPage * (pageId - 1)), recordPage);
		}

		// get all class from database
		List<Class> classList = studentService.getClassListService();

		// Send request
		modelMap.addAttribute("studentList", studentList);
		modelMap.addAttribute("classList", classList);
		modelMap.addAttribute("page", page);

		// Update session
		modelMap.addAttribute("keySearch", new Student());
		modelMap.addAttribute("keySearchDate", new ArrayList<Date>());

		// Check record last record there is of one page if true update session
		// pageCurrent
		if (countStudent % 10 == 1 && countStudent != 1) {
			modelMap.addAttribute("pageCurrent", pageId - 1);
		} else {
			modelMap.addAttribute("pageCurrent", pageId);
		}
	}
	/**
	 * Process display the student list when searching for a paging
	 * 
	 * @param Model
	 * @param Student
	 * @param Date
	 * @param Date
	 * @param int
	 * 
	 */
	public void showStudentListByAll(ModelMap modelMap, Student student, Date startDate, Date endDate, int pageId) {
		int recordPage = 10; // number record of page
		List<Student> studentList = new ArrayList<Student>();
		// number all student
		int countStudents = studentService.countStudentsByAllService(student, startDate, endDate);
		// init page
		Page page = new Page(countStudents, recordPage, pageId);

		// case user import larger than size of page
		if (page.getSize() < pageId) {
			pageId = (int) page.getSize();
			page.setCurrentPosition(pageId);
		}

		// case user import number 0 or negative number
		if (pageId < 1) {
			pageId = 1;
			page.setCurrentPosition(pageId);
		}

		if (pageId == 1) {
			// a list students index
			studentList = studentService.findStudentByAllService(student, startDate, endDate, 0, recordPage);
		} else {
			// a list students when turning pages
			studentList = studentService.findStudentByAllService(student, startDate, endDate,
					(int) (recordPage * (pageId - 1)), recordPage);
		}

		// get all class from database
		List<Class> classList = studentService.getClassListService();

		// Send request
		modelMap.addAttribute("studentList", studentList);
		modelMap.addAttribute("classList", classList);
		modelMap.addAttribute("page", page);

		// update session
		List<Date> dateList = new ArrayList<Date>();
		dateList.add(startDate);
		dateList.add(endDate);
		modelMap.addAttribute("keySearchDate", dateList);
		modelMap.addAttribute("keySearch", student);

		// Check record last record there is of one page if true update session
		// pageCurrent
		if (countStudents % 10 == 1 && countStudents != 1) {
			modelMap.addAttribute("pageCurrent", pageId - 1);
		} else {
			modelMap.addAttribute("pageCurrent", pageId);
		}
	}
}
