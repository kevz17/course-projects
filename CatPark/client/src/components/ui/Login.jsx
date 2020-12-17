import React, { useState, useContext } from 'react';
import axios from 'axios';
import UserContext from '../../context/UserContext';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Badge from 'react-bootstrap/Badge';
import Form from 'react-bootstrap/Form';
import Alert from 'react-bootstrap/Alert';

const Login = () => {
    const { setUserData } = useContext(UserContext);
    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState({ type: '', content: '' });
    const [loggedinMessage, setLoggedinMessage] = useState('');

    const validate = e => {
        e.preventDefault();
        if (!userName || !password) {
            setMessage({ type: 'info', content: 'Invalid inputs' });
            return;
        }

        handleLogin();
    };

    const handleLogin = async () => {
        const config = {
            headers: { 'Content-Type': 'application/json' },
        };

        try {
            const res = await axios.post(
                '/auth/login',
                { userName, password },
                config
            );
            setLoggedinMessage('Successfully logged in');
            setUserData({
                token: res.data.token,
                user: res.data.user,
            });
            localStorage.setItem('auth-token', res.data.token);
        } catch (err) {
            setMessage({
                type: 'warning',
                content: err.response.data,
            });
        }
    };

    return (
        <Card>
            <Card.Header style={{ textAlign: 'center' }}>
                <strong>Log In</strong>
            </Card.Header>
            <Card.Body className="register-body">
                {loggedinMessage ? (
                    <Alert variant="success" style={{ textAlign: 'center' }}>
                        {loggedinMessage}
                    </Alert>
                ) : (
                    <Form onSubmit={validate} action="/">
                        <Form.Group controlId="userName">
                            <Form.Label>User Name</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter user name"
                                autoComplete="off"
                                onChange={e => {
                                    setUserName(e.target.value.trim());
                                }}
                            />
                        </Form.Group>
                        <Form.Group controlId="password">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Enter password"
                                autoComplete="off"
                                onChange={e => {
                                    setPassword(e.target.value.trim());
                                }}
                            />
                        </Form.Group>
                        {message.type ? (
                            <Alert variant={message.type}>
                                {message.content}
                            </Alert>
                        ) : (
                            ''
                        )}
                        <Button variant="primary" type="submit" block>
                            Log In
                        </Button>
                    </Form>
                )}
            </Card.Body>
            <Card.Footer className="text-muted">
                <div style={{ textAlign: 'left' }}>
                    <span>
                        <Badge variant="secondary">Note</Badge>
                    </span>
                    <p style={{ fontSize: 'small' }}>
                        1. Haven't registered?{' '}
                        <a href="/register">
                            <strong>Register</strong>
                        </a>
                        <br></br>
                        2. Once logged in, you'll be redirected to home page
                    </p>
                </div>
            </Card.Footer>
        </Card>
    );
};

export default Login;
