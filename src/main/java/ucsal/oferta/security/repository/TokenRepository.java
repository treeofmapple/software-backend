package ucsal.oferta.security.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ucsal.oferta.security.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	@Query("""
			SELECT t FROM Token t
			WHERE t.user.id = :userId AND t.expired = false AND t.revoked = false
			""")
	List<Token> findAllValidTokensByUserId(UUID userId);

	Optional<Token> findFirstByUserIdOrderByIdDesc(UUID userId);

	Optional<Token> findByToken(String token);

	@Modifying
	@Transactional
	@Query("DELETE FROM Token t WHERE t.expired = true OR t.revoked = true")
	void deleteAllInvalidTokens();
}
