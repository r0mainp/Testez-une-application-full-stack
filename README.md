## Yoga App

### Project setup

- Get project from github with `git clone https://github.com/r0mainp/Testez-une-application-full-stack.git`
- Move to `front` folder with `cd front` and install dependencies with `npm install`
- Move to `back` folder with `cd ../back` and install dependencies with `mvn install`

### Create database

We assume MySql is already installed on your computer. If not you can follow this link https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/.

Depending on your root user and password update `spring.datasource.username` and `spring.datasource.password` in `back\src\main\resources\application.properties`.

- Run mysql with :
    - `mysql -u root -p` on Mac or Linux
    - Running `MYSQL command line client` on Windows
- To create database use `CREATE DATABASE test;`
- Select the database with : `USE test;`
- Assuming you're at the root of the project load the script `script.sql` with `mysql -u root -p test < ressources\sql\script.sql`

### Run and use app

- In the folder `back` use: `mvn spring-boot:run` to launch the backend app
- In the folder `front` use: `ng serve` to launch the front end app
- in the same folder install cypress with `./node_modules/.bin/cypress install`

App is available at `http://localhost:4200`.

To log into the app as an admin use

- email: `yoga@studio.com`
- password: `test!1234`

or create a new user (non admin)

### Run the tests

#### Front

- In the `front` folder run `npm test`
- once tests are over you can find the coverage report in `front\coverage\jest\lcov-report\index.html` 

![Front Report](ressources\docs\front-report.png)

#### Back

- In the `back` folder run `mvn clean test`
- once tests are over you can find the coverage report in `back\target\site\jacoco\index.html` 

![Back Report](ressources\docs\back-report.png)

#### End to End

- In the `front` folder run `npm run e2e:ci`
- if your local server is running you'll be ask if you want to use a different port, answer `Y`
- once tests are over you can find the coverage report in `front\coverage\lcov-report\index.html` 

![E2E Report](ressources\docs\e2e-report.png)
