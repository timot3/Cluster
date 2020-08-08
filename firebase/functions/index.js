const app = require('express')();
const functions = require('firebase-functions');
const FBAuth = require('./helpers/fbAuth');
const { signup, login, getUserInfo } = require('./handlers/users');
const { createNewCluster } = require('./handlers/clusters');
const{ createQuestion, getQuestion } = require('./handlers/questions')

app.get('/users/:uid', getUserInfo)
app.post('/signup', signup);
app.post('/login', login);
app.post('/createCluster', FBAuth, createNewCluster);

app.post('/questions', createQuestion)
app.get('/questions/:uid', getQuestion)

exports.api = functions.https.onRequest(app);
