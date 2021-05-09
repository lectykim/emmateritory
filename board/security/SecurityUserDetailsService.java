package com.flowering.project.board.security;

import com.flowering.project.board.domain.Member;
import com.flowering.project.board.domain.Role;
import com.flowering.project.board.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SecurityUserDetailsService  implements UserDetailsService{

    @Autowired
    private MemberRepository memberRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optional = memberRepo.findByUsername(username);
        Member member = optional.get();
        System.out.println("member1 : " + member.toString());

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(member.getRole()== Role.MEMBER){
            authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUsername(),member.getPassword(),authorities);

    }
}
