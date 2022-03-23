import * as React from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Center from 'react-center';
import NestedList from './nestedList';
import TextField from '@mui/material/TextField';
import FormGroup from '@mui/material/FormGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';

class SectorsForm extends React.Component {
    constructor(props) {
        super(props);
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
                            width: 600,
                            height: 800,
                        },
                    }}>
                    <Paper>
                        <Box sx={{ fontSize: 18, m: 2, textAlign: 'left' }}>
                            Please enter your name and pick the Sectors you are currently involved in.
                            <TextField sx={{ mt: 1, width: '100%' }} required id="outlined-basic" label="Name" variant="outlined" />

                            <Paper sx={{ mt: 1, width: '100%' }} variant="outlined">
                                <NestedList />
                            </Paper>

                            <FormGroup sx={{ mt: 1 }}>
                                <FormControlLabel control={<Checkbox />} label="Agree to terms" />                                
                            </FormGroup>
                            
                            <Box sx={{ display: "flex", justifyContent: 'flex-end', alignItems: "flex-end", width: '100%' }}>
                                <Button sx={{ width: '150px' }} variant="contained">Save</Button>
                            </Box>                            
                        </Box>
                        
                    </Paper>
                </Box>
            </Center>
        );
    }
}

export default SectorsForm;