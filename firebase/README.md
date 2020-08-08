# Firebase functions for Cluster

## Signup
url:  https://us-central1-cluster-hackathon-group.cloudfunctions.net/api/signup

POST request

json input:
```
{
	"name": "John Smith",
	"email": "JohnSmith@email.com",
	"password": "password123"
}
```

## Create cluster
url: https://us-central1-cluster-hackathon-group.cloudfunctions.net/api/createCluster

POST request

json input:
```
{
	"active": true,
	"name": "Cluster Name",
	"password": "password (blank if none)"
}
```

MUST BE AUTHENTICATED - a user token needs to be added as a header (key = Authorization, value = "Bearer KEY"). Keys are returned when a user signs up, although I think they expire at some point (60 mins?).

## Get user info
url: https://us-central1-cluster-hackathon-group.cloudfunctions.net/api/users/:uid where ":uid" is the id of a given user

GET request

Probably should add some authentication to this. No input.

## Create question
url: https://us-central1-cluster-hackathon-group.cloudfunctions.net/api/questions

json input
```
{
  "answerChoices": ["array of answer choices"],
  "prompt": "prompt of question",
  "type": "type of input expected",
  submissionType: "submission types accepted",
}
```

## Get question
url: https://us-central1-cluster-hackathon-group.cloudfunctions.net/api/questions/:uid where ":uid" is the id of a given question

GET request

Probably should add some authentication to this. No input.
