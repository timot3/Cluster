const { admin, db } = require('../helpers/admin');


exports.createQuestion = (req, res) => {
    const newQuestion = {
        answerChoices: req.body.answerChoices,
        meetingId: req.body.meetingId,
        prompt: req.body.prompt,
        type: req.body.type,
        submissionType: req.body.submissionType,
        userAnswers: []
    };

    db.collection('questions').add(newQuestion).
    then(doc => {
        const resQuestion = newQuestion;
        resQuestion.questionId = doc.id;
        res.json({ message: `question ${doc.id} created successfully` });
        return;
    }).catch(err => {
        res.status(500).json({ error: `something went wrong` });
        console.error(err);
    });
};

exports.getQuestion = (req, res) => {
    let questionData = {};
    db.doc(`/questions/${req.params.uid}`).get().then(doc => {
        if(!doc.exists)
            return res.status(404).json({ error: 'Question not found' });

        questionData = doc.data();
        questionData.uid = doc.id;
        return db.collection('questions').where('uid', '==', req.params.uid).get();
    }).then(data => {

        return res.json(questionData);
    }).catch(err => {
        console.error(err);
        return res.status(500).json({ error: err.code });
    });
};
