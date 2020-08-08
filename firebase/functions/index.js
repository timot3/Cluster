const app = require('express')();
const functions = require('firebase-functions');
const FBAuth = require('./helpers/fbAuth');
const { signup, login, getUserInfo } = require('./handlers/users');
const { createNewCluster } = require('./handlers/clusters');

app.get('/users/:uid', getUserInfo)
app.post('/signup', signup);
app.post('/login', login);
app.post('/createCluster', FBAuth, createNewCluster);

exports.api = functions.https.onRequest(app);
