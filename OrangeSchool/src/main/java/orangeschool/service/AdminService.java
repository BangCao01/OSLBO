package orangeschool.service;


import java.util.List;

import orangeschool.model.Admin;;

public interface AdminService {
    void save(Admin _admin);

    Admin findByUsername(String _username);
    Admin findByUserID(Integer _id);
    List<Admin> findAll();
    void deleteById(Integer _id);
    boolean match(Admin _admin, String _password);
    
}