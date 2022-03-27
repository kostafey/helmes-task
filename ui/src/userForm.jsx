import * as React from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Center from 'react-center';
import NestedList from './nestedList';
import TextField from '@mui/material/TextField';
import FormGroup from '@mui/material/FormGroup';
import FormControl from '@mui/material/FormControl';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormHelperText from '@mui/material/FormHelperText';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';
import axios from 'axios';

class UserForm extends React.Component {
    constructor(props) {
        super(props);
        this.handleSave = this.handleSave.bind(this);
        this.handleLoad = this.handleLoad.bind(this);
        this.state = {
            name: '',
            categoryId: null,
            agreeToTerms: false,
            inProgress: false,
            nameError: false,
            nameErrorText: "",
            agreeToTermsError: false,
            agreeToTermsErrorText: "",
        }
    }

    gotoCategoryForm() {
        window.location.hash = "categoryForm";
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

    handleLoad() {
        const config = { headers: { 'Content-Type': 'application/json',
                                    'X-Requested-With': 'HttpRequest',
                                    'Csrf-Token': 'nocheck'},
                         timeout: 0};
         this.setState({ inProgress: true });
         axios.get("/user/get", null, config)
         .then( (response) => {
             if (response.status === 200) {
                this.setState({
                    inProgress: false,
                    name: response.data.name,
                    categoryId: response.data.category.id,
                    agreeToTerms: response.data.agreeToTerms });
                
             }
         })
         .catch( (error) => {
            this.setState({ inProgress: false });
            console.log(error);
         });            
    }

    handleSave() {
        let nameError = false;
        let nameErrorText = '';
        let categoryIdError = false;
        let categoryIdErrorText = '';
        let agreeToTermsError = false;
        let agreeToTermsErrorText = '';
        if (this.state.name === '') {
            nameError = true;
            nameErrorText = "Can't be empty.";
        } else {
            nameError = false;
            nameErrorText = "";
        }
        if (this.state.categoryId == null) {
            categoryIdError = true;
            categoryIdErrorText = "Should be selected.";
        } else {
            categoryIdError = false;
            categoryIdErrorText = "";
        }        
        if (!this.state.agreeToTerms) {
            agreeToTermsError = true;
            agreeToTermsErrorText = "Should be checked";
        } else {
            this.setState({ 
                agreeToTermsError: false,
                agreeToTermsErrorText: ""})
        }
        this.setState({ 
            nameError: nameError,
            nameErrorText: nameErrorText,
            categoryIdError: categoryIdError,
            categoryIdErrorText: categoryIdErrorText,
            agreeToTermsError: agreeToTermsError,
            agreeToTermsErrorText: agreeToTermsErrorText},
            () => {
                if (![this.state.categoryIdError,
                    this.state.categoryIdError,
                    this.state.agreeToTermsError].includes(true)) {
                  const config = { headers: { 'Content-Type': 'application/json',
                                              'X-Requested-With': 'HttpRequest',
                                              'Csrf-Token': 'nocheck'},
                                   timeout: 0};
                  const data = new FormData();
                  ['name',
                   'categoryId',
                   'agreeToTerms'].forEach(f => {
                      data.append(f, this.state[f]);
                   });
                   axios.post("/user/save", data, config)
                   .then( (response) => {
                       if (response.status === 200) {
                        // Refill the form using stored data
                        this.handleLoad();
                       }
                   })
                   .catch( (error) => {
                      console.log(error);
                   });            
              }
            });        
    }

    render() {
        return (
            <Center>
                {this.state.inProgress
                ? <Box sx={{ display: 'flex', mt: 20 }}>
                    <CircularProgress size={90} />
                  </Box>
                : <Box
                    sx={{
                        display: 'flex',
                        flexWrap: 'wrap',
                        '& > :not(style)': {
                            m: 1,
                            width: 600
                        },
                    }}>
                    <Paper>
                        <Box sx={{ fontSize: 18, m: 2, textAlign: 'left' }}>
                            Please enter your name and pick the Sectors you are currently involved in.
                            <TextField sx={{ mt: 1, width: '100%' }}
                                onChange={this.handleChange("name")}
                                value={this.state.name}
                                required
                                error={this.state.nameError}
                                helperText={this.state.nameErrorText}
                                label="Name"
                                variant="outlined" />

                            <FormControl sx={{ width: '100%' }}
                                error={this.state.categoryIdError}>
                                <Paper sx={{ mt: 1, width: '100%' }} variant="outlined">
                                    <NestedList parent={this} />
                                </Paper>
                                <FormHelperText>{this.state.categoryIdErrorText}</FormHelperText>
                            </FormControl>

                            <FormGroup sx={{ mt: 1 }}>
                                <FormControl
                                    error={this.state.agreeToTermsError}>
                                <FormControlLabel
                                    control={
                                        <Checkbox
                                            required={true}
                                            onChange={this.handleChangeBoolean("agreeToTerms")}
                                            checked={this.state.agreeToTerms} />
                                    }
                                    label="Agree to terms"/>
                                        <FormHelperText>{this.state.agreeToTermsErrorText}</FormHelperText>
                                    </FormControl>
                            </FormGroup>
                            
                            <Box sx={{ display: "flex", 
                                       justifyContent: 'flex-end',
                                       alignItems: "flex-end",
                                       width: '100%' }}>
                                <Button
                                    color="primary"
                                    sx={{ width: '200px' }} 
                                    variant="contained"
                                    onClick={this.gotoCategoryForm}>Edit Categories</Button>                                           
                                <Button
                                    color="success"
                                    sx={{ width: '150px', ml: 1 }} 
                                    variant="contained"
                                    onClick={this.handleLoad}>Load</Button>
                                <Button
                                    color="primary"
                                    sx={{ width: '150px', ml: 1 }} 
                                    variant="contained"
                                    onClick={this.handleSave}>Save</Button>
                            </Box>                            
                        </Box>
                        
                    </Paper>
                  </Box>
                }
            </Center>
        );
    }
}

export default UserForm;