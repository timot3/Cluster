const firebase = require('firebase');
const config = require('../config/config');
const { createRandID, validateSignUpData } = require('../helpers/helper');

firebase.initializeApp(config);

// exports.signup = (req, res) => {
//     const newUser = {
//         name: req.body.name,
//         email: req.body.email,
//         password: req.body.password,
//         confirmPassword: req.body.confirmPassword,
//     };
//
//     // const { valid, errors } = validateSignUpData(newUser);
//     // if(!valid)
//     //     return res.status(400).json(errors);
//
//     let token, userId;
//     //var randID = createRandID();
//     var randID = "eruigheriugherisughuersghesirughuersg"
//
//     db.doc(`/users/${randID}`).get().then(doc => {
//         if(doc.exists) {
//             return res.status(400).json({ handle: 'this handle is already taken' });
//         } else {
//             return firebase.auth().createUserWithEmailAndPassword(newUser.email, newUser.password);
//         }
//     }).then(data => {
//         userId = data.user.uid;
//         return data.user.getIdToken();
//     }).then(idToken => {
//         token = idToken;
//         const userCredentials = {
//             email: newUser.email,
//             name: newUser.name,
//             clusters: [],
//             ownerOf: [],
//         };
//
//         return db.doc(`/users/${randID}`).set(userCredentials);
//     }).then(() => {
//         return res.status(201).json({ token });
//     }).catch(err => {
//         console.error(err);
//         if(err.code === 'auth/email-already-in-use') {
//             return res.status(400).json({ email: 'Email is already in use' })
//         } else {
//             return res.status(500).json({ error: err.code });
//         }
//     });
// };



exports.signup = (req, res) => {
    const newUser = {
        firstName: req.body.firstName,
        lastName: req.body.lastName,
        email: req.body.email,
        password: req.body.password,
        confirmPassword: req.body.confirmPassword,
        handle: req.body.handle,
        tags: req.body.tags,
        eventsWorked: req.body.eventsWorked,
        currentEvents: req.body.currentEvents
    };

    const { valid, errors } = validateSignUpData(newUser);
    if(!valid)
        return res.status(400).json(errors);

    const noImg = 'no-img.png'

    let token, userId;

    db.doc(`/users/${newUser.handle}`).get().then(doc => {
        if(doc.exists) {
            return res.status(400).json({ handle: 'this handle is already taken' });
        } else {
            return firebase.auth().createUserWithEmailAndPassword(newUser.email, newUser.password);
        }
    }).then(data => {
        userId = data.user.uid;
        return data.user.getIdToken();
    }).then(idToken => {
        token = idToken;
        const userCcredentials = {
            handle: newUser.handle,
            email: newUser.email,
            firstName: newUser.firstName,
            lastName: newUser.lastName,
            tags: newUser.tags,
            eventsWorked: req.body.eventsWorked,
            currentEvents: req.body.currentEvents,
            createdAt: new Date().toISOString(),
            imageUrl: `https://firebasestorage.googleapis.com/v0/b/${config.storageBucket}/o/${noImg}?alt=media`,
            userId
        };

        return db.doc(`/users/${newUser.handle}`).set(userCcredentials);
    }).then(() => {
        return res.status(201).json({ token });
    }).catch(err => {
        console.error(err);
        if(err.code === 'auth/email-already-in-use') {
            return res.status(400).json({ email: 'Email is already in use' })
        } else {
            return res.status(500).json({ error: err.code });
        }
    });
};
