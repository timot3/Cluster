// const functions = require('firebase-functions');
//
// // // Create and Deploy Your First Cloud Functions
// // // https://firebase.google.com/docs/functions/write-firebase-functions
// //
//
//
// const app = require('express')();
// const { signup, login } = require('./handlers/users');
//
// app.post('/signup', signup);
// //app.post('/login', login);
//
//
//
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
//
// exports.api = functions.https.onRequest(app);



const functions = require('firebase-functions');
const app = require('express')();
const FBAuth = require('./util/fbAuth');
const { signup, login, uploadImage, addUserDetails, getAuthenticatedUser } = require('./handlers/users');



app.post('/signup', signup);

exports.api = functions.https.onRequest(app);
