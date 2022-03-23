import * as React from 'react';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import axios from 'axios';

class NestedList extends React.Component {
    constructor(props) {
        super(props);
        this.handleListItemClick = this.handleListItemClick.bind(this);
    }

    state = {
        categories: []
    };

    loadCategories() {
        axios.get('/category/list')
            .then((response) => {
                this.setState({ categories: response.data });
            })
            .catch((error) => {
                console.log(error);
            });
    }

    renderRow(index, name, offset, subCategories) {
        return (
            <>
            <ListItemButton
                sx={{ pl: offset }}
                selected={this.props.parent.state.categoryIndex === index}
                onClick={(_) => this.handleListItemClick(index)}>
                <ListItemText primary={name} />
            </ListItemButton>            
            {subCategories != null
             ? subCategories.map((category) => (
                 this.renderRow(category.id, category.name, offset + 4, category.subCategories)
               ))
             : ""}
            </>
        );
    }

    handleListItemClick = (index) => {
        this.props.parent.setState({ categoryIndex: index });
    };

    componentDidMount() {
        this.loadCategories();
    }

    render() {
        return (
            <List sx={{
                width: '100%',
                maxHeight: 560,
                position: 'relative',
                overflow: 'auto',
                bgcolor: 'background.paper'
            }}>
                {this.state.categories.map((category) => (
                    this.renderRow(category.id, category.name, 2, category.subCategories)
                ))}
            </List>
        );
    }
}

export default NestedList;