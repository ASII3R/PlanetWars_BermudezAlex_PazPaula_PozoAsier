CREATE DATABASE planetwars;
USE planetwars;

-- Tabla principal de estadísticas del planeta
CREATE TABLE Planet_stats (
    planet_id INT NOT NULL PRIMARY KEY,
    name VARCHAR(100),
    resource_metal_amount INT,
    resource_deuterion_amount INT,
    technology_defense_level INT,
    technology_attack_level INT,
    battle_counter INT,
    missile_launcher_remaining INT,
    ion_cannon_remaining INT,
    plasma_cannon_remaining INT,
    light_hunter_remaining INT,
    heavy_hunter_remaining INT,
    battleship_remaining INT,
    armored_ship_remaining INT
);

-- Estadísticas generales de la batalla
CREATE TABLE Battle_stats (
    planet_id INT NOT NULL,
    num_battle INT NOT NULL,
    resource_metal_acquired INT,
    resource_deuterion_acquired INT,
    PRIMARY KEY (planet_id, num_battle),
    FOREIGN KEY (planet_id) REFERENCES Planet_stats(planet_id)
);

-- Registro detallado de cada entrada en la batalla
CREATE TABLE Battle_log (
    planet_id INT NOT NULL,
    num_battle INT NOT NULL,
    num_line INT NOT NULL,
    log_entry TEXT,
    PRIMARY KEY (planet_id, num_battle, num_line),
    FOREIGN KEY (planet_id, num_battle) REFERENCES Battle_stats(planet_id, num_battle)
);

-- Defensas construidas y destruidas por tipo
CREATE TABLE Planet_battle_defense (
    planet_id INT NOT NULL,
    num_battle INT NOT NULL,
    missile_launcher_built INT,
    missile_launcher_destroyed INT,
    ion_cannon_built INT,
    ion_cannon_destroyed INT,
    plasma_cannon_built INT,
    plasma_cannon_destroyed INT,
    PRIMARY KEY (planet_id, num_battle),
    FOREIGN KEY (planet_id) REFERENCES Planet_stats(planet_id)
);

-- Tropas propias construidas y destruidas
CREATE TABLE Planet_battle_army (
    planet_id INT NOT NULL,
    num_battle INT NOT NULL,
    light_hunter_built INT,
    light_hunter_destroyed INT,
    heavy_hunter_built INT,
    heavy_hunter_destroyed INT,
    battleship_built INT,
    battleship_destroyed INT,
    armored_ship_built INT,
    armored_ship_destroyed INT,
    PRIMARY KEY (planet_id, num_battle),
    FOREIGN KEY (planet_id) REFERENCES Planet_stats(planet_id)
);

-- Amenazas del enemigo
CREATE TABLE Enemy_army (
    planet_id INT NOT NULL,
    num_battle INT NOT NULL,
    light_hunter_threat INT,
    light_hunter_destroyed INT,
    heavy_hunter_threat INT,
    heavy_hunter_destroyed INT,
    battleship_threat INT,
    battleship_destroyed INT,
    armored_ship_threat INT,
    armored_ship_destroyed INT,
    PRIMARY KEY (planet_id, num_battle),
    FOREIGN KEY (planet_id) REFERENCES Planet_stats(planet_id)
);