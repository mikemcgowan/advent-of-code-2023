# Advent of Code 2023

## What

https://adventofcode.com/2023

## How

```shell
$ ./gradlew clean build
OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended

> Task :test

com.github.mikemcgowan.adventofcode2023.days.day01.Day01Test

  Test part1() PASSED
  Test part2() PASSED

com.github.mikemcgowan.adventofcode2023.days.day02.Day02Test

  Test part1() PASSED
  Test part2() PASSED

SUCCESS: Executed 4 tests in 1.5s


Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/8.5/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD SUCCESSFUL in 4s
9 actionable tasks: 9 executed

$ find -name "*.jar"
./gradle/wrapper/gradle-wrapper.jar
./build/libs/advent-of-code-2023-0.0.1-SNAPSHOT.jar
./build/libs/advent-of-code-2023-0.0.1-SNAPSHOT-plain.jar

$ java -jar ./build/libs/advent-of-code-2023-0.0.1-SNAPSHOT.jar
2023-12-02T09:32:35.913Z  INFO 16383 --- [           main] c.g.m.adventofcode2023.ApplicationKt     : Starting ApplicationKt v0.0.1-SNAPSHOT using Java 17.0.8 with PID 16383 (/home/mikemcgowan/Projects/github-mikemcgowan/advent-of-code-2023/build/libs/advent-of-code-2023-0.0.1-SNAPSHOT.jar started by mikemcgowan in /home/mikemcgowan/Projects/github-mikemcgowan/advent-of-code-2023)
2023-12-02T09:32:35.923Z  INFO 16383 --- [           main] c.g.m.adventofcode2023.ApplicationKt     : No active profile set, falling back to 1 default profile: "default"
2023-12-02T09:32:37.226Z  INFO 16383 --- [           main] c.g.m.adventofcode2023.ApplicationKt     : Started ApplicationKt in 1.751 seconds (process running for 2.163)
shell:>day1
Part1: spoiler
Part2: spoiler
shell:>day2
Part1: spoiler
Part2: spoiler
shell:>
```
