package io.github.mrtimeey.records;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RecordValidatorTest {

  public record TopLevelRecord(String name) {}

  public static class Container {
    public static record StaticNestedRecord(int count) {}
  }

  public static class NotARecord {
    String name;
  }

  public static class Outer {
    public class Inner {
      String value;
    }
  }

  @Test
  void topLevelRecord_shouldPass() {
    assertThatCode(() -> RecordValidator.ensureWithRecord(TopLevelRecord.class))
        .doesNotThrowAnyException();
  }

  @Test
  void staticNestedRecord_shouldPass() {
    assertThatCode(() -> RecordValidator.ensureWithRecord(Container.StaticNestedRecord.class))
        .doesNotThrowAnyException();
  }

  @Test
  void notARecord_shouldFail() {
    assertThatThrownBy(() -> RecordValidator.ensureWithRecord(NotARecord.class))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("is not a record");
  }

  @Test
  void requiresEnclosingInstance_detects_inner_class_correctly() {
    assertThat(RecordValidator.requiresEnclosingInstance(Outer.Inner.class)).isTrue();
  }
}
