# Email server

Build the workspace:
```
gradle wrapper
./gradlew clean
./gradlew build -x test
```

Run the mail server (port 25)
```
sudo ./gradlew bootRun
```

Run the mail server and JUnit tests
```
sudo ./gradlew test
```
