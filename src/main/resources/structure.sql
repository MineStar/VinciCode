-- -----------------------------------------------------
-- Table `message`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `message` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `sender` VARCHAR(255) NOT NULL ,
  `receiver` VARCHAR(255) NOT NULL ,
  `timestamp` BIGINT NOT NULL ,
  `type` TINYINT NOT NULL ,
  `text` TEXT NOT NULL ,
  `isRead` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `sender` (`sender` ASC) ,
  INDEX `target` (`target` ASC) )
ENGINE = InnoDB;