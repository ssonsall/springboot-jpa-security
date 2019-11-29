package com.cos.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.crud.model.MyUser;

//기본 정의 되어있는 CRUD가 자동 발동
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
	MyUser findByUsername(String username);
}
