package ucsal.oferta.logic.common;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ListToJsonConversion implements AttributeConverter<List<String>, String> {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao converter lista para JSON", e);
		}
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.isEmpty())
			return List.of();
		try {
			return objectMapper.readValue(dbData, new TypeReference<List<String>>() {});
		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler JSON do banco", e);
		}
	}
}
