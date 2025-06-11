package espm.data;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Table(name = "data")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class DataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "registro_id", nullable = false)
    private Long registroId;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Column(name = "id_sensor", nullable = false)
    private Short idSensor;

    @Column(name = "id_especialidade", nullable = false)
    private Short idEspecialidade;

    @Column(name = "delta", nullable = false)
    private Integer delta;

    @Column(name = "pessoas", nullable = false)
    private Short pessoas;

    @Column(name = "luminosidade", nullable = false)
    private Float luminosidade;

    @Column(name = "umidade", nullable = false)
    private Float umidade;

    @Column(name = "temperatura", nullable = false)
    private Float temperatura;

    @Column(name = "sensor", nullable = false)
    private String sensor;

    // Construtor que recebe o domínio
    public DataModel(Data d) {
        this.id = d.id();
        this.registroId = d.registroId();
        this.data = d.data();
        this.idSensor = d.idSensor();
        this.idEspecialidade = d.idEspecialidade();
        this.delta = d.delta();
        this.pessoas = d.pessoas();
        this.luminosidade = d.luminosidade();
        this.umidade = d.umidade();
        this.temperatura = d.temperatura();
        this.sensor = d.sensor();
    }

    // Método para converter para o domínio
    public Data to() {
        return Data.builder()
                .id(id)
                .registroId(registroId)
                .data(data)
                .idSensor(idSensor)
                .idEspecialidade(idEspecialidade)
                .delta(delta)
                .pessoas(pessoas)
                .luminosidade(luminosidade)
                .umidade(umidade)
                .temperatura(temperatura)
                .sensor(sensor)
                .build();
    }
}
