[![CircleCI](https://circleci.com/gh/travismcchesney/mail-it.svg?style=shield)](https://circleci.com/gh/travismcchesney/mail-it)

# mailit

How to start the mailit application
---

1. Run `mvn package` to build and package up the application
1. Start it up with `java -jar target/mailit-1.0.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see application health, navigate to `http://localhost:8081/healthcheck`
