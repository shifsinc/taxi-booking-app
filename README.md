# API Platform

#### A set of APIs that can handle simple operations

#### Software requirments
* Oracle Java 8
* Apache Maven 3.3.9
* MySQL - Version later to 5.6.19 
* Apache Tomcat 8.0.30 or later

##### Project setup
* Install JDK 1.8
* Install Apache Maven 3.3.9 or above
* Install latest version of Eclipse Juno/IntelliJ depending on your preference
* Install m2e plugin if you are using Eclipse
* Install MySQL latest version or run mysql in docker container using docker run --name=mysql -it -p 3306 -e MYSQL_ROOT_PASSWORD={{PASSWORD}} -d mysql
* Install a SQL client so we can connect to MySQL and write queries
* Install Git client (Atlassian SourceTree if you prefer a GUI interface)
* Git clone this repo
* In case of Eclipse, Import project into Eclipse using the Import Exisiting Maven Projects Wizard.
	* Edit your project facet by going to Project Properties
	*   Change your Java Facet to 1.8
	*   Add a new Facet 'Dynamic Web Module' with version 3.0
* In case of Eclipse, In the project, right click on pom.xml and check if you have Maven as a option in the drop down. And that we are able to add dependenices to the project.
* In IntelliJ, you can directly open the project and let the editor do the magic for you.
		
		AFTER IMPORTING MAVEN PROJECT IN ECLIPSE, NEVER RIGHT CLICK ON PROJECT AND DO A UPDATE PROJECT. It will mess up your life and nobody will help you if you do.
* When you run Maven, Jaxb will generate classes but you might have to explicitly add the generated-sources/jaxb folder to the list of source folders in your project's build path in eclipse.

##### Running the project
Assumptions:

* You have the Tomcat server running
* You also have a ${USER_HOME}/.m2/settings.xml file

Environments:

* dev -- this is the environment which the PO and testers use to test the application
* qa -- this is the environment which the users will use to test the application
* pre-prod -- this will be used for infra/security/regression testing
* prod -- the final go-live version

We have different config files for each of these environments.

##### One time setup

