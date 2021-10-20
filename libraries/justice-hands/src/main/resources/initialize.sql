CREATE TABLE IF NOT EXISTS `fv_jh_player`
(
  `uuid`   CHAR(36) NOT NULL PRIMARY KEY,
  `points` INT      NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS `fv_jh_sanction`
(
  `id`                   INT(255) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `player_uuid`          CHAR(255)         NOT NULL,
  `sanction_name`        CHAR(255)         NOT NULL,
  `sanction_reason`      CHAR(255)         NOT NULL,
  `sanction_points`      INT(255) UNSIGNED NOT NULL,
  `sanction_beginning_date`  TIMESTAMP         NOT NULL DEFAULT (sysdate() + interval 2 hour),
  `sanction_ending_date`    TIMESTAMP         NULL     DEFAULT NULL,
  `sanction_author_uuid` CHAR(255)         NOT NULL,
  `sanction_type`        CHAR(255)         NOT NULL,
  `sanction_state`       CHAR(255)         NOT NULL DEFAULT 'active'
);
