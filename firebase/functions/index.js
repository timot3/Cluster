const app = require('express')();
const functions = require('firebase-functions');
const FBAuth = require('./helpers/fbAuth');
const { signup, login, getUserInfo } = require('./handlers/users');
const { createNewCluster } = require('./handlers/clusters');
const{ createQuestion } = require('./handlers/questions')

app.get('/users/:uid', getUserInfo)
app.post('/signup', signup);
app.post('/login', login);
app.post('/createCluster', FBAuth, createNewCluster);

//app.get('/questions/:uid', getQuestion);
app.post('/questions', createQuestion)

exports.api = functions.https.onRequest(app);
