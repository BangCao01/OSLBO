package orangeschool.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import orangeschool.model.Admin;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	Admin findByUsername(String _username);
	Admin findByUserID(Integer _id);
}