package systems.comodal.jsoniter.jmh.data.exchange;

import java.time.Duration;

public interface RateLimit {

  static RateLimit.Builder build() {
    return new RateLimitVal.RateLimitBuilder();
  }

  enum Type {
    REQUEST_WEIGHT, ORDERS, RAW_REQUESTS
  }

  Type getType();

  Duration getInterval();

  int getLimit();

  interface Builder {

    RateLimit create();

    Builder type(final String type);

    Builder intervalUnit(final String intervalUnit);

    Builder interval(final long interval);

    Builder limit(final int limit);
  }
}
