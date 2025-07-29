# record-wither

[![Maven Central](https://img.shields.io/maven-central/v/io.github.mrtimeey/record-wither.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/io.github.mrtimeey/record-wither)
[![Build](https://github.com/mrtimeey/record-wither/actions/workflows/ci.yml/badge.svg)](https://github.com/mrtimeey/record-wither/actions)
[![javadoc](https://javadoc.io/badge2/io.github.mrtimeey/record-wither/javadoc.svg)](https://javadoc.io/doc/io.github.mrtimeey/record-wither)
[![License](https://img.shields.io/github/license/MrTimeey/record-wither.svg)](https://github.com/MrTimeey/record-wither/blob/main/LICENSE)
![Java](https://img.shields.io/badge/Java-17+-green)

A small Java library to make working with immutable Java Records easier.  
It provides a convenient **with** method for records to modify single fields immutably.

---

## Installation

Add the dependency from **Maven Central**:

```xml
<dependency>
  <groupId>io.github.mrtimeey</groupId>
  <artifactId>record-wither</artifactId>
  <version>${record-wither.version}</version>
</dependency>
```

---

## Usage

### 1. Make your record implement `Withable`

```java
import io.github.mrtimeey.records.Withable;

public record User(String name, int age) implements Withable<User> {}
```

### 2. Use the `with` method

```java
User user = new User("Alice", 30);

// Change one field immutably
User older = user.with(User::age, 31);

System.out.println(older); // User[name=Alice, age=31]
```

---

## Why?

Records in Java are immutable by design.  
While this is great, it means that modifying a single field requires calling the constructor with all parameters again.

`record-wither` provides an easy way to do this, inspired by "with" methods in other languages.

---

## Development

- Built with Java 17+
- Tested with JUnit 5 and AssertJ
- Published to [Maven Central](https://central.sonatype.com/artifact/io.github.mrtimeey/record-wither)

Run the tests:

```bash
mvn clean verify
```

---

## License

This project is licensed under the [MIT License](LICENSE).

## Contributing

Contributions are welcome! 🎉  
Please read the [Contributing Guidelines](.github/CONTRIBUTING.md) for details on our development process, coding standards, and how to propose changes.

By participating in this project, you are expected to uphold our [Code of Conduct](.github/CODE_OF_CONDUCT.md).
