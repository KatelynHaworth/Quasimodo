Quasimodo 
---------

[![Build Status](https://travis-ci.org/HiveMedia/Quasimodo.svg?branch=master)](https://travis-ci.org/HiveMedia/Quasimodo)

Quasimodo is a service that runs on a system providing the ability to discovery MikroTik devices and configure those devices

Installing
----------

### Red Hat Family Systems

To install you first need to add the repository, there are two repos *stable*, containing builds from master, and *development*, containing builds from all branchs. To add the repos run the following script (just replace *development* with *stable* to change repos)

`curl -s https://packagecloud.io/install/repositories/hivemedia/development/script.rpm.sh | sudo bash`

Once that is done, just run the following

`sudo yum install quasimodo`

This will install the service along with *openjdk-1.8.0* and start it.

### Debian Family Systems

Same steps as above apply with a minor change to the install script, remember just change *development* with *stable* to change release streams.

`curl -s https://packagecloud.io/install/repositories/hivemedia/development/script.deb.sh | sudo bash`

Before installing check if your debain repositories provides *openjdk-8-jre*, if not run the following to add the official OpenJDK PPA

`sudo add-apt-repository ppa:openjdk-r/ppa`

Finally run the install

`sudo apt-get install quasimodo`

Authors, License and Copyright
------------------------------

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

|                   |                                                |
|:------------------|:-----------------------------------------------|
| **Author**        | Liam Haworth <liam.haworth@hivemedia.net.au>   |
|                   |                                                |
| **Copyright**     | (c) 2015, Hive Media Productions.              |
