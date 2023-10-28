# GitHubClient API

GitHub Client get owner repository data from GitHub API by reactive client and retrieve it using its own REST API after request: 

`http://localhost:3333/repositories/{owner}/{repo}`

`http://localhost:3333/repositories/{user}`

API supports also response error handling with regional time provider handling repetitions if no response. 


## Requirements:

 - JDK 11 
 -- vide: `https://itslinuxfoss.com/how-to-fix-the-java-command-not-found-in-linux/`

 - Maven 
 -- vide: `https://linuxhint.com/mvn-command-found/`

 - Docker 
 -- vide: `https://docs.docker.com/get-docker/`

 - Linux OS (Ubuntu)

 - IDE for JDK with Lombok and Spring plugins 
 -- vide: `https://www.eclipse.org/downloads/packages/`
   (ex. standalone "Eclipse IDE for Enterprise Java and Web Developers" with soft link on the desktop) 
 -- vide: `https://www.baeldung.com/lombok-ide`	

  - the new GitHubb personal temporary token if any expired would be useful, as well  
  -- vide: `https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens`
 

## Run:

1. Clone repository using: 
```
	$ git clone <repository-url> 
```
2. Get in main `pom.xml` directory and type in CLI:   
```
	$ mvn clean package -DskipTests
```
2. Get in `repeater/target/` directory and type in CLI:
```
	$ cd repeater/target/
	$ java -jar *-SNAPSHOT.jar
``` 


## Examples of:

- useful end-points:

 GET `http://localhost:3333/repositories/wkrzywiec/docker-postgres`

 GET `http://localhost:3333/repositories/awieclawski`


- result:

 `{"fullName":"wkrzywiec/docker-postgres","description":"Postgres Dockerfile with simple SQL script","cloneUrl":"https://github.com/wkrzywiec/docker-postgres.git","stars":8,"createdAt":"2019-06-25T04:33:33"}`

- error report:

 `{"status":404,"error":"Not Found","message":"Requested repository can not be found","timestamp":"2023-08-28T15:50:29.356449776"}`

