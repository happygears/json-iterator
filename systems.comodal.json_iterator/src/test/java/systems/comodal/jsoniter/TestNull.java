package systems.comodal.jsoniter;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

final class TestNull {

  @Test
  void test_null_as_String() throws IOException {
    var iter = JsonIterator.parse("{\"field\":null}");
    iter.readObject();
    assertNull(iter.readString());

    iter = JsonIterator.parse("{\"field\":null}");
    assertNull(iter.applyObject(TRUE, ((context, len, buf, jsonIterator) -> {
      assertEquals("field", new String(buf, 0, len));
      assertEquals(TRUE, context);
      return jsonIterator.readString();
    })));
  }

  @Test
  void test_null_as_Object() throws IOException {
    var iter = JsonIterator.parse("{\"field\":null}");
    iter.readObject();
    assertNull(iter.readObject());
  }

  @Test
  void test_null_as_BigDecimal() throws IOException {
    var iter = JsonIterator.parse("{\"field\":null}");
    iter.readObject();
    assertNull(iter.readBigDecimal());
  }

  @Test
  void test_null_as_BigInteger() throws IOException {
    var iter = JsonIterator.parse("{\"field\":null}");
    iter.readObject();
    assertNull(iter.readBigInteger());
  }
}
