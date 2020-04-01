import React, { Component } from 'react';
import { Button, Form, FormGroup, Input, ButtonGroup, Container, Table } from 'reactstrap';
import NavigationBar from './NavigationBar';
import { Link } from 'react-router-dom';

class AllBookstores extends Component {

    constructor(props) {
        super(props);
        this.state = {
            allbookstores: [],
        }
    }

    componentDidMount(){
        fetch('/api/getBookstores')
            .then(response => response.json())
            .catch(err => alert(err))
            .then(data => this.setState({allbookstores: data}));

    }

    render() {

        const {allbookstores} = this.state; //set the state to bookstores (the list)
        //console.log(bookstores);
        const bookstoreslist = allbookstores.map(bookstore => {//mapping
            const title =` ${bookstore.name || ''} `
            return <tr key = {bookstore.id}>
                <td>{bookstore.name}</td>

            </tr>
        });

        return (
            <div>
                <NavigationBar/>
                <Container fluid>
                    <div className="float-right">

                    </div>
                    <br>
                    </br>
                    <h3>All Bookstores available:</h3>
                    <Table className="mt-4">
                        <tbody>
                        {bookstoreslist} {/* result of the mapping*/}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }

}


export default AllBookstores;