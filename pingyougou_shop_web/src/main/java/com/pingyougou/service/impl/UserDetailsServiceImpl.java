package com.pingyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import com.pingyougou.pojo.TbSeller;
import com.pingyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author yin
 * @Date 2018/8/15 22:22
 * @Method
 */

public class UserDetailsServiceImpl implements UserDetailsService {


    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("经过权限认证");
        ArrayList<GrantedAuthority> grentedAuths = new ArrayList<GrantedAuthority>();
        grentedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        TbSeller seller = sellerService.findOne(username);
        if (null != seller) {
            if ("1".equals(seller.getStatus())) {
                return new User(username, seller.getPassword(), grentedAuths);
            }
        }
        return null;
    }
}
