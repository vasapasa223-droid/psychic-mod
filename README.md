# Psychic Powers (Fabric 1.21.1)

Предмети-сили (креатив, вкладка "Інструменти"). ПКМ - застосувати:
- PSI Blast - урон + відкидання цілі
- Пірокінез - підпал цілі/блока
- Левітація - ціль злітає; з Shift - ти сам
- Телекінез - притягує ціль; з Shift - відкидає

## Збірка
У проєкті немає gradle wrapper. Найпростіше: склонуй
https://github.com/FabricMC/fabric-example-mod (гілка 1.21),
скопіюй у нього папку src/ та файли build.gradle і gradle.properties,
потім `./gradlew build` -> jar у build/libs/.
