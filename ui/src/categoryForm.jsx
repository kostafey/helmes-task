import * as React from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Center from 'react-center';
import NestedList from './nestedList';
import TextField from '@mui/material/TextField';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import OutlinedInput from '@mui/material/OutlinedInput';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Button from '@mui/material/Button';
import axios from 'axios';

class CategoryForm extends React.Component {
    constructor(props) {
        super(props);
        this.handleSave = this.handleSave.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
        this.loadCategoriesFlat = this.loadCategoriesFlat.bind(this);
        this.state = {
            categories: [],
            name: '',
            categoryId: -1,
            parentId: -1,
            refreshCategories: false,
            nameError: false,
            nameErrorText: "",
        }
    }

    gotoUserForm() {
        window.location.hash = "userForm";
    }

    loadCategoriesFlat() {
        axios.get('/category/flatList')
            .then((response) => {
                this.setState({ categories: response.data });
            })
            .catch((error) => {
                console.log(error);
            });
    }

    componentDidMount() {
        this.loadCategoriesFlat();
    }

    handleChange = name => event => {
        this.setState({
            [name]: event.target.value,
        });
    };

    handleChangeBoolean = name => event => {
        this.setState({
            [name]: event.target.checked
        });
    };

    handleDelete() {
        let nameError = false;
        let nameErrorText = '';
        if (this.state.name === '') {
            nameError = true;
            nameErrorText = "Select category for delete";
        } else {
            nameError = false;
            nameErrorText = "";
        }
        this.setState({
            nameError: nameError,
            nameErrorText: nameErrorText
        },
            () => {
                const config = {
                    headers: {
                        'Content-Type': 'application/json',
                        'X-Requested-With': 'HttpRequest',
                        'Csrf-Token': 'nocheck'
                    },
                    timeout: 0
                };
                const data = new FormData();
                ['name',
                 'categoryId',
                 'parentId'].forEach(f => {
                     data.append(f, this.state[f]);
                 });
             axios.post("/category/delete", data, config)
                 .then((response) => {
                     if (response.status === 200) {
                         this.setState({ refreshCategories: true });
                         this.loadCategoriesFlat();                                
                     }
                 })
                 .catch((error) => {
                     console.log(error);
                 });
            });
    }

    handleSave = (isAdd) => () => {
        let nameError = false;
        let nameErrorText = '';
        if (this.state.name === '') {
            nameError = true;
            nameErrorText = "Can't be empty.";
        } else {
            nameError = false;
            nameErrorText = "";
        }
        this.setState({
            nameError: nameError,
            nameErrorText: nameErrorText,
            categoryId: isAdd ? -1 : this.state.categoryId,
        },
            () => {
                if (!this.state.nameError) {
                    const config = {
                        headers: {
                            'Content-Type': 'application/json',
                            'X-Requested-With': 'HttpRequest',
                            'Csrf-Token': 'nocheck'
                        },
                        timeout: 0
                    };
                    const data = new FormData();
                       ['name',
                        'categoryId',
                        'parentId'].forEach(f => {
                            data.append(f, this.state[f]);
                        });
                    axios.post("/category/save", data, config)
                        .then((response) => {
                            if (response.status === 200) {
                                this.setState({ refreshCategories: true });
                                this.loadCategoriesFlat();                                
                            }
                        })
                        .catch((error) => {
                            console.log(error);
                        });
                }
            });
    }

    render() {
        return (
            <Center>
                <Box
                    sx={{
                        display: 'flex',
                        flexWrap: 'wrap',
                        '& > :not(style)': {
                            m: 1,
                            width: 600
                        },
                    }}>
                    <Paper>
                        <Box sx={{ fontSize: 18, m: 2, mt: 1, textAlign: 'left' }}>
                            <FormControl sx={{ width: '100%' }}>
                                <Paper sx={{ mt: 1, width: '100%' }} variant="outlined">
                                    <NestedList parent={this}
                                    refresh = {this.state.refreshCategories}
                                    onChange={(index) => {
                                        let category = this.state.categories.find((c) =>
                                            c.id === index);
                                        this.setState({ 
                                            categoryId: index,
                                            name: category.name,
                                            parentId: (category.parentId === undefined)
                                                ? -1
                                                : category.parentId});
                                    }}/>
                                </Paper>
                            </FormControl>

                            <FormControl sx={{ mt: 2, width: '100%' }}>
                                <InputLabel id="parent-category-label" shrink>
                                    Parent category
                                </InputLabel>
                                <Select
                                    input={<OutlinedInput notched label="Parent category" />}
                                    id="parent-category-select"
                                    value={this.state.parentId}
                                    labelId="parent-category-label"
                                    onChange={this.handleChange("parentId")}>
                                    {this.state.categories.map((category) => (
                                        <MenuItem value={category.id}>{category.name}</MenuItem>
                                    ))}
                                </Select>
                            </FormControl>

                            <TextField sx={{ mt: 2, width: '100%' }}
                                onChange={this.handleChange("name")}
                                value={this.state.name}
                                required
                                error={this.state.nameError}
                                helperText={this.state.nameErrorText}
                                label="Category name"
                                variant="outlined" />

                            <Box sx={{
                                mt: 2,
                                display: "flex",
                                justifyContent: 'flex-end',
                                alignItems: "flex-end",
                                width: '100%'
                            }}>
                                <Button
                                    color="primary"
                                    sx={{ width: '150px' }}
                                    variant="contained"
                                    onClick={this.gotoUserForm}>User Form</Button>
                                <Button
                                    color="secondary"
                                    sx={{ width: '150px', ml: 1 }}
                                    variant="contained"
                                    onClick={this.handleDelete}>Delete</Button>
                                <Button
                                    color="success"
                                    sx={{ width: '150px', ml: 1 }}
                                    variant="contained"
                                    onClick={this.handleSave(true)}>Add</Button>
                                <Button
                                    color="primary"
                                    sx={{ width: '150px', ml: 1 }}
                                    variant="contained"
                                    onClick={this.handleSave(false)}>Save</Button>
                            </Box>
                        </Box>

                    </Paper>
                </Box>
            </Center>
        );
    }
}

export default CategoryForm;