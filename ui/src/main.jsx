import React from 'react';
import { Switch, Route, HashRouter } from 'react-router-dom';
import SectorsForm from './sectorsForm';
import NestedList from './nestedList';

class Main extends React.Component {

    render() {
        return (
            <HashRouter>
                <Switch>
                    <Route path="/sectorsForm" render={(props) =>
                        <SectorsForm {...props}
                                  context={this.context}
                                  parent={this}/>}/>                                  

                    <Route path="/" render={(props) =>
                        <NestedList {...props}/>} />
                </Switch>
            </HashRouter>
        );
    }
}

export default Main;