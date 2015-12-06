#!/bin/bash

version=`git tag`
release=`git describe --tags --abbrev=5 | gawk -F "-" '{print $2}'`
commit=`git describe --tags --abbrev=5 | gawk -F "-" '{print $3}'`

mvn package -Dquasimodo.version=$version -Dquasimodo.release=$release -Dquasimodo.commit=$commit