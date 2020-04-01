import React, { Component } from 'react';
import './App.css';
import Home from "../Home";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import BookList from "../../components/BookList";
import BookStoreList from "../../components/BookStoreList";
import AllBookstores from '../../components/AllBookstores';
import AddBook from "../AddBook";

class App extends Component {
    render() {
        return (
            <Router>
            <Switch>
            <Route path='/' exact={true} component={Home}/>
        <Route path='/books' exact={true} component={BookList}/>
        <Route path='/bookstores' exact={true} component={BookStoreList}/>
        <Route path='/allbookstores' exact={true} component={AllBookstores}/>
        <Route path='/books/:id' component={AddBook}/>
        </Switch>
        </Router>
    )
    }
}

export default App;