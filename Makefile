all: docker.push

.PHONY: docker.build
.PHONY: docker.push

IMAGE ?= s3pweb/graphhopper
VERSION ?= 7.0

docker.push: docker.build

docker.build:
	@docker build -t ${IMAGE}:${VERSION} .

docker.push:
	@docker push ${IMAGE}:${VERSION}
