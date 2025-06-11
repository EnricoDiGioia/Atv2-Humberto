CREATE TABLE data (
  id BIGSERIAL PRIMARY KEY,
  registro_id BIGINT NOT NULL,
  data TIMESTAMP NOT NULL,
  id_sensor SMALLINT NOT NULL,
  id_especialidade SMALLINT NOT NULL,
  delta INTEGER NOT NULL,              -- Alterações no valor de pessoas
  pessoas SMALLINT NOT NULL,
  luminosidade REAL NOT NULL,
  umidade REAL NOT NULL,
  temperatura REAL NOT NULL,
  sensor TEXT NOT NULL,                -- Tipo ou nome do sensor
  INDEX_data_id_sensor_id_especialidade_id_sensor_idx 
    (data, id_sensor, id_especialidade)
);
