const { admin, db } = require('../helpers/admin');
const { makeId } = require('../helpers/helper');

exports.createNewCluster = (req, res) => {
    var length = 5;
    var result           = '';
    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for ( var i = 0; i < length; i++ ) {
       result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    
    const newCluster = {
        active: req.body.active,
        meetings: [],
        members: [],
        name: req.body.name,
        ownerEmail: req.user.email,
        password: req.body.password,
        joinCode: result,
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
