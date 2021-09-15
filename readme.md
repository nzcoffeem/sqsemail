#### Building
- In pendula run 
```./gradlew clean build```

#### Running
- To run
```docker-compose build && docker-compose up```

You can go to [this link](http://localhost:8080). Initially, I mis-read the requirements and thought it was a websocket instead of a webhook. I coded it and then left it there.

You can also execute the webhook by running the command
```
curl -X POST -H "Content-Type: application/json" \
    -d '{"firstName": "homan", "lastName": "ma", "email": "hma@gmail.com", "phone": "23232232", "postcode": "NW8 sds" }' \
    http://localhost:8080/services/payload
```
    
This implementation uses elasticmq:
- Check elasticmq queue status [here](http://localhost:9325/)
- Check emails sent [here](http://localhost:8025/). Mailhog captures the emails sent.

##### Things I have not added:
- Testing
	- Unit
	- Integration
- Documentation
- Build docker images as part of the ./gradlew build therefore avoiding the docker-compose build step