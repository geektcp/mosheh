#!/bin/sh

date
mvn clean javadoc:jar deploy -P release,gpg
date
