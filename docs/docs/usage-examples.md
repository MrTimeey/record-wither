# Usage Examples

The `record-wither` library makes it simple to create modified copies of immutable Java records.

Below are practical examples derived directly from the test suite:

```java
import io.github.mrtimeey.records.Withable;

public enum Status {
    ACTIVE,
    INACTIVE
}

public record TestRecord(String name, String description, int count, boolean active, double score, Status status)
        implements Withable<TestRecord> {}
```

## Example: Updating an `int` field
```java
TestRecord base = new TestRecord("Test", "RecordWither", 3, true, 1.5, Status.ACTIVE);

TestRecord updated = base.with(TestRecord::count, 42);

// base remains unchanged
System.out.println(base.count()); // 3

// updated contains the new value
System.out.println(updated.count()); // 42
```

## Example: Updating a `String` field
```java
TestRecord updated = base.with(TestRecord::name, "Neu");

System.out.println(base.name());   // Test
System.out.println(updated.name()); // Neu
```

## Example: Updating another `String` field
```java
TestRecord updated = base.with(TestRecord::description, "NeuDesc");

System.out.println(base.description());   // RecordWither
System.out.println(updated.description()); // NeuDesc
```

## Example: Updating a `boolean` field
```java
TestRecord updated = base.with(TestRecord::active, false);

System.out.println(base.active());   // true
System.out.println(updated.active()); // false
```

## Example: Updating a `double` field
```java
TestRecord updated = base.with(TestRecord::score, 9.99);

System.out.println(base.score());   // 1.5
System.out.println(updated.score()); // 9.99
```

## Example: Updating an `enum` field
```java
TestRecord updated = base.with(TestRecord::status, Status.INACTIVE);

System.out.println(base.status());   // ACTIVE
System.out.println(updated.status()); // INACTIVE
```
