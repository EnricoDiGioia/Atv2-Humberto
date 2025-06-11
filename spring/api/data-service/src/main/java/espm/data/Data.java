package espm.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Accessors(fluent = true)
public class Data {

    private Long id;
    private Long registroId;
    private LocalDateTime data;
    private Short idSensor;
    private Short idEspecialidade;
    private Integer delta;
    private Short pessoas;
    private Float luminosidade;
    private Float umidade;
    private Float temperatura;
    private String sensor;

}
