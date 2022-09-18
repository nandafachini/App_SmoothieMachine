# Smoothie API
## _The greatest smoothie ingredient selector API_

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Smoothie API is an API for selecting smoothies, removing specific ingredients in order for your generated smoothie to be accordingly to your diet. It receives as input a smoothie name and a list of forbidden ingredients and returns an ordered list containing all allowed ingredients that build up the smoothie

## Features

- List smoothie ingredients
- Remove specific ingredients from the smoothie

## Coming soon

- Add specific ingredients to the smoothie



> "After trying this new software from QRED team,
> I won't be worried about making wrong smoothies again.
> This service allows me to focus on doing my job - which is make the best
> smoothie for my clients - instead of write down which
> ingredients I need to remove or not.
> Thank you QRED Team!"
> Mike (the smoothie operator)

## Technologies used

Smoothie API uses a number of libraries to work properly:

- [Maven](https://maven.apache.org/download.cgi) - Package manager
- [SpringBoot](https://start.spring.io/) - Api development library

And of course Smoothie API itself is open source with a [public repository](https://github.com/nandafachini/qred_challenge)
on GitHub.

## Installation

Smoothie API requires [Java11](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html) to run.

Install the dependencies and start the server.

```sh
mvn clean install
java -jar target/smoothie.machine-0.0.1-SNAPSHOT.jar
```

Verify the deployment by navigating to your server address in
your preferred browser.

```sh
127.0.0.1:8080
```

For production environments...

```sh
mvn clean install
java -jar target/smoothie.machine-0.0.1-SNAPSHOT.jar
Started Application in 8.649 seconds (JVM running for 9.598)
```

## License

GNU

**Free Software, enjoy!**
