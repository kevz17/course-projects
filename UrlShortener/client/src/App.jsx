import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Header from './components/Header';
import UserInput from './components/UserInput';
import Edit from './components/Edit';
import Record from './components/Record';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
    return (
        <div className="root-container">
            <Router>
                <div className="app">
                    <Header />
                    <Switch>
                        <Route exact path="/" component={UserInput} />
                        <Route exact path="/edit" component={Edit} />
                        <Route exact path="/record" component={Record} />
                        <Route render={() => <h1>Page Not found!</h1>} />
                    </Switch>
                </div>
            </Router>
        </div>
    );
}

export default App;
