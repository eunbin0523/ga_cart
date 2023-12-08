package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.AccountDAO;
import com.example.demo.entity.Account;

@Service
public class AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private boolean signupSuccessful; // 추가: 회원가입 성공 여부를 저장하는 변수

    @Transactional
    public boolean registerNewUser(String userName, String password) {
        // 사용자명이 이미 존재하는지 확인
        if (accountDAO.findAccount(userName) != null) {
            signupSuccessful = false; // 이미 존재하면 회원가입 실패
            return signupSuccessful;
        }

        // 패스워드 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 사용자 정보 생성
        Account newUser = new Account();
        newUser.setUserName(userName);
        newUser.setEncrytedPassword(encodedPassword);
        newUser.setUserRole("ROLE_EMPLOYEE"); // 기본 역할 설정 (프로젝트에 맞게 수정)
        newUser.setActive(true); // 사용자 활성화 설정

        // 데이터베이스에 사용자 정보 저장
        accountDAO.save(newUser);

        signupSuccessful = true; // 회원가입 성공
        return signupSuccessful;
    }

    // getEncrytedPassword 메서드 추가
    @Transactional(readOnly = true)
    public String getEncrytedPassword(String userName) {
        // 사용자명을 기반으로 DB에서 비밀번호를 가져옴
        Account account = accountDAO.findAccount(userName);
        return (account != null) ? account.getEncrytedPassword() : null;
    }

    // 추가: 회원가입 성공 여부 반환 메서드
    public boolean isSignupSuccessful() {
        return signupSuccessful;
    }
}