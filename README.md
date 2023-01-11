# Diagon Alley

A Harry Potter themed webshop, built with Maven and Eclipse Jetty webserver/servlet container.

## How to start (DAO mode)

1. Check whether Maven is installed:
    ```shell
    $ mvn -v
    ```
   The output should look something like this:
    ```shell
    $ mvn -v
    Apache Maven 3.6.3
    Maven home: /usr/share/maven
    Java version: 17.0.5, vendor: Private Build, runtime: /usr/lib/jvm/java-17-openjdk-amd64
    Default locale: hu_HU, platform encoding: UTF-8
    OS name: "linux", version: "5.15.0-56-generic", arch: "amd64", family: "unix"
   ```
   If maven is not installed, first update the package list:
    ```shell
    $ sudo apt update
    ```
   Install maven:
    ```shell
    $ sudo apt install maven
    ```

2. Start jetty container:
   Open a terminal in the root folder, then type the following command:
   ```shell
   $ mvn jetty:run 
   ```

3. Start a web browser and open localhost:8080 or click [here](http://localhost:8080)
