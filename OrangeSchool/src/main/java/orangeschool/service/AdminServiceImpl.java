package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import orangeschool.repository.AdminRepository;
import orangeschool.model.Admin;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
//    @Autowired
//    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Admin _admin) {
        _admin.setPassword(bCryptPasswordEncoder.encode(_admin.getPassword()));
        //_admin.setRoles(new HashSet<>(roleRepository.findAll()));
        adminRepository.save(_admin);
    }

    @Override
    public Admin findByUsername(String _username) {
        Admin admin =  adminRepository.findByUsername(_username);
        return admin;
    }
    @Override
    public Admin findByUserID(Integer _id)
    {
    	return this.adminRepository.findByUserID(_id);
    }
    
    @Override
    public List<Admin> findAll()
    {
    	return this.adminRepository.findAll();
    }
    
    @Override
    public void deleteById(Integer _id)
    {
    	this.adminRepository.deleteById(_id);
    }
    public boolean match(Admin _admin, String _password)
    {
    	return bCryptPasswordEncoder.encode(_password) == _admin.getPassword();
    }
}