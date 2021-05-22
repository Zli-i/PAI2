package com.kasyno.kasyno.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//public class ApplicationUserService  implements UserDetailsService {
//
//    private final ApplicationUserDao applicationUserDao;
//
//    public ApplicationUserService(ApplicationUserDao applicationUserDao) {
//        this.applicationUserDao = applicationUserDao;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return applicationUserDao.selectApplicationUserByUsername(s).orElseThrow(()-> new UsernameNotFoundException(""));
//    }
//}
