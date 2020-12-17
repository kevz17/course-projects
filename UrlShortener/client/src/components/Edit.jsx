import React, { useState } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Alert from 'react-bootstrap/Alert';
import Form from 'react-bootstrap/Form';

function Edit() {
    const location = useLocation();
    const getUrlId = () => {
        return new URLSearchParams(location.search).get('urlid');
    };
    const [urlId] = useState(getUrlId());
    const [newUrl, setNewUrl] = useState('');
    const [message, setMessage] = useState('');
    const [hasError, setHasError] = useState(false);
    const [showMessage, setShowMessage] = useState(true);

    const handleUpdate = async (e) => {
        e.preventDefault();
        const config = {
            headers: { 'Content-Type': 'application/json' },
        };

        try {
            await axios.put(
                `/api/url/shorten/${urlId}`,
                { longUrl: newUrl },
                config
            );
            setMessage('Successfully updated');
            setHasError(false);
            setShowMessage(true);
        } catch (err) {
            setMessage(err.response.data);
            setHasError(true);
            setShowMessage(true);
        }
    };

    const handleDelete = async () => {
        try {
            await axios.delete(`/api/url/shorten/${urlId}`);
            setMessage(
                'Successfully deleted the record associated with this URL ID, check RECORD to verify'
            );
            setHasError(false);
            setShowMessage(true);
        } catch (err) {
            setMessage(err.response.data);
            setHasError(true);
            setShowMessage(true);
        }
    };

    const renderMessage = () => {
        if (showMessage) {
            return (
                <Alert
                    id="alert-edit"
                    variant={hasError ? 'danger' : 'success'}
                    onClose={() => setShowMessage(false)}
                    dismissible
                >
                    {message}
                </Alert>
            );
        }
    };

    return (
        <Card className="text-center">
            <Card.Body>
                <form onSubmit={handleUpdate}>
                    <Card.Title>
                        <label>URL ID: {getUrlId()}</label>
                    </Card.Title>
                    <Card.Text>
                        <input
                            className="new-url"
                            onChange={(e) => {
                                setNewUrl(e.target.value);
                            }}
                            type="text"
                            name="newUrl"
                            placeholder="Enter a new URL"
                            autoComplete="off"
                        />
                        <Form.Text className="text-muted">
                            URL input can be left blank for deletion
                        </Form.Text>
                    </Card.Text>
                    {message !== '' ? renderMessage() : message}
                    <Button
                        className="buttons"
                        type="submit"
                        variant="outline-primary"
                    >
                        <svg
                            width="1em"
                            height="1em"
                            viewBox="0 0 16 16"
                            className="bi bi-wrench"
                            fill="currentColor"
                            xmlns="http://www.w3.org/2000/svg"
                            style={{ margin: '0px 5px 4px 0px' }}
                        >
                            <path
                                fillRule="evenodd"
                                d="M.102 2.223A3.004 3.004 0 0 0 3.78 5.897l6.341 6.252A3.003 3.003 0 0 0 13 16a3 3 0 1 0-.851-5.878L5.897 3.781A3.004 3.004 0 0 0 2.223.1l2.141 2.142L4 4l-1.757.364L.102 2.223zm13.37 9.019L13 11l-.471.242-.529.026-.287.445-.445.287-.026.529L11 13l.242.471.026.529.445.287.287.445.529.026L13 15l.471-.242.529-.026.287-.445.445-.287.026-.529L15 13l-.242-.471-.026-.529-.445-.287-.287-.445-.529-.026z"
                            />
                        </svg>
                        Update
                    </Button>
                    <Button
                        className="buttons"
                        onClick={() => handleDelete()}
                        variant="outline-danger"
                    >
                        <svg
                            width="1em"
                            height="1em"
                            viewBox="0 0 16 16"
                            className="bi bi-trash"
                            fill="currentColor"
                            xmlns="http://www.w3.org/2000/svg"
                            style={{ margin: '0px 5px 4px 0px' }}
                        >
                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z" />
                            <path
                                fillRule="evenodd"
                                d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"
                            />
                        </svg>
                        Delete
                    </Button>
                </form>
            </Card.Body>
        </Card>
    );
}

export default Edit;
