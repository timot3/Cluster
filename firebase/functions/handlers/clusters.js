const { admin, db } = require('../helpers/admin');
const { makeId } = require('../helpers/helper');

exports.createNewCluster = (req, res) => {
    var length = 5;
    var result = '';
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for(var i = 0; i < length; i++) {
       result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }

    // // edit IDWITHCODES to include new cluster
    // let idData = {};
    // db.doc(`/clusters/IDWITHCODES`).get().then(doc => {
    //     idData = doc.data();
    // }).catch(err => {
    //     console.error(err);
    //     return res.status(500).json({ error: err.code });
    // });
    //


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

exports.getCluster = (req, res) => {
    let clusterData = {};
    db.doc(`/clusters/${req.params.uid}`).get().then(doc => {
        if(!doc.exists)
            return res.status(404).json({ error: 'Cluster not found' });

        clusterData = doc.data();
        clusterData.uid = doc.id;
        return db.collection('clusters').where('uid', '==', req.params.uid).get();
    }).then(data => {

        return res.json(clusterData);
    }).catch(err => {
        console.error(err);
        return res.status(500).json({ error: err.code });
    });
};

exports.joinClusterByCode = (req, res) => {
  let clusterData = {};

  db.doc(`/clusters/IDWITHCODES`).get().then(doc => {
      clusterData = doc.data().CODES;
      return db.collection('clusters').where('uid', '==', req.params.uid).get();
  }).then(data => {
      if(clusterData.hasOwnProperty(req.params.uid)) {
        let newId = clusterData[req.params.uid];

        // add cluster to token user object

        return res.json({ clusterId: newId });
      } else {
        return res.json({ error: "invalid code" });
      }
  }).catch(err => {
      console.error(err);
      return res.status(500).json({ error: err.code });
  });
};
