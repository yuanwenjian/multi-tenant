package com.yuanwj.multitenant.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class UserAuthentication  extends AbstractAuthenticationToken {

    private String tenant;

    public UserAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public UserAuthentication(Collection<? extends GrantedAuthority> authorities, String tenant) {
        super(authorities);
        super.setAuthenticated(false);
        this.tenant = tenant;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public static UserAuthentication getUserAuthentication(){
        return (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
