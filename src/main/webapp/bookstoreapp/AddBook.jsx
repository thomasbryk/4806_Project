import React, { Component } from 'react';
import { Link, withRouter} from "react-router-dom";
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import NavigationBar from "./NavigationBar";

class AddBook extends Component {

    emptyItem = {
        bookstoreId: '',
        name: '',
        isbn: '',
        picture: '',
        description: '',
        author: '',
        publisher: '',
        available: true,
    };

    constructor(props) {
        super(props);
        this.state= {
            item: this.emptyItem
        };
        this.setState(this.state.item.bookstoreId = props.bookstoreId)
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const book = await (await fetch (`/api/book/$this.props.match.params.id}`)).json();
            this.setState({item: book});
        }
    }

    handleChange(e) {
        const target = e.target;
        const value = target.value;
        const name = target.name
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(e) {
        e.preventDefault();
        const {item} = this.state;

        await fetch('books', {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/api/newBook');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Book' : 'Add Book'}</h2>;

        return <div>
            <NavigationBar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="isbn">ISBN</Label>
                        <Input type="text" name="isbn" id="isbn" value={item.isbn || ''}
                               onChange={this.handleChange} autoComplete="000"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="picture">Picture</Label>
                        <Input type="text" name="picture" id="picture" value={item.picture || ''}
                               onChange={this.handleChange} autoComplete=""/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="text-box" name="description" id="description" value={item.description || ''}
                               onChange={this.handleChange} autoComplete="A book"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="author">Author</Label>
                        <Input type="text" name="author" id="author" value={item.author || ''}
                               onChange={this.handleChange} autoComplete=""/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="publisher">Publisher</Label>
                        <Input type="text" name="publisher" id="publisher" value={item.publisher || ''}
                               onChange={this.handleChange} autoComplete=""/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/books">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(AddBook);