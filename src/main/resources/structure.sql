-- -----------------------------------------------------
-- Table `message`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `message` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `sender` TEXT NOT NULL ,
  `target` TEXT NOT NULL ,
  `prefix` TEXT NOT NULL ,
  `message` TEXT NOT NULL ,
  `prefixColor` TEXT NOT NULL ,
  `messageColor` TEXT NOT NULL ,
  `timestamp` BIGINT NOT NULL ,
  `isOfficial` TINYINT(1) NOT NULL ,
  `isRead` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;
