import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Table from 'react-bootstrap/Table';

function Record() {
    const [recordCount, setRecordCount] = useState(0);
    const [recordList, setRecordList] = useState([]);
    const [canDisplay, setCanDisplay] = useState(false);

    const handleDisplay = () => {
        recordCount === 0 ? setCanDisplay(false) : setCanDisplay(true);
    };

    useEffect(() => {
        axios
            .get('/api/url')
            .then(res => {
                setRecordCount(res.data.count);
                setRecordList(res.data.data);
            })
            .catch(err => {
                console.log(err);
            });
    }, []);

    return (
        <Card className="text-center">
            <Card.Header>
                Displaying all the{' '}
                <span className="count-number">
                    {canDisplay ? recordCount : 0}
                </span>{' '}
                records in database
            </Card.Header>
            <Card.Body>
                {canDisplay ? (
                    <Table responsive striped bordered hover>
                        <thead>
                            <tr>
                                <th>#</th>
                                {Object.keys(recordList[0]).map(
                                    (name, index) => (
                                        <th key={index}>{name}</th>
                                    )
                                )}
                            </tr>
                        </thead>
                        <tbody>
                            {recordList.map((record, i) => {
                                return (
                                    <tr key={i}>
                                        <td key={i * 100 + 0}>{i + 1}</td>
                                        <td key={i * 100 + 1}>{record._id}</td>
                                        <td key={i * 100 + 2}>
                                            {record.longUrl}
                                        </td>
                                        <td key={i * 100 + 3}>
                                            {record.shortUrl}
                                        </td>
                                        <td key={i * 100 + 4}>
                                            {record.urlId}
                                        </td>
                                        <td key={i * 100 + 5}>
                                            {record.createdDate}
                                        </td>
                                        <td key={i * 100 + 6}>
                                            {record.lastUpdatedDate}
                                        </td>
                                        <td key={i * 100 + 7}>{record.__v}</td>
                                    </tr>
                                );
                            })}
                        </tbody>
                    </Table>
                ) : (
                    ''
                )}
                <Button variant="primary" type="submit" onClick={handleDisplay}>
                    Get Records
                </Button>
            </Card.Body>
        </Card>
    );
}

export default Record;
