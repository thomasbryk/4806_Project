import React, { Component } from 'react';
import '../App/App.css';
import NavigationBar from "../../components/NavigationBar";
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <NavigationBar/>
                <Container fluid>
                    <Button color="link"><Link to="/books">Manage Books</Link></Button>
                </Container>
                <Container fluid>
                    <Button color="link"><Link to="/bookstores">Find Bookstores by Owner</Link></Button>
                </Container>
                <Container fluid>
                    <Button color="link"><Link to="/allbookstores">View all Bookstores</Link></Button>
                </Container>
            </div>
        );
    }
}

export default Home;