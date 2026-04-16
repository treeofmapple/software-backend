package ucsal.oferta.repository;

import java.util.HashMap;
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
import ucsal.oferta.infraestrutura.model.TipoEquipamento;
import ucsal.oferta.infraestrutura.repository.EquipamentoRepository;
import ucsal.oferta.interfaces.ContainerJpaTest;

@Order(3)
@ContainerJpaTest
@DisplayName("Equipamento Repository Test")
public class EquipamentoRepositoryTest {

	@Autowired
	private EquipamentoRepository equipamentoRepository;

	@Autowired
	private Faker faker;

	@Test
	@Order(1)
	@DisplayName("Should create a new equipment successfully")
	void createEquipamentoTest() {
		var dataForTest = generateAnEquipamento();
		Long equipamentoId = (Long) dataForTest.get("id");
		Assertions.assertThat(equipamentoId).isNotNull();
	}

	@Test
	@Order(2)
	@DisplayName("Should create and then find an equipment by ID")
	void createEquipamentoAndFind() {
		var dataForTest = generateAnEquipamento();
		Long equipamentoId = (Long) dataForTest.get("id");

		Optional<Equipamento> found = equipamentoRepository.findById(equipamentoId);
		Assertions.assertThat(found).isPresent();
	}

	@Test
	@Order(3)
	@DisplayName("Should find a created equipment and verify its properties")
	void findEquipamentoCreated() {
		var dataForTest = generateAnEquipamento();
		Long equipamentoId = (Long) dataForTest.get("id");
		String expectedNome = (String) dataForTest.get("nome");
	    TipoEquipamento expectedTipo = (TipoEquipamento) dataForTest.get("tipo");

		Optional<Equipamento> found = equipamentoRepository.findById(equipamentoId);

		Assertions.assertThat(found).isPresent();
		found.ifPresent(equipamento -> {
			Assertions.assertThat(equipamento.getNome()).isEqualTo(expectedNome);
			Assertions.assertThat(equipamento.getTipoEquipamento()).isEqualTo(expectedTipo);
		});
	}

	@Test
	@Order(4)
	@DisplayName("Should update the name of an existing equipment")
	void updateCreatedEquipamento() {
		var dataForTest = generateAnEquipamento();
		Long equipamentoId = (Long) dataForTest.get("id");

		String newNome = faker.commerce().productName();

		Optional<Equipamento> toUpdate = equipamentoRepository.findById(equipamentoId);
		Assertions.assertThat(toUpdate).isPresent();

		Equipamento updated = toUpdate.get();
		updated.setNome(newNome);
		equipamentoRepository.save(updated);

		Optional<Equipamento> foundUpdated = equipamentoRepository.findById(equipamentoId);
		Assertions.assertThat(foundUpdated).isPresent();
		Assertions.assertThat(foundUpdated.get().getNome()).isEqualTo(newNome);
	}

	@Test
	@Order(5)
	@DisplayName("Should delete an equipment by ID")
	void deleteCreatedEquipamento() {
		var dataForTest = generateAnEquipamento();
		Long equipamentoId = (Long) dataForTest.get("id");

		equipamentoRepository.deleteById(equipamentoId);

		Optional<Equipamento> foundAfterDelete = equipamentoRepository.findById(equipamentoId);
		Assertions.assertThat(foundAfterDelete).isNotPresent();
	}

	@AfterAll
	void clearAllData() {
		equipamentoRepository.deleteAll();
	}

	private Map<String, Object> generateAnEquipamento() {
		var equipamento = createEquipamento();
		var savedEquipamento = equipamentoRepository.save(equipamento);
		Map<String, Object> data = new HashMap<>();
		data.put("id", savedEquipamento.getId());
		data.put("nome", savedEquipamento.getNome());
		data.put("tipo", savedEquipamento.getTipoEquipamento());
		data.put("descricao", savedEquipamento.getDescricao());
		return data;
	}

	private Equipamento createEquipamento() {
		String nome = faker.commerce().productName();
		TipoEquipamento tipo = faker.options().option(TipoEquipamento.class);
		String descricao = faker.lorem().sentence();
		return new Equipamento(nome, tipo, descricao, null);
	}
}
