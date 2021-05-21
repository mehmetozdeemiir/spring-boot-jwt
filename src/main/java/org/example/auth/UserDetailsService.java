package org.example.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//UserSecurityden gelen UserDetailsService class ını implement etmemiz gerekli

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    //veritabanına bağlamadığımız için liste olusturup username sifre verdik
    private Map<String,String> users= new HashMap<>();

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        users.put("temelt",passwordEncoder.encode("123"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(users.containsKey(username)){
            return new User(username,users.get(username),new ArrayList<>()); //username,password,arraylist parametre verilmesi gerekli
        }
        throw new UsernameNotFoundException(username);
    }
    //gelen users içerisinde bu username var ise return spring framework un User ını implement edicez(spring in anlayacağı bir user vermemiz gerekiyo geriye)
    //ya bulacak listeden nesneyi spring in anladığı bir user nesnesi olarak dönecek yada hata fırlatıcak.
}
