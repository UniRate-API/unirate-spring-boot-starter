# UniRate Spring Boot Starter

Spring Boot auto-configuration for the [UniRate API](https://unirateapi.com) — free
currency exchange rates, conversion, historical data, and VAT rates. Drop the
starter on your classpath, set one property, and inject a ready-to-use
`UniRateClient`.

Built on the official [`com.unirateapi:unirate-api`](https://central.sonatype.com/artifact/com.unirateapi/unirate-api)
Java client. Get a free API key at **[unirateapi.com](https://unirateapi.com)**.

## Install

Maven:

```xml
<dependency>
    <groupId>com.unirateapi</groupId>
    <artifactId>unirate-spring-boot-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```

Gradle:

```groovy
implementation 'com.unirateapi:unirate-spring-boot-starter:0.1.0'
```

Requires Spring Boot 3.x and Java 17+.

## Configure

```yaml
unirate:
  api-key: ${UNIRATE_API_KEY}        # required — no key, no client bean
  base-url: https://api.unirateapi.com   # optional
  timeout: 30s                           # optional
```

| Property          | Default                        | Description                                  |
|-------------------|--------------------------------|----------------------------------------------|
| `unirate.api-key` | —                              | Your UniRate API key (required).             |
| `unirate.base-url`| `https://api.unirateapi.com`   | API base URL. Handy for tests/mocks.         |
| `unirate.timeout` | `30s`                          | Per-request timeout (Spring duration syntax).|

The `UniRateClient` bean is created **only** when `unirate.api-key` is set, and
only if your application hasn't already defined its own `UniRateClient` bean
(`@ConditionalOnMissingBean`) — so the starter is safe to add without surprises.

## Use

```java
import com.unirateapi.client.UniRateClient;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    private final UniRateClient unirate;

    public PricingService(UniRateClient unirate) {
        this.unirate = unirate;
    }

    public double priceInEur(double usd) {
        return unirate.convert(usd, "USD", "EUR");
    }

    public double usdToGbp() {
        return unirate.getRate("USD", "GBP");
    }
}
```

The injected client exposes the full UniRate surface — `getRate`, `convert`,
`getAllRates`, `getSupportedCurrencies`, `getVatRates`/`getVatRate`, plus the
Pro-gated historical/time-series methods. See the
[client docs](https://github.com/UniRate-API/unirate-api-java) for the complete
method list.

## License

MIT — see [LICENSE](LICENSE).
