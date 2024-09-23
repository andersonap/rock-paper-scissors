DROP TABLE IF EXISTS game_config;
DROP TABLE IF EXISTS games;

CREATE TABLE game_config (id int not null auto_increment,
                          user_id int not null,
                          opponent varchar(50),
                          PRIMARY KEY (id));

CREATE TABLE games (id int not null auto_increment,
                    user_id int not null,
                    status varchar(50) not null,
                    date timestamp,
                    opponent varchar(50),
                    player_move varchar(50),
                    opponent_move varchar(50),
                    PRIMARY KEY (id));