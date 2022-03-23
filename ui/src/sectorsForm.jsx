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
import axios from 'axios';

class SectorsForm extends React.Component {
    constructor(props) {
        super(props);
        this.handleSave = this.handleSave.bind(this);
        this.state = {
            name: '',
            categoryIndex: null,
            isAgreeToTerms: false,
            nameError: false,
            nameErrorText: "",
            isAgreeToTermsError: false,
            isAgreeToTermsErrorText: "",
        }
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

    handleSave() {
        if (this.state.name === '') {
            this.setState({ 
                nameError: true,
                nameErrorText: "Can't be empty."});
        } else {
            this.setState({ 
                nameError: false,
                nameErrorText: ""})
        }
        if (this.state.categoryIndex == null) {
            this.setState({ 
                categoryIndexError: true,
                categoryIndexErrorText: "Should be selected."});
        } else {
            this.setState({ 
                categoryIndexError: false,
                categoryIndexErrorText: ""})
        }        
        if (!this.state.isAgreeToTerms) {
            this.setState({ 
                isAgreeToTermsError: true,
                isAgreeToTermsErrorText: "Should be checked"});
        } else {
            this.setState({ 
                isAgreeToTermsError: false,
                isAgreeToTermsErrorText: ""})
        }
        if (![this.state.categoryIndexError,
              this.state.isAgreeToTermsError,
              this.state.isAgreeToTermsError].includes(true)) {
            const config = { headers: { 'Content-Type': 'application/json',
                                        'X-Requested-With': 'HttpRequest',
                                        'Csrf-Token': 'nocheck'},
                             timeout: 0};
            const data = new FormData();
            ['name',
             'categoryIndex',
             'isAgreeToTerms'].forEach(f => {
                data.append(f, this.state[f]);
             });
             axios.post("/user/save", data, config)
             .then( (response) => {
                 if (response.status === 200) {
                 }
             })
             .catch( (error) => {
                console.log(error);
             });            
        }
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
                                error={this.state.categoryIndexError}>
                                <Paper sx={{ mt: 1, width: '100%' }} variant="outlined">
                                    <NestedList parent={this} />
                                </Paper>
                                <FormHelperText>{this.state.categoryIndexErrorText}</FormHelperText>
                            </FormControl>

                            <FormGroup sx={{ mt: 1 }}>
                                <FormControl
                                    error={this.state.isAgreeToTermsError}>
                                <FormControlLabel                                     
                                    control={
                                        <Checkbox
                                            required={true}
                                            onChange={this.handleChangeBoolean("isAgreeToTerms")}
                                            checked={this.state.isAgreeToTerms} />
                                    }
                                    label="Agree to terms"/>
                                        <FormHelperText>{this.state.isAgreeToTermsErrorText}</FormHelperText>
                                    </FormControl>
                            </FormGroup>
                            
                            <Box sx={{ display: "flex", 
                                       justifyContent: 'flex-end',
                                       alignItems: "flex-end",
                                       width: '100%' }}>
                                <Button
                                    sx={{ width: '150px' }} 
                                    variant="contained"
                                    onClick={this.handleSave}>Save</Button>
                            </Box>                            
                        </Box>
                        
                    </Paper>
                </Box>
            </Center>
        );
    }
}

export default SectorsForm;