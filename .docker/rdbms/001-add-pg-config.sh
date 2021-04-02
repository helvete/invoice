#!/bin/bash

CNF_LOC='/tmp/pg_hba.conf'
TMP_LOC='/tmp/ph-temp'

cp /pg_hba.conf $CNF_LOC

ENV_VARS="PG_USER PG_DATABASE"
for CURRENT in ${ENV_VARS}; do
	cat $CNF_LOC | sed -e "s/$CURRENT/${!CURRENT}/g" > $TMP_LOC
	mv $TMP_LOC $CNF_LOC
done
cp $CNF_LOC /var/lib/postgresql/data/
echo "include '/postgresql-extend.conf'" >> $PGDATA/postgresql.conf
