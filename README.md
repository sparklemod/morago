## Api
https://moragobackendmay7-production.up.railway.app/swagger-ui/index.html#/

#### Local launching
- Run container with the DB `docker-compose up -d --build`
- Run the command `mvn clean install -DskipTests spring-boot:run`
- Run the SQL command in [init.sql](/init/init.sql)
- Go to http://localhost:8080/swagger-ui/index.html#/
