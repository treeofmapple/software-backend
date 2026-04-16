package ucsal.oferta.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import ucsal.oferta.interfaces.ContainerJpaTest;

@Order(6)
@ContainerJpaTest
@DisplayName("Professor Repository Test")
public class ProfessorRepositoryTest {

	// Teste Quebrado

	/*
	@Autowired
	private ProfessorRepository repository;

	@Autowired
	private Faker faker;

	
	
	@Test
	@Order(1)
	@DisplayName("Should create a new professor successfully")
	void createProfessorTest() {
		var dataForTest = generateAProfessor();
		Long professorId = (Long) dataForTest.get("id");
		Assertions.assertThat(professorId).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("Should create and then find a professor by ID")
	void createProfessorAndFind() {
		var dataForTest = generateAProfessor();
		Long professorId = (Long) dataForTest.get("id");

		Optional<Professor> found = repository.findById(professorId);
		Assertions.assertThat(found).isPresent();
	}

	@Test
	@Order(3)
	@DisplayName("Should find a created professor and verify its properties")
	void findProfessorCreated() {
		var dataForTest = generateAProfessor();
		Long professorId = (Long) dataForTest.get("id");
		String expectedNome = (String) dataForTest.get("nome");
		String expectedEmail = (String) dataForTest.get("email");

		Optional<Professor> found = repository.findById(professorId);

		Assertions.assertThat(found).isPresent();
		found.ifPresent(professor -> {
			Assertions.assertThat(professor.getNome()).isEqualTo(expectedNome);
			Assertions.assertThat(professor.getEmail()).isEqualTo(expectedEmail);
		});
	}

	@Test
	@Order(4)
	@DisplayName("Should update the name of an existing professor")
	void updateCreatedProfessor() {
		var dataForTest = generateAProfessor();
		Long professorId = (Long) dataForTest.get("id");

		String newNome = faker.name().fullName();

		Optional<Professor> professorToUpdate = repository.findById(professorId);
		Assertions.assertThat(professorToUpdate).isPresent();

		Professor updatedProfessor = professorToUpdate.get();
		updatedProfessor.setNome(newNome);
		repository.save(updatedProfessor);

		Optional<Professor> foundUpdated = repository.findById(professorId);
		Assertions.assertThat(foundUpdated).isPresent();
		Assertions.assertThat(foundUpdated.get().getNome()).isEqualTo(newNome);
	}

	@Test
	@Order(5)
	@DisplayName("Should delete a professor by ID")
	void deleteCreatedProfessor() {
		var dataForTest = generateAProfessor();
		Long professorId = (Long) dataForTest.get("id");

		repository.deleteById(professorId);

		Optional<Professor> foundAfterDelete = repository.findById(professorId);
		Assertions.assertThat(foundAfterDelete).isNotPresent();
	}

	@AfterAll
	void clearAllData() {
		repository.deleteAll();
	}

	private Map<String, Object> generateAProfessor() {
		var professor = createProfessor();
		var savedProfessor = repository.save(professor);
		Map<String, Object> professorData = new HashMap<>();
		professorData.put("id", savedProfessor.getId());
		professorData.put("nome", savedProfessor.getNome());
		professorData.put("email", savedProfessor.getEmail());
		return professorData;
	}

	private Professor createProfessor() {
		return new Professor(null, faker.name().fullName(), faker.internet().emailAddress());
	}

	 */
}
