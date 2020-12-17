import React, { useContext } from 'react';
import Jumbotron from 'react-bootstrap/Jumbotron';
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import UserContext from '../../context/UserContext';

const Home = () => {
    const { userData } = useContext(UserContext);

    return (
        <>
            <Jumbotron
                style={{
                    marginBottom: '0px',
                    backgroundColor: '#ffffff',
                }}
            >
                <h2 style={{ marginBottom: '30px' }}>
                    Welcome {userData.user ? userData.user.displayName : ''},
                </h2>
                <div className="speech-bubble">
                    <ButtonGroup
                        vertical
                        size="sm"
                        style={{
                            marginBottom: '50px',
                            display: 'flex',
                        }}
                    >
                        <Button
                            href="/search"
                            variant="light"
                            style={{ background: '#f4fbfe' }}
                        >
                            Search for cat breeds
                        </Button>
                        <Button
                            href="/posts"
                            variant="light"
                            style={{ background: '#f4fbfe' }}
                        >
                            Share your thoughts
                        </Button>
                        <Button
                            href="/notification"
                            variant="light"
                            style={{ background: '#f4fbfe' }}
                        >
                            Subscribe notifications
                        </Button>
                        <Button
                            href="/about"
                            variant="info"
                            style={{ background: '#629bdd' }}
                        >
                            Learn More
                        </Button>
                    </ButtonGroup>
                </div>
            </Jumbotron>
            <div className="bg"></div>
        </>
    );
};

export default Home;
