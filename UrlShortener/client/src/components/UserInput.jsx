import React, { useState } from 'react';
import axios from 'axios';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Form from 'react-bootstrap/Form';
import Alert from 'react-bootstrap/Alert';
import Badge from 'react-bootstrap/Badge';

function UserInput() {
    const [longUrl, setLongUrl] = useState('');
    const [customUrlId, setCustomUrlId] = useState('');
    const [shortUrl, setShortUrl] = useState('');
    const [isShortenPressed, setIsShortenPressed] = useState(false);
    const [message, setMessage] = useState('');
    const [hasError, setHasError] = useState(false);
    const [showMessage, setShowMessage] = useState(true);

    const handleSubmit = async e => {
        e.preventDefault();

        const config = {
            headers: { 'Content-Type': 'application/json' },
        };

        try {
            const res = await axios.post(
                '/api/url/shorten',
                { longUrl, customUrlId },
                config
            );
            setShortUrl(res.data.shortUrl);

            // Store response messages
            setMessage('Successfully shortened a URL');
            if (res.status === 202) {
                setMessage(
                    'This URL already exists, now return its shortened URL'
                );
            }
            setHasError(false);
            setShowMessage(true);
        } catch (err) {
            setMessage(err.response.data);
            setHasError(true);
            setShowMessage(true);
        }
        setIsShortenPressed(true);
    };

    // Credit Kaiido: https://stackoverflow.com/questions/47879184/document-execcommandcopy-not-working-on-chrome/47880284
    // Copy the shortned URL to clickboard
    const copyToClipboard = () => {
        const textarea = document.createElement('textarea');
        const selection = document.getSelection();
        const range = document.createRange();
        textarea.textContent = shortUrl;
        document.body.appendChild(textarea);
        range.selectNode(textarea);
        selection.removeAllRanges();
        selection.addRange(range);
        document.execCommand('copy');
        selection.removeAllRanges();
        document.body.removeChild(textarea);
    };

    const renderCopyEditButton = () => {
        return (
            <span>
                <Button
                    className="copy-btn"
                    variant="outline-primary"
                    size="sm"
                    onClick={() => copyToClipboard()}>
                    <svg
                        width="1em"
                        height="1em"
                        viewBox="0 0 16 16"
                        className="bi bi-clipboard-plus"
                        fill="currentColor"
                        xmlns="http://www.w3.org/2000/svg">
                        <path
                            fillRule="evenodd"
                            d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1h1a1 1 0 0 1 1 1V14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V3.5a1 1 0 0 1 1-1h1v-1z"
                        />
                        <path
                            fillRule="evenodd"
                            d="M9.5 1h-3a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm-3-1A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3zM8 7a.5.5 0 0 1 .5.5V9H10a.5.5 0 0 1 0 1H8.5v1.5a.5.5 0 0 1-1 0V10H6a.5.5 0 0 1 0-1h1.5V7.5A.5.5 0 0 1 8 7z"
                        />
                    </svg>
                    &nbsp;Copy
                </Button>
                <Button
                    className="edit-btn"
                    variant="outline-primary"
                    size="sm"
                    type="submit">
                    <svg
                        width="1em"
                        height="1em"
                        viewBox="0 0 16 16"
                        className="bi bi-pencil-square"
                        fill="currentColor"
                        xmlns="http://www.w3.org/2000/svg">
                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
                        <path
                            fillRule="evenodd"
                            d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"
                        />
                    </svg>
                    &nbsp;Edit
                </Button>
            </span>
        );
    };

    const renderMessage = () => {
        if (showMessage) {
            return (
                <Alert
                    id="alert"
                    variant={hasError ? 'danger' : 'success'}
                    onClose={() => setShowMessage(false)}
                    dismissible>
                    {message}
                </Alert>
            );
        }
    };

    const renderInstruction = () => {
        return (
            <div className="note">
                <div className="note-title">
                    <Badge variant="secondary">NOTE</Badge>
                </div>
                <span>
                    1. Once a shortened URL is returned, either click the edit
                    button next to it to edit this particular record, or enter
                    the shortened URL with suffix
                    <span className="highlight-text">
                        <em> /edit</em>
                    </span>{' '}
                    in address bar.
                </span>
                <br></br>
                <span>
                    2. Forgot shortened URL? Either enter link again and press
                    shorten to retrieve, or go to RECORD to check all the URL
                    records.
                </span>
            </div>
        );
    };

    return (
        <Card className="text-center">
            <Card.Body>
                <form onSubmit={handleSubmit}>
                    <Card.Text>
                        <input
                            className="url-input"
                            type="text"
                            value={longUrl}
                            onChange={e => {
                                setLongUrl(e.target.value);
                            }}
                            name="longUrl"
                            placeholder="Your link"
                            autoComplete="off"
                        />
                        <input
                            className="urlid-input"
                            type="text"
                            value={customUrlId}
                            onChange={e => {
                                setCustomUrlId(e.target.value);
                            }}
                            name="customUrlId"
                            placeholder="Custom ending"
                            autoComplete="off"
                        />
                        <Form.Text className="text-muted">
                            Leave the custom URL ending input blank for
                            unbranded shorten, or branded result will return
                        </Form.Text>
                    </Card.Text>
                    {message !== '' ? renderMessage() : message}
                    <Button
                        className="shorten-button"
                        type="submit"
                        variant="primary">
                        Shorten
                    </Button>
                </form>
            </Card.Body>
            <form action={`${shortUrl}/edit`} method="get">
                <Card.Footer className="result-display">
                    {!hasError && isShortenPressed ? shortUrl : ''}
                    {!hasError && isShortenPressed
                        ? renderCopyEditButton()
                        : ''}
                    {!isShortenPressed ? renderInstruction() : ''}
                </Card.Footer>
            </form>
        </Card>
    );
}

export default UserInput;
