const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//


const app = require('express')();
const { signup, login } = require('./handlers/users');

app.post('/signup', signup);
app.post('/login', login);




exports.helloWorld = functions.https.onRequest((request, response) => {
 response.send("Hello from Firebase!");
});
