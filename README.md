[![CircleCI](https://circleci.com/gh/travismcchesney/mail-it.svg?style=shield)](https://circleci.com/gh/travismcchesney/mail-it)

# Mail It

## Building

1. Run `mvn package` to build and package up the application

## Starting

The `jar` contains everything needed to run the application. Simply start it like so:

1. `java -jar target/mailit-1.0.jar server config.yml`

## Configuring

Update the `config.yml` file to direct which email provider will be used to send email. The `mailProvider` config
setting can be set to either `mailgun` or `sendgrid`. If neither is provided, `sendgrid` is the default.

An API key is required for whichever service is chosen. The `mailgunApiKey` and `sendgridApiKey` config settings are
used for this.

## Using

1. If running locally, the application can now be used at `http://localhost:8080`
1. To test things out without running locally, use `http://mailit.travis.technology`.

## Monitoring

To inspect the health of the application, navigate to `http://localhost:8081/healthcheck`

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

## Docker

To build the docker container for a new version, run from the project root:

`docker build -t <repo>/mailit:<version> .`

`docker push`
