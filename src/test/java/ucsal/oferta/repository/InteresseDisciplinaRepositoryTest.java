package ucsal.oferta.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import ucsal.oferta.interfaces.ContainerJpaTest;

@Order(5)
@ContainerJpaTest
@DisplayName("Interesse Disciplina Repository Test")
public class InteresseDisciplinaRepositoryTest {

	// Teste Quebrado
	
	/*
	
	@Autowired
	private InteresseDisciplinaRepository interesseDisciplinaRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private Faker faker;

	@Test
	@Order(1)
	@DisplayName("Should create a new subject interest successfully")
	void createInteresseDisciplinaTest() {
		var dataForTest = generateAnInteresseDisciplina();
		Long interesseId = (Long) dataForTest.get("id");
		Assertions.assertThat(interesseId).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("Should create and then find a subject interest by ID")
	void createInteresseDisciplinaAndFind() {
		var dataForTest = generateAnInteresseDisciplina();
		Long interesseId = (Long) dataForTest.get("id");

		Optional<InteresseDisciplina> found = interesseDisciplinaRepository.findById(interesseId);
		Assertions.assertThat(found).isPresent();
	}

	@Test
	@Order(3)
	@DisplayName("Should find a created subject interest and verify its properties")
	void findInteresseDisciplinaCreated() {
		var dataForTest = generateAnInteresseDisciplina();
		Long interesseId = (Long) dataForTest.get("id");
		Professor expectedProfessor = (Professor) dataForTest.get("professor");
		Integer expectedSemestre = (Integer) dataForTest.get("semestre");

		Optional<InteresseDisciplina> found = interesseDisciplinaRepository.findById(interesseId);

		Assertions.assertThat(found).isPresent();
		found.ifPresent(interesse -> {
			Assertions.assertThat(interesse.getProfessor().getId()).isEqualTo(expectedProfessor.getId());
			Assertions.assertThat(interesse.getSemestre()).isEqualTo(expectedSemestre);
			Assertions.assertThat(interesse.getDisciplina()).isNotEmpty();
		});
	}

	@Test
	@Order(4)
	@DisplayName("Should update the semester of an existing subject interest")
	void updateCreatedInteresseDisciplina() {
		var dataForTest = generateAnInteresseDisciplina();
		Long interesseId = (Long) dataForTest.get("id");
		Integer newSemestre = 20252;

		Optional<InteresseDisciplina> toUpdate = interesseDisciplinaRepository.findById(interesseId);
		Assertions.assertThat(toUpdate).isPresent();

		InteresseDisciplina updated = toUpdate.get();
		updated.setSemestre(newSemestre);
		interesseDisciplinaRepository.save(updated);

		Optional<InteresseDisciplina> foundUpdated = interesseDisciplinaRepository.findById(interesseId);
		Assertions.assertThat(foundUpdated).isPresent();
		Assertions.assertThat(foundUpdated.get().getSemestre()).isEqualTo(newSemestre);
	}

	@Test
	@Order(5)
	@DisplayName("Should delete a subject interest by ID")
	void deleteCreatedInteresseDisciplina() {
		var dataForTest = generateAnInteresseDisciplina();
		Long interesseId = (Long) dataForTest.get("id");

		interesseDisciplinaRepository.deleteById(interesseId);

		Optional<InteresseDisciplina> foundAfterDelete = interesseDisciplinaRepository.findById(interesseId);
		Assertions.assertThat(foundAfterDelete).isNotPresent();
	}

	@AfterAll
	void clearAllData() {
		interesseDisciplinaRepository.deleteAll();
		professorRepository.deleteAll();
		disciplinaRepository.deleteAll();
	}

	private Professor createAndSaveProfessor() {
		Professor professor = new Professor(null, faker.name().fullName(), faker.internet().emailAddress());
		return professorRepository.save(professor);
	}

	private Disciplina createAndSaveDisciplina() {
		Disciplina disciplina = new Disciplina(faker.educator().course(), faker.lorem().word(),
				faker.number().numberBetween(40, 80));
		return disciplinaRepository.save(disciplina);
	}

	private Map<String, Object> generateAnInteresseDisciplina() {
		var interesse = createInteresseDisciplina();
		var savedInteresse = interesseDisciplinaRepository.save(interesse);
		Map<String, Object> data = new HashMap<>();
		data.put("id", savedInteresse.getId());
		data.put("professor", savedInteresse.getProfessor());
		data.put("disciplina", savedInteresse.getDisciplina());
		data.put("semestre", savedInteresse.getSemestre());
		return data;
	}

	private InteresseDisciplina createInteresseDisciplina() {
		Professor professor = createAndSaveProfessor();
		Disciplina disciplina = createAndSaveDisciplina();
		Integer semestre = 20251;

		return new InteresseDisciplina(null, professor, new ArrayList<>(Collections.singletonList(disciplina)),
				semestre);
	}

	 */
}
