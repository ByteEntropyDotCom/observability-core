## 🎯 Observability-Core

The Observability-Core is the central telemetry engine for the ByteEntropy ecosystem. It provides automated distributed tracing, performance metrics, and a proactive alerting system to ensure 99.99% system availability.

## 🚀 Features
1. Distributed Tracing: Automated trace propagation using Micrometer Tracing and Zipkin.

2. Performance Metrics: Real-time JVM and application metrics exported to Prometheus.

3. AOP Observations: Custom @Observed aspect for sub-millisecond execution timing.

4. Log Correlation: Trace IDs automatically injected into SLF4J logs for instant debugging.

5. Self-Health Alerts: Automated alerts triggered on system startup and health degradation.

## 🏗️ Project Structure

```Plaintext
├── .github/workflows/   # Automated CI/CD Pipeline
├── docker-compose.yml   # Infrastructure (Prometheus, Grafana, Zipkin)
├── prometheus.yml       # Scrape configuration
├── Dockerfile           # Optimized JRE 21 Container
├── src/                 # Java 21 / Spring Boot 3.4.1 Source
```

## 🛠️ Getting Started
### Prerequisites

Java 21
Docker & Docker Compose
Maven 3.9+

### 1. Launch Infrastructure

```Bash
docker-compose up -d
```

### 2. Build and Test

```Bash
mvn clean install
```

### 3. Run the Core

```Bash
mvn spring-boot:run
```

## 📊 Monitoring Dashboard

Tool	URL	Purpose

1. App Endpoint	http://localhost:8083	Application Entry
2. Prometheus	http://localhost:9090	Metrics Query Engine
3. Zipkin	http://localhost:9411	Distributed Trace Maps
4. Grafana	http://localhost:3000	Visual Dashboards (admin/admin)

## 🤖 CI/CD Pipeline
The included GitHub Action performs:

1. Code Validation: Compiles and runs all ObservabilityUseCases.

2. Artifact Archiving: Stores the production-ready JAR.

3. Docker Delivery: Automatically builds the Docker image on successful main branch merges.

---

## How a Developer Integrates It

If they already have an existing service, they would use your project like this:

### Step A: Add your "Core" as a dependency

They add your JAR to their pom.xml:
```
XML
<dependency>
    <groupId>com.byteentropy</groupId>
    <artifactId>observability-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Step B: Use your Annotations

Instead of writing complex logic, they just "tag" their code:

```Java
@Service
public class PaymentService {

    @Observed(name = "process.credit.card") // Uses your ObservationConfig
    public void pay() {
        // Business logic here...
    }
}
```

### Step C: Watch the Dashboard

They open the Grafana dashboard you provided in the docker-compose.yml. They can now see:

```
"How many payments failed today?"

"Which service is the slowest?"

"Is the CPU spiking on the Payment service?"
```


---



## Maintained by 
ByteEntropyDotCom