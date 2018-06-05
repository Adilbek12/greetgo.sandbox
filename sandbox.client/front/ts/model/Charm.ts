export class Charm {
  public name: string;
  public description: string;
  public energy: number;

  public static getRandomCharms(): Charm[] {
    let charms: Charm[] = [];

    let charm = new Charm();
    charm.name = "Гипертимный";
    charm.description = "Таким людям свойственны общительность, словоохотливость, развитая мимика. Часто легкомысленны и раздражительны, очень инициативны и энергичны.";
    charm.energy = 1.4;
    charms.push(charm);

    charm = new Charm();
    charm.name = "Дистимный";
    charm.description = "Это замкнутые, пессимистично настроенные люди. Они не любят шумных компаний, чувствуют себя в них некомфортно. Очень ценят своих друзей, имеют обостренное чувство несправедливости. Часто неповоротливы и медлительны в принятии решений.";
    charm.energy = 1.4;
    charms.push(charm);

    charm = new Charm();
    charm.name = "Циклоидный";
    charm.description = "Настроение у этих людей меняется часто, в зависимости от которого они то гиперобщительны, то невероятно замкнуты.";
    charm.energy = 1.4;
    charms.push(charm);

    charm = new Charm();
    charm.name = "Возбудимый";
    charm.description = "Это конфликтные, тяжелые в общении люди. В семье обычно властны, в коллективе неуживчивы. В спокойном состоянии аккуратны и внимательны, а в дурном настроении вспыльчивы, раздражительны.";
    charm.energy = 1.4;
    charms.push(charm);

    charm = new Charm();
    charm.name = "Застревающий";
    charm.description = "Такие люди несговорчивы, любят всех поучать, часто провоцируют конфликты. Предъявляют высокие требования к себе и окружающим.";
    charm.energy = 1.4;
    charms.push(charm);

    charm = new Charm();
    charm.name = "Педантичный";
    charm.description = "Это типичные бюрократы, обнаруживающие чрезмерное внимание к мелочам. Не любят быть лидерами, очень добросовестны, не упускают возможности поворчать.";
    charm.energy = 1.4;
    charms.push(charm);

    charm = new Charm();
    charm.name = "Тревожный";
    charm.description = "Люди с таким характером неуверенны в себе, поэтому не любят конфликтовать, а в случае споров ищут поддержки у других людей. Они дружелюбны и самокритичны, но этот тип характера не такой волевой, поэтому такие люди часто являются предметом шуток и насмешек.";
    charm.energy = 1.4;
    charms.push(charm);


    charm = new Charm();
    charm.name = "Демонстративный";
    charm.description = "Это контактные личности, умеющие приспособиться к любой ситуации, обожают интриги. Часто вызывают раздражение в других своей самоуверенностью, из-за чего случаются конфликты. Артистичны, обходительны, обладают неординарным мышлением, бывают эгоистичны, хвастливы и лицемерны.";
    charm.energy = 1.4;
    charms.push(charm);

    return charms;
  }

  public assign(o: any): Charm {
    this.name = o.name;
    this.description = o.description;
    this.energy = o.energy;
    return this;
  }

  public static copy(a: any): Charm {
    let ret = new Charm();
    ret.assign(a);
    return ret;
  }
}