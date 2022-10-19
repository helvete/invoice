#!/usr/bin/make -f
include .env

main: drebuild

drebuild: .docker/backend/postgresql-42.2.18.jar
	COMPOSE_DOCKER_CLI_BUILD=1 docker-compose -p inv up --build --force-recreate -d

stop:
	docker-compose -p inv stop

dlog:
	docker-compose -p inv logs -f

dcli:
	docker-compose -p inv exec backend bash

ddcli:
	docker-compose -p inv exec rdbms sh

ldbconn:
	@PGPASSWORD=${PG_PASSWORD} psql -h 127.1 -p 5430 -U ${PG_USER} ${PG_DATABASE}

dpcatlog:
	docker-compose  -p inv exec backend cat /opt/payara/appserver/glassfish/domains/domain1/logs/server.log

.docker/backend/postgresql-42.2.18.jar:
	wget https://jdbc.postgresql.org/download/postgresql-42.2.18.jar -O.docker/backend/postgresql-42.2.18.jar


####################################################
# ctags for vim
CTAGS_COMMON_EXCLUDES = \
	--exclude=*.vim \
	--exclude=temp \
	--exclude=log \
	--exclude=*.js \
	--exclude=doc

# ctags
ctags::
	rm -f TAGS
	ctags --recurse \
		--totals=yes \
		$(CTAGS_COMMON_EXCLUDES) \
		.
