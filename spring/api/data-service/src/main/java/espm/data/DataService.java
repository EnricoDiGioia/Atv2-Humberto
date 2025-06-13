package espm.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class DataService {

    private static final String API_URL_TEMPLATE =
            "https://iagen.espm.br/sensores/dados?sensor=%s&id_inferior=%d&data_inicial=2025-06-02&data_final=2025-06-03";

    private final DataRepository dataRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void acquire(String sensor) {
        log.info("Iniciando aquisição de dados para o sensor: {}", sensor);

        Long lastRegistroId = dataRepository.findBySensor(sensor).stream()
                .map(DataModel::registroId)
                .max(Long::compareTo)
                .orElse(0L);

        String uri = String.format(API_URL_TEMPLATE, sensor, lastRegistroId);
        log.info("Buscando novos dados da API com URI: {}", uri);

        List<Map<String, ?>> responseData = requestFromApi(uri);
        log.info("Total de registros recebidos: {}", responseData.size());

        saveNewData(responseData, sensor);
    }


    public List<Map<String, ?>> export(String sensor) {
        log.info("Exportando todos os dados para o sensor: {}", sensor);

        String uri = String.format(API_URL_TEMPLATE, sensor, 0);
        return requestFromApi(uri);
    }


    private List<Map<String, ?>> requestFromApi(String uri) {
        try {
            return restTemplate.getForObject(uri, List.class);
        } catch (Exception e) {
            log.warn("Erro ao consultar a API: {}", e.getMessage());
            return Collections.emptyList();
        }
    }


    private void saveNewData(List<Map<String, ?>> data, String sensor) {
        List<DataModel> novosRegistros = new ArrayList<>();

        for (Map<String, ?> item : data) {
            try {
                DataModel model = DataModel.builder()
                        .registroId(asLong(item.get("id")))
                        .data(parseDate(item.get("data")))
                        .idSensor(asShort(item.get("id_sensor")))
                        .idEspecialidade(asShort(item.get("id_especialidade")))
                        .delta(asInteger(item.get("delta")))
                        .pessoas(asShort(item.get("pessoas")))
                        .luminosidade(asFloat(item.get("luminosidade")))
                        .umidade(asFloat(item.get("umidade")))
                        .temperatura(asFloat(item.get("temperatura")))
                        .sensor(sensor)
                        .build();

                novosRegistros.add(model);
            } catch (Exception e) {
                log.warn("Erro ao converter registro: {}", e.getMessage());
            }
        }

        if (!novosRegistros.isEmpty()) {
            dataRepository.saveAll(novosRegistros);
            log.info("Total de registros salvos: {}", novosRegistros.size());
        } else {
            log.info("Nenhum novo registro válido encontrado.");
        }
    }

    // Métodos auxiliares de conversão

    private LocalDateTime parseDate(Object value) {
        if (value instanceof String s) {
            return LocalDateTime.parse(s, formatter);
        }
        return null;
    }

    private Long asLong(Object value) {
        if (value instanceof Number n) return n.longValue();
        if (value instanceof String s) return Long.parseLong(s);
        return null;
    }

    private Integer asInteger(Object value) {
        if (value instanceof Number n) return n.intValue();
        if (value instanceof String s) return Integer.parseInt(s);
        return null;
    }

    private Short asShort(Object value) {
        if (value instanceof Number n) return n.shortValue();
        if (value instanceof String s) return Short.parseShort(s);
        return null;
    }

    private Float asFloat(Object value) {
        if (value instanceof Number n) return n.floatValue();
        if (value instanceof String s) return Float.parseFloat(s);
        return null;
    }
}
