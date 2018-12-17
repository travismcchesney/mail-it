[![CircleCI](https://circleci.com/gh/travismcchesney/mail-it.svg?style=shield)](https://circleci.com/gh/travismcchesney/mail-it)

# Mail It

## Building
---

1. Run `mvn package` to build and package up the application

## Starting
---

The `jar` contains everything needed to run the application. Simply start it like so:

1. `java -jar target/mailit-1.0.jar server config.yml`

## Using
---

1. The application can now be used at `http://localhost:8080`

## Examples

```bash
curl -X POST \
  http://localhost:8080/email \
  -H 'Content-Type: application/json' \
  -d '{
	"to": "fake@example.com",
	"to_name": "Mr. Fake",
	"from": "noreply@mybrightwheel.com",
	"from_name": "Brightwheel",
	"subject": "A Message from Brightwheel",
	"body": "<h1>Your Bill</h1><p>$10</p>"
}'
```

Health Check
---

To inspect the health of the application, navigate to `http://localhost:8081/healthcheck`
