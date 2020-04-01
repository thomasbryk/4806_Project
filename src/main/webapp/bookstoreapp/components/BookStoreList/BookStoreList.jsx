import React, { Component } from 'react';
import { Button, Form, FormGroup, Input, ButtonGroup, Container, Table } from 'reactstrap';
import NavigationBar from '../NavigationBar/NavigationBar';
import { Link } from 'react-router-dom';

class BookStoreList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            bookstores: [],
            input1: 0,
        }

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        // this.remove = this.remove.bind(this);
    }


    handleChange(event) {
        const owner = event.target.value;
        console.log(owner);
    }

    handleInputChange = (e, name) => {
        this.setState({

            [name]: e.target.value
        })


    }
    async handleSubmit(e) {
        e.preventDefault()
        const id = this.state.input1

        await fetch('/api/getBookstoresByBookstoreOwner?bookstoreOwnerId='+id)
            .then(response => response.json())
            .then(data => {console.log(data); this.setState({bookstores : data})})
    }


    render() {

        const {bookstores} = this.state; //set the state to bookstores (the list)
        console.log(bookstores);
        const bookstoreslist = bookstores.map(bookstore => {//mapping
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
                    <Form onSubmit={this.handleSubmit}>
                        <label>
                            <br></br>
                            Owner's id:
                            <Input id="ownerId" type="text" value={this.state.input1} onChange={(e) => this.handleInputChange(e, 'input1')}  />
                        </label>
                        <Input type="submit" value="Submit" />
                    </Form>
                    <br>
                    </br>
                    <h3>Bookstores available:</h3>
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

export default BookStoreList;