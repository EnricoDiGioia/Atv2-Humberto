package espm.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataRepository extends CrudRepository<DataModel, Long> {

    List<DataModel> findBySensor(String sensor);

    List<DataModel> findBySensorAndIdSensor(String sensor, Short idSensor);

    List<DataModel> findBySensorAndDataBetween(String sensor, LocalDateTime inicio, LocalDateTime fim);

}
