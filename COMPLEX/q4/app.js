
const express = require('express');
const { MongoClient, ObjectId } = require('mongodb');

const app = express();
app.use(express.json());

const url = "mongodb://localhost:27017";
const client = new MongoClient(url);

const ConnectDB = async () => {
    try {
        await client.connect();
        console.log("DB is Running...");
    } catch (e) {
        console.log("error", e);
    }
};

ConnectDB();

const db = client.db('studentDB');
const studentCollection = db.collection('Student');

app.post('/api/addStudent', async (req, res) => {
    const newStudent = req.body;
    await studentCollection.insertOne(newStudent);
    res.status(200).send("Added Successfully");
});

app.get('/api/getAll', async (req, res) => {
    const studentData = await studentCollection.find().toArray();
    res.send(studentData);
});

app.put('/api/update/:id', async (req, res) => {
    const id = req.params.id;
    const newData = req.body;
    console.log(id);

    try {
        const result = await studentCollection.updateOne(
            { _id: new ObjectId(id) },
            { $set: newData }
        );

        console.log(result, "result");

        if (result.modifiedCount > 0) {
            res.send("Updated Successfully");
        } else {
            res.send("id not found");
        }
    } catch (e) {
        res.send(e);
    }
});

app.delete('/api/delete/:id', async (req, res) => {
    const id = req.params.id;
    console.log(id);

    try {
        const result = await studentCollection.deleteOne(
            { _id: new ObjectId(id) }
        );

        console.log(result, "result");

        if (result.deletedCount > 0) {
            res.send("Deleted Successfully");
        } else {
            res.send("id not found");
        }
    } catch (e) {
        res.send(e);
    }
});

app.listen(4000, () => {
    console.log("running port 4000...");
});
