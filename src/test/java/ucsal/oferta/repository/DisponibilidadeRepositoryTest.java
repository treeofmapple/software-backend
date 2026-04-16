package ucsal.oferta.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import ucsal.oferta.interfaces.ContainerJpaTest;

@Order(2)
@ContainerJpaTest
@DisplayName("Disponibilidade Repository Test")
public class DisponibilidadeRepositoryTest {

	// Teste Quebrado
	
	/*
	
	@Autowired
	private DisponibilidadeRepository disponibilidadeRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private Faker faker;

	
	@Test
	@Order(1)
	@DisplayName("Should create a new availability successfully")
	void createDisponibilidadeTest() {
		var dataForTest = generateADisponibilidade();
		Long disponibilidadeId = (Long) dataForTest.get("id");
		Assertions.assertThat(disponibilidadeId).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("Should create and then find an availability by ID")
	void createDisponibilidadeAndFind() {
		var dataForTest = generateADisponibilidade();
		Long disponibilidadeId = (Long) dataForTest.get("id");

		Optional<Disponibilidade> found = disponibilidadeRepository.findById(disponibilidadeId);
		Assertions.assertThat(found).isPresent();
	}

	@Test
	@Order(3)
	@DisplayName("Should find a created availability and verify its properties")
	void findDisponibilidadeCreated() {
		var dataForTest = generateADisponibilidade();
		Long disponibilidadeId = (Long) dataForTest.get("id");
		Dia expectedDia = (Dia) dataForTest.get("dia");
		Integer expectedSemestre = (Integer) dataForTest.get("semestre");

		Optional<Disponibilidade> found = disponibilidadeRepository.findById(disponibilidadeId);

		Assertions.assertThat(found).isPresent();
		found.ifPresent(disponibilidade -> {
			Assertions.assertThat(disponibilidade.getDia()).isEqualTo(expectedDia);
			Assertions.assertThat(disponibilidade.getSemestre()).isEqualTo(expectedSemestre);
			Assertions.assertThat(disponibilidade.getProfessor()).isNotNull();
			Assertions.assertThat(disponibilidade.getHorarioAulas()).isNotEmpty();
		});
	}

	@Test
	@Order(4)
	@DisplayName("Should update the semester of an existing availability")
	void updateCreatedDisponibilidade() {
		var dataForTest = generateADisponibilidade();
		Long disponibilidadeId = (Long) dataForTest.get("id");

		Integer newSemestre = faker.number().numberBetween(20251, 20302);

		Optional<Disponibilidade> toUpdate = disponibilidadeRepository.findById(disponibilidadeId);
		Assertions.assertThat(toUpdate).isPresent();

		Disponibilidade updated = toUpdate.get();
		updated.setSemestre(newSemestre);
		disponibilidadeRepository.save(updated);

		Optional<Disponibilidade> foundUpdated = disponibilidadeRepository.findById(disponibilidadeId);
		Assertions.assertThat(foundUpdated).isPresent();
		Assertions.assertThat(foundUpdated.get().getSemestre()).isEqualTo(newSemestre);
	}

	@Test
	@Order(5)
	@DisplayName("Should delete an availability by ID")
	void deleteCreatedDisponibilidade() {
		var dataForTest = generateADisponibilidade();
		Long disponibilidadeId = (Long) dataForTest.get("id");

		disponibilidadeRepository.deleteById(disponibilidadeId);

		Optional<Disponibilidade> foundAfterDelete = disponibilidadeRepository.findById(disponibilidadeId);
		Assertions.assertThat(foundAfterDelete).isNotPresent();
	}

	@AfterAll
	void clearAllData() {
		disponibilidadeRepository.deleteAll();
		professorRepository.deleteAll();
	}

	private Map<String, Object> generateADisponibilidade() {
		var disponibilidade = createDisponibilidade();
		var savedDisponibilidade = disponibilidadeRepository.save(disponibilidade);
		Map<String, Object> data = new HashMap<>();
		data.put("id", savedDisponibilidade.getId());
		data.put("dia", savedDisponibilidade.getDia());
		data.put("semestre", savedDisponibilidade.getSemestre());
		data.put("professorId", savedDisponibilidade.getProfessor().getId());
		return data;
	}

	private Professor createAndSaveProfessor() {
		Professor professor = new Professor(null, faker.name().fullName(), faker.internet().emailAddress());
		return professorRepository.save(professor);
	}

	private Disponibilidade createDisponibilidade() {
		Professor professor = createAndSaveProfessor();
		Dia dia = Dia.values()[faker.random().nextInt(Dia.values().length)];
		List<HorarioAula> horarios = new ArrayList<>(
				List.of(HorarioAula.values()[faker.random().nextInt(HorarioAula.values().length)]));
		Integer semestre = Integer
				.valueOf(faker.number().numberBetween(2024, 2026) + "" + faker.number().numberBetween(1, 2));

		return new Disponibilidade(null, dia, horarios, semestre, professor);
	}

	 */
}
