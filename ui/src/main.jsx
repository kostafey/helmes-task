import React from 'react';
import { Switch, Route, HashRouter } from 'react-router-dom';
import UserForm from './userForm';
import CategoryForm from './categoryForm';

class Main extends React.Component {

    render() {
        return (
            <HashRouter>
                <Switch>
                    <Route path="/userForm" render={(props) =>
                        <UserForm {...props}
                                  context={this.context}
                                  parent={this}/>}/>
                    <Route path="/categoryForm" render={(props) =>
                        <CategoryForm {...props}
                                  context={this.context}
                                  parent={this}/>}/>                                  

                    <Route path="/" render={(props) =>
                        <UserForm {...props}/>} />
                </Switch>
            </HashRouter>
        );
    }
}

export default Main;