const functions = require('firebase-functions');
const app = require('express')();
const { signup, getUserInfo } = require('./handlers/users');

app.get('/users/:uid', getUserInfo)
app.post('/signup', signup);

exports.api = functions.https.onRequest(app);
