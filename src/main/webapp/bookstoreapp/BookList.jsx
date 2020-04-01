import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import NavigationBar from './NavigationBar';
import { Link } from 'react-router-dom';

class BookList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            books: []
        }
        // this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/api/getBooks')
            .then(response => response.json())
            .catch(err => alert(err))
            .then(data => this.setState({books: data}));

    }

    render() {
        const {books} = this.state;

        const bookList = books.map(book => {
            const title =`${book.picture || ''} ${book.name || ''} ${book.author || ''}`
            return <tr key = {book.id}>
                <td>{book.picture}</td>
                <td style={{whiteSpace:'nowrap'}}>{book.name}</td>
                <td style={{whiteSpace:'nowrap'}}>{book.author}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/books/" + book.id}>Edit</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <NavigationBar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to={"/books/new"}>Add book</Button>
                    </div>
                    <h3>BOOKS YO</h3>
                    <Table className="mt-4">
                        <tbody>
                        {bookList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default BookList;