package com.ssafy.service;

import com.ssafy.dao.UserDaoImpl;
import com.ssafy.vo.User;

import java.util.HashMap;
import java.util.List;

public class CheckService {
	private UserDaoImpl userMgr = UserDaoImpl.getInstance();

	/**
	 * 싱글톤
	 */
	private static CheckService checkService;
	public static CheckService getInstance() {
		if (checkService == null) checkService = new CheckService();
		return checkService;
	}

	/**
	 * 아이디, 비밀번호 유효성 검사
	 *
	 * @param id
	 * @param pw
	 * @return
	 */
	public boolean checkAccount(String id, String pw) {
		List<User> users = userMgr.findAll();
		for (User u : users) {
			if (u.getId().equalsIgnoreCase(id) && u.getPw().equals(pw)) return true;
		}
		return false;
	}

	/**
	 * ID, PW null 체크
	 *
	 * @param id
	 * @param pw
	 * @return
	 */
	public HashMap<String, String> checkNullForLogin(String id, String pw) {
		HashMap<String, String> errorMessages = new HashMap<>();
		if (id == null || id.trim().length() == 0) errorMessages.put("idError", "아이디가 입력되지 않았습니다.");
		if (pw == null || pw.trim().length() == 0) errorMessages.put("pwError", "비밀번호가 입력되지 않았습니다.");
		return errorMessages;
	}

	/**
	 * ID, PW, NAME, AGE, GENDER 체크
	 *
	 * @param id
	 * @param pw
	 * @param name
	 * @param age
	 * @param gender
	 * @return
	 */
	public HashMap<String, String> checkForSignUp(String id, String pw, String name, int age, String gender) {
		HashMap<String, String> errorMessages = this.checkNullForLogin(id, pw);
		if (name == null || name.trim().length() == 0) errorMessages.put("nameError", "이름이 입력되지 않았습니다.");
		if (age <= 0) errorMessages.put("ageError", "나이를 올바르게 입력해주세요.");
		if (gender == null || gender.trim().length() == 0) errorMessages.put("genderError", "성별을 선택해주세요.");

		List<User> users = userMgr.findAll();
		for (User u : users) {
			if (u.getId().equalsIgnoreCase(id)) errorMessages.put("idAlready", "입력한 아이디가 이미 존재합니다.");
		}
		return errorMessages;
	}
}