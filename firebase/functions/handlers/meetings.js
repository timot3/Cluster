const { admin, db } = require('../helpers/admin');

exports.createMeeting = (req, res) => {
    const newMeeting = {
        active: req.body.active,
        questions: req.body.questions
    };

    db.collection('meetings').add(newMeeting).
    then(doc => {
        const resMeeting = newMeeting;
        resMeeting.meetingId = doc.id;
        res.json({ message: `meeting ${doc.id} created successfully` });
        return;
    }).catch(err => {
        res.status(500).json({ error: `something went wrong` });
        console.error(err);
    });
};

exports.getMeeting = (req, res) => {
    let meetingData = {};
    db.doc(`/meetings/${req.params.uid}`).get().then(doc => {
        if(!doc.exists)
            return res.status(404).json({ error: 'Meeting not found' });

        meetingData = doc.data();
        meetingData.uid = doc.id;
        return db.collection('meetings').where('uid', '==', req.params.uid).get();
    }).then(data => {

        return res.json(meetingData);
    }).catch(err => {
        console.error(err);
        return res.status(500).json({ error: err.code });
    });
};
