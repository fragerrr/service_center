# Getting Started

### Guides

Все ендпоинты хранятся в классе ClaimController

Функционал работает на основе ApplicationEventPublisher и Kafka 

Есть также docker-compose файл по директории root/docker/docker-compose.yml

Для создания таблиц в БД используется liquibase, в котором я также накидал инсерты для BreakReason(Причины поломки)

Каждая заявка обрабатывается в секундах для экономии времени.

Написаны тесты для двух сервисов.