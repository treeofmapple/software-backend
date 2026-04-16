package ucsal.oferta.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.datafaker.Faker;
import ucsal.oferta.infraestrutura.model.Equipamento;
import ucsal.oferta.infraestrutura.model.Espaco;
import ucsal.oferta.infraestrutura.model.TipoEquipamento;
import ucsal.oferta.infraestrutura.model.TipoEspaco;
import ucsal.oferta.infraestrutura.repository.EquipamentoRepository;
import ucsal.oferta.infraestrutura.repository.EspacoRepository;
import ucsal.oferta.interfaces.ContainerJpaTest;

@Order(4)
@ContainerJpaTest
@DisplayName("Espaco Repository Test")
public class EspacoRepositoryTest {

	@Autowired
	private EspacoRepository espacoRepository;

	@Autowired
	private EquipamentoRepository equipamentoRepository;

	@Autowired
	private Faker faker;

	@Test
	@Order(1)
	@DisplayName("Should create a new space successfully")
	void createEspacoTest() {
		var dataForTest = generateAnEspaco();
		Long espacoId = (Long) dataForTest.get("id");
		Assertions.assertThat(espacoId).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("Should create and then find a space by ID")
	void createEspacoAndFind() {
		var dataForTest = generateAnEspaco();
		Long espacoId = (Long) dataForTest.get("id");

		Optional<Espaco> found = espacoRepository.findById(espacoId);
		Assertions.assertThat(found).isPresent();
	}

	@Test
	@Order(3)
	@DisplayName("Should find a created space and verify its properties")
	void findEspacoCreated() {
		var dataForTest = generateAnEspaco();
		Long espacoId = (Long) dataForTest.get("id");
		String expectedSigla = (String) dataForTest.get("sigla");
		TipoEspaco expectedTipo = (TipoEspaco) dataForTest.get("tipoEspaco");

		Optional<Espaco> found = espacoRepository.findById(espacoId);

		Assertions.assertThat(found).isPresent();
		found.ifPresent(espaco -> {
			Assertions.assertThat(espaco.getSigla()).isEqualTo(expectedSigla);
			Assertions.assertThat(espaco.getTipoEspaco()).isEqualTo(expectedTipo);
			Assertions.assertThat(espaco.getEquipamentos()).isNotEmpty();
		});
	}

	@Test
	@Order(4)
	@DisplayName("Should update the description of an existing space")
	void updateCreatedEspaco() {
		var dataForTest = generateAnEspaco();
		Long espacoId = (Long) dataForTest.get("id");

		String newDescricao = faker.lorem().sentence(10);

		Optional<Espaco> toUpdate = espacoRepository.findById(espacoId);
		Assertions.assertThat(toUpdate).isPresent();

		Espaco updated = toUpdate.get();
		updated.setDescricao(newDescricao);
		espacoRepository.save(updated);

		Optional<Espaco> foundUpdated = espacoRepository.findById(espacoId);
		Assertions.assertThat(foundUpdated).isPresent();
		Assertions.assertThat(foundUpdated.get().getDescricao()).isEqualTo(newDescricao);
	}

	@Test
	@Order(5)
	@DisplayName("Should delete a space by ID")
	void deleteCreatedEspaco() {
		var dataForTest = generateAnEspaco();
		Long espacoId = (Long) dataForTest.get("id");

		espacoRepository.deleteById(espacoId);

		Optional<Espaco> foundAfterDelete = espacoRepository.findById(espacoId);
		Assertions.assertThat(foundAfterDelete).isNotPresent();
	}

	@AfterAll
	void clearAllData() {
		espacoRepository.deleteAll();
		equipamentoRepository.deleteAll();
	}

	private Map<String, Object> generateAnEspaco() {
		var espaco = createEspaco();
		var savedEspaco = espacoRepository.save(espaco);
		Map<String, Object> data = new HashMap<>();
		data.put("id", savedEspaco.getId());
		data.put("sigla", savedEspaco.getSigla());
		data.put("descricao", savedEspaco.getDescricao());
		data.put("capacidadeMaxima", savedEspaco.getCapacidadeMaxima());
		data.put("localizacao", savedEspaco.getLocalizacao());
		data.put("tipoEspaco", savedEspaco.getTipoEspaco());
		return data;
	}

	private Espaco createEspaco() {
		String sigla = faker.letterify("???-###");
		String descricao = faker.lorem().sentence();
		int capacidadeMaxima = faker.number().numberBetween(20, 100);
		TipoEquipamento tipoEquip = faker.options().option(TipoEquipamento.class);

		Equipamento equipamento = new Equipamento(faker.commerce().productName(), tipoEquip, faker.lorem().sentence(),
				null);
		equipamentoRepository.save(equipamento);

		List<Equipamento> recursos = new ArrayList<>(Collections.singletonList(equipamento));

		String localizacao = "Prédio " + faker.number().numberBetween(1, 5) + ", Sala "
				+ faker.number().numberBetween(101, 599);
		TipoEspaco tipoEspaco = faker.options().option(TipoEspaco.class);

		Espaco espaco = new Espaco();
		espaco.setSigla(sigla);
		espaco.setDescricao(descricao);
		espaco.setCapacidadeMaxima(capacidadeMaxima);
		espaco.setEquipamentos(recursos);
		espaco.setLocalizacao(localizacao);
		espaco.setTipoEspaco(tipoEspaco);
		return espaco;
	}

}
