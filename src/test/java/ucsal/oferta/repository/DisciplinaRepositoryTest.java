package ucsal.oferta.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import ucsal.oferta.interfaces.ContainerJpaTest;

@Order(1)
@ContainerJpaTest
@DisplayName("Disciplina Repository Test")
public class DisciplinaRepositoryTest {

	/*
	
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private Faker faker;

	@Test
	@Order(1)
	@DisplayName("Should create a new subject successfully")
	void createDisciplinaTest() {
		var dataForTest = generateADisciplina();
		Long disciplinaId = (Long) dataForTest.get("id");
		Assertions.assertThat(disciplinaId).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("Should create and then find a subject by ID")
	void createDisciplinaAndFind() {
		var dataForTest = generateADisciplina();
		Long disciplinaId = (Long) dataForTest.get("id");

		Optional<Disciplina> found = disciplinaRepository.findById(disciplinaId);
		Assertions.assertThat(found).isPresent();
	}

	@Test
	@Order(3)
	@DisplayName("Should find a created subject and verify its properties")
	void findDisciplinaCreated() {
		var dataForTest = generateADisciplina();
		Long disciplinaId = (Long) dataForTest.get("id");
		String expectedCodigo = (String) dataForTest.get("codigo");
		String expectedNome = (String) dataForTest.get("nome");
		Integer expectedCh = (Integer) dataForTest.get("ch");

		Optional<Disciplina> found = disciplinaRepository.findById(disciplinaId);

		Assertions.assertThat(found).isPresent();
		found.ifPresent(disciplina -> {
			Assertions.assertThat(disciplina.getCodigo()).isEqualTo(expectedCodigo);
			Assertions.assertThat(disciplina.getNome()).isEqualTo(expectedNome);
			Assertions.assertThat(disciplina.getCh()).isEqualTo(expectedCh);
		});
	}

	@Test
	@Order(4)
	@DisplayName("Should update the name of an existing subject")
	void updateCreatedDisciplina() {
		var dataForTest = generateADisciplina();
		Long disciplinaId = (Long) dataForTest.get("id");

		String newNome = faker.educator().course();

		Optional<Disciplina> toUpdate = disciplinaRepository.findById(disciplinaId);
		Assertions.assertThat(toUpdate).isPresent();

		Disciplina updated = toUpdate.get();
		updated.setNome(newNome);
		disciplinaRepository.save(updated);

		Optional<Disciplina> foundUpdated = disciplinaRepository.findById(disciplinaId);
		Assertions.assertThat(foundUpdated).isPresent();
		Assertions.assertThat(foundUpdated.get().getNome()).isEqualTo(newNome);
	}

	@Test
	@Order(5)
	@DisplayName("Should delete a subject by ID")
	void deleteCreatedDisciplina() {
		var dataForTest = generateADisciplina();
		Long disciplinaId = (Long) dataForTest.get("id");

		disciplinaRepository.deleteById(disciplinaId);

		Optional<Disciplina> foundAfterDelete = disciplinaRepository.findById(disciplinaId);
		Assertions.assertThat(foundAfterDelete).isNotPresent();
	}

	@AfterAll
	void clearAllData() {
		disciplinaRepository.deleteAll();
	}

	private Map<String, Object> generateADisciplina() {
		var disciplina = createDisciplina();
		var savedDisciplina = disciplinaRepository.save(disciplina);
		Map<String, Object> data = new HashMap<>();
		data.put("id", savedDisciplina.getId());
		data.put("codigo", savedDisciplina.getCodigo());
		data.put("nome", savedDisciplina.getNome());
		data.put("ch", savedDisciplina.getCh());
		return data;
	}

	private Disciplina createDisciplina() {
		String codigo = "UCS" + faker.number().numberBetween(100, 999);
		String nome = faker.educator().course();
		Integer ch = faker.number().numberBetween(40, 120);
		return new Disciplina(codigo, nome, ch);
	}

	*/
}
