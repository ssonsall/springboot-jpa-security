package com.cos.crud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.crud.model.MyUser;
import com.cos.crud.repository.MyUserRepository;

@Service
public class MyUserDetailService implements UserDetailsService{

	@Autowired
	private MyUserRepository mRepo;
	
	// loginForm에서 action="user/loginProcess"
	// ==Spring Filter Chain이 낚아챔== 그래서 loadUserByUsername 함수를 호출
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser user = mRepo.findByUsername(username);
		MyUserDetails userDetails = null;
			
		if(user != null) {
			userDetails = new MyUserDetails();
			userDetails.setUser(user);
			return userDetails;
		}else {
			throw new UsernameNotFoundException("해당 유저 네임은 없습니다. >> " + username);
		}
	}

}
