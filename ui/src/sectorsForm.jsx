import * as React from 'react';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Center from 'react-center';
import NestedList from './nestedList';

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
                        <Box sx={{ fontSize: 18, m: 2 , textAlign: 'left' }}>
                            Please enter your name and pick the Sectors you are currently involved in.
                        </Box>
                        <NestedList>                            
                        </NestedList>
                    </Paper>
                </Box>
            </Center>
        );
    }
}

export default SectorsForm;