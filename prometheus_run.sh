# Запуск Prometheus в docker ДЛЯ РУЧНЫХ ТЕСТОВ
#CHANGE ./prometheus.yml to full path
docker run -d -p 9090:9090 -v ./prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus