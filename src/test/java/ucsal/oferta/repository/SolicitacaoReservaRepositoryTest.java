package ucsal.oferta.repository;

// -- Unsued 

/*
@ContainerJpaTest
public class SolicitacaoReservaRepositoryTest extends PostgresInstance {

	@Autowired
	private SolicitacaoReservaRepository solicitacaoReservaRepository;

	@Autowired
	private EspacoRepository espacoRepository;

	@Autowired
	private EquipamentoRepository equipamentoRepository;

	@Autowired
	private Faker faker;

	@Test
	@Order(1)
	@DisplayName("Should create a new reservation request successfully")
	void createSolicitacaoReservaTest() {
		var dataForTest = generateASolicitacaoReserva();
		Long solicitacaoId = (Long) dataForTest.get("id");
		Assertions.assertThat(solicitacaoId).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("Should create and then find a reservation request by ID")
	void createSolicitacaoReservaAndFind() {
		var dataForTest = generateASolicitacaoReserva();
		Long solicitacaoId = (Long) dataForTest.get("id");

		Optional<SolicitacaoReserva> found = solicitacaoReservaRepository.findById(solicitacaoId);
		Assertions.assertThat(found).isPresent();
	}

	@Test
	@Order(3)
	@DisplayName("Should find a created reservation request and verify its properties")
	void findSolicitacaoReservaCreated() {
		var dataForTest = generateASolicitacaoReserva();
		Long solicitacaoId = (Long) dataForTest.get("id");
		String expectedFinalidade = (String) dataForTest.get("finalidade");
		StatusSolicitacao expectedStatus = (StatusSolicitacao) dataForTest.get("status");

		Optional<SolicitacaoReserva> found = solicitacaoReservaRepository.findById(solicitacaoId);

		Assertions.assertThat(found).isPresent();
		found.ifPresent(solicitacao -> {
			Assertions.assertThat(solicitacao.getFinalidade()).isEqualTo(expectedFinalidade);
			Assertions.assertThat(solicitacao.getStatusSolicitacao()).isEqualTo(expectedStatus);
		});
	}

	@Test
	@Order(4)
	@DisplayName("Should update the status of an existing reservation request")
	void updateCreatedSolicitacaoReserva() {
		var dataForTest = generateASolicitacaoReserva();
		Long solicitacaoId = (Long) dataForTest.get("id");

		Optional<SolicitacaoReserva> toUpdate = solicitacaoReservaRepository.findById(solicitacaoId);
		Assertions.assertThat(toUpdate).isPresent();

		SolicitacaoReserva updated = toUpdate.get();
		updated.setStatusSolicitacao(StatusSolicitacao.Aprovado);
		solicitacaoReservaRepository.save(updated);

		Optional<SolicitacaoReserva> foundUpdated = solicitacaoReservaRepository.findById(solicitacaoId);
		Assertions.assertThat(foundUpdated).isPresent();
		Assertions.assertThat(foundUpdated.get().getStatusSolicitacao()).isEqualTo(StatusSolicitacao.Aprovado);
	}

	@Test
	@Order(5)
	@DisplayName("Should delete a reservation request by ID")
	void deleteCreatedSolicitacaoReserva() {
		var dataForTest = generateASolicitacaoReserva();
		Long solicitacaoId = (Long) dataForTest.get("id");

		solicitacaoReservaRepository.deleteById(solicitacaoId);

		Optional<SolicitacaoReserva> foundAfterDelete = solicitacaoReservaRepository.findById(solicitacaoId);
		Assertions.assertThat(foundAfterDelete).isNotPresent();
	}

	@AfterAll
	void clearAllData() {
		solicitacaoReservaRepository.deleteAll();
		espacoRepository.deleteAll();
		equipamentoRepository.deleteAll();
	}

	private Map<String, Object> generateASolicitacaoReserva() {
		var solicitacao = createSolicitacaoReserva();
		var savedSolicitacao = solicitacaoReservaRepository.save(solicitacao);
		Map<String, Object> data = new HashMap<>();
		data.put("id", savedSolicitacao.getIdSolicitacao());
		data.put("idEspaco", savedSolicitacao.getIdEspaco());
		data.put("finalidade", savedSolicitacao.getFinalidade());
		data.put("status", savedSolicitacao.getStatusSolicitacao());
		return data;
	}

	private SolicitacaoReserva createSolicitacaoReserva() {
		Espaco espaco = createAndSaveEspaco();
		String finalidade = faker.lorem().sentence(5);
		StatusSolicitacao status = StatusSolicitacao.Pendente;

		return new SolicitacaoReserva(null, espaco.getId(), finalidade, status);
	}

	private Espaco createAndSaveEspaco() {
		Equipamento equipamento = new Equipamento(null, faker.commerce().productName(), faker.commerce().department(),
				faker.lorem().sentence());
		equipamentoRepository.save(equipamento);

		Espaco espaco = new Espaco(null, faker.letterify("???-###"), faker.lorem().sentence(),
				faker.number().numberBetween(20, 100), Collections.singletonList(equipamento),
				"Prédio " + faker.number().numberBetween(1, 5), faker.options().option(TipoEspaco.class));
		return espacoRepository.save(espaco);
	}
}
*/