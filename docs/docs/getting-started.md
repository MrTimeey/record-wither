# Getting Started with Record Wither

`record-wither` makes working with immutable Java records easier by generating "with" methods.

## Installation

### Maven
```xml
<dependency>
  <groupId>io.github.mrtimeey</groupId>
  <artifactId>record-wither</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle
```groovy
implementation 'io.github.mrtimeey:record-wither:1.0.0'
```

## Basic Example

```java
import io.github.mrtimeey.records.Withable;

public record Person(String name, int age) implements Withable<Person> {
}

class Example {
    public static void main(String[] args) {
        Person p1 = new Person("Alice", 30);
        Person p2 = p1.with(Person::name, "Bob");

        System.out.println(p1); // Person[name=Alice, age=30]
        System.out.println(p2); // Person[name=Bob, age=30]
    }
}
```
