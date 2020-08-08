const { admin, db } = require('../helpers/admin');
const { makeId } = require('../helpers/helper');

exports.createNewCluster = (req, res) => {
    const newCluster = {
        active: req.body.active,
        meetings: [],
        members: [],
        name: req.body.name,
        ownerEmail: req.user.email,
        password: req.body.password,
        joinCode: makeId(5)
    };

    db.collection('clusters').add(newCluster).
    then(doc => {
        const resCluster = newCluster;
        resCluster.clusterId = doc.id;
        res.json({ message: `cluster ${doc.id} created successfully` });
        return;
    }).catch(err => {
        res.status(500).json({ error: `something went wrong` });
        console.error(err);
    });
};
