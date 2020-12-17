import React from 'react';
import { NavLink } from 'react-router-dom';
import Navbar from 'react-bootstrap/Navbar';

function Header() {
    return (
        <Navbar expand="true" bg="dark" variant="dark">
            <Navbar.Brand href="/">
                <h3>
                    <svg
                        width="1em"
                        height="1em"
                        viewBox="0 0 16 16"
                        className="bi bi-lightning"
                        fill="currentColor"
                        xmlns="http://www.w3.org/2000/svg"
                        style={{ margin: '0px 5px 4px' }}>
                        <path
                            fillRule="evenodd"
                            d="M11.251.068a.5.5 0 0 1 .227.58L9.677 6.5H13a.5.5 0 0 1 .364.843l-8 8.5a.5.5 0 0 1-.842-.49L6.323 9.5H3a.5.5 0 0 1-.364-.843l8-8.5a.5.5 0 0 1 .615-.09zM4.157 8.5H7a.5.5 0 0 1 .478.647L6.11 13.59l5.732-6.09H9a.5.5 0 0 1-.478-.647L9.89 2.41 4.157 8.5z"
                        />
                    </svg>
                    URL Shortener
                </h3>
            </Navbar.Brand>
            <span>
                <NavLink
                    className="nav-link"
                    activeClassName="active-link"
                    exact
                    to="/">
                    HOME
                </NavLink>
                <NavLink
                    className="nav-link"
                    activeClassName="active-link"
                    exact
                    to="/record">
                    RECORD
                </NavLink>
            </span>
        </Navbar>
    );
}

export default Header;
