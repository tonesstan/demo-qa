    Настройка локального и удалённого выполнения тестов проекта.

1. Скачать проект на ПК.
2. Скачать, установить и запустить приложение Docker Desktop (https://www.docker.com/products/docker-desktop)
3. Открыть командную строку.
4. Перейти в папку проекта (используя команду cd).
5. Загрузить образ браузера для Selenoid (команда: docker pull selenoid/vnc:chrome_126.0).
6. Запустить контейнеры Selenoid и Selenoid-ui (команда: docker-compose -f docker-compose.yml up -d)
7. Запустить контейнер Selenoid с конфигурацией теста (команда: docker-compose -f docker-compose-tests.yml up -d)