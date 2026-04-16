package ucsal.oferta.security.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ucsal.oferta.security.model.User;

@Repository
public interface UserRepository extends JpaRepository <User, UUID>, JpaSpecificationExecutor<User> {
	
	@Query("SELECT u FROM User u WHERE u.accountNonLocked = true AND u.enabled = true")
	Page<User> findAllActiveUsers(Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE u.id = :id AND u.accountNonLocked = true AND u.enabled = true")
	Optional<User> findActiveUserById(UUID id);
	
	Optional<User> findByNickname(String nickanme);
	Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);
    boolean existsByNicknameAndIdNot(String nickname, UUID id);
    
}
