package com.flowering.project.board.security;

import com.flowering.project.board.domain.Member;
import com.flowering.project.board.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser extends User {
    private static final long serialVersionUID = 1L;
    private Member member;

    public SecurityUser(Member member){
        super(member.getUsername(),"{noob}"+member.getPassword(), authorities(member));
        this.member=member;
    }

    public Member getMember(){
        return member;
    }

    private static Collection<? extends GrantedAuthority> authorities(Member member){
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(member.getRole()== Role.MEMBER){
            authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }
}