* Open ${USER_HOME}/.m2/settings.xml (if it doesn't exist, create it)

* Log file location is `/var/log/microservices` - Make sure you have this directory and also that the user who is running Tomcat has write permissions on this directory.

* Run the src/main/scripts/table_scripts.sql in the database to bootstrap database.

##### Building and deploying with docker and ansible

Turning your Continuous Integration process into a Continuous Deployment Pipeline is easier than you think. Using a tool like [Ansible](http://www.ansible.com/), we can break the deployment process into simple repeatable roles that can handle extremely complex deployment scenarios. We can also integrate this deployment process with Maven to make the entire process of committing, building, testing and deploying into one streamline delivery pipeline.

Ansible is a powerful collection of open source modules that allow you to remotely manage the state of your servers. Ansible is agentless. There is no additional software that needs to be installed on a target host(s).

This prototype project, much like previous ones, will be constructing a simple [Spring Boot](http://projects.spring.io/spring-boot/) REST service packaged in a [Docker](https://www.docker.com/) container. However, in this case we will leverage a [Maven Ansible Plugin](https://github.com/tmullender/ansible-maven-plugin) to automate the installation of our Docker container on a host.

## Pre-Requisites

### Docker Installation

This project requires the installation of Docker. See the Docker [installation](https://docs.docker.com/installation/#installation) guide to get up and running.

Once you have Docker installed, you will need to authenticate with the Docker Registry. To complete this step you will need to create an account [here](https://hub.docker.com/account/signup/)

Once you have created an account you should run the following command:

	docker login
	
You will be prompted for all authentication details.

### Ansible Installation

This project requires the installation of Ansible. See the Ansible [installation](http://docs.ansible.com/intro_installation.html) guide to get up and running.

## Quick Start

The easiest way to get started with the prototype is to fork, clone or download this repository.

	git clone git@github.com:usrlocalts/Spring-Tomcat-Docker-Ansible.git
	
This prototype project consists of a simple spring boot REST service. To build the project simply run the following command:

	mvn clean install
	
At this point you will have a complete runnable Spring Boot jar file in the project's target directory. 

### Build the Docker Image
	
This project has the Maven Docker Plugin pre-configured in the pom.xml file. To build the docker image simply run the following command:

	mvn docker:build
	
### Tag the Docker Image

Now that the image has been created, we should tag the image to indicate the version of the Spring Boot artifact that it contains. 

	mvn docker:tag -build-number=1
	
Running the `docker images` command should now produce the following output.

	MacBook-Pro:$ docker images
    REPOSITORY                              TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
    usrlocalts/microservices                0.1.1               a03afd6a47d6        7 minutes ago       365.3 MB
    usrlocalts/microservices                latest              a03afd6a47d6        7 minutes ago       365.3 MB

### Push the Docker Image

To make the image available outside of your local system, you need to push the image to a valid Docker Registry.

To push the new images to the repository run the following command:

	mvn docker:push

	or

	docker push usrlocalts/microservices

At this point you should be able to browse [Docker Registry] and see 2 tag files.

### Define The Deployment Playbook

This prototype project contains a sample [playbook](src/main/ansible/deploy.yml) to deploy the Docker image we just created. This playbook can deploy the image to any number of hosts that we define in the [hosts](src/main/ansible/hosts) file. For the purposes of this project we are simply going to execute the playbook against the localhost. However, you can define any number of remote hosts and connection details for those hosts using the inventory file. For more information see the Ansible [Invetory](http://docs.ansible.com/intro_inventory.html) documentation.

	---
	- hosts: all 
	  tasks:
	   - name: Install docker-py
	     pip:
	       name: 'https://pypi.python.org/packages/source/d/docker-py/docker-py-1.1.0.tar.gz'
    	   state: present
	   - name: Start Docker Container
	     docker:
	       name: spring-boot-prototype-ansible
	       image: "{{ dockerImagePrefix }}/{{ projectArtifactId }}:{{ projectVersion }}"
	       pull: always
	       insecure_registry: true
	       state: started
	       ports:
	         - "8080:8080"
	       expose:
	         - 8080

The deploy.yml file included in the prototype project simply instructs Ansible to ensure that there our container is up and running on the target host. It indicates that the a pull attempt will always be made. Additionally, port 8080 on the container will be bound to port 8080 on the target host.

### Define The Target Host

The prototype contains a hosts file at src/main/ansible/hosts. It contains a single line

	localhost ansible_connection=local
	
The hosts file indicates that the only target host for our operation is the localhost. To define a remote host you could modify the file like the following:

	192.168.56.12 ansible_connection=ssh ansible_ssh_user=redhat

Make sure your public key is in ~/.ssh/authorized_keys in the remote host for enabling ssh access. Also, the user should have a passwordless sudo access.

For CentOS/RedHat, it it is observed that there is a problem with permissions of authorized_keys file and .ssh folder. Please make sure the following file/folder has the correct permissions:

 ```
 chmod 700 ~/.ssh
 chmod 600 ~/.ssh/authorized_keys
 ```

### Plugin Configuration

	<plugin>
		<groupId>co.escapeideas.maven</groupId>
		<artifactId>ansible-maven-plugin</artifactId>
		<version>1.2.0</version>
		<configuration>
			<inventory>${basedir}/src/main/ansible/hosts</inventory>
			<playbook>${basedir}/src/main/ansible/deploy.yml</playbook>
			<extraVars>
				<docker.registry>dockerRegistry=${docker.registry}</docker.registry>
				<docker.image.prefix>dockerImagePrefix=${docker.image.prefix}</docker.image.prefix>
				<project.artifactId>projectArtifactId=${project.artifactId}</project.artifactId>
				<project.version>projectVersion=${project.version}</project.version>
			</extraVars>
		</configuration>
	</plugin>
            
As you can see, we have defined the Ansible inventory and playbook that will be used during the plugin execution. Also, we have defined several variables that will be made available to the playbook. These allow the docker container configuration to be dynamic during the build process.

### Run The Playbook

To run the playbook execute the following command:

	mvn -X -Dprofile=dev -Dusername={{dbusername}} -Dpassword={{dbpassword}} -Ddbhostip=192.168.99.100:3306 ansible:playbook -Dbuild-number=1

You should see the following output:

	Step 1 : FROM tomcat:8-jre8
    ---> 4966ce0ad136
    Step 2 : VOLUME /tmp
    ---> Using cache
    ---> b3e8260c120d
    Step 3 : RUN rm /usr/local/tomcat/conf/server.xml /usr/local/tomcat/conf/tomcat-users.xml /usr/local/tomcat/conf/context.xml
    ---> Using cache
    ---> 127a97713fc1
    Step 4 : COPY context.xml /usr/local/tomcat/conf/context.xml
    ---> Using cache
    ---> e38a06cb7c3d
    Step 5 : COPY server.xml /usr/local/tomcat/conf/server.xml
    ---> aadfb7602882
    Removing intermediate container a4d909fbebe6
    Step 6 : COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
    ---> 4bae28493dfc
    Removing intermediate container bafb45880c5e
    Step 7 : COPY secrets.properties /srv/microservices/secrets.properties
    ---> d65a4c6fc168
    Removing intermediate container 81564474204a
    Step 8 : COPY microservices.war /usr/local/tomcat/webapps/microservices.war
    ---> cb5a60053045
    Removing intermediate container b629f8cd4956
    Step 9 : MAINTAINER "TS Shriram <tsshriram@live.in">
    ---> Running in 09b5fce9c377
    ---> a03afd6a47d6
    Removing intermediate container 09b5fce9c377
    Successfully built a03afd6a47d6
    [INFO] Built usrlocalts/microservices
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 4.628 s
    [INFO] Finished at: 2016-06-27T11:17:44+05:30
    [INFO] Final Memory: 27M/213M
    [INFO] ------------------------------------------------------------------------

You can verify that the docker container is up and running on the localhost:

	MacBook-Pro:$ docker ps -a
    CONTAINER ID        IMAGE                      COMMAND                  CREATED             STATUS              PORTS                    NAMES
    c6a3bf857b09        usrlocalts/microservices   "catalina.sh run"        3 minutes ago       Up 3 minutes        0.0.0.0:8080->8080/tcp   gloomy_swanson

Once the Spring Boot application is up and running you can execute a HTTP `GET` request on the sample resource. Depending on your docker setup localhost may not work. If you are running on OS X, you may need to use the IP address of the boot2docker VM.

	http://192.168.99.100:8080/microservices/login
	
You should see the following response:

	{"message":"Request method 'GET' not supported"}
