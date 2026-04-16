package ucsal.oferta.professor.model.enums;

import java.time.DayOfWeek;
import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ucsal.oferta.exception.sql.DataViolationException;

@Getter
@RequiredArgsConstructor
public enum Dia {
    
	SEG(DayOfWeek.MONDAY, "Segunda"),
    TER(DayOfWeek.TUESDAY, "Terça"),
    QUA(DayOfWeek.WEDNESDAY, "Quarta"),
    QUI(DayOfWeek.THURSDAY, "Quinta"),
    SEX(DayOfWeek.FRIDAY, "Sexta"),
    SAB(DayOfWeek.SATURDAY, "Sabado"),
    DOM(DayOfWeek.SUNDAY, "Domingo");

	private final DayOfWeek dayOfWeek;
	private final String weekAsString;

	public static Dia from(String value) {
		if (value == null || value.isBlank()) {
			throw new DataViolationException("Dia cannot be null or blank");
		}

		return Arrays.stream(values())
				.filter(d -> d.name().equalsIgnoreCase(value) || d.getWeekAsString().equalsIgnoreCase(value))
				.findFirst().orElseThrow(() -> new IllegalArgumentException("Invalid dia: " + value));
	}
	
}
