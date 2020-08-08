const { admin, db } = require('../helpers/admin');


exports.createQuestion = (req, res) => {
    const newQuestion = {
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
