# External API Monitor

A Spring Boot application for monitoring external API performance with Prometheus metrics and Grafana visualization.

## Overview

This application provides a REST API endpoint to monitor external API latency and response codes. It integrates with Prometheus for metrics collection and Grafana for visualization, making it easy to track the performance of external APIs over time.

## Features

- **API Performance Monitoring**: Monitor response time and status codes of external APIs
- **Prometheus Integration**: Automatic metrics collection with histograms for latency tracking
- **Grafana Dashboard**: Ready-to-use visualization for API performance metrics
- **Docker Support**: Complete containerized setup with docker-compose
- **Percentile Metrics**: Built-in P95 and P99 latency calculations

## Tech Stack

- **Java 17**
- **Spring Boot 3.5.10**
- **Spring Web & Actuator**
- **Micrometer Prometheus Registry**
- **Prometheus**
- **Grafana**
- **Docker & Docker Compose**

## Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd external-api-monitor
   ```

2. **Start the monitoring stack**
   ```bash
   docker-compose up -d
   ```

3. **Run the Spring Boot application**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080`

### Accessing Services

- **Application**: http://localhost:8080
- **Prometheus**: http://localhost:9090
- **Grafana**: http://localhost:3000 (admin/admin)
- **Actuator Metrics**: http://localhost:8080/actuator/prometheus

## API Usage

### Monitor External API

Send a POST request to `/monitor` with a JSON payload containing the URL to monitor:

```bash
curl -X POST http://localhost:8080/monitor \
  -H "Content-Type: application/json" \
  -d '{"url": "https://api.example.com/endpoint"}'
```

**Response:**
```json
{
  "url": "https://api.example.com/endpoint",
  "Code": "200 OK"
}
```

## Metrics

The application automatically exposes the following metrics to Prometheus:

- `external_api_latency_seconds`: Histogram of API response times
- `external_api_latency_p95`: 95th percentile of response times
- `external_api_latency_p99`: 99th percentile of response times

## Project Structure

```
src/main/java/com/rodrigo134/apianalyzer/
├── ApiPerformaceAnalyzerApplication.java  # Main application class
├── config/
│   └── RestTemplateConfig.java           # RestTemplate configuration
├── controller/
│   └── MonitorController.java            # REST API endpoints
└── service/
    └── MonitorService.java               # Core monitoring logic

infra/prometheus/
├── prometheus.yml                        # Prometheus configuration
└── rules/
    └── external-api.rules.yml           # Recording rules for latency percentiles
```

## Configuration

### Application Properties

The application uses default Spring Boot configuration. You can customize:

- Server port
- Actuator endpoints
- Prometheus metrics settings

### Prometheus Configuration

Located in `infra/prometheus/prometheus.yml`:
- Scrape interval: 5 seconds
- Metrics endpoint: `/actuator/prometheus`
- Target: `host.docker.internal:8080`

### Grafana Setup

1. Access Grafana at http://localhost:3000
2. Add Prometheus as data source (http://prometheus:9090)
3. Import or create dashboards using the available metrics

## Development

### Building the Project

```bash
./mvnw clean build
```

### Running Tests

```bash
./mvnw test
```

### Adding New Metrics

1. Inject `MeterRegistry` in your service
2. Create metrics using Micrometer APIs
3. Metrics will be automatically exposed to Prometheus

Example:
```java
private final Counter requestCounter;

public YourService(MeterRegistry meterRegistry) {
    this.requestCounter = Counter.builder("custom_requests_total")
        .description("Total custom requests")
        .register(meterRegistry);
}
```

## Monitoring & Alerting

### Available Metrics

- **Response Time**: Full histogram with percentiles
- **Status Codes**: HTTP response code tracking
- **Request Rate**: Number of API calls over time

### Setting Up Alerts

Create alerting rules in Prometheus to get notified about:
- High latency thresholds
- Error rate increases
- Service availability issues

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For issues and questions:
- Check the [Issues](../../issues) page
- Review the application logs
- Verify Prometheus and Grafana configurations
