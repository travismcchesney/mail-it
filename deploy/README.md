## Deploy

This directory exists only to aid deployment to [`now`](https://zeit.co/now). Upon deployment, `now` will attempt to
use the Dockerfile to build the project. In our case, the image has already been built and deployed to Docker Hub, so
 a simple Dockerfile pointing to that image will suffice.

Deploying to `now`
---

From the `deploy/` directory, using the `now` CLI, run (using mailgun, for example):

`now \
  -e "DW_MAIL_PROVIDER=mailgun" \
  -e "DW_MAILGUN_API_KEY=<mailgun-api-key>" \
  --docker`

Alias the new deployment to our domain:

`now alias set <deployment-subdomain>.now.sh mailit.travis.technology`